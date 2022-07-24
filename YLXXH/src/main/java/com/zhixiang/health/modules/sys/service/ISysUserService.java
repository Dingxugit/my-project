package com.zhixiang.health.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhixiang.health.common.http.pagination.Page;
import com.zhixiang.health.common.model.bean.User;
import com.zhixiang.health.common.model.param.UserParam;
import com.zhixiang.health.common.model.result.ServiceResult;
import com.zhixiang.health.modules.sys.model.dto.SysUserDto;
import com.zhixiang.health.modules.sys.model.entity.SysUser;
import com.zhixiang.health.modules.sys.model.param.SysUserParam;
import com.zhixiang.health.modules.sys.model.param.SysUserPasswordParam;
import com.zhixiang.health.modules.sys.model.param.SysUserSearchParam;

/**
 * @Description: 系统用户管理 service
 * @Auther: HeJiawang
 * @Date: 2020-04-17
 */
public interface ISysUserService extends IService<SysUser> {

    /**
     * 分页获取用户基本信息以及用户部门、角色信息
     * @param param param
     * @return Page
     */
    Page<SysUserDto> page(SysUserSearchParam param);

    /**
     * 保存用户信息、角色关联信息、部门关联信息
     * @param param param
     * @return boolean
     */
    Boolean save(SysUserParam param);

    /**
     * 修改用户信息，并重置角色关联信息、部门关联信息
     * @param param param
     * @return boolean
     */
    Boolean updateById(SysUserParam param);

    /**
     * 停用用户
     * @param id 用户id
     * @return true
     */
    Boolean pauseById(Integer id);

    /**
     * 启用用户
     * @param id 用户id
     * @return true
     */
    Boolean resumeById(Integer id);

    /**
     * 校验用户登录名是否可用
     * @param user 用户id及登陆名称信息
     * @return true 登陆名称可用
     */
    Boolean checkUserName(SysUser user);

    /**
     * 修改密码
     * @param user 用户id、登陆密码
     * @return true
     */
    Boolean updatePassword(SysUser user);

    /**
     * 根据用户登陆名称，获取认证用户信息
     * @param userName 登陆名称
     * @return 认证信息
     */
    User getOauthUser(String userName);

    /**
     * 根据用户登陆名称，获取 UserParam 信息
     * @param userName 用户登陆名称
     * @return UserParam
     */
    UserParam getUserParam(String userName);

    /**
     * 校验并修改当前登录人的登录密码
     * @param passwordParam 登录密码信息
     * @return boolean
     */
    ServiceResult<Boolean> modifyPassword(SysUserPasswordParam passwordParam);

    /**
     * 校验原始密码是否正确
     * @param passwordParam passwordParam
     * @return Boolean
     */
    Boolean checkPassword(SysUserPasswordParam passwordParam);
}
