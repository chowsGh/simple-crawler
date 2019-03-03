package top.clydezhou.lab.crawler.task.component.spider;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import lombok.Getter;
import lombok.Setter;
import top.clydezhou.lab.crawler.extend.componet.spider.CommonSpider;
import top.clydezhou.lab.crawler.extend.strategy.ICrawlStrategy;
import top.clydezhou.lab.crawler.task.utils.TaskUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 通用任务爬虫类
 * 
 * @author chows
 * @date 2019/02/20
 */
public class TaskCommonSpider extends CommonSpider {

    public TaskCommonSpider(PageProcessor pageProcessor) {
        super(pageProcessor);
    }

    /**
     * 任务id
     */
    @Getter
    @Setter
    public long taskId = 0L;
    /**
     * 爬虫策略
     */
    @Getter
    @Setter
    public ICrawlStrategy crawlStrategy;
    /**
     * 集成任务管道，将处理好的数据保存到数据库
     */
    @Getter
    @Setter
    private Pipeline taskPipeline;

    /**
     * 抽取地址并添加到调度器里
     * 
     * @param page
     * @param spawnUrl
     *            是否从page中抽取地址
     */
    @Override
    protected void extractAndAddRequests(Page page, boolean spawnUrl) {
        if (spawnUrl && CollectionUtils.isNotEmpty(page.getTargetRequests())) {
            for (Request request : page.getTargetRequests()) {
                if (canAddToPool(request)) {
                    // 设置request 的 url parent id
                    TaskUtils.setRequestParentId(request, TaskUtils.getRequestId(page.getRequest()));
                    addRequest(request);
                }
            }
        }
    }

    @Override
    public CommonSpider addPipeline(Pipeline pipeline) {
        checkIfRunning();
        if (this.pipelines.size() == 0) {
            this.pipelines.add(pipeline);
            this.pipelines.add(getTaskPipeline());
        } else {
            this.pipelines.set(this.pipelines.size() - 1, pipeline);
            this.pipelines.add(getTaskPipeline());
        }
        return this;
    }

    @Override
    public CommonSpider setPipelines(List<Pipeline> pipelines) {
        checkIfRunning();
        this.pipelines = pipelines;
        this.pipelines.add(getTaskPipeline());
        return this;
    }

    // TODO 如果爬的是api 要怎么处理？ POST GET ? 还是需要定制
    protected boolean canAddToPool(Request request) {
        return crawlStrategy.checkUrl(request.getUrl());
    }
}
