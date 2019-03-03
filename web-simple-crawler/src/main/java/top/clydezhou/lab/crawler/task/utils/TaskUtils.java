package top.clydezhou.lab.crawler.task.utils;

import java.util.HashMap;

import lombok.extern.slf4j.Slf4j;
import top.clydezhou.lab.crawler.extend.componet.task.ITaskWrapper;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;

/**
 * 
 * @author chows
 * @date 2019/02/21
 */
@Slf4j
public class TaskUtils {
    public static final String TASK_URL_PARENT_ID = "TASK_URL_PARENT_ID";
    public static final String TASK_URL_ID = "TASK_URL_ID";

    public static void setRequestParentId(Request request, Long urlParentId) {
        if (urlParentId != null) {
            request.putExtra(TASK_URL_PARENT_ID, urlParentId);
        } else {
            log.error("id is null");
        }
    }

    public static Long getRequestParentId(Request request) {
        Long id = null;
        Object idObj = request.getExtra(TASK_URL_PARENT_ID);
        if (idObj != null) {
            id = (Long)idObj;
        }
        return id;
    }

    public static void setRequestId(Request request, Long urlId) {
        if (urlId != null) {
            request.putExtra(TASK_URL_ID, urlId);
        } else {
            log.error("id is null");
        }
    }

    public static Long getRequestId(Request request) {
        Long id = null;
        Object idObj = request.getExtra(TASK_URL_ID);
        if (idObj != null) {
            id = (Long)idObj;
        }
        return id;
    }
    /**
     * 初始化request对象
     * @param request
     */
    public static void initRequest(Request request) {
        if(request.getExtras() == null) { 
            synchronized (request) {
                if(request.getExtras() == null) {
                    request.setExtras(new HashMap<String, Object>());
                }
            }
        }
    }
    /**
     * 转换成task wrapper
     * @param task
     * @return
     */
    public static ITaskWrapper convertFromTask(Task task) {
        return (ITaskWrapper)task;
    }
}
