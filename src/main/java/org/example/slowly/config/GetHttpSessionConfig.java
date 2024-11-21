package org.example.slowly.config;

import jakarta.servlet.http.HttpSession;
import jakarta.websocket.HandshakeResponse;
import jakarta.websocket.server.HandshakeRequest;
import jakarta.websocket.server.ServerEndpointConfig;

/**
 * 获取HttpSession，这样的话，ChatEndpoint类就能操作HttpSession
 */
public class GetHttpSessionConfig extends ServerEndpointConfig.Configurator {

    /**
     * @param serverEndpointConfig:配置对象
     * @param request: 握手的请求对象
     * @param response：握手的响应对象
     */
    @Override
    public void modifyHandshake(ServerEndpointConfig serverEndpointConfig, HandshakeRequest request, HandshakeResponse response) {
        // 获取 HttpSession 对象
        HttpSession httpSession = (HttpSession) request.getHttpSession();

        // 将 httpSession 对象保存起来，存到 ServerEndpointConfig 对象中
        // 在 ChatEndpoint 类的 onOpen 方法就能通过 EndpointConfig 对象获取在这里存入的数据
        if (httpSession != null) {
            serverEndpointConfig.getUserProperties().put(HttpSession.class.getName(), httpSession);
        } else {
            // 处理 httpSession 为 null 的情况，可能是记录日志或者抛出异常
            System.out.println("httpSession is null, cannot add to user properties.");
        }
    }

}