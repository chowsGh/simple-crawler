package top.clydezhou.lab.crawler.extend.strategy;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.DigestUtils;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import top.clydezhou.lab.crawler.task.dao.po.CrawlUrlPriorityConstant;

@Slf4j
public class BaseCrawlStrategy implements ICrawlStrategy {
    // 黑名单
    @Getter
    @Setter
    private List<String> blackRegexList = new ArrayList<>();
    // url 要求强制要求白名单以外的地址，需要满足里面的每个条件
    @Getter
    @Setter
    private List<String> requireRegexList = new ArrayList<>();
    // 白名单
    @Getter
    @Setter
    private List<String> whiteRegexList = new ArrayList<>();
    // 优先级 正则 map
    @Getter
    @Setter
    private Map<Integer, List<String>> priorityMap = new HashMap<>();

    /**
     * 是否匹配正则表达式
     * 
     * @param regex
     * @param str
     * @return
     */
    public static boolean matches(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    @Override
    public boolean checkUrl(String link) {
        // 是否在黑名单里
        for (String reg : blackRegexList) {
            if (matches(reg, link)) {
                return false;
            }
        }
        // 是否在白名单
        for (String whiteReg : whiteRegexList) {
            if (matches(whiteReg, link)) {
                return true;
            }
        }
         //是否符合正则 TODO 需要思考如何判断url
        boolean matchRequire = true;
         for (String require : requireRegexList) {
             if (false == matches(require, link)) {
                 matchRequire = false;
                 break;
             }
         }
        return matchRequire;
    }

    /**
     * 默认去除hash
     */
    @Override
    public String formatUrl(String crawUrlStr) {
        if (crawUrlStr.contains("#")) {
            crawUrlStr = crawUrlStr.substring(0, crawUrlStr.lastIndexOf("#"));
        }
        return crawUrlStr;
    }

    @Override
    public String hashStrategy(String link) {
        if (link.contains("#")) {
            // 去除hash
            link = link.substring(0, link.lastIndexOf("#"));
        }
        // 去除协议
        link = link.substring(link.indexOf(":"));
        //TODO 参数策略
        //1. 去除参数
        //2. 解析link参数，将参数排序进行hash全匹配
        return link;
    }

    @Override
    public String getHashCode(String link) {
        String hashCode = null;
        try {
            hashCode = DigestUtils.md5DigestAsHex(hashStrategy(link).getBytes(StandardCharsets.UTF_8.toString()));
        } catch (UnsupportedEncodingException e) {
            log.error("原则上不会出异常，但是还是出现UnsupportedEncodingException了", e);
        }
        return hashCode;
    }

    @Override
    public int assignPriority(String link) {
        int priority = CrawlUrlPriorityConstant.NORMAL;
        outer:
        for (Integer key : priorityMap.keySet()) {
            for (String regex : priorityMap.get(key)) {
                if (matches(regex, link)) {
                    priority = key;
                    break outer;
                }
            }
        }
        return priority;
    }
}
