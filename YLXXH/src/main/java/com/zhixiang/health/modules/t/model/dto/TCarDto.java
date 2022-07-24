package com.zhixiang.health.modules.t.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 车辆信息表
 * </p>
 *
 * @author Dingx
 * @since 2021-06-01
 */
@Data
@Accessors(chain = true)
public class TCarDto {

    /**
     * 车辆id
     */
    private String id;

    /**
     * 车牌号
     */
    private String numberplate;

    /**
     * 厂牌型号
     */
    private String brandmodel;

    /**
     * 车架号码
     */
    private String framenumber;

    /**
     * 发动机号
     */
    private String enginenumber;

    /**
     * 结转的行驶里程
     */
    private String carryovermileage;

    /**
     * 单位名称
     */
    private String unitName;

    /**
     * 加油卡卡号
     */
    private String oilcardno ;
}
