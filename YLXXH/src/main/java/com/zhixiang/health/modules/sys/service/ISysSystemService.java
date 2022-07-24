package com.zhixiang.health.modules.sys.service;

import com.zhixiang.health.modules.sys.model.dto.SysSystemDto;
import com.zhixiang.health.modules.sys.model.entity.SysSystem;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 系统配置 服务类
 * </p>
 *
 * @author HeJiawang
 * @since 2020-06-24
 */
public interface ISysSystemService extends IService<SysSystem> {

    /**
     * 查询所有系统信息
     * @return list
     */
    List<SysSystemDto> listAll();

}
