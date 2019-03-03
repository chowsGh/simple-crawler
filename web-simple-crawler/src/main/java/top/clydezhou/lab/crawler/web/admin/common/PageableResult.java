package top.clydezhou.lab.crawler.web.admin.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageableResult<T> extends SimpleResult {
    //分页数据
    PageableDto<T> result;
}
