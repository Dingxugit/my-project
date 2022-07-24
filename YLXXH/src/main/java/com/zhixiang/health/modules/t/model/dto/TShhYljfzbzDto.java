package com.zhixiang.health.modules.t.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.zhixiang.health.common.model.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 2021年油料经费指标账
 * </p>
 *
 * @author Dingx
 * @since 2021-06-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class TShhYljfzbzDto extends BaseEntity<TShhYljfzbzDto> {


    /**
     * id
     */
    private Integer id;

    /**
     * 年度
     */
    private Integer year;

    /**
     * 编制单位
     */
    private String bianzhidanwei;

    /**
     * 类型 1.单位 2车
     */
    private Integer type;

    /**
     * 车号（type=1 单位名  type=2 车牌号）
     */
    private String numberplate;

    /**
     * 年度分配
     */
    private Double niandufenpei;

    /**
     * 主卡经费
     */
    private Double zhukajingfei;

    /**
     * 副卡消耗类型92#
     */
    private Double zhichuleixing92;

    /**
     * 副卡消耗类型95#
     */
    private Double zhichuleixing95;

    /**
     * 副卡消耗类型-35#
     */
    private Double zhichuleixing35;

    /**
     * 结存
     */
    private Double unitinformation;

}
