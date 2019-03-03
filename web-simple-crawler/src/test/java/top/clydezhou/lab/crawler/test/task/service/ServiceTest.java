package top.clydezhou.lab.crawler.test.task.service;

import javax.annotation.Resource;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;
import top.clydezhou.lab.crawler.extend.utils.JsonUtils;
import top.clydezhou.lab.crawler.task.dao.po.TaskInfoPO;
import top.clydezhou.lab.crawler.task.service.ICrawlTaskDataService;
import top.clydezhou.lab.crawler.task.service.ITaskInfoService;
import top.clydezhou.lab.crawler.test.common.BaseTest;

@Slf4j
public class ServiceTest extends BaseTest {

    @Resource
    ITaskInfoService taskInfoService;

    @Resource
    ICrawlTaskDataService service;

    @Test
    public void testGetByPage() {
        log.info(JsonUtils.getJson(service.getByPage(0L, 1, 10)));
    }

    @Test
    public void startTaskTest() {
        TaskInfoPO po = taskInfoService.startTask(0);
        log.info(JsonUtils.getJson(po));
    }

    @Test
    public void shutdownTaskTest() {
        TaskInfoPO po = taskInfoService.shutdownTask(0);
        log.info(JsonUtils.getJson(po));
    }
}
