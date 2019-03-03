package top.clydezhou.lab.crawler.task.manager.impl;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import top.clydezhou.lab.crawler.extend.utils.JsonUtils;
import top.clydezhou.lab.crawler.task.dao.mapper.ICrawlTaskDataMapper;
import top.clydezhou.lab.crawler.task.dao.mapper.ICrawlTaskUrlMapper;
import top.clydezhou.lab.crawler.task.dao.mapper.ITaskInfoMapper;
import top.clydezhou.lab.crawler.task.dao.po.CrawlTaskDataPO;
import top.clydezhou.lab.crawler.task.dao.po.CrawlTaskUrlPO;
import top.clydezhou.lab.crawler.task.dao.po.TaskInfoPO;
import top.clydezhou.lab.crawler.task.dao.po.TaskStatus;
import top.clydezhou.lab.crawler.task.exception.CrawlTaskException;
import top.clydezhou.lab.crawler.task.manager.ICrawlTaskManager;
import top.clydezhou.lab.crawler.task.utils.TaskUtils;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;

@Slf4j
@Component
public class CrawlTaskManager implements ICrawlTaskManager {

    @Resource
    private ICrawlTaskDataMapper taskCrawledDataMapper;

    @Resource
    private ICrawlTaskUrlMapper taskUrlMapper;
    
    @Resource
    private ITaskInfoMapper taskInfoMapper;

    @Override
    @Transactional
    public void initTaskCrawlUrl(Request request, Task task, String hashCode) {
        long taskId = TaskUtils.convertFromTask(task).getId();
        String url = request.getUrl();
        Integer priority = (int)request.getPriority();
        // 初始化任务url
        CrawlTaskUrlPO initTaskUrl = new CrawlTaskUrlPO();
        initTaskUrl.setTaskId(taskId);
        initTaskUrl.setHashCode(hashCode);
        Object cycleTriedTimesObject = request.getExtra(Request.CYCLE_TRIED_TIMES);
        // 当前任务还没有保存过这个url记录
        if (taskUrlMapper.selectCount(initTaskUrl) == 0) {
            initTaskUrl.setUrl(url);
            // 设置 parent id
            initTaskUrl.setParentId(TaskUtils.getRequestParentId(request));
            initTaskUrl.setPriority(priority);
            initTaskUrl.setRequestParam(JsonUtils.getJson(request));
            initTaskUrl.setStatus(TaskStatus.PENDING);
            taskUrlMapper.insertSelective(initTaskUrl);
        } else if (cycleTriedTimesObject != null) {
            // 重试次数
            initTaskUrl = taskUrlMapper.selectOne(initTaskUrl);
            initTaskUrl.setPriority(priority);
            initTaskUrl.setRequestParam(JsonUtils.getJson(request));
            initTaskUrl.setStatus(TaskStatus.PENDING);
            initTaskUrl.hasUpdated();
            taskUrlMapper.updateByPrimaryKeySelective(initTaskUrl);
        } else {
            log.error("重复添加url：{}", url);
        }
    }

    @Override
    @Transactional
    public void finishCrawUrl(long taskId, String hashCode, String data) {
        CrawlTaskUrlPO taskUrl = new CrawlTaskUrlPO();
        taskUrl.setTaskId(taskId);
        taskUrl.setHashCode(hashCode);
        taskUrl.setStatus(TaskStatus.CRAWLING);
        // 查询task Url
        taskUrl = taskUrlMapper.selectOne(taskUrl);
        if (taskUrl == null) {
            throw new CrawlTaskException(hashCode + " does not exists, get task url failed.");
        }
        try {
            // 新增处理后的数据
            if (StringUtils.isNotBlank(data)) {
                CrawlTaskDataPO crawledData = new CrawlTaskDataPO();
                crawledData.setTaskId(taskId);
                crawledData.setUrlId(taskUrl.getId());
                crawledData.setData(data);
                taskCrawledDataMapper.insertSelective(crawledData);
            } else {
                log.debug("url[{}] no data, so skipped.", taskUrl.getUrl());
            }
        } catch (Exception e) {
            log.error("insert crawled data exception.", e);
        } finally {
            taskUrl.setStatus(TaskStatus.FINISH);
            taskUrl.hasUpdated();
            taskUrlMapper.updateByPrimaryKeySelective(taskUrl);
        }
    }

    @Override
    @Transactional
    public CrawlTaskUrlPO pollUrl(long taskId) {
        CrawlTaskUrlPO url = taskUrlMapper.getTaskUrl(taskId, TaskStatus.PENDING);
        if (url != null) {
            url.setStatus(TaskStatus.CRAWLING);
            url.hasUpdated();
            taskUrlMapper.updateByPrimaryKeySelective(url);
        }
        return url;
    }

    @Override
    public Request pollUrlRequest(long taskId) {
        Request request = null;
        CrawlTaskUrlPO url = pollUrl(taskId);
        if (url == null) {
            log.error("poll failed.");
        } else if (url.getRequestParam() == null) {
            request = new Request(url.getUrl());
            TaskUtils.setRequestId(request, url.getId());
            log.debug("legacy crawlurl, just warp request.");
        } else {
            request = JsonUtils.readValue(url.getRequestParam(), Request.class);
            TaskUtils.setRequestId(request, url.getId());
        }
        return request;
    }

    @Override
    public TaskInfoPO startTask(Long taskId) {
        TaskInfoPO taskInfoPO = taskInfoMapper.selectByPrimaryKey(taskId);
        if(taskInfoPO.getStatus() != TaskStatus.CRAWLING) {
            // 更新爬虫状态
            taskInfoPO.setStatus(TaskStatus.CRAWLING);
            taskInfoMapper.updateByPrimaryKey(taskInfoPO);
            //更新未完成任务为 未开始
            taskUrlMapper.changeTaskUrlStatus(taskId, TaskStatus.CRAWLING, TaskStatus.NOT_START);
        }
        return taskInfoPO;
    }
}
