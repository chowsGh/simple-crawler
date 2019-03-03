package top.clydezhou.lab.crawler.task.component.scheduler;

import java.io.Closeable;

import lombok.Getter;
import lombok.Setter;
import top.clydezhou.lab.crawler.extend.strategy.ICrawlStrategy;
import us.codecraft.webmagic.scheduler.Scheduler;

public abstract class CrawlTaskScheduler implements Scheduler, Closeable {

    @Getter
    @Setter
    protected ICrawlStrategy crawlStrategy;
}