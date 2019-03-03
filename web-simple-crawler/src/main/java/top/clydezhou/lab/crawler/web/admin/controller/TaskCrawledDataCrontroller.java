package top.clydezhou.lab.crawler.web.admin.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import top.clydezhou.lab.crawler.task.entity.CrawlTaskDataEntity;
import top.clydezhou.lab.crawler.task.service.ICrawlTaskDataService;
import top.clydezhou.lab.crawler.web.admin.common.PageableDto;
import top.clydezhou.lab.crawler.web.admin.common.PageableResult;
import top.clydezhou.lab.crawler.web.admin.common.ResultUtils;

/**
 * 任务数据controller
 * @author chows
 * @date 2019/02/20
 */
//TODO 设置跨域，如果路由到同域则不用设置。
@CrossOrigin("*")

@RestController
@RequestMapping("/task")
public class TaskCrawledDataCrontroller {
    
    @Resource
    ICrawlTaskDataService crawlTaskDataService;
    
    @RequestMapping(value = "/crawl-data", method = RequestMethod.GET)
    public PageableResult<CrawlTaskDataEntity> requestMethodName(
        @RequestParam long taskId,
        @RequestParam int page,
        @RequestParam int size) {
        PageableResult<CrawlTaskDataEntity> r = ResultUtils.createPageableResult(CrawlTaskDataEntity.class);
        //r.setResult(crawlTaskDataService.getByPage(taskId, page, size));
        PageableDto<CrawlTaskDataEntity> dto = crawlTaskDataService.getByPage(taskId, page, size);
        dto.getData().add(dto.getData().get(0));
        dto.getData().add(dto.getData().get(0));
        dto.getData().add(dto.getData().get(0));
        dto.getData().add(dto.getData().get(0));
        dto.getData().add(dto.getData().get(0));
        dto.getData().add(dto.getData().get(0));
        dto.getData().add(dto.getData().get(0));
        dto.getData().add(dto.getData().get(0));
        dto.getData().add(dto.getData().get(0));
        dto.getData().add(dto.getData().get(0));
        dto.getData().add(dto.getData().get(0));
        dto.getData().add(dto.getData().get(0));
        dto.getData().add(dto.getData().get(0));
        dto.getData().add(dto.getData().get(0));
        dto.getData().add(dto.getData().get(0));
        dto.getData().add(dto.getData().get(0));
        r.setResult(dto);
        r.success();
        return r;
    }

}
