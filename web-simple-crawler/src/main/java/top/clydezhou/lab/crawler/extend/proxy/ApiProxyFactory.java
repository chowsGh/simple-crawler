package top.clydezhou.lab.crawler.extend.proxy;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import lombok.extern.slf4j.Slf4j;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.proxy.ProxyProvider;

/**
 * TODO 需要其他代理连接池集成
 * @author chows
 * @date 2019/03/03
 */
@Slf4j
public class ApiProxyFactory implements ProxyProvider {

    Map<String, Date> lastUsedProxy = Collections.synchronizedMap(new HashMap<>());

    private Proxy get() {
        Proxy p = null;
        String json = null;
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            // 创建httppost
            HttpGet get = new HttpGet("http://127.0.0.1:8080/get/");
            json = IOUtils.toString(httpclient.execute(get).getEntity().getContent(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("ApiProxyFactory", e);
        }
        if(StringUtils.isNotBlank(json)) {
            if(isBusy(json)) {
                p = get();
            }
            else {
                String[] proxyStrArr = json.split(":");
                p = new Proxy(Type.HTTP, new InetSocketAddress(proxyStrArr[0], Integer.parseInt(proxyStrArr[1])));
            }
            log.info("get proxy:{}", json);
        }
        return p;
    }
    int cacheTime = 20 * 1000;
    private void clearLastUsedProxy() {
        log.info("clearLastUsedProxy, has cached proxy count:{}", lastUsedProxy.size());
        Iterator<Entry<String, Date>> i = lastUsedProxy.entrySet().iterator();
        Date date = new Date();
        List<String> keyList = new ArrayList<>();
        while(i.hasNext()) {
            Entry<String, Date> lastUsed = i.next();
            if(date.getTime() - lastUsed.getValue().getTime() >= cacheTime) {
                keyList.add(lastUsed.getKey());
            }
        }
        for(String key : keyList) {
            lastUsedProxy.remove(key);
        }
        log.info("after clear, has cached proxy count:{}", lastUsedProxy.size());
    }
    /**
     * -代理是否忙碌 默认设置为 1s 为忙碌
     * @param proxy
     * @return
     */
    private boolean isBusy(String proxy) {
        boolean busy = false;
        clearLastUsedProxy();
        Date date = new Date();
        if(lastUsedProxy.containsKey(proxy)) {
            Date lastUsed = lastUsedProxy.get(proxy);
            if(date.getTime() - lastUsed.getTime() < cacheTime) {
                log.info("{} is busy", proxy);
                busy = true;
            }
        } else {
            lastUsedProxy.put(proxy, date);
        }
        return busy;
    }
    private String toAddress(us.codecraft.webmagic.proxy.Proxy proxy) {
        return proxy.getHost() + ":" + proxy.getPort();
    }
    @Override
    public void returnProxy(us.codecraft.webmagic.proxy.Proxy proxy, Page page, Task task) {
        boolean badProxy = page.isDownloadSuccess() == false;
        if(badProxy) {
            try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
                String address = toAddress(proxy);
                HttpGet get = new HttpGet("http://127.0.0.1:8080/delete?proxy=" + address);
                String json = IOUtils.toString(httpclient.execute(get).getEntity().getContent(), StandardCharsets.UTF_8);
                if("success".equalsIgnoreCase(json)) {
                    log.info("delete proxy {} success", address);
                } else {
                    log.debug("delete proxy {} fail. result={}", address, json);
                }
            } catch (Exception e) {
                log.error("ApiProxyFactory", e);
            }
        }
    }

    @Override
    public us.codecraft.webmagic.proxy.Proxy getProxy(Task task) {
        return convertFromProxy(get());
    }

    /**
     * -转换
     * @param p
     * @return
     */
    private us.codecraft.webmagic.proxy.Proxy convertFromProxy(Proxy p) {
        InetSocketAddress isa = (InetSocketAddress)p.address();
        return new us.codecraft.webmagic.proxy.Proxy(isa.getHostName(), isa.getPort());
    }
}
