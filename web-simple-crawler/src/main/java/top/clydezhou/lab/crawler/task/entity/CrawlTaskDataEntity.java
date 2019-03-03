package top.clydezhou.lab.crawler.task.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * crawltaskData 和 crawltaskUrl 的组合实体
 * @author chows
 * @date 2019/03/01
 */
@Getter
@Setter
public class CrawlTaskDataEntity {

    public Long id;
    
    public Long taskId;
    /**
     * url
     */
    public String url;
    /**
     * 经过处理后的数据
     */
    public String data;
    
    public Date createTime;
}
