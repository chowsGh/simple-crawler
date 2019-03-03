package top.clydezhou.lab.crawler.task.config;

import lombok.Getter;
import lombok.Setter;

/**
 * 选择器配置
 * TODO 目前只实现了css选择
 * @author chows
 * @date 2019/03/03
 */
@Getter
@Setter
public class SelectorConfig {
    String type;
    String value;
}
