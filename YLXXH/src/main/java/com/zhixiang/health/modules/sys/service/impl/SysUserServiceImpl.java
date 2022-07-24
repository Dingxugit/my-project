package com.zhixiang.health.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhixiang.health.common.http.pagination.Page;
import com.zhixiang.health.common.model.bean.User;
import com.zhixiang.health.common.model.enums.UsedStateEnum;
import com.zhixiang.health.common.model.param.UserParam;
import com.zhixiang.health.common.model.result.ServiceResult;
import com.zhixiang.health.common.oauth.bean.SecurityConstants;
import com.zhixiang.health.modules.sys.mapper.SysUserMapper;
import com.zhixiang.health.modules.sys.model.dto.SysUserDto;
import com.zhixiang.health.modules.sys.model.entity.SysUser;
import com.zhixiang.health.modules.sys.model.param.SysUserParam;
import com.zhixiang.health.modules.sys.model.param.SysUserPasswordParam;
import com.zhixiang.health.modules.sys.model.param.SysUserSearchParam;
import com.zhixiang.health.modules.sys.service.ISysUserDeptService;
import com.zhixiang.health.modules.sys.service.ISysUserRoleService;
import com.zhixiang.health.modules.sys.service.ISysUserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;

/**
 * @Description: 系统用户管理 service impl
 * @Auther: HeJiawang
 * @Date: 2020-04-17
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Resource
    private SysUserMapper mapper;

    @Resource
    private ISysUserRoleService sysUserRoleService;

    @Resource
    private ISysUserDeptService sysUserDeptService;

    @Resource
    private TokenStore tokenStore;

    /**
     * 用户信息密码加密规则
     */
    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();

    /**
     * 分页获取用户基本信息以及用户部门、角色信息
     * @param param param
     * @return Page
     */
    @Override
    public Page<SysUserDto> page(SysUserSearchParam param) {
        param.renderLimitStart();

        Page<SysUserDto> pageResult = new Page<>();
        pageResult.setRecords(mapper.pageList(param))
                .setTotal(mapper.pageTotal(param));

        return pageResult;
    }

    /**
     * 保存用户信息、角色关联信息、部门关联信息
     * @param param param
     * @return boolean
     */
    @Transactional
    @Override
    public Boolean save(SysUserParam param) {
        SysUser sysUser = param.convertToEntity();
        sysUser.setPassword(ENCODER.encode(param.getPassword()));
        Boolean result = super.save(sysUser);

        return result
                && sysUserRoleService.merge(sysUser.getId(), param.getRoleIdList())
                && sysUserDeptService.merge(sysUser.getId(), param.getDeptIdList());
    }

    /**
     * 修改用户信息，并重置角色关联信息、部门关联信息
     * @param param param
     * @return boolean
     */
    @Transactional
    @Override
    public Boolean updateById(SysUserParam param) {
        // 修改用户时，将以登录的用户强制退出，避免修改角色后，用户权限没有改变
        Collection<OAuth2AccessToken> tokens = tokenStore.findTokensByClientIdAndUserName(SecurityConstants.CLIENT_ID, param.getUserName());
        tokens.forEach(token -> tokenStore.removeAccessToken(token) );

        SysUser sysUser = param.convertToEntity();
        Boolean result = super.updateById(sysUser);

        return result
                && sysUserRoleService.merge(sysUser.getId(), param.getRoleIdList())
                && sysUserDeptService.merge(sysUser.getId(), param.getDeptIdList());
    }

    /**
     * 删除用户信息，以及用户与角色、部门的关联信息
     * @param id 用户id
     * @return true
     */
    @Transactional
    @Override
    public boolean removeById(Serializable id) {
        Integer userId = Integer.valueOf(id.toString());

        return super.removeById(id)
                && sysUserDeptService.removeByUserId(userId)
                && sysUserRoleService.removeByUserId(userId);
    }

    /**
     * 停用用户
     * @param id 用户id
     * @return true
     */
    @Override
    public Boolean pauseById(Integer id) {
        return mapper.updateUsedStateById(id, UsedStateEnum.NO) >= 0;
    }

    /**
     * 启用用户
     * @param id 用户id
     * @return true
     */
    @Override
    public Boolean resumeById(Integer id) {
        return mapper.updateUsedStateById(id, UsedStateEnum.YES) >= 0;
    }

    /**
     * 校验用户登录名是否可用
     * @param user 用户id及登陆名称信息
     * @return true 登陆名称可用
     */
    @Override
    public Boolean checkUserName(SysUser user) {
        return mapper.checkUserName(user) <= 0;
    }

    /**
     * 修改密码
     * @param user 用户id、登陆密码
     * @return true
     */
    @Override
    public Boolean updatePassword(SysUser user) {
        user.setPassword(ENCODER.encode(user.getPassword()));

        return mapper.updatePassword(user) >= 0;
    }

    /**
     * 根据用户登陆名称，获取认证用户信息
     * @param userName 登陆名称
     * @return 认证信息
     */
    @Override
    public User getOauthUser(String userName) {
        return mapper.getOauthUser(userName);
    }

    /**
     * 根据用户登陆名称，获取 UserParam 信息
     * @param userName 用户登陆名称
     * @return UserParam
     */
    @Override
    public UserParam getUserParam(String userName) {
        return mapper.getUserParam(userName);
    }

    /**
     * 校验并修改当前登录人的登录密码
     * @param passwordParam 登录密码信息
     * @return boolean
     */
    @Override
    public ServiceResult<Boolean> modifyPassword(SysUserPasswordParam passwordParam) {
        ServiceResult<Boolean> result = new ServiceResult<>();

        SysUser user = super.getById(passwordParam.getId());
        if (!ENCODER.matches(passwordParam.getOldPassword(), user.getPassword())) {
            return result.error("原始密码输入错误！");
        }
        if (!passwordParam.getCheckPassword().equals(passwordParam.getNewPassword())) {
            return result.error("确认密码与新密码不一致！");
        }

        user.setPassword(ENCODER.encode(passwordParam.getNewPassword()));
        return new ServiceResult<>(mapper.updatePassword(user) >= 0 );
    }

    /**
     * 校验原始密码是否正确
     * @param passwordParam passwordParam
     * @return Boolean
     */
    @Override
    public Boolean checkPassword(SysUserPasswordParam passwordParam) {
        SysUser user = super.getById(passwordParam.getId());
        return ENCODER.matches(passwordParam.getOldPassword(), user.getPassword());
    }
}
