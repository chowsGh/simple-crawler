package top.clydezhou.lab.crawler.extend.componet.processor;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import top.clydezhou.lab.crawler.extend.utils.PageUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

public class CommonPageProcessor implements PageProcessor {

    protected Site site = null;

    /**
     * 默认抽取 不包含 " ' # 的地址
     */
    @Setter@Getter
    protected String extractUrlPattern;

    public CommonPageProcessor() {
        this.site = Site.me();
    }

    public CommonPageProcessor(Site site) {
        this.site = site;
    }

    @Override
    public void process(Page page) {
        //TODO 如果是爬虫路径url 则添加到数据库，如果不是则直接爬去数据
        if (addToTarget(page)) {
            page.addTargetRequests(extractUrl(page));
        }
        //TODO 为什么要跳过？
        if (skip(page)) {
            // skip this page
            page.setSkip(true);
        }
        PageUtils.addPageSelfToResult(page);
    }

    @Override
    public Site getSite() {
        return site;
    }
    /**
     * -是否可以添加到待爬地址
     * 
     * @param page
     * @return
     */
    protected boolean addToTarget(Page page) {
        return true;
    }

    /**
     * -是否跳过pipeline
     * 
     * @param page
     * @return
     */
    protected boolean skip(Page page) {
        return false;
    }

    /**
     * 抽取url
     * @return
     */
    protected List<String> extractUrl(Page page) {
        Selectable s = page.getHtml().links(); 
        if(extractUrlPattern != null) 
        {
            s.regex(extractUrlPattern);
        }
        return s.all();
    }
}