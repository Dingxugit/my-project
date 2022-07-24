package com.zhixiang.health.modules.t.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhixiang.health.common.http.pagination.Page;
import com.zhixiang.health.common.model.param.UserParam;
import com.zhixiang.health.common.model.result.ServiceResult;
import com.zhixiang.health.modules.t.model.dto.TLqbzYlgyzbzDto;
import com.zhixiang.health.modules.t.model.entity.TLqbzYlgyzbz;
import com.zhixiang.health.modules.t.model.param.TLqbzYlgyzbzParam;

import java.util.List;

/**
 * <p>
 * 油料供应指标账 服务类
 * </p>
 *
 * @author Dingx
 * @since 2021-06-15
 */
public interface ITLqbzYlgyzbzService extends IService<TLqbzYlgyzbz> {

    Page<TLqbzYlgyzbzDto> pageList(TLqbzYlgyzbzParam param);

    Boolean sjdwSave(TLqbzYlgyzbz entity, UserParam param);

    Boolean sjdwUpdate(TLqbzYlgyzbz entity, UserParam param);

    List<TLqbzYlgyzbzDto> getDeptZbz(TLqbzYlgyzbzParam param,UserParam param1);

    Boolean ejdwSave(TLqbzYlgyzbz entity, UserParam param);

    Boolean ejdwUpdate(TLqbzYlgyzbz entity, UserParam param);

    Page<TLqbzYlgyzbzDto> pageEjdw(TLqbzYlgyzbzParam param);

    ServiceResult<String> importData(TLqbzYlgyzbzParam param,UserParam userParam);

    Page<TLqbzYlgyzbzDto> pageEjdwYdbb(TLqbzYlgyzbzParam param);

    Boolean searchExists(TLqbzYlgyzbzParam param);

    List<TLqbzYlgyzbz> getExportDataList(TLqbzYlgyzbzParam param);
}
