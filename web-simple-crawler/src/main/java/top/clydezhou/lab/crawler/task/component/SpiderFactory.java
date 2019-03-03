package top.clydezhou.lab.crawler.task.component;

import top.clydezhou.lab.crawler.extend.componet.processor.CommonPageProcessor;
import top.clydezhou.lab.crawler.extend.componet.spider.CommonSpider;
import top.clydezhou.lab.crawler.extend.proxy.ProxyMakerGenarator;
import top.clydezhou.lab.crawler.extend.utils.SpringUtil;
import top.clydezhou.lab.crawler.task.component.pipeline.MysqlTaskPipeline;
import top.clydezhou.lab.crawler.task.component.scheduler.MysqlTaskScheduler;
import top.clydezhou.lab.crawler.task.component.spider.TaskCommonSpider;
import top.clydezhou.lab.crawler.task.config.BaseTaskConfig;
import top.clydezhou.lab.crawler.task.config.CrawlTaskConfig;
import us.codecraft.webmagic.downloader.HttpClientDownloader;

public class SpiderFactory {
    /**
     * 初始化任务task config，返回一个基于mysql 保存任务数据的 爬虫配置
     * @return
     */
    public static CrawlTaskConfig initTaskConfig(BaseTaskConfig taskConfig) {
        CrawlTaskConfig mysqlTaskConfig = new CrawlTaskConfig();
        //下载器
        HttpClientDownloader downloader = new HttpClientDownloader();
        mysqlTaskConfig.setDownloader(downloader);
        //url调度器
        MysqlTaskScheduler scheduler = SpringUtil.getBean(MysqlTaskScheduler.class);
        mysqlTaskConfig.setTaskScheduler(scheduler);
        //页面处理器
        mysqlTaskConfig.setPageProcessor(new CommonPageProcessor(mysqlTaskConfig.getSite()));
        //数据处理管道
        MysqlTaskPipeline pipeline = SpringUtil.getBean(MysqlTaskPipeline.class);
        mysqlTaskConfig.setTaskPipeline(pipeline);
        //是否使用代理
        if(mysqlTaskConfig.isUseProxy()) {
            downloader.setProxyProvider(ProxyMakerGenarator.genarateProvider());
        }
        mysqlTaskConfig.resolve(taskConfig);
        return mysqlTaskConfig;
    }
    /**
     * 获取一个基于mysql 的爬虫
     * @param spiderConfig
     * @return
     */
    public static CommonSpider getTaskSpider(CrawlTaskConfig spiderConfig) {
        //创建爬虫
        TaskCommonSpider spider = new TaskCommonSpider(spiderConfig.getPageProcessor());
        spider.setTaskId(spiderConfig.getTaskId());
        //设置起始地址
        spider.addRequest(spiderConfig.getStartUrlList());
        spider.setCrawlStrategy(spiderConfig.getCrawlStrategy());
        spider.setScheduler(spiderConfig.getTaskScheduler());
        spider.setDownloader(spiderConfig.getDownloader());
        //内部任务处理流程管道
        spider.setTaskPipeline(spiderConfig.getTaskPipeline());
        //页面处理管道
        spider.setPipelines(spiderConfig.getResolvePipeline());
        spider.thread(spiderConfig.getSpiderThreadCount());
        return spider;
    }
}
