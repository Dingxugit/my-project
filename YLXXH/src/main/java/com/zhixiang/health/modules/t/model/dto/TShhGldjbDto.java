package com.zhixiang.health.modules.t.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.zhixiang.health.common.model.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 油料社会化保障加油卡日常管理登记表
 * </p>
 *
 * @author Dingx
 * @since 2021-06-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class TShhGldjbDto extends BaseEntity<TShhGldjbDto> {

    /**
     * id
     */
    private Integer id;

    /**
     * 领卡事由
     */
    private String lingkashiyou;

    /**
     * 领取时间
     */
    private String lingqushijian;

    /**
     * 加油卡卡号
     */
    private String jiayoukakahao;

    /**
     * 车牌号
     */
    private String numberplate;

    /**
     * 厂牌型号
     */
    private String changpaixinghao;

    /**
     * 油品
     */
    private String youpin;

    /**
     * 上次里程读数
     */
    private Double shangcilichengdushu;

    /**
     * 本次里程读数
     */
    private Double bencilichengdushu;

    /**
     * 行驶里程
     */
    private Double xiingshilicheng;

    /**
     * 本次加油量
     */
    private Double bencijiayouliang;

    /**
     * 本次加油金额
     */
    private Double bencijiayoujine;

    /**
     * 卡内余额
     */
    private Double kaneiyue;

    /**
     * 归还时间
     */
    private String guihuanshijian;

    /**
     * 是否上交小票 0 否 1是
     */
    private String shifoushangjiaoxiaopiao;

    /**
     * 驾驶员签字名称
     */
    private String jiashiyanname;

    /**
     * 是否入经费指标账 0否1是
     */
    private String isrz;

    /**
     * 加油时间
     */
    private String jiayoushijian ;

}
