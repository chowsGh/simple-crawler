package top.clydezhou.lab.crawler.extend.strategy;

public interface ICrawlStrategy {

    /**
     * 是否可以爬取
     * @param link
     * @return
     */
    public boolean checkUrl(String link);

    /**
     * 格式化url 例如去掉hash
     * @param crawUrlStr
     * @return
     */
    public String formatUrl(String crawUrlStr);

    /**
     * 生成hash策略
     * @param link
     * @return
     */
    public String hashStrategy(String link);

    /**
     * 生成hashcode
     * @param link
     * @return
     */
    public String getHashCode(String link);

    /**
     * 分配优先级
     * @param link
     * @return
     */
    public int assignPriority(String link);
}
