package org.example.model;

/**
 * ClassName:MessageCenter
 * Package:org.example.model
 * Description:
 *
 * @Author:HP
 * @date:2021/5/17 20:36
 */

import javax.websocket.Session;
import java.io.IOException;
import java.util.Enumeration;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 保存websocket所有信息
 *
 */
public class MessageCenter {

    /**
     * 支持线程安全的map结构，并且满足高并发
     */
    private static final ConcurrentHashMap<Integer, Session> client = new ConcurrentHashMap<>();

    /**
     * 阻塞队列：用来存放消息，接收的客户端消息就放在里边
     * 再启动一个线程，不停的拉取队列中的消息，发送（发送和接收并发并行执行，分离）
     * @param userId
     * @param session
     */
    private static BlockingQueue<String> queue = new LinkedBlockingQueue<>();

    private static MessageCenter center;

    private MessageCenter(){}
    public static MessageCenter getInstance(){
        if(center == null){//单例模式，以后可以改造为双重校验锁的单例模式
            center = new MessageCenter();
            new Thread(()->{//启动一个线程，不停的从阻塞队列拿数据
                try {
                    String message = queue.take();//获取数据，如果队列为空，阻塞等待
                    sendMessage(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        return center;
    }

    /**
     * 不直接发消息，先把消息存放在队列中，由另一个线程去发
     */
    public void addMessage(String message){
        queue.add(message);
    }

    //建立连接时，添加用户id和客户端session，保存起来
    public static void addOnlineUser(Integer userId,Session session) {
        client.put(userId,session);
    }

    //关闭连接和出错时，删除客户端session
    public static void delOnlineUser(Integer userId){
        client.remove(userId);
    }

    /**
     * 接收到某用户的消息时，转发到所有客户端:
     * 存在一个消息转发所有客户端，存在性能问题
     * 如果接收到的信息数量m很多，同时在线的用户数量n也很多
     * 要转发的次数就是m*n，每个接收消息都是一个线程，都要
     * 等待WebSocket中onMessage回调方法执行完，性能差
     */
    public static void sendMessage(String message){
        try {
            Enumeration<Session> e = client.elements();
            while(e.hasMoreElements()){
                Session session = e.nextElement();
                session.getBasicRemote().sendText(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
