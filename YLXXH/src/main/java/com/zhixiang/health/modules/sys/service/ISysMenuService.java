package com.zhixiang.health.modules.sys.service;

import com.zhixiang.health.modules.sys.model.dto.SysMenuTreeDto;
import com.zhixiang.health.modules.sys.model.entity.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhixiang.health.modules.sys.model.param.SysMenuSaveParam;

import java.util.List;

/**
 * <p>
 * 菜单配置 服务类
 * </p>
 *
 * @author HeJiawang
 * @since 2020-06-24
 */
public interface ISysMenuService extends IService<SysMenu> {

    /**
     * 保存菜单配置信息，
     * 保存菜单资源信息
     * @param saveParam saveParam
     * @return boolean
     */
    boolean save(SysMenuSaveParam saveParam);

    /**
     * 修改实体信息
     * @param param 实体信息
     * @return Boolean
     */
    boolean updateById (SysMenuSaveParam param);

    /**
     * 获取菜单树
     * @param parentId 上级资源id（sys_resource.id）
     * @return 菜单树
     */
    List<SysMenuTreeDto> tree(Integer parentId);
}
