package top.clydezhou.lab.crawler.task.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import top.clydezhou.lab.crawler.common.dao.mapper.BaseMapper;
import top.clydezhou.lab.crawler.task.dao.po.TaskInfoPO;
import top.clydezhou.lab.crawler.task.dao.po.TaskParamPO;

/**
 * 爬虫任务基础信息 
 * @author chows
 * @date 2019/02/16
 */
@Mapper
public interface ITaskInfoMapper extends BaseMapper<TaskInfoPO> {

    @Select("SELECT * FROM TaskParam WHERE taskid = #{taskId}")
    public TaskParamPO getTaskParam(long taskId);
}
