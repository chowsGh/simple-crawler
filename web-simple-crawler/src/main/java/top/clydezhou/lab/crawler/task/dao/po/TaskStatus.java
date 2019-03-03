package top.clydezhou.lab.crawler.task.dao.po;

/**
 * url 爬取状态
 * @author chows
 * @date 2019/02/14
 */
public class TaskStatus {
    //通用状态
    public static final int NOT_START = -1;

    public static final int RUNNING = 1000;

    //url任务状态
    //未开始
    public static final int PENDING = 0;
    //正在爬取
    public static final int CRAWLING = 1000;
    //public static final int CACHED = 2000;
    //已经解析了
    public static final int RESOLVE = 3000;
    public static final int FINISH = 10000;
}
