package top.clydezhou.lab.crawler.task.dao.po;

import java.util.Date;

import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import top.clydezhou.lab.crawler.common.dao.po.BaseIdAutoIncreseDBObject;
/**
 * 任务爬取的url
 * 流程 
 * 爬取流程：
 *  startUrl -> 保存到taskCrawlUrl+crawlurl 
 *      -> 下载信息 并保存到 CrawlCachedRawData, 更新crawlurl记录
 *      -> 抽取 数据 保存到crawledData 
 *      -> 抽取新的url newUrl 重新执行爬取流程
 * 
 * @author chows
 * @date 2019/02/15
 */
@Getter
@Setter
@ToString
@Table(name = "crawltaskurl")
public class CrawlTaskUrlPO extends BaseIdAutoIncreseDBObject {
    private Long taskId;
    private String url;
    //根据url生成hashCode 用来查询 
    private String hashCode;

    //缓存url相关信息, url, 
    //public Long cacheId;

    //CrawlStatus 冗余字段，从CrawlCachedRawData中获取的
    private Integer status;
    private Integer priority;
    //任务请求参数 json形式
    private String requestParam;
    //父节点的url id
    private Long parentId;
    
    public void hasUpdated() {
        setLastUpdateTime(new Date());
    }
}