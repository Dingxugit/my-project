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
 * XXX单车油耗卡片（实物油）
 * </p>
 *
 * @author Dingx
 * @since 2021-06-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class TSwyDcyh extends BaseEntity<TSwyDcyh> {

    private static final long serialVersionUID=1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 年份
     */
    @ExcelField(title = "年份")
    private Integer year ;

    /**
     * 月份
     */
    @ExcelField(title = "月份")
    private String yuefen;

    /**
     * 用油单位
     */
    @ExcelField(title = "用油单位")
    private String yongyoudanwei;

    /**
     * 车牌号
     */
    @ExcelField(title = "车牌号")
    private String chepaihaoma;

    /**
     * 油品
     */
    @ExcelField(title = "油品")
    private String youpin;

    /**
     * 类型
     */
    @ExcelField(title = "类型")
    private String leixing;

    /**
     * 品牌
     */
    @ExcelField(title = "品牌")
    private String pinpai;

    /**
     * 排量
     */
    @ExcelField(title = "排量")
    private String pailiang;

    /**
     * 型号
     */
    @ExcelField(title = "型号")
    private String xinghao;


    /**
     * 消耗标准（升/百公里）
     */
    @ExcelField(title = "消耗标准（升/百公里）")
    private Double youhaobiaozhun;

    /**
     * 执行标准（升/百公里）
     */
    @ExcelField(title = "执行标准（升/百公里）")
    private Double zhixingbiaozhun;

    /**
     * 里程初读数（公里）
     */
    @ExcelField(title = "里程初读数（公里）")
    private Double lichengchudu;

    /**
     * 里程末读数（公里）
     */
    @ExcelField(title = "里程末读数（公里）")
    private Double lichengweidu;

    /**
     * 行驶里程（公里）
     */
    @ExcelField(title = "行驶里程（公里）")
    private Double xingshilicheng;

    /**
     * 摩托小时（小时）
     */
    @ExcelField(title = "摩托小时（小时）")
    private Double motuoxiaoshi;

    /**
     * 耗油量（升）
     */
    @ExcelField(title = "耗油量（升）")
    private Double haoyouliang;

    /**
     * 确认签字
     */
    @ExcelField(title = "确认签字")
    private String qianziname;

    private String isdc ;

}
