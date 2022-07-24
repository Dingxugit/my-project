package com.zhixiang.health.common.oauth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhixiang.health.common.http.result.HttpResult;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Description: 授权拒绝处理器，覆盖默认的OAuth2AccessDeniedHandler
 * 当hasPermission() 返回false时触发
 *
 * @Auther: HeJiawang
 * @Date: 2020-04-24
 */
@Component
public class AccessDeniedHandler extends OAuth2AccessDeniedHandler {

    @Resource
    private ObjectMapper objectMapper;

    /**
     * 授权失败
     * @param request request
     * @param response response
     * @param authException authException
     * @throws IOException IOException
     * @throws ServletException ServletException
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException authException)
            throws IOException, ServletException {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.setStatus(401);

        HttpResult<String> result = new HttpResult<>();
        result.setCode(401)
                .setIsSuccess(false)
                .setMessage("授权失败");

        PrintWriter printWriter = response.getWriter();
        printWriter.append(objectMapper.writeValueAsString(result));
    }
}
