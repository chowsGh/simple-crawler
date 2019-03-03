package top.clydezhou.lab.crawler.task.component.pipeline;

import java.util.Objects;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import top.clydezhou.lab.crawler.extend.componet.task.ITaskWrapper;
import top.clydezhou.lab.crawler.extend.utils.JsonUtils;
import top.clydezhou.lab.crawler.extend.utils.PageUtils;
import top.clydezhou.lab.crawler.task.manager.ICrawlTaskManager;
import top.clydezhou.lab.crawler.task.utils.TaskUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;

/**
 * 基于mysql实现的任务处理管道
 * @author chows
 * @date 2019/02/20
 */
@Slf4j
@Component
@Scope("prototype")
public class MysqlTaskPipeline extends CrawlTaskPipeline {

    @Resource
    protected ICrawlTaskManager crawlTaskManager;

    @Override
    public void process(ResultItems resultItems, Task task) {
        String url = PageUtils.getPage(resultItems).getUrl().toString();
        log.info("url:{}, after extact data.", url);
        String data;
        try {
            data = generateData(resultItems, task);
        } catch (Exception e1) {
            log.error("TaskCrawUrl:{} generateData exception.", url, e1);
            return;
        }
        finishTask(TaskUtils.convertFromTask(task), url, data);
    }
    /**
     * 更新爬虫url任务记录
     * @param task
     * @param url
     * @param data
     */
    protected void finishTask(ITaskWrapper task, String url, String data) {
        // 开始记录
        try {
            //TODO 检查url是否是数据节点，
            //TODO 非文本类型是否保存到数据库？或者保存一个路径？
            //如果是数据节点但是data为空，则记录日志，保存原始文本到数据库，并标记任务为失败
            String hashCode = crawlStrategy.getHashCode(url);
            crawlTaskManager.finishCrawUrl(task.getId(), hashCode, data);
            log.info("url:{}, after updateTaskCrawUrl.", url);
        } catch (Exception e) {
            log.error("update TaskCrawUrl:{} exception. data:{}", url, data, e);
        }
    }
    /**
     * 抽取数据
     * 
     * @param resultItems
     * @param task
     * @return
     */
    protected String generateData(ResultItems resultItems, Task task) {
        String crawlData = null;
        try {
            Object dataObj = getCrawledData(resultItems);
            if(dataObj instanceof String) {
                crawlData = (String)dataObj;
            } else if (dataObj instanceof byte[]) {
                //TODO 暂时不支持非文本类型的数据
            } else {
                //TODO 目前只是简单粗暴的转成json
                crawlData = JsonUtils.getJson(dataObj);
            }
            Page page = PageUtils.getPage(resultItems);

            //TODO 可能页面内容太大不太好放到数据库
//            if (Objects.isNull(crawlData)) {
//                crawlData = page.getRawText();
//            }
//            if (Objects.isNull(crawlData)) {
//                crawlData = new String(page.getBytes(), StandardCharsets.UTF_8);
//            }
            if (Objects.isNull(crawlData)) {
                log.info("page:{}, get data failed.", page.getUrl().toString());
            }
        } catch (Exception e) {
            log.error("generateData exception.", e);
        }
        return crawlData;
    }
}
