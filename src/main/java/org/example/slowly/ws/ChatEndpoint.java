package org.example.slowly.ws;


import com.alibaba.fastjson.JSON;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import org.example.slowly.config.GetHttpSessionConfig;
import org.example.slowly.utils.MessageUtils;
import org.example.slowly.ws.pojo.Message;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


@ServerEndpoint(value = "/chat", configurator = GetHttpSessionConfig.class)
@Component
public class ChatEndpoint {
    private static final Map<String, Session> onlineUsers = new ConcurrentHashMap<>();
    private HttpSession session;
    /**
     * 建立websocket连接后，被调用
     * @param session
     */
    @OnOpen
    public void onOpen(Session session, EndpointConfig config){
        //1、将session进行保存
        this.session = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        String user = (String) this.session.getAttribute("currentUser");
        onlineUsers.put(user,session);
        //2、广播消息
        String message = MessageUtils.getMessage(true,null, getFriends());
        broadcastAllUsers(message);



    }
    private Set<String> getFriends() {
        return onlineUsers.keySet();
    }

    private void broadcastAllUsers(String message) {
        try {
            Set<Map.Entry<String, Session>> entries = onlineUsers.entrySet();

            for (Map.Entry<String, Session> entry : entries) {
                // 获取到所有用户对应的 session 对象
                Session session = entry.getValue();

                // 使用 getBasicRemote() 方法发送同步消息
                session.getBasicRemote().sendText(message);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }



    @OnMessage
    public void OnMessage(String message) throws IOException {
        try {
            // 将消息推送给指定的用户
            Message msg = JSON.parseObject(message, Message.class);

            // 获取消息接收方的用户名
            String toName = msg.getToName();
            String tempMessage = msg.getMessage();

            // 获取消息接收方用户对象的 session 对象
            Session session = onlineUsers.get(toName);
            String currentUser = (String) this.session.getAttribute("currentUser");
            String messageToSend = MessageUtils.getMessage(false, currentUser, tempMessage);

            session.getBasicRemote().sendText(messageToSend);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    @OnClose
    public void OnClose(Session session) throws IOException {
        // 1.从 onlineUsers 中删除当前用户的 session 对象，表示当前用户已下线
        String user = (String) this.session.getAttribute("currentUser");
        if (user != null) {
            Session remove = onlineUsers.remove(user);
            if (remove != null) {
                remove.close();
            }

            session.close();
        }

        // 2.通知其他用户，当前用户已下线
        // 注意：不是发送类似于 xxx 已下线的消息，而是向在线用户重新发送一次当前在线的所有用户
        String message = MessageUtils.getMessage(true, null, getFriends());
        broadcastAllUsers(message);
    }
}
