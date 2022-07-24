package com.zhixiang.health.modules.sys.service;

import com.zhixiang.health.modules.sys.model.dto.SysButtonDto;
import com.zhixiang.health.modules.sys.model.entity.SysButton;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhixiang.health.modules.sys.model.param.SysButtonSaveParam;

import java.util.List;

/**
 * <p>
 * 按钮及访问权限配置 服务类
 * </p>
 *
 * @author HeJiawang
 * @since 2020-06-24
 */
public interface ISysButtonService extends IService<SysButton> {

    /**
     * 保存按钮及访问权限配置
     * 保存资源信息
     * @param saveParam saveParam
     * @return true
     */
    boolean save(SysButtonSaveParam saveParam);

    /**
     * 修改实体信息
     * @param param 实体信息
     * @return Boolean
     */
    boolean updateById(SysButtonSaveParam param);

    /**
     * 根据上级资源id，获取按钮权限信息
     * @param parentId 上级资源id
     * @return 按钮权限信息
     */
    List<SysButtonDto> listByParentId(Integer parentId);

    /**
     * 根据用户登录名获取访问权限
     * @param userName 用户登录名
     * @return 访问权限
     */
    List<SysButton> listByUserName(String userName);
}
