package top.clydezhou.lab.crawler.web.admin.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import top.clydezhou.lab.crawler.task.dao.po.TaskInfoPO;
import top.clydezhou.lab.crawler.task.service.ITaskInfoService;
import top.clydezhou.lab.crawler.web.admin.common.PageableResult;
import top.clydezhou.lab.crawler.web.admin.common.ResultUtils;

/**
 * 任务信息controller
 * 
 * @author chows
 * @date 2019/02/20
 */
//TODO 设置跨域，如果路由到同域则不用设置。
@CrossOrigin("*")

@RestController
@RequestMapping("/taskinfo")
public class TaskInfoController {

    @Resource
    ITaskInfoService taskInfoService;

    /**
     * 获取任务列表，
     * TODO 后期支持根据用户查询
     * 
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public PageableResult<TaskInfoPO> getTaskInfo(@RequestParam Integer page, @RequestParam Integer size) {
        PageableResult<TaskInfoPO> r = ResultUtils.createPageableResult(TaskInfoPO.class);
        page = page == null ? 1 : page; 
        size = size == null ? 10 : size;
        r.setResult(taskInfoService.getByPage(page, size));
        r.success();
        return r;
    }
}
