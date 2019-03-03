package top.clydezhou.lab.crawler.common.dao.po;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseIdAutoIncreseDBObject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Date createTime;
    private Date lastUpdateTime;
    private Integer flag = Flag.NORMAL;
}
