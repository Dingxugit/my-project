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
 * 价拨/实物油加注登记表
 * </p>
 *
 * @author Dingx
 * @since 2021-06-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class TSwyDj extends BaseEntity<TSwyDj> {

    private static final long serialVersionUID=1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用油单位
     */
    @ExcelField(title = "用油单位")
    private String yongyoudanwei;

    /**
     * 凭证号
     */
    @ExcelField(title = "凭证号")
    private String pingzhenghao;

    /**
     * 日期
     */
    @ExcelField(title = "日期")
    private String riqi;

    /**
     * 装备号
     */
    @ExcelField(title = "装备号")
    private String zhuangbeihao;

    /**
     * 发动机号
     */
    @ExcelField(title = "发动机号")
    private String fadongjihao;

    /**
     * 车架号
     */
    @ExcelField(title = "车架号")
    private String chejiahao;

    /**
     * 油品
     */
    @ExcelField(title = "油品")
    private String youpin;

    /**
     * 加油量
     */
    @ExcelField(title = "加油量")
    private Double youliaozhibiaojeicun;

    /**
     * 司机签字
     */
    @ExcelField(title = "司机签字")
    private String sijiname;

    /**
     * 密度
     */
    @ExcelField(title = "密度")
    private Double midu;

    /**
     * 加油员签字
     */
    @ExcelField(title = "加油员签字")
    private String jiayouyuanname;

    private String isdc ;

}
