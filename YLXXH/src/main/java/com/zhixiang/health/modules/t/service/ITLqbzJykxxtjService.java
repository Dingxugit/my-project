package com.zhixiang.health.modules.t.service;

import com.zhixiang.health.common.http.pagination.Page;
import com.zhixiang.health.common.model.param.UserParam;
import com.zhixiang.health.common.model.result.ServiceResult;
import com.zhixiang.health.modules.t.model.dto.TLqbzJykxxtjDto;
import com.zhixiang.health.modules.t.model.entity.TLqbzJykxxtj;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhixiang.health.modules.t.model.entity.TLqbzYlgyzbz;
import com.zhixiang.health.modules.t.model.param.TLqbzJykxxtjParam;

import java.util.List;

/**
 * <p>
 * 加油卡信息统计 服务类
 * </p>
 *
 * @author Dingx
 * @since 2021-06-17
 */
public interface ITLqbzJykxxtjService extends IService<TLqbzJykxxtj> {

    Page<TLqbzJykxxtjDto> pageList(TLqbzJykxxtjParam param);

    Boolean sjdwSave(TLqbzJykxxtj entity, UserParam param);

    List<TLqbzJykxxtjDto> getJykxxtjList(TLqbzJykxxtjParam param);

    ServiceResult<String> importData(TLqbzJykxxtjParam param, UserParam userParam);

    List<TLqbzJykxxtjDto> getJykxxtjLists(TLqbzJykxxtjParam param) ;

    List<TLqbzJykxxtj> getJykxxtjDcLists(TLqbzJykxxtjParam param);

    List<TLqbzJykxxtj> getExportDataList(TLqbzJykxxtjParam param);
}
