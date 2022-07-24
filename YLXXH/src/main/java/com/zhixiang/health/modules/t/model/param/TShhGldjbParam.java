package com.zhixiang.health.modules.t.model.param;

import com.zhixiang.health.modules.t.model.entity.TShhGldjb;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class TShhGldjbParam extends TShhGldjb {

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
     * renderLimitStart
     * @return this
     */
    public TShhGldjbParam renderLimitStart() {
        this.limitStart = pageSize * ( current - 1 );

        return this;
    }
}
