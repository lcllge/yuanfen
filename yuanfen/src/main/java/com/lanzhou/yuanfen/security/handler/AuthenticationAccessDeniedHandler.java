package com.lanzhou.yuanfen.security.handler;

import com.alibaba.fastjson.JSON;
import com.lanzhou.yuanfen.response.ServerResponseResult;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author LanZhou
 */
@Configuration
public class AuthenticationAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        if (!isAjaxRequest(request)) {
            request.setAttribute(WebAttributes.ACCESS_DENIED_403, e);
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            request.getRequestDispatcher("/error/403").forward(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter out = response.getWriter();
            ServerResponseResult unauthorized = ServerResponseResult.unauthorized("权限不足，请联系管理员!");
            out.write(JSON.toJSONString(unauthorized));
            out.flush();
            out.close();
        }
    }


    /**
     * 判断请求是否是Ajax请求
     *
     * @param request
     * @return
     */
    private static boolean isAjaxRequest(HttpServletRequest request) {
        String header = request.getHeader("X-Requested-With");
        return !StringUtils.isEmpty(header) && "XMLHttpRequest".equals(header);
    }

}
