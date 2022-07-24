package com.zhixiang.health.modules.sys.service.impl;

import com.zhixiang.health.modules.sys.model.dto.SysButtonDto;
import com.zhixiang.health.modules.sys.model.entity.SysButton;
import com.zhixiang.health.modules.sys.mapper.SysButtonMapper;
import com.zhixiang.health.modules.sys.model.param.SysButtonSaveParam;
import com.zhixiang.health.modules.sys.service.ISysButtonService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhixiang.health.modules.sys.service.ISysResourceService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 按钮及访问权限配置 服务实现类
 * </p>
 *
 * @author HeJiawang
 * @since 2020-06-24
 */
@Service
public class SysButtonServiceImpl extends ServiceImpl<SysButtonMapper, SysButton> implements ISysButtonService {

    @Resource
    private SysButtonMapper mapper;

    @Resource
    private ISysResourceService sysResourceService;

    /**
     * 保存按钮及访问权限配置
     * 保存资源信息
     * @param saveParam saveParam
     * @return true
     */
    @CacheEvict(value = "JMonkey_Permission", allEntries = true)
    @Transactional
    @Override
    public boolean save(SysButtonSaveParam saveParam) {
        SysButton button = saveParam.convertToEntity();
        super.save(button);

        return sysResourceService.save(
                saveParam.convertToResource().setResourceId(button.getId())
        );
    }

    /**
     * 修改实体信息
     * @param param 实体信息
     * @return Boolean
     */
    @CacheEvict(value = "JMonkey_Permission", allEntries = true)
    @Override
    public boolean updateById(SysButtonSaveParam param) {
        SysButton button = param.convertToEntity();

        return super.updateById(button)
                && sysResourceService.updateParentId(param.convertToResource());
    }

    /**
     * 根据上级资源id，获取按钮权限信息
     * @param parentId 上级资源id
     * @return 按钮权限信息
     */
    @Override
    public List<SysButtonDto> listByParentId(Integer parentId) {
        return mapper.listByParentId(parentId);
    }

    /**
     * 根据用户登录名获取访问权限
     * @param userName 用户登录名
     * @return 访问权限
     */
    @Cacheable(value = "JMonkey_Permission", key = "#userName  + '_permission'")
    @Override
    public List<SysButton> listByUserName(String userName) {
        return mapper.listByUserName(userName);
    }
}
