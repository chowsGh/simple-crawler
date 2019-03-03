package top.clydezhou.lab.crawler.task.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import top.clydezhou.lab.crawler.common.dao.mapper.BaseMapper;
import top.clydezhou.lab.crawler.task.dao.po.CrawlTaskDataPO;
import top.clydezhou.lab.crawler.task.entity.CrawlTaskDataEntity;

/**
 * 
 * @author chows
 * @date 2019/02/16
 */
@Mapper
public interface ICrawlTaskDataMapper extends BaseMapper<CrawlTaskDataPO> {

    /**
     * 根据任务id获取数据实体
     * @param taskId
     * @return
     */
    public List<CrawlTaskDataEntity> getDataEntityByTaskId(long taskId);
}
