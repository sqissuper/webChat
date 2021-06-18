package org.example.servlet;

import org.example.dao.ChannelDao;
import org.example.model.Channel;
import org.example.model.Response;
import org.example.util.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * ClassName:ChannelServlet
 * Package:org.example.servlet
 * Description:
 *
 * @Author:HP
 * @date:2021/5/17 16:20
 */
@WebServlet("/channel")
public class ChannelServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        Response response = new Response();
        try{
            //查询channel
            List<Channel> list = ChannelDao.query();
            response.setOk(true);
            response.setData(list);
        } catch(Exception e) {
            e.printStackTrace();
            response.setReason(e.getMessage());
        }
        resp.getWriter().println(Util.serialize(response));
    }


}
