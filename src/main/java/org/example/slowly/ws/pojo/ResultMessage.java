package org.example.slowly.ws.pojo;

import lombok.Data;

/**
 * @author njx
 * @date 2024/11/11
 * @Description
 */
@Data
public class ResultMessage {
    private boolean isSystem;
    private String fromName;
    private Object message;// 如果是系统消息是数组
}
