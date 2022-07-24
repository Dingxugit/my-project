package com.zhixiang.health.modules.sys.service.impl;

import com.zhixiang.health.common.utils.TreeUtil;
import com.zhixiang.health.modules.sys.model.dto.SysMenuTreeDto;
import com.zhixiang.health.modules.sys.model.entity.SysMenu;
import com.zhixiang.health.modules.sys.mapper.SysMenuMapper;
import com.zhixiang.health.modules.sys.model.param.SysMenuSaveParam;
import com.zhixiang.health.modules.sys.service.ISysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhixiang.health.modules.sys.service.ISysResourceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 菜单配置 服务实现类
 * </p>
 *
 * @author HeJiawang
 * @since 2020-06-24
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    @Resource
    private SysMenuMapper mapper;

    @Resource
    private ISysResourceService sysResourceService;

    /**
     * 保存菜单配置信息，
     * 保存菜单资源信息
     * @param saveParam saveParam
     * @return boolean
     */
    @Transactional
    @Override
    public boolean save(SysMenuSaveParam saveParam) {
        SysMenu menu = saveParam.convertToEntity();
        super.save(menu);

        return sysResourceService.save(
                saveParam.convertToResource().setResourceId(menu.getId())
        );
    }

    /**
     * 修改实体信息
     * @param param 实体信息
     * @return Boolean
     */
    @Transactional
    @Override
    public boolean updateById(SysMenuSaveParam param) {
        SysMenu menu = param.convertToEntity();

        return super.updateById(menu)
                && sysResourceService.updateParentId(param.convertToResource());
    }

    /**
     * 获取菜单树
     * @param parentId 上级资源id（sys_resource.id）
     * @return 菜单树
     */
    @Override
    public List<SysMenuTreeDto> tree(Integer parentId) {
        List<SysMenuTreeDto> list = mapper.treeData();

        return TreeUtil.build(list, parentId);
    }
}
