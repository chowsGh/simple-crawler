package top.clydezhou.lab.crawler.extend.utils;

import org.apache.commons.lang3.StringUtils;

import top.clydezhou.lab.crawler.extend.strategy.BaseCrawlStrategy;

public class RegexUtils {
    /**
     * 转义正则特殊字符 （$()*+.[]?\^{}
     * \\需要第一个替换，否则replace方法替换时会有逻辑bug
     */
    public static String makeQueryStringAllRegExp(String str) {
        if(StringUtils.isBlank(str)){
            return str;
        }
        return str.replace("\\", "\\\\").replace("*", "\\*")
                .replace("+", "\\+").replace("|", "\\|")
                .replace("{", "\\{").replace("}", "\\}")
                .replace("(", "\\(").replace(")", "\\)")
                .replace("^", "\\^").replace("$", "\\$")
                .replace("[", "\\[").replace("]", "\\]")
                .replace("?", "\\?").replace(",", "\\,")
                .replace(".", "\\.").replace("&", "\\&");
    }
    public static String escapeSlash(String str) {
        return str.replace("\\", "\\\\");
    }
    
    public static void main(String[] args) {
        boolean ma = BaseCrawlStrategy.matches(".*www\\.liepin\\.com/zhaopin/\\?init=-1\\&headckid=e5388d658e58fa6b\\&dqs=170020\\&fromSearchBtn=2\\&salary=20%2430\\&ckid=327055f163dd8ff5.*",
            "https://www.liepin.com/zhaopin/?init=-1&headckid=e5388d658e58fa6b&dqs=170020&fromSearchBtn=2&salary=20%2430&ckid=327055f163dd8ff5&degradeFlag=0&sfrom=click-pc_homepage-centre_searchbox-search_new&key=java&siTag=k_cloHQj_hyIn0SLM9IfRg~noobCPU2quldc_XoQ98zPg&d_sfrom=search_unknown&d_ckId=1954c2466d78cebe8f5218332506f129&d_curPage=0&d_pageSize=40&d_headId=de7719c53042e8428de8037c7336c057&curPage=22");
            System.out.println(ma);
        System.out.println(escapeSlash(makeQueryStringAllRegExp("www.liepin.com/zhaopin/?init=-1&headckid=e5388d658e58fa6b&dqs=170020&fromSearchBtn=2&salary=20%2430&ckid=327055f163dd8ff5")));
    }
}
