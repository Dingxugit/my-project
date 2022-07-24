package com.zhixiang.health.modules.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhixiang.health.common.model.bean.User;
import com.zhixiang.health.common.model.enums.UsedStateEnum;
import com.zhixiang.health.common.model.param.UserParam;
import com.zhixiang.health.modules.sys.model.dto.SysUserDto;
import com.zhixiang.health.modules.sys.model.entity.SysUser;
import com.zhixiang.health.modules.sys.model.param.SysUserSearchParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description: 系统用户管理 mapper
 * @Auther: HeJiawang
 * @Date: 2020-04-17
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 获取分页数据
     * @param param param
     * @return list
     */
    List<SysUserDto> pageList(SysUserSearchParam param);

    /**
     * 分页条数
     * @param param param
     * @return 总条数
     */
    long pageTotal(SysUserSearchParam param);

    /**
     * 更新用户启用状态
     * @param id 用户id
     * @param usedState 状态
     * @return int
     */
    int updateUsedStateById(@Param("id") Integer id, @Param("usedState") UsedStateEnum usedState);

    /**
     * 校验用户登录名是否可用
     * @param user 用户id及登陆名称信息
     * @return <= 0 登陆名称可用
     */
    int checkUserName(SysUser user);

    /**
     * 修改密码
     * @param user 用户id、登陆密码
     * @return true
     */
    int updatePassword(SysUser user);

    /**
     * 根据用户登陆名称，获取认证用户信息
     * @param userName 登陆名称
     * @return 认证信息
     */
    User getOauthUser(@Param("userName") String userName);

    /**
     * 根据用户登陆名称，获取 UserParam 信息
     * @param userName 用户登陆名称
     * @return UserParam
     */
    UserParam getUserParam(@Param("userName") String userName);
}
