package top.clydezhou.lab.crawler.task.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskEntity {
    private Long id;
  //任务名称
    private String name;
    //任务描述
    private String description;

    private String creator;
    //任务类型
    private Integer type;
    //状态
    private Integer status;
    //参数
    private String param;

    private Date createTime;
    private Date lastUpdateTime;
    private Integer flag;
}
