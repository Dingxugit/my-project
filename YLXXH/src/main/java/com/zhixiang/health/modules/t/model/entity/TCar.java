package com.zhixiang.health.modules.t.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.zhixiang.health.common.model.entity.BaseEntity;
import com.zhixiang.health.common.utils.poi.ExcelField;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class TCar extends BaseEntity<TCar> {

    private static final long serialVersionUID=1L;

    /**
     * 车辆id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    /**
     * 车牌号
     */
    @ExcelField(title = "车牌号")
    private String numberplate;

    /**
     * 厂牌型号
     */
    @ExcelField(title = "厂牌型号")
    private String brandmodel;

    /**
     * 车架号码
     */
    @ExcelField(title = "车架号码")
    private String framenumber;

    /**
     * 发动机号
     */
    @ExcelField(title = "发动机号")
    private String enginenumber;

    /**
     * 结转的行驶里程
     */
    @ExcelField(title = "结转的行驶里程")
    private String carryovermileage;

    /**
     * 单位名称
     */
    @ExcelField(title = "所属单位")
    private String unitName ;

    /**
     * 加油卡卡号
     */
    @ExcelField(title = "加油卡号")
    private String oilcardno ;

    /**
     * 单位id
     */
    private Integer unitId;

    /**
     * 是否导出
     */
    private String isdc ;

}
