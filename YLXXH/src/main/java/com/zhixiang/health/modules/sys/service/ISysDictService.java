package com.zhixiang.health.modules.sys.service;

import com.zhixiang.health.common.http.pagination.Page;
import com.zhixiang.health.modules.sys.model.dto.SysDictTreeDto;
import com.zhixiang.health.modules.sys.model.entity.SysDict;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 字典表 服务类
 * </p>
 *
 * @author HeJiawang
 * @since 2020-04-30
 */
public interface ISysDictService extends IService<SysDict> {

    /**
     * 校验字典值是否可用
     * @param dict 字典信息
     * @return true 可用
     */
    Boolean checkValue(SysDict dict);

    /**
     * tree page
     * @param page page
     * @return SysDictTreeDto Page
     */
    Page<SysDictTreeDto> tree(Page<SysDictTreeDto> page);

    /**
     * 根据父节点信息，获取节点下的所有层级的字典信息
     * @param parentValue 父节点value
     * @return 字典树信息
     */
    List<SysDictTreeDto> treeByParentValue(String parentValue);
}
