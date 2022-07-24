package com.zhixiang.health.modules.t.service;

import com.zhixiang.health.common.http.pagination.Page;
import com.zhixiang.health.common.model.param.UserParam;
import com.zhixiang.health.common.model.result.ServiceResult;
import com.zhixiang.health.modules.t.model.dto.TSwyDcyhDto;
import com.zhixiang.health.modules.t.model.entity.TSwyDcyh;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhixiang.health.modules.t.model.param.TSwyDcyhParam;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * XXX单车油耗卡片（实物油） 服务类
 * </p>
 *
 * @author Dingx
 * @since 2021-06-25
 */
public interface ITSwyDcyhService extends IService<TSwyDcyh> {

    Boolean sjdwSave(TSwyDcyh entity, UserParam param);

    Boolean sjdwUpdate(TSwyDcyh entity, UserParam param);

    Page<TSwyDcyhDto> pageList(TSwyDcyhParam param);

    TSwyDcyhDto getLastGlsBycarnum(TSwyDcyhParam param);

    List<TSwyDcyhDto> getDcyhList(TSwyDcyhParam param);

    ServiceResult<String> importData(TSwyDcyhParam param, UserParam userParam);

    Map<String,Object> getDcyhxxBycarnum(TSwyDcyhParam param);

    Page<TSwyDcyhDto> pageEjdwYhfx(TSwyDcyhParam param) throws ParseException;

    List<TSwyDcyh> getDcyhxxList(TSwyDcyhParam param);

    TSwyDcyh getDcyhxx(TSwyDcyhParam param);
}
