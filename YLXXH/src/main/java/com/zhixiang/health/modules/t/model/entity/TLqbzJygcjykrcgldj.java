package com.zhixiang.health.modules.t.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.zhixiang.health.common.model.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 军油工程加油卡日常管理登记
 * </p>
 *
 * @author Dingx
 * @since 2021-06-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class TLqbzJygcjykrcgldj extends BaseEntity<TLqbzJygcjykrcgldj> {

    private static final long serialVersionUID=1L;

    /**
     * 加油卡登记id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 加油卡卡号
     */
    private String fuelcardnumber;

    /**
     * 车牌号
     */
    private String numberplate;

    /**
     * 油品
     */
    private String youpin;

    /**
     * 领卡事由
     */
    private String lingkashiyou;

    /**
     * 领取时间
     */
    private String lingqusj;

    /**
     * 卡内油量L
     */
    private Double kaneiyouliang;

    /**
     * 归还时间
     */
    private String guihuanshijian;

    /**
     * 卡内余额
     */
    private Double kaneiyue;

    /**
     * 驾驶员签字
     */
    private String jaishiyuanqianzi;


}
