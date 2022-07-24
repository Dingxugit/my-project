package com.zhixiang.health.modules.t.service;

import com.zhixiang.health.common.http.pagination.Page;
import com.zhixiang.health.common.model.param.UserParam;
import com.zhixiang.health.common.model.result.ServiceResult;
import com.zhixiang.health.modules.t.model.dto.TSwySwylzDto;
import com.zhixiang.health.modules.t.model.entity.TSwySwylz;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhixiang.health.modules.t.model.param.TSwySwylzParam;

import java.util.List;

/**
 * <p>
 * 价拨/实物油料帐 服务类
 * </p>
 *
 * @author Dingx
 * @since 2021-06-25
 */
public interface ITSwySwylzService extends IService<TSwySwylz> {

    Page<TSwySwylzDto> pageList(TSwySwylzParam param);

    List<TSwySwylzDto> getDeptZbzList(TSwySwylzParam param);

    Boolean sjdwSave(TSwySwylz entity, UserParam param);

    Boolean sjdwUpdate(TSwySwylz entity, UserParam param);

    ServiceResult<String> importData(TSwySwylzParam param, UserParam userParam);

    Boolean searchExists(TSwySwylzParam param);

    List<TSwySwylz> getExportDataList(TSwySwylzParam param);
}
