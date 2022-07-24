package com.zhixiang.health.modules.t.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.zhixiang.health.common.model.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 加油卡信息统计
 * </p>
 *
 * @author Dingx
 * @since 2021-06-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class TShhJytjDto extends BaseEntity<TShhJytjDto> {

    /**
     * 加油卡id
     */
    private Integer id;

    /**
     * 加油卡卡号
     */
    private String jiayoukahao;

    /**
     * 车辆牌号
     */
    private String numberplate;

    /**
     * 厂牌型号
     */
    private String changpaixinghao;

    /**
     * 发动机号
     */
    private String fadongjihao;

    /**
     * 车架号码
     */
    private String chejiahaoma;

    /**
     * 剩余指标
     */
    private Double shengyuzhibiao;

    /**
     * 编制单位
     */
    private String bianzhidanwei;

    /**
     * 填报时间
     */
    private String tianbaoshijian;

    /**
     * 确认签字名称
     */
    private String querequanzu;

    private String photo  ;

}
