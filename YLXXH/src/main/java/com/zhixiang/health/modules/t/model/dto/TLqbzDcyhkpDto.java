package com.zhixiang.health.modules.t.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.zhixiang.health.common.model.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * XXX单车油耗考核卡片
 * </p>
 *
 * @author Dingx
 * @since 2021-06-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class TLqbzDcyhkpDto extends BaseEntity<TLqbzDcyhkpDto> {


    /**
     * id
     */
    private Integer id;

    /**
     * 用油单位
     */
    private String yingyoudanwei;

    /**
     * 车牌号
     */
    private String numberplate;

    /**
     * 油品
     */
    private String youpin;

    /**
     * 类型
     */
    private String leixing;

    /**
     * 品牌
     */
    private String pinpai;

    /**
     * 排量
     */
    private Double pailiang;

    /**
     * 型号
     */
    private String xinghao;

    /**
     * 月份
     */
    private String yuefen;

    /**
     * 油耗标准
     */
    private Double youhaobiaozhun;

    /**
     * 执行标准
     */
    private Double zhixingbiaozhun;

    /**
     * 里程初读
     */
    private Double lichengchudu;

    /**
     * 里程未读
     */
    private Double lichengweidu;

    /**
     * 行驶里程
     */
    private Double xingshilicheng;

    /**
     * 摩托小时
     */
    private Double motuoxiaoshi;

    /**
     * 耗油量
     */
    private Double haoyouliang;

    /**
     * 确认签字名称
     */
    private String qianziname;

    /**
     * 年份
     */
    private Integer year ;

    /**
     * 时间
     */
    private String sj ;

    /**
     * 厂牌型号
     */
    private String cpxx ;

}
