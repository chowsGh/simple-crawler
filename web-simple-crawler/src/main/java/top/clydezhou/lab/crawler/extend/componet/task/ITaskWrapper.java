package top.clydezhou.lab.crawler.extend.componet.task;

import us.codecraft.webmagic.Task;

public interface ITaskWrapper extends Task{
    /**
     * 提供long类型的唯一id,用来支持用long来标识的任务
     * @return id
     */
    public default long getId() {
        return 0L;
    }
}
