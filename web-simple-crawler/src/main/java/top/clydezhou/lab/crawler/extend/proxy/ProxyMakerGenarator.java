package top.clydezhou.lab.crawler.extend.proxy;

import us.codecraft.webmagic.proxy.ProxyProvider;

public class ProxyMakerGenarator {
    public static ProxyProvider genarateProvider() {
        return new ApiProxyFactory();
    }
}
