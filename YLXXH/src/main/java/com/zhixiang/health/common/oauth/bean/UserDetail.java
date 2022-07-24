package com.zhixiang.health.common.oauth.bean;

import cn.hutool.core.collection.CollectionUtil;
import com.zhixiang.health.common.model.bean.Role;
import com.zhixiang.health.common.model.bean.User;
import com.zhixiang.health.common.model.enums.DeleteStateEnum;
import com.zhixiang.health.common.model.enums.UsedStateEnum;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * @Description: UserDetails
 * @Auther: HeJiawang
 * @Date: 2020-04-23
 */
public class UserDetail implements UserDetails {

    /**
     * 登陆名称
     */
    private String userName;

    /**
     * 登陆密码
     */
    private String password;

    /**
     * 删除状态 1已删除 0未删除
     */
    protected DeleteStateEnum deleteState;

    /**
     * 启用状态 1启用 0禁用
     */
    private UsedStateEnum usedState;

    /**
     * 角色集合
     */
    private List<Role> roleList;

    /**
     * 构造函数
     * @param user 登陆用户信息
     */
    public UserDetail(User user) {
        this.userName = user.getUserName();
        this.password = user.getPassword();
        this.deleteState = user.getDeleteState();
        this.usedState = user.getUsedState();
        this.roleList = user.getRoleList();
    }

    /**
     * 获取用户角色信息
     * @return 角色信息
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorityList = CollectionUtil.newArrayList();
        roleList.forEach( role -> authorityList.add(new SimpleGrantedAuthority(role.getCode())) );

        return authorityList;
    }

    /**
     * 获取用户登陆密码
     * @return 登陆密码
     */
    @Override
    public String getPassword() {
        return this.password;
    }

    /**
     * 获取用户登陆名称
     * @return 登陆名称
     */
    @Override
    public String getUsername() {
        return this.userName;
    }

    /**
     * 账户是否失效
     * @return true 未失效
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 账户是否启用
     * @return true 未启用
     */
    @Override
    public boolean isAccountNonLocked() {
        return this.usedState == UsedStateEnum.YES;
    }

    /**
     * 账户认证是否失效
     * @return true 未失效
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 账户是否可用
     * @return true 可用
     */
    @Override
    public boolean isEnabled() {
        return this.deleteState == DeleteStateEnum.NO;
    }
}
