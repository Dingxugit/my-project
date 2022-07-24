package com.zhixiang.health.modules.sys.mapper;

import com.zhixiang.health.common.model.param.UserParam;
import com.zhixiang.health.modules.sys.model.dto.SysDeptTreeDto;
import com.zhixiang.health.modules.sys.model.entity.SysDept;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 部门信息 Mapper 接口
 * </p>
 *
 * @author HeJiawang
 * @since 2020-04-28
 */
public interface SysDeptMapper extends BaseMapper<SysDept> {

    /**
     * 获取 tree dto 数据集合
     * @return list
     */
    List<SysDeptTreeDto> listTreeDto();

    /**
     * 校验部门编码是否可用
     * @param dept 部门信息
     * @return true 部门编码可用
     */
    int checkCode(SysDept dept);

    SysDeptTreeDto getSysDept(Integer id);

    List<SysDept> getSysDeptList(UserParam param);
}
