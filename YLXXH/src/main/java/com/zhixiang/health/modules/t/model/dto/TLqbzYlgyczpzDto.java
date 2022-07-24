package com.zhixiang.health.modules.t.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.zhixiang.health.common.model.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 油料供应充值凭证
 * </p>
 *
 * @author Dingx
 * @since 2021-06-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class TLqbzYlgyczpzDto extends BaseEntity<TLqbzYlgyczpzDto> {


    /**
     * 充值凭证id
     */
    private Integer id;

    /**
     * 用油单位
     */
    private String yongyoudanwei;

    /**
     * 充值时间
     */
    private String chongzhisj;

    /**
     * 车牌号
     */
    private String numberplate;

    /**
     * 油品
     */
    private String youpin;

    /**
     * 数量(公斤)
     */
    private Double shuliang;

    /**
     * 经办人
     */
    private String jingbanren;

    /**
     * 上次仪表公里数
     */
    private Double shangciyibiaoshu;

    /**
     * 本次仪表公里数
     */
    private Double benciyibiaoshu;

    /**
     * 是否已经计入油料供应指标账
     */
    private String isrz ;

    /**
     * 是否已经打印
     */
    private String isprint ;

    /**
     * 结存是否小于数量
     */
    private int isbr ;


}
