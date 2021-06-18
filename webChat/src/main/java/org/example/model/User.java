package org.example.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName:User
 * Package:org.example.model
 * Description:
 *
 * @Author:HP
 * @date:2021/5/16 11:50
 */

//数据库使用，前后端ajax，session保存时基于对象和二进制数据转化（实现序列化接口）
@Setter
@Getter
@ToString
public class User extends Response implements Serializable {

    private  static final Long serialVersionUID = 1L;

    private Integer userId;
    private String name;
    private String password;
    private String nickName;
    private String iconPath;
    private String signature;
    private java.util.Date lastLogout;

}
