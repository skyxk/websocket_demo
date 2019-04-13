package com.clt.websocket.websocket_spring;


import com.clt.websocket.HTTP.HttpClient;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.*;
import java.util.ArrayList;

import static com.clt.websocket.FTP.FtpUtils.downloadFile;
import static com.clt.websocket.FTP.FtpUtils.uploadFile;
import static com.clt.websocket.utils.dateUtil.getTimeShort;

public class MyWebSocketHandler extends TextWebSocketHandler{


//    public String ftpHost = "192.168.1.111";
//    public String ftpUserName = "pangjiaxiang";
//    public String ftpPassword = "123456";
//    public int ftpPort = 21;
//    public String ftpPath = "";
//    public String localPath = "D:\\";
//    public String fileName = "essSy12.txt";




//    private static final Logger logger = Logger.getLogger(MyWebSocketHandler.class);

    public static final ArrayList<WebSocketSession> users = new ArrayList<WebSocketSession>();

    private String webSocketId="";

    //初次链接成功执行
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        users.add(session);
        webSocketId = (String) session.getAttributes().get("WebSocketId");
        System.out.println("本次连接获取的id--"+webSocketId);
        sendMessageToUser(webSocketId,new TextMessage( "本次连接唯一ID:"+webSocketId));
    }

    // 接受消息处理消息
    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage)
            throws Exception {
        //sendMessageToUsers(new TextMessage(webSocketMessage.getPayload() + ""));
        String Message = webSocketMessage.getPayload() + "";

        if("001".equals(Message)){
            //签章方调用
            //调用向FTP服务器发送文件并附上webSocketId
            sendMessageToUser(webSocketId,new TextMessage( "开始上传文件:"+getTimeShort()));
            boolean result = uploadFile("D:\\",webSocketId+".pdf","D:\\pdf/测试文档.pdf");
            if(result){
                sendMessageToUser(webSocketId,new TextMessage( "文档上传完成！"+getTimeShort()));
                //FTP文件传输完成后 向签章服务器发送处理请求
                sendMessageToUser(webSocketId,new TextMessage( "向签章服务器发送处理请求！"+getTimeShort()));
                String url = "http://192.144.176.134:8080/analyze?fileName="+webSocketId+".pdf";
//                String url = "http://localhost:8080/analyze?fileName="+webSocketId+".pdf";
                String HttpResult = HttpClient.doGet(url);
                if("ESSOK".equals(HttpResult)){
                    //切图完成
                    sendMessageToUser(webSocketId,new TextMessage( "签章服务器已接受文件并完成切图"+getTimeShort()));
                    //发送消息，让页面进行跳转；
                    sendMessageToUser(webSocketId,new TextMessage( "002@"+webSocketId));
                }
            }
        }
        if("003".equals(Message)){
            sendMessageToUser(webSocketId,new TextMessage( "接受指令类型003,开始下载PDF文件:"+getTimeShort()));
            //去FTP服务器取出pdf文件，并进行切图
            boolean result1 = downloadFile("D:\\",webSocketId+".pdf","D:\\");
            if(result1){
                sendMessageToUser(webSocketId,new TextMessage( "文档保存成功！文档路径:D:\\"+
                        webSocketId+".pdf"+getTimeShort()));
            }else{
                sendMessageToUser(webSocketId,new TextMessage( "文档保存失败"+getTimeShort()));
            }
        }
//        sendMessageToUsers(new TextMessage(webSocketMessage.getPayload() + ""));
    }
    //连接出现异常时的处理方法
    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        if (webSocketSession.isOpen()) {
            webSocketSession.close();
        }
//        logger.debug("链接出错，关闭链接......");
        users.remove(webSocketSession);
    }
    //连接关闭后的处理方法
    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
//        logger.debug("链接关闭......" + closeStatus.toString());
        users.remove(webSocketSession);
    }

    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 给所有在线用户发送消息
     *
     * @param message
     */
    public void sendMessageToUsers(TextMessage message) {
        for (WebSocketSession user : users) {
//            System.out.println(user.getAttributes().get("userName"));
            try {
                if (user.isOpen()) {
                    user.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 给某个用户发送消息，模拟给admin发信息
     * @param WebSocketId
     * @param message
     */
    public void sendMessageToUser(String WebSocketId, TextMessage message) {
        for (WebSocketSession user : users) {
//            System.out.println("从session里面获取的id"+user.getAttributes().get("WebSocketId"));
            if (user.getAttributes().get("WebSocketId").equals(WebSocketId)) {
                try {
                    if (user.isOpen()) {
                        user.sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
}
