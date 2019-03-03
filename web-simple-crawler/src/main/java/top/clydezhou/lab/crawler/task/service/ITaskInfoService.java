package top.clydezhou.lab.crawler.task.service;

import top.clydezhou.lab.crawler.task.dao.po.TaskInfoPO;
import top.clydezhou.lab.crawler.task.dao.po.TaskParamPO;
import top.clydezhou.lab.crawler.web.admin.common.PageableDto;

public interface ITaskInfoService {

    /**
     * 
     * @param taskId
     * @param page
     * @param size
     * @return
     */
    PageableDto<TaskInfoPO> getByPage(int page, int size);
    
    /**
     * 启动任务
     * @param taskid
     * @return
     */
    TaskInfoPO startTask(long taskid);
    
    /**
     * 关闭任务
     * @param taskid
     * @return
     */
    TaskInfoPO shutdownTask(long taskid);
    /**
     * 删除任务
     * @param taskid
     * @return
     */
    boolean deleteTask(long taskid);

    /**
     * 保存，不存在创建。
     * @param taskInfo
     * @return
     */
    int saveTaskInfo(TaskInfoPO taskInfo);
    /**
     * 保存任务参数不存在创建，存在更新
     * @param param
     * @return
     */
    int saveTaskParam(TaskParamPO param);

}