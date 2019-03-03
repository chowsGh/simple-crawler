package top.clydezhou.lab.crawler.task.dao.po;

import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import top.clydezhou.lab.crawler.common.dao.po.BaseIdAutoIncreseDBObject;

/**
 * 经过处理后的数据
 * @author chows
 * @date 2019/02/14
 */
@Getter
@Setter
@ToString
@Table(name = "crawltaskdata")
public class CrawlTaskDataPO extends BaseIdAutoIncreseDBObject {
    //关联任务id
    public Long taskId;
    /**
     * url id 
     */
    public Long urlId;
    /**
     * 经过处理后的数据
     */
    public String data;

    public CrawlTaskDataPO() {}
    public CrawlTaskDataPO(long urlId, String data) {
        this.urlId = urlId;
        this.data = data;
    }
}
