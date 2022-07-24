package com.zhixiang.health.modules.t.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.zhixiang.health.common.model.entity.BaseEntity;
import com.zhixiang.health.common.utils.poi.ExcelField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.DefaultValue;

/**
 * <p>
 * 油料供应指标账
 * </p>
 *
 * @author Dingx
 * @since 2021-06-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class TLqbzYlgyzbz extends BaseEntity<TLqbzYlgyzbz> {

    private static final long serialVersionUID=1L;

    /**
     * 指标帐id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 年度
     */
    @ExcelField(title = "年度")
    private Integer year;

    /**
     * 类型 1单位 2 车
     */
    private Integer type;

    /**
     * 车牌号 类型=1时为单位名称 2时为车牌号
     */
    @ExcelField(title = "摘要")
    private String numberplate;

    /**
     * 年度分配
     */
    @ExcelField(title = "年度分配")
    private Double niandufenpei;

    /**
     * 增拨
     */
    @ExcelField(title = "战区增拨")
    private Double zengbo;

    /**
     * 转供
     */
    @ExcelField(title = "转供支出")
    private Double zhuangong;

    /**
     * 充卡支出92#
     */
    private Double zhichuleixing92;

    /**
     * 充卡支出95#
     */
    private Double zhichuleixing95;

    /**
     * 充卡支出-35#
     */
    private Double zhichuleixing35;

    /**
     * 编制单位
     */
    private String bianzhidanwei;

    /**
     * 充卡支出
     */
    @ExcelField(title = "充卡支出")
    private Double chongkazc ;

    /**
     * 结存
     */
    @ExcelField(title = "结存")
    private Double unitinformation;



}
