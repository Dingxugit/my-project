package com.zhixiang.health.common.http.config;

import com.zhixiang.health.common.constant.CommonConstant;
import com.zhixiang.health.common.model.param.UserParam;
import com.zhixiang.health.common.utils.UserUtil;
import com.zhixiang.health.modules.sys.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * 将token转换为 UserParam
 *
 * @author HeJiawang
 * @since 2020-05-29
 */
@Slf4j
@Configuration
public class TokenArgumentResolverConfig implements HandlerMethodArgumentResolver {

    @Resource
    private CacheManager cacheManager;

    @Resource
    private ISysUserService sysUserService;

    /**
     * 当api接口参数中有 UserParam 时，转换参数
     * @param methodParameter methodParameter
     * @return boolean
     */
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().equals(UserParam.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {

        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        String token = UserUtil.getToken(request);
        if (StringUtils.isBlank(token)) return null;

        Optional<UserParam> optional = Optional.ofNullable(
                cacheManager.getCache(CommonConstant.TOKEN_USER_PARAM).get(token, UserParam.class)
        );

        if(optional.isPresent()) return optional.get(); // 如果缓存中有，直接返回缓存中的UserParam信息
        else return optional.orElseGet(() -> renderByToken(request, token)); // 缓存中没有信息，获取信息并填到缓存中
    }

    private UserParam renderByToken(HttpServletRequest request, String token) {
        String userName = UserUtil.getUserName(request);

        UserParam userParam = sysUserService.getUserParam(userName);
        cacheManager.getCache(CommonConstant.TOKEN_USER_PARAM).put(token, userParam);

        return userParam;
    }
}
