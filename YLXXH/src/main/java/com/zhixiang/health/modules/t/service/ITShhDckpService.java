package com.zhixiang.health.modules.t.service;

import com.zhixiang.health.common.http.pagination.Page;
import com.zhixiang.health.common.model.param.UserParam;
import com.zhixiang.health.common.model.result.ServiceResult;
import com.zhixiang.health.modules.t.model.dto.TShhDckpDto;
import com.zhixiang.health.modules.t.model.entity.TShhDckp;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhixiang.health.modules.t.model.param.TShhDckpParam;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 油料社会化保障车辆单车耗油考核卡片 服务类
 * </p>
 *
 * @author Dingx
 * @since 2021-06-20
 */
public interface ITShhDckpService extends IService<TShhDckp> {

    Boolean sjdwSave(TShhDckp entity, UserParam param);

    Boolean sjdwUpdate(TShhDckp entity, UserParam param);

    Page<TShhDckpDto> pageList(TShhDckpParam param);

    TShhDckpDto getLastGlsBycarnum(TShhDckpParam param);

    List<TShhDckpDto> getDcyhList(TShhDckpParam param);

    ServiceResult<String> importData(TShhDckpParam param, UserParam userParam);

    Map<String,Object> getDcyhxxBycarnum(TShhDckpParam param);

    Page<TShhDckpDto> pageEjdwYhfx(TShhDckpParam param) throws ParseException;

    TShhDckp getDcyhxx(TShhDckpParam param);

    List<TShhDckp> getDcyhxxList(TShhDckpParam param);
}
