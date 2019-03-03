package top.clydezhou.lab.crawler.task.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;

import lombok.extern.slf4j.Slf4j;
import top.clydezhou.lab.crawler.task.dao.mapper.ITaskInfoMapper;
import top.clydezhou.lab.crawler.task.dao.po.TaskInfoPO;
import top.clydezhou.lab.crawler.task.dao.po.TaskParamPO;
import top.clydezhou.lab.crawler.task.dao.po.TaskStatus;
import top.clydezhou.lab.crawler.task.manager.SpiderManager;
import top.clydezhou.lab.crawler.task.manager.impl.CrawlTaskManager;
import top.clydezhou.lab.crawler.task.service.ITaskInfoService;
import top.clydezhou.lab.crawler.web.admin.common.PageableDto;

@Slf4j
@Component
public class TaskInfoServiceImpl implements ITaskInfoService {

    @Resource
    private ITaskInfoMapper taskInfoMapper;
    @Resource
    private SpiderManager spiderManager;
    @Resource
    private CrawlTaskManager crawlTaskManager;

    @Override
    public PageableDto<TaskInfoPO> getByPage(int page, int size) {
        PageableDto<TaskInfoPO> pageable = new PageableDto<>();
        pageable.setPageInfo(page, size, taskInfoMapper.selectCount(new TaskInfoPO()));
        // 所有未删除的任务
        PageHelper.startPage(page, size);
        pageable.setData(taskInfoMapper.selectAll());
        return pageable;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TaskInfoPO startTask(long taskId) {
        TaskInfoPO taskInfoPO = taskInfoMapper.selectByPrimaryKey(Long.valueOf(taskId));
        boolean isRunning = spiderManager.isRunning(taskId);

        if (taskInfoPO.getStatus() == TaskStatus.CRAWLING && isRunning) {
            log.debug("{} has already started.", taskId);
        } else {
            //爬虫还没有启动
            // 更新任务状态
            crawlTaskManager.startTask(taskId);
            // 启动爬虫
            TaskParamPO param = taskInfoMapper.getTaskParam(taskId);
            if (param != null) {
                spiderManager.startSpider(param, isRunning);
            }
        }
        return taskInfoPO;
    }

    @Override
    public TaskInfoPO shutdownTask(long taskId) {
        TaskInfoPO taskInfoPO = null;
        try {
            taskInfoPO = taskInfoMapper.selectByPrimaryKey(Long.valueOf(taskId));
            taskInfoPO.setStatus(TaskStatus.NOT_START);
            taskInfoMapper.updateByPrimaryKey(taskInfoPO);
            if (spiderManager.isRunning(taskId)) {
                spiderManager.shutdownSpider(taskId);
            }
        } catch (Exception e) {
            log.debug("exception when shutdownTask {} .", taskId, e);
        }
        return taskInfoPO;
    }

    @Override
    public boolean deleteTask(long taskid) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int saveTaskInfo(TaskInfoPO taskInfo) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int saveTaskParam(TaskParamPO param) {
        // TODO Auto-generated method stub
        return 0;
    }

}