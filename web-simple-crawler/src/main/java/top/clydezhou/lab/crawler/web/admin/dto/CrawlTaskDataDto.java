package top.clydezhou.lab.crawler.web.admin.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 爬取到的数据DTO
 * @author chows
 * @date 2019/02/26
 */
@Getter
@Setter
public class CrawlTaskDataDto {
    
    public Long taskId;
    /**
     * url id 
     */
    public Long urlId;
    /**
     * 经过处理后的数据
     */
    public String data;
    
    public String url;
}
