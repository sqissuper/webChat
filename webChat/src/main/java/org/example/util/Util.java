package org.example.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.example.exception.AppException;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * ClassName:Util
 * Package:org.example.util
 * Description:
 *
 * @Author:HP
 * @date:2021/5/16 11:23
 */
public class Util {
    private static final ObjectMapper  M = new ObjectMapper();

    private static final MysqlDataSource source = new MysqlDataSource();

    static {
        //设置json序列化与反序列化的日期合适
        DateFormat df = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
        M.setDateFormat(df);

        source.setUrl("jdbc:mysql://localhost:3306/java_chatroom");
        source.setUser("root");
        source.setPassword("12345678");
        source.setUseSSL(false);
        source.setCharacterEncoding("UTF-8");
    }


    /**
     * json序列化：java对象转化成json字符串
     */
    public static String serialize(Object o) {
        try {
            return M.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new AppException("json序列化失败:" + o, e);
        }
    }

    /**
     * 反序列化json
     */
    public static <T> T deserialize(String s,Class<T> c) {
        try {
            return M.readValue(s,c);
        } catch (JsonProcessingException e) {
            //json字符串中的键，在class中没有找到对应的属性
            throw new AppException("json序列化失败", e);
        }
    }

    public static <T> T deserialize(InputStream is, Class<T> c) {
        try {
            return M.readValue(is,c);
        } catch (IOException e) {
            //json字符串中的键，在class中没有找到对应的属性
            throw new AppException("json序列化失败", e);
        }
    }

    /**
     * 获取数据库链接
     */
    public static Connection getConnection() {
        try {
            return source.getConnection();
        } catch (SQLException e) {
            throw new AppException("获取数据库连接失败",e);
        }
    }

    /**
     * 释放jdbc资源
     */
    public static void close (Connection c, Statement s, ResultSet r) {

        try {
            if(r != null) r.close();
            if(s != null) s.close();
            if(c != null) c.close();
        } catch (SQLException e) {
            throw new AppException("释放数据库资源失败",e);
        }

    }
    public static void close(Connection c,Statement s) {
        close(c,s,null);
    }

//    public static void main(String[] args) {
//        //测试序列化
//        Map<String,Object> map = new HashMap<>();
//        map.put("ok",true);
//        map.put("d",new Date());
//        System.out.println(serialize(map));
//
//
//        System.out.println(getConnection());
//    }

}
