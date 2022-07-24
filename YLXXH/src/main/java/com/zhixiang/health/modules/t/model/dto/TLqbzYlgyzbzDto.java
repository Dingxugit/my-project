package com.zhixiang.health.modules.t.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.zhixiang.health.common.model.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 油料供应指标账
 * </p>
 *
 * @author Dingx
 * @since 2021-06-15
 */
@Data
@Accessors(chain = true)
public class TLqbzYlgyzbzDto extends BaseEntity<TLqbzYlgyzbzDto> {


    /**
     * 指标帐id
     */
    private Integer id;

    /**
     * 年度
     */
    private Integer year;

    /**
     * 类型 1单位 2 车
     */
    private Integer type;

    /**
     * 车牌号 类型=1时为单位名称 2时为车牌号
     */
    private String numberplate;

    /**
     * 年度分配
     */
    private Double niandufenpei;

    /**
     * 增拨
     */
    private Double zengbo;

    /**
     * 转供
     */
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
     * 结存
     */
    private Double unitinformation;

    /**
     * 充卡支出
     */
    private Double chongkazc ;

}
