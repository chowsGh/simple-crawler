package top.clydezhou.lab.crawler.web.admin.common;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PageableDto <T> {
    //页面大小
    int size;
    //当前页
    int page;
    //总页数
    int totalPage;
    //总数
    int total;
    //分页数据
    List<T> data;
    
    public void setPageInfo(int page, int size, int total) {
        this.page = page;
        this.size = size;
        this.total = total;
        this.totalPage = (int)Math.ceil(total / (size * 1.0));
    }
}
