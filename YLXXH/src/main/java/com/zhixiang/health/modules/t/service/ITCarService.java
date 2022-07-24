package com.zhixiang.health.modules.t.service;

import com.zhixiang.health.common.http.pagination.Page;
import com.zhixiang.health.common.model.param.UserParam;
import com.zhixiang.health.common.model.result.ServiceResult;
import com.zhixiang.health.modules.t.model.dto.TCarDto;
import com.zhixiang.health.modules.t.model.entity.TCar;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhixiang.health.modules.t.model.param.TCarParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 车辆信息表 服务类
 * </p>
 *
 * @author Dingx
 * @since 2021-06-01
 */
public interface ITCarService extends IService<TCar> {

    Page<TCarDto> pageList(TCarParam param);

    List<TCarDto> getCarInfo(String firstDeptId);

    List<TCarDto> getCarList(String firstDeptId);

    ServiceResult<String> importData(TCarParam param, UserParam userParam);

    TCarDto getCarInfoBynum(TCarParam param);

    List<TCarDto> getCarInfoByDeptname(@Param("deptname") String deptname) ;
}
