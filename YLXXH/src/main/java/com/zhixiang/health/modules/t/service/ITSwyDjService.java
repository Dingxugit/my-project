package com.zhixiang.health.modules.t.service;

import com.zhixiang.health.common.http.pagination.Page;
import com.zhixiang.health.common.model.param.UserParam;
import com.zhixiang.health.common.model.result.ServiceResult;
import com.zhixiang.health.modules.t.model.dto.TSwyDjDto;
import com.zhixiang.health.modules.t.model.entity.TSwyDj;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhixiang.health.modules.t.model.param.TLqbzJykxxtjParam;
import com.zhixiang.health.modules.t.model.param.TSwyDjParam;

import java.util.List;

/**
 * <p>
 * 价拨/实物油加注登记表 服务类
 * </p>
 *
 * @author Dingx
 * @since 2021-06-25
 */
public interface ITSwyDjService extends IService<TSwyDj> {

    Page<TSwyDjDto> pageList(TSwyDjParam param);

    Boolean sjdwSave(TSwyDj entity, UserParam param);

    Boolean sjdwUpdate(TSwyDj entity, UserParam param);

    ServiceResult<String> importData(TSwyDjParam param, UserParam userParam);

    List<TSwyDjDto> getDjList(TSwyDjParam param);

    List<TSwyDj> getExportDataList(TSwyDjParam param);
}
