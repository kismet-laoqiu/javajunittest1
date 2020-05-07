package com.example.demo.interceptor;

import com.example.demo.dao.UserDao;
import com.example.demo.pojo.table.User;
import com.example.demo.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * @ClassName: AuthenticateInterceptor
 * @Description: token验证
 * @Author: 刘敬
 * @Date: 2019/6/9 19:58
 **/
public class AuthenticateInterceptor implements HandlerInterceptor {

    @Autowired
    UserDao userDao;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url=request.getRequestURI();
        String type=url.split("/")[1];
        String token = null;
        if(request.getCookies() != null){
            for(Cookie cookie : request.getCookies()){
                if(cookie.getName().equals(type+"_token")){
                    token = cookie.getValue();
                    break;
                }
            }
        }
        if(token != null ){
            String checkToken = TokenUtil.validateToken(token);
            if(checkToken.equals("F")||checkToken.equals("G")||checkToken.equals("S")){
                response.sendRedirect("/login?type=patient");
                return false;
            }else{
                long id=Long.parseLong(checkToken);
                Optional<User> u= userDao.findById(id);
                if(u.isPresent()){
                    User user=u.get();
                    HttpSession session=request.getSession();
                    session.setAttribute("userId",id);
                    session.setAttribute("userName",user.getName());
                    session.setAttribute("userAvatarUrl",user.getAvatarUrl());
                    return true;
                }else{
                    response.sendRedirect("/login?type=patient");
                    return false;
                }
            }
        }else{
            response.sendRedirect("/login?type=patient");
            return false;
        }
    }

}
