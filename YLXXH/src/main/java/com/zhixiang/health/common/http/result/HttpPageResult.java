package com.zhixiang.health.common.http.result;

import com.zhixiang.health.common.http.pagination.Page;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Description: 列表返回结果对象
 * @Auther: HeJiawang
 * @Date: 2018/6/29
 */
@Data
@Accessors(chain = true)
public class HttpPageResult<T> {

    private int code;

    private Boolean success = true;
    private List<T> data;
    private long total;
    private long current;
    private long pageSize;

    public HttpPageResult(Page<T> page) {
        this.code = 200;
        this.success = true;

        this.data = page.getRecords();
        this.total = page.getTotal();
        this.pageSize = page.getSize();
        this.total = page.getTotal();
    }

    public HttpPageResult() {
    }

}
