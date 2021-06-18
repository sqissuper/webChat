package org.example.dao;

import org.example.exception.AppException;
import org.example.model.Channel;
import org.example.model.Message;
import org.example.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:MessageDao
 * Package:org.example.dao
 * Description:
 *
 * @Author:HP
 * @date:2021/5/17 15:11
 */
public class MessageDao {
    public static int insert(Message msg) {
        Connection c = null;
        PreparedStatement ps = null;
        try {
            c = Util.getConnection();
            String sql = "insert into message values(null,?,?,?,?)";
            ps = c.prepareStatement(sql);
            ps.setInt(1,msg.getUserId());
            ps.setInt(2,msg.getChannelId());
            ps.setString(3,msg.getContent());
            ps.setTimestamp(4,new Timestamp(System.currentTimeMillis()));
            return ps.executeUpdate();
        } catch (Exception e) {
            throw new AppException("消息保存出错",e);
        } finally {
            Util.close(c,ps);
        }
    }

    public static List<Message> queryByLastLogout(Integer userId) {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        //定义返回数据
        List<Message> list = new ArrayList<>();
        try {
            //获取数据库连接
            c = Util.getConnection();
            //Connection 创建操作命令对象Statement
            String sql = "select m.*,u.nickName from message m join user u on u.userId=m.userId where m.sendTime>(select lastLogout from user where userId=?)";
            ps = c.prepareStatement(sql);
            ps.setInt(1,userId);
            //执行sql
            rs = ps.executeQuery();
            //处理结果集
            while(rs.next()) {
                Message m = new Message();
                m.setUserId(userId);
                m.setContent(rs.getString("content"));
                m.setNickName(rs.getString("nickName"));
                m.setChannelId(rs.getInt("channelId"));
                list.add(m);
            }
            return list;
        } catch (Exception e){
            throw new AppException("查询用户消息列表失败",e);
        } finally {
            Util.close(c,ps,rs);
        }
    }
}
