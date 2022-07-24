package com.zhixiang.health.modules.sys.service;

import com.zhixiang.health.common.model.param.UserParam;
import com.zhixiang.health.modules.sys.model.dto.SysDeptTreeDto;
import com.zhixiang.health.modules.sys.model.entity.SysDept;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 部门信息 服务类
 * </p>
 *
 * @author HeJiawang
 * @since 2020-04-28
 */
public interface ISysDeptService extends IService<SysDept> {

    /**
     * 获取树形结构数据
     * @return 树形结构数据集合
     */
    List<SysDeptTreeDto> listTree();

    /**
     * 校验部门编码是否可用
     * @param dept 部门信息
     * @return true 部门编码可用
     */
    Boolean checkCode(SysDept dept);

    SysDeptTreeDto getSysDept(Integer id);

    List<SysDept> getSysDeptList(UserParam param);

    SysDept getOauthUser(Integer id);
}
