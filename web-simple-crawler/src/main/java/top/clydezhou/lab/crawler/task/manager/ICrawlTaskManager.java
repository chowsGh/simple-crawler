package top.clydezhou.lab.crawler.task.manager;

import top.clydezhou.lab.crawler.task.dao.po.CrawlTaskUrlPO;
import top.clydezhou.lab.crawler.task.dao.po.TaskInfoPO;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;

public interface ICrawlTaskManager {

    /**
     * 初始化任务url
     * @param taskId
     * @param url
     * @param hashCode
     */
    void initTaskCrawlUrl(Request request, Task task, String hashCode);

    /**
     * 更新任务TaskUrlStatus。，保存task需要的taskCrawledData
     */
    void finishCrawUrl(long taskId, String hashCode, String data);

    /**
     * 返回一个未开始的任务，并更新状态为处理中
     *  id
     *  taskId
     *  urlCrawlStatus
     *  priority (权重 优先级)
     * @return
     */
    CrawlTaskUrlPO pollUrl(long taskId);
    /**
     * 基于pollUrl 返回一个封装好的request
     * @param taskId
     * @return
     */
    Request pollUrlRequest(long taskId);
    
    /**
     * 开启任务，更新之前未完成的任务为未开始
     * @param taskId
     */
    TaskInfoPO startTask(Long taskId);
}