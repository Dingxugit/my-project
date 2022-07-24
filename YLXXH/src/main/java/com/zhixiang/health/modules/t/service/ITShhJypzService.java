package com.zhixiang.health.modules.t.service;

import com.zhixiang.health.common.http.pagination.Page;
import com.zhixiang.health.common.model.param.UserParam;
import com.zhixiang.health.common.model.result.ServiceResult;
import com.zhixiang.health.modules.t.model.dto.TShhJypzDto;
import com.zhixiang.health.modules.t.model.entity.TShhJypz;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhixiang.health.modules.t.model.param.TLqbzYlgyczpzParam;
import com.zhixiang.health.modules.t.model.param.TShhJypzParam;

import java.util.List;

/**
 * <p>
 * 油料社会化保障加油凭证 服务类
 * </p>
 *
 * @author Dingx
 * @since 2021-06-20
 */
public interface ITShhJypzService extends IService<TShhJypz> {

    Boolean sjdwSave(TShhJypz entity, UserParam param);

    Page<TShhJypzDto> pageList(TShhJypzParam param);

    TShhJypzDto getLastCzpzBycarnum(TShhJypzParam param);

    List<TShhJypzDto> getCzpz(TShhJypzParam param);

    ServiceResult<String> importData(TShhJypzParam param, UserParam userParam);

    TShhJypzDto getJypzById(TShhJypzParam param);

    Boolean saveTozbz(TShhJypzParam param);

    List<TShhJypz> getExportDataList(TShhJypzParam param);
}
