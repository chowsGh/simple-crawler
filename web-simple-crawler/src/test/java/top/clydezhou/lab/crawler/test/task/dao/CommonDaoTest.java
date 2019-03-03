package top.clydezhou.lab.crawler.test.task.dao;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;

import lombok.extern.slf4j.Slf4j;
import top.clydezhou.lab.crawler.CrawlApplication;
import top.clydezhou.lab.crawler.extend.utils.JsonUtils;
import top.clydezhou.lab.crawler.task.dao.mapper.ICrawlTaskDataMapper;
import top.clydezhou.lab.crawler.task.dao.mapper.ITaskInfoMapper;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CrawlApplication.class)
public class CommonDaoTest {
    
    @Resource
    private ITaskInfoMapper taskInfoMapper;
    
    @Resource
    private ICrawlTaskDataMapper taskDataMapper;
    
    @Test
    @Transactional
    public void getDataEntityByTaskIdTest() {
        PageHelper.startPage(1, 1);
        PageHelper.orderBy("createtime desc");
        log.info(JsonUtils.getJson( taskDataMapper.getDataEntityByTaskId(0L)));
    }
    
    @Test
    @Transactional
    public void getTaskParamTest() {
        log.info(JsonUtils.getJson(taskInfoMapper.getTaskParam(0)));
    }
}
