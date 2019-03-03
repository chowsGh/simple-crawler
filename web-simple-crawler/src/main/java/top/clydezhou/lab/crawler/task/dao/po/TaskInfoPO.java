package top.clydezhou.lab.crawler.task.dao.po;

import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import top.clydezhou.lab.crawler.common.dao.po.BaseIdAutoIncreseDBObject;

/**
 * 任务基础信息表
 * @author chows
 * @date 2019/02/15
 */
@Getter
@Setter
@Table(name = "taskinfo")
@ToString
public class TaskInfoPO extends BaseIdAutoIncreseDBObject {

    //任务名称
    private String name;
    //任务描述
    private String description;

    private String creator;
    //任务类型
    private Integer type;
    //状态
    private Integer status;
}