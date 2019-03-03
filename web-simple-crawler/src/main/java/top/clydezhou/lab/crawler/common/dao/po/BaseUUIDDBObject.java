package top.clydezhou.lab.crawler.common.dao.po;

import java.util.Date;

import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseUUIDDBObject {

    /**
     * uuid
     */
    @Id
    public String id;
    
    public Date createTime;
    public Date lastUpdateTime;
    public Integer flag = Flag.NORMAL;
}
