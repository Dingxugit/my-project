package com.zhixiang.health.modules.t.model.param;

import com.zhixiang.health.modules.t.model.entity.TShhYljfzbz;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class TShhYljfzbzParam extends TShhYljfzbz {

    /**
     * 每页显示条数，默认 10
     */
    private long pageSize = 10;

    /**
     * 当前页
     */
    private long current = 1;

    /**
     * limitStart
     */
    private long limitStart;

    /**
     * 文件路径
     */
    private String wenjianlj ;

    /**
     * 开始时间
     */
    private String startDate ;

    /**
     * 结束时间
     */
    private String endDate ;

    /**
     * renderLimitStart
     * @return this
     */
    public TShhYljfzbzParam renderLimitStart() {
        this.limitStart = pageSize * ( current - 1 );

        return this;
    }
}
