package com.zhixiang.health.modules.t.service;

import com.zhixiang.health.common.http.pagination.Page;
import com.zhixiang.health.common.model.param.UserParam;
import com.zhixiang.health.common.model.result.ServiceResult;
import com.zhixiang.health.modules.t.model.dto.TLqbzYlgyczpzDto;
import com.zhixiang.health.modules.t.model.entity.TLqbzYlgyczpz;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhixiang.health.modules.t.model.param.TLqbzJykxxtjParam;
import com.zhixiang.health.modules.t.model.param.TLqbzYlgyczpzParam;

import java.util.List;

/**
 * <p>
 * 油料供应充值凭证 服务类
 * </p>
 *
 * @author Dingx
 * @since 2021-06-18
 */
public interface ITLqbzYlgyczpzService extends IService<TLqbzYlgyczpz> {

    Boolean sjdwSave(TLqbzYlgyczpz entity, UserParam param);

    Page<TLqbzYlgyczpzDto> pageList(TLqbzYlgyczpzParam param);

    TLqbzYlgyczpzDto getLastCzpzBycarnum(TLqbzYlgyczpzParam param);

    TLqbzYlgyczpzDto getJypzById(TLqbzYlgyczpzParam param);

    Boolean saveTozbz(TLqbzYlgyczpzParam param);

    List<TLqbzYlgyczpzDto> getCzpz(TLqbzYlgyczpzParam param);

    ServiceResult<String> importData(TLqbzYlgyczpzParam param, UserParam userParam);

    Boolean sjdwUpdate(TLqbzYlgyczpz entity, UserParam param);

    List<TLqbzYlgyczpz> getExportDataList(TLqbzYlgyczpzParam param);
}
