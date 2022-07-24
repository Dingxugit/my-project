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
 * 加油卡信息统计
 * </p>
 *
 * @author Dingx
 * @since 2021-06-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class TShhJytj extends BaseEntity<TShhJytj> {

    private static final long serialVersionUID=1L;

    /**
     * 加油卡id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 编制单位
     */
    @ExcelField(title = "编制单位")
    private String bianzhidanwei;

    /**
     * 加油卡卡号
     */
    @ExcelField(title = "加油卡卡号")
    private String jiayoukahao;

    /**
     * 车牌号
     */
    @ExcelField(title = "车牌号")
    private String numberplate;

    /**
     * 厂牌型号
     */
    @ExcelField(title = "厂牌型号")
    private String changpaixinghao;

    /**
     * 发动机号
     */
    @ExcelField(title = "发动机号")
    private String fadongjihao;

    /**
     * 车架号
     */
    @ExcelField(title = "车架号")
    private String chejiahaoma;

    /**
     * 剩余指标
     */
    @ExcelField(title = "剩余指标")
    private Double shengyuzhibiao;

    /**
     * 确认签字
     */
    @ExcelField(title = "确认签字")
    private String querequanzu;

    /**
     * 填报时间
     */
    @ExcelField(title = "填报时间")
    private String tianbaoshijian;

    private String photo  ;

    private String isdc ;

    private String isdctp ;

}
