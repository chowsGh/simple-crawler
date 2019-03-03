package top.clydezhou.lab.crawler.web.admin.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DefaultResult <T> extends SimpleResult {
    T result;
}
