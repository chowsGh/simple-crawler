package top.clydezhou.lab.crawler.web.admin.common;

public class ResultUtils {
    
    /**
     * 创建一个默认实例，状态码是失败
     * @return
     */
    public static <T>  PageableResult<T> createPageableResult(Class<T> cls)
    {
        PageableResult<T>  r = new PageableResult<T>();
        return r;
    }
}
