package org.example.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * ClassName:Response
 * Package:org.example.model
 * Description:
 *
 * @Author:HP
 * @date:2021/5/17 14:23
 */
@Getter
@Setter
@ToString
public class Response {
    //当前接口响应是否操作成功
    private boolean ok;
    private String reason;
    private Object data;
}
