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
 * 油料社会化保障加油凭证
 * </p>
 *
 * @author Dingx
 * @since 2021-06-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class TShhJypz extends BaseEntity<TShhJypz> {

    private static final long serialVersionUID=1L;

    /**
     * 加油凭证id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用油单位
     */
    @ExcelField(title = "用油单位")
    private String yongyoudanwei;

    /**
     * 加油时间
     */
    @ExcelField(title = "加油时间")
    private String jiayousj;

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
     * 数量(升)
     */
    @ExcelField(title = "数量(升)")
    private Double shuliang;

    /**
     * 卡内余额(元)
     */
    @ExcelField(title = "卡内余额(元)")
    private Double yue;

    /**
     * 经办人
     */
    @ExcelField(title = "经办人")
    private String jingbanren;

    /**
     * 仪表显示公里上次
     */
    @ExcelField(title = "仪表显示公里上次")
    private Double shangciyibiaoshu;

    /**
     * 仪表显示公里本次
     */
    @ExcelField(title = "仪表显示公里本次")
    private Double benciyibiaoshu;

    /**
     * 是否入指标账
     */
    private String isrz ;

    /**
     * 是否已打印
     */
    private String isprint ;

    private String isdc ;

}
