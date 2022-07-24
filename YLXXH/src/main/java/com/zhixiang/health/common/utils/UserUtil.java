package com.zhixiang.health.common.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhixiang.health.common.oauth.bean.SecurityConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 获取当前登陆用户信息工具类
 * @author HeJiawang
 * @since 2020-05-29
 */
@Slf4j
public class UserUtil {

    /**
     * thread local userName
     */
    private static final ThreadLocal<String> THREAD_LOCAL_USERNAME = new ThreadLocal<>();

    /**
     * thread local roleCode
     */
    private static final ThreadLocal<List<String>> THREAD_LOCAL_ROLECODE = new ThreadLocal<>();

    /**
     * 获取请求中token
     * @param request request
     * @return token
     */
    public static String getToken(HttpServletRequest request) {
        String authorization = request.getHeader(SecurityConstants.REQ_HEADER);
        authorization = StrUtil.isNotEmpty(authorization) ? authorization : StrUtil.EMPTY;

        return StrUtil.subAfter(authorization, SecurityConstants.TOKEN_SPLIT, false);
    }

    /**
     * 获取请求中的用户登录名称
     * @param request request
     * @return 用户登录名称
     */
    public static String getUserName(HttpServletRequest request) {
        String token = UserUtil.getToken(request);
        if (StrUtil.isEmpty(token)) return StrUtil.EMPTY;

        return getUserName(token);
    }

    /**
     * 获取token中的用户登录名称
     * @param token token
     * @return 用户登录名称
     */
    public static String getUserName(String token) {
        String userName = StrUtil.EMPTY;

        try {
            Jwt jwt = JwtHelper.decode(token);
            JSONObject jsonObject = JSON.parseObject(jwt.getClaims());

            userName = jsonObject.getString("user_name");
        } catch (Exception e) {
            log.error("UserUtil.getUserName : 获取请求中的用户登录名称异常", e);
        }

        return userName;
    }

    /**
     * 获取请求中的用户角色list
     * @param request request
     * @return 用户角色list
     */
    public static List<String> getRoleCodeList(HttpServletRequest request) {
        String token = UserUtil.getToken(request);
        if (StrUtil.isEmpty(token)) return CollUtil.newArrayList();

        return getRoleCodeList(token);
    }

    /**
     * 获取token中的用户角色list
     * @param token token
     * @return 用户角色list
     */
    public static List<String> getRoleCodeList(String token) {
        List<String> roleCodeList = CollUtil.newArrayList();

        try {
            Jwt jwt = JwtHelper.decode(token);
            JSONObject jsonObject = JSON.parseObject(jwt.getClaims());

            roleCodeList = jsonObject.getJSONArray("authorities").toJavaList(String.class);
        } catch (Exception e) {
            log.error("UserUtil.getUserName : 获取请求中的用户登录名称异常", e);
        }

        return roleCodeList;
    }

    /**
     * 设置threadLocal中的用户名
     * @param userName 用户名
     */
    public static void setUserName(String userName) {
        THREAD_LOCAL_USERNAME.set(userName);
    }

    /**
     * 从threadLocal 获取用户名
     * @return 用户名
     */
    public static String getUserName() {
        return THREAD_LOCAL_USERNAME.get();
    }

    /**
     * 清空threadLocal中的用户名
     */
    public static void clearUserName() {
        THREAD_LOCAL_USERNAME.remove();
    }

    /**
     * 设置threadLocal中的角色集合
     * @param roleCodeList 角色集合
     */
    public static void setRoleCodeList(List<String> roleCodeList) {
        THREAD_LOCAL_ROLECODE.set(roleCodeList);
    }

    /**
     * 从threadLocal 获取角色集合
     * @return 角色集合
     */
    public static List<String> getRoleCodeList() {
        return THREAD_LOCAL_ROLECODE.get();
    }

    /**
     * 清空threadLocal中的角色集合
     */
    public static void clearRoleCodeList() {
        THREAD_LOCAL_ROLECODE.remove();
    }
}
