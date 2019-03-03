package top.clydezhou.lab.crawler.web.admin.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SimpleResult {
    //状态
    int status = RequestStatus.FAIL;
    //信息
    String info;
    //错误信息
    String errorInfo;
    
    public void success() {
        status = RequestStatus.SUCCESS;
    }
}
