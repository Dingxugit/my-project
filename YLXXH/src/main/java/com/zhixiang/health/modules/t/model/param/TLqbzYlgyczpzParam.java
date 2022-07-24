package com.zhixiang.health.modules.t.model.param;

import com.zhixiang.health.modules.t.model.entity.TLqbzYlgyczpz;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class TLqbzYlgyczpzParam extends TLqbzYlgyczpz {

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
    private String wenjianlj;

    /**
     * renderLimitStart
     * @return this
     */
    public TLqbzYlgyczpzParam renderLimitStart() {
        this.limitStart = pageSize * ( current - 1 );

        return this;
    }
}
