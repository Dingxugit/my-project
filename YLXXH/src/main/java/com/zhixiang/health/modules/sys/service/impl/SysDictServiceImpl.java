package com.zhixiang.health.modules.sys.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhixiang.health.common.http.pagination.Page;
import com.zhixiang.health.common.utils.TreeUtil;
import com.zhixiang.health.modules.sys.model.dto.SysDictTreeDto;
import com.zhixiang.health.modules.sys.model.entity.SysDict;
import com.zhixiang.health.modules.sys.mapper.SysDictMapper;
import com.zhixiang.health.modules.sys.service.ISysDictService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 字典表 服务实现类
 * </p>
 *
 * @author HeJiawang
 * @since 2020-04-30
 */
@Service
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements ISysDictService {

    @Resource
    private SysDictMapper mapper;

    /**
     * 获取所有信息
     * @return list
     */
    @Override
    public List<SysDict> list() {
        QueryWrapper<SysDict> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("sort");

        return super.list(wrapper);
    }

    /**
     * save
     * @param entity dict
     * @return true
     */
    @CacheEvict(value = "sys_dict", allEntries = true)
    @Override
    public boolean save(SysDict entity) {
        return super.save(entity);
    }

    /**
     * update
     * @param entity dict
     * @return true
     */
    @CacheEvict(value = "sys_dict", allEntries = true)
    @Override
    public boolean updateById(SysDict entity) {
        return super.updateById(entity);
    }

    /**
     * 删除本级及下级字典信息
     * @param id 字典id
     * @return true
     */
    @CacheEvict(value = "sys_dict", allEntries = true)
    @Transactional
    @Override
    public boolean removeById(Serializable id) {
        QueryWrapper<SysDict> wrapper = new QueryWrapper<>(
                new SysDict().setParentId(Integer.valueOf(id.toString()))
        );
        List<SysDict> childrenDictList = super.list(wrapper);
        if (CollUtil.isNotEmpty(childrenDictList)) {
            childrenDictList.forEach(childrenDict -> {
                this.removeById(childrenDict.getId());
            });
        }

        return super.removeById(id);
    }

    /**
     * 校验字典值是否可用
     * @param dict 字典信息
     * @return true 可用
     */
    @Override
    public Boolean checkValue(SysDict dict) {
        return mapper.checkValue(dict) <= 0;
    }

    /**
     * tree page
     * @param page page
     * @return SysDictTreeDto Page
     */
    @Override
    public Page<SysDictTreeDto> tree(Page<SysDictTreeDto> page) {
        List<SysDictTreeDto> treeDtoList = mapper.listAllTreeDto();
        List<SysDictTreeDto> list = TreeUtil.build(treeDtoList);

        long current = page.getCurrent(),size = page.getSize();
        long start = size * ( current - 1 ) > list.size() ? list.size() : size * ( current - 1 ),
                end = size * current > list.size() ? list.size() : size * current;

        return page.setTotal(list.size())
                .setRecords(list.subList((int)start, (int)end));
    }

    /**
     * 根据父节点信息，获取节点下的所有层级的字典信息
     * @param parentValue 父节点value
     * @return 字典树信息
     */
    @Cacheable(value = "sys_dict", key = "'sys_dict_' + #parentValue")
    @Override
    public List<SysDictTreeDto> treeByParentValue(String parentValue) {
        SysDict parentDict = mapper.getByValue(parentValue);
        if (null == parentDict) return CollUtil.newArrayList();

        List<SysDictTreeDto> treeDtoList = mapper.listAllTreeDto();
        return TreeUtil.build(treeDtoList, parentDict.getId());
    }
}
