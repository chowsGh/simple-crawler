package top.clydezhou.lab.crawler.task.dao.po;

import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import top.clydezhou.lab.crawler.common.dao.po.BaseIdAutoIncreseDBObject;

/**
 * 任务参数，暂时没有用，后期拓展用
 * @author chows
 * @date 2019/02/16
 */
@Getter
@Setter
@Table(name = "taskparam")
@ToString
public class TaskParamPO extends BaseIdAutoIncreseDBObject {
    //任务id
    private Long taskid;
    //参数
    private String param;
}