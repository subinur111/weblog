package com.subinuer.weblog.common.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.util.List;
import java.util.Objects;

@Data
public class CatListResponse<T> extends Response<List<T>> {
    // private boolean success = true;          //是否成功,,默认值为成功
    // private String message;           // 内容
    // private String errorCode;           // 错误码
    // private T data;                   // 响应数据，泛类

    /**
     * 总记录数
     */
    private long total = 0L;
    /**
     * 每页展示数量
     */
    private long size = 10L;
    /**
     * 当前页码
     */
    private long current = 1L;
    /**
     * 总页数
     */
    private long pages = 0L;

    public static <T> CatListResponse<T> success(IPage page, List<T> data){
        CatListResponse<T> response = new CatListResponse<>();

        if(Objects.nonNull(page)){
            response.setSuccess(true);
            response.setTotal(page.getTotal());
            response.setSize(page.getSize());
            response.setCurrent(page.getCurrent());
            response.setData(data);
        }
        return response;
    }

}
