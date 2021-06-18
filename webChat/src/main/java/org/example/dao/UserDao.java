package org.example.dao;

import org.example.exception.AppException;
import org.example.model.User;
import org.example.util.Util;

import java.sql.*;

/**
 * ClassName:UserDao
 * Package:org.example.dao
 * Description:
 *
 * @Author:HP
 * @date:2021/5/17 15:11
 */
public class UserDao {
    public static User queryByName(String name) {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        //定义返回数据
        User u = null;
        try {
            //获取数据库连接
            c = Util.getConnection();
            //Connection 创建操作命令对象Statement
            String sql = "select * from user where name=?";
            ps = c.prepareStatement(sql);
            ps.setString(1,name);
            //执行sql
            rs = ps.executeQuery();

            //处理结果集
            while(rs.next()) {
                u = new User();
                u.setUserId(rs.getInt("userId"));
                u.setName(name);
                u.setPassword(rs.getString("password"));
                u.setNickName(rs.getString("nickName"));
                u.setIconPath(rs.getString("iconPath"));
                u.setSignature(rs.getString("signature"));
                java.sql.Timestamp lastLogout = rs.getTimestamp("lastLogout");
                u.setLastLogout(new Date(lastLogout.getTime()));

            }
            return u;
        } catch (Exception e){
            throw new AppException("查询用户操作失败",e);
        } finally {
            Util.close(c,ps,rs);
        }


    }

    public static int updateLastLogout(Integer userId) {
        Connection c = null;
        PreparedStatement ps = null;
        try {
            c = Util.getConnection();
            String sql = "update user set lastLogout=? where userId=?";
            ps = c.prepareStatement(sql);
            ps.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            ps.setInt(2,userId);

            return ps.executeUpdate();
        } catch (Exception e) {
            throw new AppException("修改用户上次登录信息出错",e);
        } finally {
            Util.close(c,ps,null);
        }
    }
}
