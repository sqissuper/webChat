package org.example.servlet;

import org.example.dao.UserDao;
import org.example.exception.AppException;
import org.example.model.User;
import org.example.util.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * ClassName:LoginServlet
 * Package:org.example.servlet
 * Description:
 *
 * @Author:HP
 * @date:2021/5/17 14:43
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    //检测登录状态接口
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        User user = new User();
        //获取当前请求的session，并且获取用户信息
        HttpSession session = req.getSession(false);
        if(session != null) {
            User get = (User)session.getAttribute("User");
            if(get != null) {
                //获取到用户信息
                user = get;
                user.setOk(true);

                resp.getWriter().println(Util.serialize(user));
                return;
            }
        }
        user.setOk(false);
        user.setReason("用户未登录");
        //3.返回响应数据:从对象获取到数据流，打印输出
        resp.getWriter().println(Util.serialize(user));
    }

    //登录接口
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        //响应的数据
        User user = new User();
        try{
            //1.解析请求数据:根据文档使用反序列化操作
            User input = Util.deserialize(req.getInputStream(),User.class);
            //2.业务处理:数据库验证账号密码。创建session
            //根据账号查询
            User query = UserDao.queryByName(input.getName());
            if(query == null) {
                throw new AppException("用户不存在");
            }
            if(!query.getPassword().equals(input.getPassword())) {
                throw new AppException("账号或密码错误");
            }
            //账号密码正确
            HttpSession session = req.getSession();
            session.setAttribute("user",query);

            user = query;
            //构造操作成功的正常返回
            user.setOk(true);
        } catch(Exception e) {
            e.printStackTrace();
            //构造操作失败的错误信息
            user.setOk(false);
            //自定义异常，自己抛
            if(e instanceof AppException) {
                user.setReason(e.getMessage());
            } else {
                user.setReason("未知的错误，请联系管理员");
            }
        }
        //3.返回响应数据:从对象获取到数据流，打印输出
        resp.getWriter().println(Util.serialize(user));
    }
}
