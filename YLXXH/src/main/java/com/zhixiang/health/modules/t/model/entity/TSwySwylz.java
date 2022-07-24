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
 * 价拨/实物油料帐
 * </p>
 *
 * @author Dingx
 * @since 2021-06-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class TSwySwylz extends BaseEntity<TSwySwylz> {

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
     * 日期
     */
    @ExcelField(title = "日期")
    private String riqi;

    /**
     * 凭证号
     */
    @ExcelField(title = "凭证号")
    private String pingzhenghao;

    /**
     * 摘要
     */
    @ExcelField(title = "摘要")
    private String zhaiyao;

    /**
     * 油品
     */
    @ExcelField(title = "油品")
    private String youpin;

    /**
     * 油料指标收入
     */
    @ExcelField(title = "油料指标收入")
    private Double youliaozhibiaoshouru;

    /**
     * 油料指标支出
     */
    @ExcelField(title = "油料指标支出")
    private Double youliaozhibiaozhichu;

    /**
     * 油料指标结存
     */
    @ExcelField(title = "油料指标结存")
    private Double youliaozhibiaojeicun;

}
