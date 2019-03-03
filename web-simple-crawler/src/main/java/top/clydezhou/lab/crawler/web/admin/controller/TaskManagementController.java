package top.clydezhou.lab.crawler.web.admin.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import top.clydezhou.lab.crawler.task.dao.po.TaskInfoPO;
import top.clydezhou.lab.crawler.task.service.ITaskInfoService;
import top.clydezhou.lab.crawler.web.admin.common.DefaultResult;

/**
 * TODO 任务管理controller
 * 
 * @author chows
 * @date 2019/02/20
 */
//TODO 设置跨域，如果路由到同域则不用设置。
@CrossOrigin("*")
@Slf4j
@RestController
@RequestMapping("/taskManagement")
public class TaskManagementController {

    @Resource
    ITaskInfoService taskInfoService;

    /**
     * 启动爬虫
     * 
     * @return
     */
    @RequestMapping(value = "/start", method = RequestMethod.POST)
    public DefaultResult<TaskInfoPO> startTask(@RequestBody TaskInfoPO taskId) {
        DefaultResult<TaskInfoPO> r = new DefaultResult<>();
        TaskInfoPO taskInfo = null;
        try {
            taskInfo = taskInfoService.startTask(taskId.getId());
            if (taskInfo != null) {
                r.setInfo(taskId.getId() + " is start success");
                r.setResult(taskInfo);
                r.success();
            } else {
                r.setErrorInfo(taskId.getId() + " is start failed");
            }    
        } catch (Exception e) {
            log.error("startTask exception.", e);
            r.setErrorInfo("startTask exception.");
        }
        return r;
    }
    @RequestMapping(value = "/shutdown", method = RequestMethod.POST)
    public DefaultResult<TaskInfoPO> shutdownTask(@RequestBody TaskInfoPO taskId) {
        DefaultResult<TaskInfoPO> r = new DefaultResult<>();
        TaskInfoPO taskInfo = taskInfoService.shutdownTask(taskId.getId());
        if (taskInfo != null) {
            r.setInfo(taskId.getId() + " is shutdown success");
            r.setResult(taskInfo);
            r.success();
        } else {
            r.setErrorInfo(taskId.getId() + " is shutdown failed");
        }
        return r;
    }
}
