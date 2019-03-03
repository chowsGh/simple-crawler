package top.clydezhou.lab.crawler.task.component.pipeline;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import top.clydezhou.lab.crawler.extend.utils.PageUtils;
import top.clydezhou.lab.crawler.task.config.SelectorConfig;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * TODO 通用解析pipeline
 * 1. css选择器抽取
 * 2. xpath选择器抽取
 * 保存普通的key-value形式的数据
 * @author chows
 * @date 2019/02/20
 */
@Slf4j
@Component("commonResolvePipeline")
@Scope("prototype")
public class CommonResolvePipeline implements Pipeline {

    @Getter
    @Setter
    Map<String, SelectorConfig> selectorMap = new HashMap<>();

    @Override
    public void process(ResultItems resultItems, Task task) {
        Page page = PageUtils.getPage(resultItems);
        Map<String, String> data = new LinkedHashMap<>();
        for(String key : selectorMap.keySet()) {
            SelectorConfig config = selectorMap.get(key);
            switch (config.getType()) {
                case "css":
                    String value = page.getHtml().$(config.getValue()).toString();
                    if(StringUtils.isNotBlank(value)) {
                        data.put(key, value);
                    }
                    break;
                case "xpath":
                    //TODO 待添加
                    log.error("TBD:to be done");
                    break;
                default:
                    break;
            }
        }
        if(false == data.isEmpty()) {
            PageUtils.setData(resultItems, data);
        }
    }

}
