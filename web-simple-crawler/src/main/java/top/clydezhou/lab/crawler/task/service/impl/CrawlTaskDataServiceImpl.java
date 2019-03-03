package top.clydezhou.lab.crawler.task.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.github.pagehelper.PageHelper;

import top.clydezhou.lab.crawler.task.dao.mapper.ICrawlTaskDataMapper;
import top.clydezhou.lab.crawler.task.entity.CrawlTaskDataEntity;
import top.clydezhou.lab.crawler.task.service.ICrawlTaskDataService;
import top.clydezhou.lab.crawler.web.admin.common.PageableDto;

@Component
public class CrawlTaskDataServiceImpl implements ICrawlTaskDataService {

    @Resource
    ICrawlTaskDataMapper dataMapper;
    /* (non-Javadoc)
     * @see top.clydezhou.lab.crawler.admin.web.service.impl.ICrawlTaskDataService#getByPage(long, int, int)
     */
    @Override
    public PageableDto<CrawlTaskDataEntity> getByPage(long taskId, int page, int size) {
        PageableDto<CrawlTaskDataEntity> pageDto = new PageableDto<>();
        //TODO 验证参数是否正确
        PageHelper.startPage(page, size);
        pageDto.setData(dataMapper.getDataEntityByTaskId(taskId));
        return pageDto;
    }
}
