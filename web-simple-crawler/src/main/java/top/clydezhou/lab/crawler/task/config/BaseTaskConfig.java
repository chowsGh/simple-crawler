package top.clydezhou.lab.crawler.task.config;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * 爬虫基础配置实体
 * @author chows
 * @date 2019/03/03
 */
@Getter
@Setter
public class BaseTaskConfig {
    //下载完后睡眠时间 单位 ms
    private int sleepTime;
    //是否使用代理
    private boolean useProxy;
    //下载超时时间 单位 ms
    private int downloadTimeOut;
    //出错重试3次
    private int retryTimes = 3;
    //爬虫启动线程数
    private int threadCount = 1;
    //起始地址
    private List<String> startUrlList;
    //设置黑名单
    private List<String> blackRegexList;
    private List<String> whiteRegexList;
    //url 要求，例如限定在某个域名，某个文件，html之类的
    private List<String> requireRegexList;
    //匹配优先级，key:优先级，value: url正则的list
    private Map<Integer, List<String>> priorityMap;
    //可拓展的 key:value 信息
    private Map<String, Object> extendMap;
    //管道的名字
    private List<String> pipelineList;
    //数据抽取选择器配置，
    private Map<String, SelectorConfig> selectorMap;
}
