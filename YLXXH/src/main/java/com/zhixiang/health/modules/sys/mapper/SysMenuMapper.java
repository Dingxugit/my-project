package com.zhixiang.health.modules.sys.mapper;

import com.zhixiang.health.modules.sys.model.dto.SysMenuDto;
import com.zhixiang.health.modules.sys.model.dto.SysMenuTreeDto;
import com.zhixiang.health.modules.sys.model.entity.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 菜单配置 Mapper 接口
 * </p>
 *
 * @author HeJiawang
 * @since 2020-06-24
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 获取菜单树
     * @return 菜单树
     */
    List<SysMenuTreeDto> treeData();

    /**
     * 获取用户所有的主页菜单
     * @param userId 用户id
     * @return 主页菜单
     */
    List<SysMenuDto> listIndexByUserId(@Param("userId") Integer userId);
}
