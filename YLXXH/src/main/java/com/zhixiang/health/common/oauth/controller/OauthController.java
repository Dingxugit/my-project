package com.zhixiang.health.common.oauth.controller;

import cn.hutool.core.date.DateUtil;
import com.zhixiang.health.common.http.result.HttpResult;
import com.zhixiang.health.common.oauth.bean.OAuthInfo;
import com.zhixiang.health.common.utils.UserUtil;
import com.zhixiang.health.modules.log.model.entity.LogLogin;
import com.zhixiang.health.modules.log.model.enums.LogLoginResultEnum;
import com.zhixiang.health.modules.log.service.ILogLoginService;
import com.zhixiang.health.modules.sys.service.ISysDeptService;
import com.zhixiang.health.modules.sys.service.ISysUserDeptService;
import com.zhixiang.health.modules.sys.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.Map;

/**
 * @Description: 登陆接口
 * @Auther: HeJiawang
 * @Date: 2020-04-23
 */
@Slf4j
@RestController
@RequestMapping("/oauth")
public class OauthController {

    @Resource
    private TokenEndpoint tokenEndpoint;

    @Resource
    private ISysUserService userService;

    @Resource
    private ILogLoginService logLoginService;

    @Resource
    private ISysDeptService deptService ;

    /**
     * 获取token
     * @param principal principal
     * @param parameters parameters
     * @return OAuthInfo
     */
    @PostMapping("/token")
    public HttpResult<OAuthInfo> postAccessTokenWithUserInfo(Principal principal, @RequestParam Map<String, String> parameters) {
        HttpResult<OAuthInfo> result = new HttpResult<>();

        try {
            OAuth2AccessToken accessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();
            OAuthInfo oAuthInfo = convertToken(accessToken);

            result.setResult(oAuthInfo).setIsSuccess(true).setCode(200).setMessage("登陆成功");
        } catch (InternalAuthenticationServiceException exception) {
            /**
             * 捕获用户账号不存在的异常
             */

            result.setCode(402).setIsSuccess(false).setMessage("用户名或密码错误");
        } catch (InvalidGrantException exception) {
            /**
             * 该异常捕获时，用户已输入正确的用户登陆名称
             * 1、捕获用户密码输入错误     detailMessage：用户名或密码错误
             * 2、捕获用户已被逻辑删除   detailMessage：用户已失效
             * 3、捕获用户被禁用    detailMessage：用户帐号已被锁定
             */

            result.setCode(402).setIsSuccess(false).setMessage(exception.getMessage());
        } catch (HttpRequestMethodNotSupportedException exception) {

            log.error("/oauth/token", exception);
            result.setCode(405).setIsSuccess(false).setMessage("未知错误，请联系管理员");
        } catch (Exception exception) {
            /**
             * 捕获客户端与服务端 OAuth2 认证信息不一致异常
             */

            result.setCode(500).setIsSuccess(false).setMessage("认证信息错误，请联系管理员");
        } finally {

            // 记录登录日志
            LogLogin logLogin = new LogLogin()
                    .setYear(DateUtil.thisYear())
                    .setMonth(DateUtil.thisMonth() + 1)
                    .setDay(DateUtil.thisDayOfMonth())
                    .setUserName(parameters.get("username"))
                    .setResult(result.getIsSuccess() ? LogLoginResultEnum.SUCCESS : LogLoginResultEnum.ERROR)
                    .setCode(result.getCode())
                    .setMessage(result.getMessage());

            logLoginService.save(logLogin);
        }

        return result;
    }

    /**
     * 个性化返回token信息
     * @param accessToken accessToken
     * @return token信息
     */
    private OAuthInfo convertToken(OAuth2AccessToken accessToken) {
        OAuthInfo oAuthInfo = new OAuthInfo();
        oAuthInfo.setExpiresIn(accessToken.getExpiresIn());
        oAuthInfo.setAccessToken(accessToken.getValue());
        if (accessToken.getRefreshToken() != null) {
            oAuthInfo.setRefreshToken(accessToken.getRefreshToken().getValue());
        }

        oAuthInfo.setRoleCodeList(UserUtil.getRoleCodeList(accessToken.getValue()));
        oAuthInfo.setUser(userService.getOauthUser(UserUtil.getUserName(accessToken.getValue())));
        oAuthInfo.setDept(deptService.getOauthUser(oAuthInfo.getUser().getId()));
        return oAuthInfo;
    }
}
