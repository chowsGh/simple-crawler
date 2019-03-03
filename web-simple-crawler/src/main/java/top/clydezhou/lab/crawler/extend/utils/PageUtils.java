package top.clydezhou.lab.crawler.extend.utils;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.selector.Html;

public class PageUtils {
    //存放page 克隆的key
    private static final String COPY_KEY = "curPageCopy";
    //存放爬取到的数据的key
    private static final String CRAWLED_DATA = "CRAWLED_DATA";
    /**
     * 将page放入存入自己的结果中。这样会循环引用，注意不要转成json
     * @param page
     */
    public static void addPageSelfToResult(Page page) {
        Page copy = new Page();
        copy.setUrl(page.getUrl());
        copy.setStatusCode(page.getStatusCode());
        copy.setDownloadSuccess(page.isDownloadSuccess());
        copy.setCharset(page.getCharset());
        copy.setRequest(new Request(page.getRequest().getUrl()));
        copy.setRawText(page.getRawText());
        copy.setBytes(page.getBytes());
        page.putField(COPY_KEY, copy);
    }

    /**
     * 获取page的html
     * @param items
     * @return
     */
    public static Html getHtml(ResultItems items) {
        return getPage(items).getHtml();
    }

    /**
     * 获取page
     * @param items
     * @return
     */
    public static Page getPage(ResultItems items) {
        return items.get(COPY_KEY);
    }
    
    public static void setData(ResultItems items, Object data) {
        items.put(CRAWLED_DATA, data);
    }
    
    public static Object getData(ResultItems items) {
        return items.get(CRAWLED_DATA);
    }
}
