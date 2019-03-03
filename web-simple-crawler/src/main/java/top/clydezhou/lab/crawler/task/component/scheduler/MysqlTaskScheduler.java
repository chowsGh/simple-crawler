package top.clydezhou.lab.crawler.task.component.scheduler;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import top.clydezhou.lab.crawler.task.manager.ICrawlTaskManager;
import top.clydezhou.lab.crawler.task.utils.TaskUtils;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;

@Slf4j
@Component
@Scope("prototype")
public class MysqlTaskScheduler extends CrawlTaskScheduler {

    private Object lock = new Object();

    @Getter
    @Setter
    private boolean sync = true;

    private ExecutorService asyncPool = Executors.newFixedThreadPool(4);

    @Resource
    ICrawlTaskManager crawlTaskManager;

    /**
     * 下载请求进入队列
     */
    @Override
    public void push(Request request, Task task) {
        String link = request.getUrl();
        String hashCode = crawlStrategy.getHashCode(link);
        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (lock) {
                        request.setPriority(crawlStrategy.assignPriority(link));
                        crawlTaskManager.initTaskCrawlUrl(request, task, hashCode);
                    }
                } catch (Exception e) {
                    log.error("Runnable addUrl exception", e);
                }
            }
        };
        if (sync) {
            r.run();
        } else {
            asyncPool.execute(r);
        }
    }

    /**
     * -获取url
     * TODO 支持使用不同的策略来获取链接，例如广度，深度遍历的策略
     */
    @Override
    public Request poll(Task task) {
        return crawlTaskManager.pollUrlRequest(TaskUtils.convertFromTask(task).getId());
    }

    @Override
    public void close() throws IOException {
        asyncPool.shutdown();
    }
}
