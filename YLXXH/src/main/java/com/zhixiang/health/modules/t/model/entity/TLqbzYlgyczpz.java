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
 * 油料供应充值凭证
 * </p>
 *
 * @author Dingx
 * @since 2021-06-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class TLqbzYlgyczpz extends BaseEntity<TLqbzYlgyczpz> {

    private static final long serialVersionUID=1L;

    /**
     * 充值凭证id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用油单位
     */
    @ExcelField(title = "用油单位")
    private String yongyoudanwei;

    /**
     * 充值时间
     */
    @ExcelField(title = "充值时间")
    private String chongzhisj;

    /**
     * 车牌号
     */
    @ExcelField(title = "车牌号")
    private String numberplate;

    /**
     * 油品
     */
    @ExcelField(title = "油品")
    private String youpin;

    /**
     * 数量(公斤)
     */
    @ExcelField(title = "数量(公斤)")
    private Double shuliang;

    /**
     * 经办人
     */
    @ExcelField(title = "经办人")
    private String jingbanren;

    /**
     * 上次仪表公里数
     */
    @ExcelField(title = "上次")
    private Double shangciyibiaoshu;

    /**
     * 本次仪表公里数
     */
    @ExcelField(title = "本次")
    private Double benciyibiaoshu;

    /**
     * 是否已经计入油料供应指标账
     */
    private String isrz ;

    /**
     * 是否已经打印
     */
    private String isprint ;

    private String isdc ;


}
