package top.clydezhou.lab.crawler.task.component.pipeline;

import lombok.Getter;
import lombok.Setter;
import top.clydezhou.lab.crawler.extend.strategy.ICrawlStrategy;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.pipeline.Pipeline;

public abstract class CrawlTaskPipeline implements Pipeline {

    public static final String CRAWLED_DATA = "CRAWLED_DATA";
    
    @Getter@Setter
    public ICrawlStrategy crawlStrategy;
    /**
     * 设置数据
     * @param resultItems
     * @param data
     */
    public static final void setCrawledData(ResultItems resultItems, String data) {
            resultItems.put(CRAWLED_DATA, data);
    }
    /**
     * 获取爬取的数据
     * @param resultItems
     * @return
     */
    public static final Object getCrawledData(ResultItems resultItems) {
        return resultItems.get(CRAWLED_DATA);
    }
}