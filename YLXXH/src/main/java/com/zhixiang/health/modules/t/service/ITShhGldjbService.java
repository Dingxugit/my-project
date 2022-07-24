package com.zhixiang.health.modules.t.service;

import com.zhixiang.health.common.http.pagination.Page;
import com.zhixiang.health.common.model.param.UserParam;
import com.zhixiang.health.modules.t.model.dto.TShhGldjbDto;
import com.zhixiang.health.modules.t.model.entity.TShhGldjb;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhixiang.health.modules.t.model.param.TShhGldjbParam;

import java.util.List;

/**
 * <p>
 * 油料社会化保障加油卡日常管理登记表 服务类
 * </p>
 *
 * @author Dingx
 * @since 2021-06-20
 */
public interface ITShhGldjbService extends IService<TShhGldjb> {

    Page<TShhGldjbDto> pageList(TShhGldjbParam param);

    Boolean sjdwSave(TShhGldjb entity, UserParam param);

    Boolean sjdwUpdate(TShhGldjb entity, UserParam param);

    List<TShhGldjb> getExportDataList(TShhGldjbParam param);
}
