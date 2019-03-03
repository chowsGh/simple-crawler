package top.clydezhou.lab.crawler.task.dao.po;
/**
 * 数值越小，优先级越低，反之
 * 优先级 最高优先级 是 1000
 * 最低优先级是 0
 * @author chows
 * @date 2019/02/17
 */
public class CrawlUrlPriorityConstant {
    public static final int MAX_HIGH = 1000;
    public static final int HIGH = 20;
    public static final int NORMAL = 0;
    public static final int MIN = -1000;
}
