package top.clydezhou.lab.crawler.task.service;

import top.clydezhou.lab.crawler.task.entity.CrawlTaskDataEntity;
import top.clydezhou.lab.crawler.web.admin.common.PageableDto;

public interface ICrawlTaskDataService {

    /**
     * 默认根据id排序
     * 
     * @param taskId
     * @param page
     * @param size
     * @return
     */
    PageableDto<CrawlTaskDataEntity> getByPage(long taskId, int page, int size);

}