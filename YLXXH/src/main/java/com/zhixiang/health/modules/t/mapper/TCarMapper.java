package com.zhixiang.health.modules.t.mapper;

import com.zhixiang.health.modules.t.model.dto.TCarDto;
import com.zhixiang.health.modules.t.model.entity.TCar;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhixiang.health.modules.t.model.param.TCarParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 车辆信息表 Mapper 接口
 * </p>
 *
 * @author Dingx
 * @since 2021-06-01
 */
public interface TCarMapper extends BaseMapper<TCar> {

    List<TCarDto> pageList(TCarParam param);

    long pageTotal(TCarParam param);

    List<TCarDto> getCarInfo(String firstDeptId);

    TCarDto getCarInfoByNum(String numberplate);

    TCar getCarInfoByCjh(String framenumber);

    List<TCarDto> getCarList(String firstDeptId);

    void updateCar(String firstDeptId);

    List<TCarDto> getCarInfoByDeptname(@Param("deptname") String deptname);
}
