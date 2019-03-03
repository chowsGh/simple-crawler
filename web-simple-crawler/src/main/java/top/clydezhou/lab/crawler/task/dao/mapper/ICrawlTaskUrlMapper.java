package top.clydezhou.lab.crawler.task.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import top.clydezhou.lab.crawler.common.dao.mapper.BaseMapper;
import top.clydezhou.lab.crawler.task.dao.po.CrawlTaskUrlPO;

/**
 * 任务 url 信息，状态，权重，维护url路径关系（parenturlid）
 * 
 * @author chows
 * @date 2019/02/16
 */
@Mapper
public interface ICrawlTaskUrlMapper extends BaseMapper<CrawlTaskUrlPO> {

    /**
     * 查询任务对应状态的url 根据id和优先级高到低顺序
     * 
     * @param taskId
     * @param status
     * @return
     */
    @Select("SELECT * FROM CrawlTaskUrl WHERE taskid = #{taskId} and status = #{status} order by priority desc, id limit 1")
    CrawlTaskUrlPO getTaskUrl(long taskId, int status);

    /**
     * 更新任务状态
     * 
     * @param taskId
     * @param originStatus
     * @param status
     * @return
     */
    @Update("update CrawlTaskUrl set status = #{status} WHERE taskid = #{taskId} and status = #{originStatus} and flag >= 1")
    int changeTaskUrlStatus(long taskId, int originStatus, int status);

}
