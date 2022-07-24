package com.zhixiang.health.modules.t.service;

import com.zhixiang.health.common.http.pagination.Page;
import com.zhixiang.health.common.model.param.UserParam;
import com.zhixiang.health.common.model.result.ServiceResult;
import com.zhixiang.health.modules.t.model.dto.TLqbzDcyhkpDto;
import com.zhixiang.health.modules.t.model.entity.TLqbzDcyhkp;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhixiang.health.modules.t.model.param.TLqbzDcyhkpParam;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * XXX单车油耗考核卡片 服务类
 * </p>
 *
 * @author Dingx
 * @since 2021-06-19
 */
public interface ITLqbzDcyhkpService extends IService<TLqbzDcyhkp> {

    Boolean sjdwSave(TLqbzDcyhkp entity, UserParam param);

    Boolean sjdwUpdate(TLqbzDcyhkp entity, UserParam param);

    Page<TLqbzDcyhkpDto> pageList(TLqbzDcyhkpParam param);

    TLqbzDcyhkpDto getLastGlsBycarnum(TLqbzDcyhkpParam param);

    List<TLqbzDcyhkpDto> getDcyhList(TLqbzDcyhkpParam param);

    ServiceResult<String> importData(TLqbzDcyhkpParam param, UserParam userParam);

    Map<String,Object> getDcyhxxBycarnum(TLqbzDcyhkpParam param);

    Page<TLqbzDcyhkpDto> pageEjdwYhfx(TLqbzDcyhkpParam param) throws ParseException;

    TLqbzDcyhkp getDcyhxx(TLqbzDcyhkpParam param);

    List<TLqbzDcyhkp> getDcyhxxList(TLqbzDcyhkpParam param) ;
}
