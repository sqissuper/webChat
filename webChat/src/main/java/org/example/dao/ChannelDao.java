package org.example.dao;

import org.example.exception.AppException;
import org.example.model.Channel;
import org.example.model.User;
import org.example.util.Util;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:ChannelDao
 * Package:org.example.dao
 * Description:
 *
 * @Author:HP
 * @date:2021/5/17 15:11
 */
public class ChannelDao {
    public static List<Channel> query() {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        //定义返回数据
        List<Channel> list = new ArrayList<>();
        try {
            //获取数据库连接
            c = Util.getConnection();
            //Connection 创建操作命令对象Statement
            String sql = "select * from channel";
            ps = c.prepareStatement(sql);
            //执行sql
            rs = ps.executeQuery();

            //处理结果集
            while(rs.next()) {
                Channel channel = new Channel();
                channel.setChannelId((rs.getInt("channelId")));
                channel.setChannelName(rs.getString("channelName"));
                list.add(channel);

            }
            return list;
        } catch (Exception e){
            throw new AppException("查询频道列表失败",e);
        } finally {
            Util.close(c,ps,rs);
        }
    }
}
