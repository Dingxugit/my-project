package com.zhixiang.health.modules.t.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhixiang.health.common.http.pagination.Page;
import com.zhixiang.health.common.model.param.UserParam;
import com.zhixiang.health.common.model.result.ServiceResult;
import com.zhixiang.health.modules.t.model.dto.TShhYljfzbzDto;
import com.zhixiang.health.modules.t.model.entity.TLqbzYlgyzbz;
import com.zhixiang.health.modules.t.model.entity.TShhYljfzbz;
import com.zhixiang.health.modules.t.model.param.TShhYljfzbzParam;

import java.util.List;

/**
 * <p>
 * 2021年油料经费指标账 服务类
 * </p>
 *
 * @author Dingx
 * @since 2021-06-20
 */
public interface ITShhYljfzbzService extends IService<TShhYljfzbz> {

    Page<TShhYljfzbzDto> pageList(TShhYljfzbzParam param);

    Boolean sjdwSave(TShhYljfzbz entity, UserParam param);

    Boolean sjdwUpdate(TShhYljfzbz entity, UserParam param);

    List<TShhYljfzbzDto> getDeptZbz(TShhYljfzbzParam param, UserParam param1);

    Page<TShhYljfzbzDto> pageEjdw(TShhYljfzbzParam param);

    ServiceResult<String> importData(TShhYljfzbzParam param, UserParam userParam);

    Boolean ejdwSave(TShhYljfzbz entity, UserParam param);

    Boolean ejdwUpdate(TShhYljfzbz entity, UserParam param);

    Page<TShhYljfzbzDto> pageEjdwYdbb(TShhYljfzbzParam param);

    Boolean searchExists(TShhYljfzbzParam param);

    List<TShhYljfzbz> getExportDataList(TShhYljfzbzParam param);
}
