package top.clydezhou.lab.crawler.test.task.manager;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import top.clydezhou.lab.crawler.CrawlApplication;
import top.clydezhou.lab.crawler.task.dao.mapper.ICrawlTaskUrlMapper;
import top.clydezhou.lab.crawler.task.dao.po.CrawlTaskUrlPO;
import top.clydezhou.lab.crawler.task.dao.po.CrawlUrlPriorityConstant;
import top.clydezhou.lab.crawler.task.dao.po.TaskStatus;
import top.clydezhou.lab.crawler.task.manager.ICrawlTaskManager;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Task;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CrawlApplication.class)
public class CrawlTaskManagerTest {
    @Resource
    private ICrawlTaskManager manager;

    @Resource
    private ICrawlTaskUrlMapper taskUrlMapper;
    
    @Test
    @Transactional
    public void crawlTaskTest() {
        long taskId = 0;
        String url = "bing.com";
        String hashCode = "hash";
        String highPriorityHashCode = "high";
        Task task = new Task() {
            @Override
            public String getUUID() {
                return null;
            }
            @Override
            public Site getSite() {
                return null;
            }
        };
        //初始化高优先级url
        Request highRequest = new Request(url);
        highRequest.setPriority(CrawlUrlPriorityConstant.HIGH);
        manager.initTaskCrawlUrl(highRequest, task, highPriorityHashCode);
        //初始化url
        Request normalRequest = new Request(url);
        manager.initTaskCrawlUrl(normalRequest, task, hashCode);
        //查询初始化后的状态
        CrawlTaskUrlPO selectPO = new CrawlTaskUrlPO();
        selectPO.setHashCode(hashCode);
        CrawlTaskUrlPO resultUrl = taskUrlMapper.selectOne(selectPO);
        Assert.assertEquals(hashCode, resultUrl.getHashCode());
        Assert.assertEquals(TaskStatus.PENDING, resultUrl.getStatus().intValue());
        
        CrawlTaskUrlPO selectHighPO = new CrawlTaskUrlPO();
        selectHighPO.setHashCode(highPriorityHashCode);
         resultUrl = taskUrlMapper.selectOne(selectHighPO);
        Assert.assertEquals(highPriorityHashCode, resultUrl.getHashCode());
        Assert.assertEquals(TaskStatus.PENDING, resultUrl.getStatus().intValue());
        
        //poll出优先级高的
        CrawlTaskUrlPO pollUrlPO = manager.pollUrl(taskId);
        Assert.assertEquals(highPriorityHashCode, pollUrlPO.getHashCode());
        Assert.assertEquals(TaskStatus.CRAWLING, pollUrlPO.getStatus().intValue());
        //继续poll
        pollUrlPO = manager.pollUrl(taskId);
        Assert.assertEquals(hashCode, pollUrlPO.getHashCode());
        Assert.assertEquals(TaskStatus.CRAWLING, pollUrlPO.getStatus().intValue());
        
        //结束hashCode
        manager.finishCrawUrl(taskId, hashCode, "hashCode");
        resultUrl = taskUrlMapper.selectOne(selectPO);
        Assert.assertEquals(TaskStatus.FINISH, resultUrl.getStatus().intValue());
        //结束highPriorityHashCode
        manager.finishCrawUrl(taskId, highPriorityHashCode, "highPriorityHashCode");
        pollUrlPO = taskUrlMapper.selectOne(selectHighPO);
        Assert.assertEquals(TaskStatus.FINISH, resultUrl.getStatus().intValue());
    }
}
