package top.clydezhou.lab.crawler.task.config;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import top.clydezhou.lab.crawler.extend.UserAgentConstant;
import top.clydezhou.lab.crawler.extend.componet.processor.CommonPageProcessor;
import top.clydezhou.lab.crawler.extend.strategy.BaseCrawlStrategy;
import top.clydezhou.lab.crawler.extend.utils.SpringUtil;
import top.clydezhou.lab.crawler.task.component.pipeline.CommonResolvePipeline;
import top.clydezhou.lab.crawler.task.component.pipeline.CrawlTaskPipeline;
import top.clydezhou.lab.crawler.task.component.scheduler.CrawlTaskScheduler;
import top.clydezhou.lab.crawler.task.dao.po.CrawlUrlPriorityConstant;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.proxy.ProxyProvider;

@Getter
@Setter
public class CrawlTaskConfig {
    /**
     * 任务id
     */
    private long taskId;

    /**
     * 爬虫运行线程数，同时也是下载器最大连接数
     */
    private int spiderThreadCount = 1;
    /**
     * 开始请求书
     */
    private List<Request> startUrlList = new ArrayList<>();

    /**
     * 爬虫策略
     */
    private BaseCrawlStrategy crawlStrategy = new BaseCrawlStrategy();

    /**
     * pipleline的名字 通过spring 获取
     */
    private List<String> pipelineNameList = new ArrayList<>();
    /**
     * 爬虫处理器
     */
    private CrawlTaskPipeline taskPipeline;
    /**
     * 爬虫调度器
     */
    private CrawlTaskScheduler taskScheduler;

    /**
     * 下载器
     */
    private Downloader downloader;

    /**
     * 页面处理器，页面下载完成后用于抽取url，保存page对象
     */
    private CommonPageProcessor pageProcessor;
    /**
     * 代理提供器
     */
    private ProxyProvider proxyProvide = null;
    // 是否使用代理
    private boolean useProxy = false;

    private List<Pipeline> resolvePipeline = new ArrayList<>();
    
    private Site site = new Site();
    {
        // 设置默认user agent
        site.setUserAgent(UserAgentConstant.USER_AGENT_CHROME);
    }
    
    /**
     * 添加起始地址
     * 
     * @param startUrls
     */
    public void addStartUrl(List<String> startUrls) {
        for (String url : startUrls) {
            Request request = new Request(url);
            // 增加start url 优先级设置为高
            request.setPriority(CrawlUrlPriorityConstant.HIGH);
            startUrlList.add(new Request(url));
        }
    }

    /**
     * 设置任务策略
     * 
     * @param crawlStrategy
     */
    public void setTaskStrategy(BaseCrawlStrategy crawlStrategy) {
        this.crawlStrategy = crawlStrategy;
    }

    /**
     * 根据任务配置转换成爬虫配置
     * 
     * @param baseTaskConfig
     */
    public void resolve(BaseTaskConfig baseTaskConfig) {
        getSite().setSleepTime(baseTaskConfig.getSleepTime());
        getSite().setTimeOut(baseTaskConfig.getDownloadTimeOut());
        getSite().setCycleRetryTimes(baseTaskConfig.getRetryTimes());

        setUseProxy(baseTaskConfig.isUseProxy());
        setSpiderThreadCount(baseTaskConfig.getThreadCount());

        getCrawlStrategy().setBlackRegexList(baseTaskConfig.getBlackRegexList());
        getCrawlStrategy().setWhiteRegexList(baseTaskConfig.getWhiteRegexList());
        getCrawlStrategy().setRequireRegexList(baseTaskConfig.getRequireRegexList());
        getCrawlStrategy().setPriorityMap(baseTaskConfig.getPriorityMap());

        if (getTaskScheduler() != null) {
            getTaskScheduler().setCrawlStrategy(crawlStrategy);
        }
        if (getTaskPipeline() != null) {
            getTaskPipeline().setCrawlStrategy(crawlStrategy);
        }
        //数据抽取解析配置
        for (String pipelineSpringName : baseTaskConfig.getPipelineList()) {
            CommonResolvePipeline crp = SpringUtil.getBean(pipelineSpringName, CommonResolvePipeline.class);
            if (crp != null) {
                crp.setSelectorMap(baseTaskConfig.getSelectorMap());
            }
            resolvePipeline.add(crp);
        }
        //添加启动url
        addStartUrl(baseTaskConfig.getStartUrlList());
    }
}
