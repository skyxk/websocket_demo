package com.clt.websocket.websocket_spring;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.Session;



import java.util.Map;

import static com.sun.xml.internal.ws.util.JAXWSUtils.getUUID;

/**
 * WebSocket拦截器
 * @author 陈晓坤
 * @date 2019年1月16日 上午9:33:46
 * @version V1
 */
public class HandshakeInterceptor extends HttpSessionHandshakeInterceptor{
    Session session;
    // 初次握手访问前
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse serverHttpResponse,
                                   WebSocketHandler webSocketHandler, Map<String, Object> map)
            throws Exception {
        if (request instanceof ServletServerHttpRequest) {
            //获取Request
            HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
            //获取浏览器的sessionid
            String sessionId=servletRequest.getSession().getId();

//            String userName = (String)servletRequest.getSession().getAttribute("name");

//            System.out.println("获取session里面的name-------------------"+userName);

            //此map将存储到WebSocketSession中
            String webSocketId = getUUID();
            map.put("WebSocketId", webSocketId);
            servletRequest.getSession().setAttribute("WebSocketId", webSocketId);
        }
        return true;
    }

    /**
     * 初次握手访问后
     */
    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse,
                               WebSocketHandler webSocketHandler, Exception e) {

    }

}
