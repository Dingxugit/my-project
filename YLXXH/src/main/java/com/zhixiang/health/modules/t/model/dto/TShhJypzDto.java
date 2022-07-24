package com.zhixiang.health.modules.t.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.zhixiang.health.common.model.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.imageio.stream.IIOByteBuffer;

/**
 * <p>
 * 油料社会化保障加油凭证
 * </p>
 *
 * @author Dingx
 * @since 2021-06-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class TShhJypzDto extends BaseEntity<TShhJypzDto> {

    /**
     * 加油凭证id
     */
    private Integer id;

    /**
     * 用油单位
     */
    private String yongyoudanwei;

    /**
     * 加油时间
     */
    private String jiayousj;

    /**
     * 车牌号码
     */
    private String numberplate;

    /**
     * 油品
     */
    private String youpin;

    /**
     * 数量(升)
     */
    private Double shuliang;

    /**
     * 卡内余额
     */
    private Double yue;

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
     * 是否入指标账
     */
    private String isrz ;

    /**
     * 是否已打印
     */
    private String isprint ;

    /**
     * 是否不足
     */
    private int isbr ;

}
