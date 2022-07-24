package com.zhixiang.health.modules.sys.mapper;

import com.zhixiang.health.modules.sys.model.dto.SysButtonDto;
import com.zhixiang.health.modules.sys.model.entity.SysButton;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 按钮及访问权限配置 Mapper 接口
 * </p>
 *
 * @author HeJiawang
 * @since 2020-06-24
 */
public interface SysButtonMapper extends BaseMapper<SysButton> {

    /**
     * 根据上级资源id，获取按钮权限信息
     * @param parentId 上级资源id
     * @return 按钮权限信息
     */
    List<SysButtonDto> listByParentId(@Param("parentId") Integer parentId);

    /**
     * 根据用户登录名获取访问权限
     * @param userName 用户登录名
     * @return 访问权限
     */
    List<SysButton> listByUserName(@Param("userName") String userName);

    /**
     * 获取当前登录人授权的权限标志
     * @param userId 当前登录人id
     * @return 权限标志list
     */
    List<String> listPermissionByUserId(@Param("userId") Integer userId);
}
