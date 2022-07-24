package com.zhixiang.health.modules.sys.service.impl;

import com.zhixiang.health.modules.sys.model.dto.SysSystemDto;
import com.zhixiang.health.modules.sys.model.entity.SysResource;
import com.zhixiang.health.modules.sys.model.entity.SysSystem;
import com.zhixiang.health.modules.sys.mapper.SysSystemMapper;
import com.zhixiang.health.modules.sys.model.enums.SysResourceTypeEnum;
import com.zhixiang.health.modules.sys.service.ISysResourceService;
import com.zhixiang.health.modules.sys.service.ISysSystemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 系统配置 服务实现类
 * </p>
 *
 * @author HeJiawang
 * @since 2020-06-24
 */
@Service
public class SysSystemServiceImpl extends ServiceImpl<SysSystemMapper, SysSystem> implements ISysSystemService {

    @Resource
    private SysSystemMapper mapper;

    @Resource
    private ISysResourceService sysResourceService;

    /**
     * 查询所有系统信息
     * @return list
     */
    @Override
    public List<SysSystemDto> listAll() {
        return mapper.listAll();
    }

    /**
     * 保存系统信息，并加入到系统资源中
     * @param entity 系统信息
     * @return true
     */
    @Transactional
    @Override
    public boolean save(SysSystem entity) {
        super.save(entity);

        return sysResourceService.save(
                new SysResource().setResourceId(entity.getId()).setType(SysResourceTypeEnum.SYSTEM)
        );
    }

}
