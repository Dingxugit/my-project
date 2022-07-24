package com.zhixiang.health.modules.sys.mapper;

import com.zhixiang.health.modules.sys.model.dto.SysSystemDto;
import com.zhixiang.health.modules.sys.model.entity.SysSystem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 系统配置 Mapper 接口
 * </p>
 *
 * @author HeJiawang
 * @since 2020-06-24
 */
public interface SysSystemMapper extends BaseMapper<SysSystem> {

    /**
     * 查询所有系统信息
     * @return list
     */
    List<SysSystemDto> listAll();

    /**
     * 获取当前登录用户，有权限查看的系统信息
     * @param userId 当前用户信息
     * @return 系统信息
     */
    List<SysSystemDto> listByUseId(@Param("userId") Integer userId);
}
