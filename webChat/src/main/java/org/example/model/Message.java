package org.example.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * ClassName:Message
 * Package:org.example.model
 * Description:
 *
 * @Author:HP
 * @date:2021/5/17 14:13
 */

@Setter
@Getter
@ToString
public class Message {
    private Integer messageId;
    private Integer userId;
    private Integer channelId;
    private String content;
    private String nickName;
    private java.util.Date sendTime;
}
