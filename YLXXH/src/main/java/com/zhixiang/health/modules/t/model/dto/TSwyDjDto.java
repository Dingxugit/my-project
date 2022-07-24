package com.zhixiang.health.modules.t.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.zhixiang.health.common.model.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 价拨/实物油加注登记表
 * </p>
 *
 * @author Dingx
 * @since 2021-06-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class TSwyDjDto extends BaseEntity<TSwyDjDto> {

    /**
     * id
     */
    private Integer id;

    /**
     * 用油单位
     */
    private String yongyoudanwei;

    /**
     * 凭证号(第()号)
     */
    private String pingzhenghao;

    /**
     * 日期
     */
    private String riqi;

    /**
     * 装备号
     */
    private String zhuangbeihao;

    /**
     * 发动机号
     */
    private String fadongjihao;

    /**
     * 车架号
     */
    private String chejiahao;

    /**
     * 油品
     */
    private String youpin;

    /**
     * 加油量
     */
    private Double youliaozhibiaojeicun;

    /**
     * 司机签字
     */
    private String sijiname;

    /**
     * 密度
     */
    private Double midu;

    /**
     * 加油员签字
     */
    private String jiayouyuanname;

}
