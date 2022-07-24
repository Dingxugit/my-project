package com.zhixiang.health.common.oauth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhixiang.health.common.http.result.HttpResult;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description: token异常、过期的提示
 * @Auther: HeJiawang
 * @Date: 2020-04-24
 */
public class AuthExceptionHandler implements AuthenticationEntryPoint {

    /**
     * 个性化token异常、过期的提示
     * @param httpServletRequest HttpServletRequest
     * @param httpServletResponse HttpServletResponse
     * @param e AuthenticationException
     * @throws IOException IOException
     * @throws ServletException ServletException
     */
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException, ServletException {

        HttpResult<Void> result = new HttpResult<>();
        result.setCode(401).setMessage("token异常、过期").setIsSuccess(false);

        httpServletResponse.setContentType("application/json");
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(httpServletResponse.getOutputStream(), result);
        } catch (Exception exception) {
            throw new ServletException();
        }
    }
}
