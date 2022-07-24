package com.zhixiang.health.modules.t.service;

import com.zhixiang.health.common.http.pagination.Page;
import com.zhixiang.health.common.model.param.UserParam;
import com.zhixiang.health.common.model.result.ServiceResult;
import com.zhixiang.health.modules.t.model.dto.TShhJytjDto;
import com.zhixiang.health.modules.t.model.entity.TShhJytj;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhixiang.health.modules.t.model.param.TShhJytjParam;

import java.util.List;

/**
 * <p>
 * 加油卡信息统计 服务类
 * </p>
 *
 * @author Dingx
 * @since 2021-06-20
 */
public interface ITShhJytjService extends IService<TShhJytj> {

    Boolean sjdwSave(TShhJytj entity, UserParam param);

    Boolean sjdwUpdate(TShhJytj entity);

    Page<TShhJytjDto> pageList(TShhJytjParam param);

    List<TShhJytjDto> getJykxxtjList(TShhJytjParam param);

    ServiceResult<String> importData(TShhJytjParam param, UserParam userParam);

    List<TShhJytjDto> getJykxxtjLists(TShhJytjParam param);

    List<TShhJytj> getJykxxtjDcLists(TShhJytjParam param);

    List<TShhJytj> getExportDataList(TShhJytjParam param);
}
