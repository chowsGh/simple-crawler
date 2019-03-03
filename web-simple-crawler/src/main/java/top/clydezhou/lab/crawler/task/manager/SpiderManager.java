package top.clydezhou.lab.crawler.task.manager;

import java.io.Closeable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import top.clydezhou.lab.crawler.extend.componet.spider.CommonSpider;
import top.clydezhou.lab.crawler.extend.componet.spider.CommonSpider.Status;
import top.clydezhou.lab.crawler.extend.utils.JsonUtils;
import top.clydezhou.lab.crawler.task.component.SpiderFactory;
import top.clydezhou.lab.crawler.task.config.BaseTaskConfig;
import top.clydezhou.lab.crawler.task.config.CrawlTaskConfig;
import top.clydezhou.lab.crawler.task.dao.po.TaskParamPO;

/**
 * 爬虫管理器，维护爬虫
 * 
 * @author chows
 * @date 2019/02/17
 */
@Slf4j
@Component
public class SpiderManager implements Closeable {

    Map<Object, CommonSpider> spiderMap = new ConcurrentHashMap<>();

    public void putSpider(Object key, CommonSpider spider) {
        spiderMap.put(key, spider);
    }

    /**
     * 爬虫是否在运行
     * 
     * @param taskId
     * @return
     */
    public boolean isRunning(Long taskId) {
        boolean result = false;
        CommonSpider spider = getSpider(taskId);
        if (spider != null && spider.getStatus() == Status.Running) {
            result = true;
        }
        return result;
    }

    /**
     * 根据任务参数启动爬虫
     * @param taskParam
     * @param forceStart 如果之前有启动，强制根据参数启动
     * @return
     */
    public CommonSpider startSpider(TaskParamPO taskParam, boolean forceStart) {
        CommonSpider originSpider = getSpider(taskParam.getTaskid());
        if (originSpider != null) {
            if (forceStart) {
                originSpider.close();
            } else if (Status.Running == originSpider.getStatus()) {
                log.warn("{} spider has already run.", taskParam.getTaskid());
                return originSpider;
            }
        }
        String param = taskParam.getParam();
        //TODO 注意： 保存到json里的字符串'\' 要转义
        BaseTaskConfig baseConfig = JsonUtils.readValue(param, BaseTaskConfig.class);
        //TODO 将 resolvePipeline 和 selectormap进行设置
        CrawlTaskConfig config = SpiderFactory.initTaskConfig(baseConfig);
        config.setTaskId(taskParam.getTaskid());
        CommonSpider spider = SpiderFactory.getTaskSpider(config);
        // 启动爬虫
        putSpider(taskParam.getTaskid(), spider);
        spider.start();
        return spider;
    }

    public CommonSpider getSpider(Object key) {
        return spiderMap.get(key);
    }

    /**
     * 关闭某个爬虫
     * 
     * @param key
     */
    public void shutdownSpider(Object key) {
        CommonSpider spider = spiderMap.get(key);
        if (spider != null) {
            spider.close();
        }
    }

    @Override
    public void close() {
        for (CommonSpider spider : spiderMap.values()) {
            spider.close();
        }
    }
}
