package com.zhixiang.health.modules.t.api;

import com.zhixiang.health.common.http.pagination.Page;
import com.zhixiang.health.common.http.result.HttpPageResult;
import com.zhixiang.health.common.http.result.HttpResult;
import com.zhixiang.health.modules.t.model.dto.TLqbzJygcjykrcgldjDto;
import com.zhixiang.health.modules.t.model.dto.TLqbzYlgyzbzDto;
import com.zhixiang.health.modules.t.model.entity.TLqbzJygcjykrcgldj;
import com.zhixiang.health.modules.t.model.param.TLqbzJygcjykrcgldjParam;
import com.zhixiang.health.modules.t.model.param.TLqbzYlgyzbzParam;
import com.zhixiang.health.modules.t.service.ITLqbzJygcjykrcgldjService;
import org.springframework.web.bind.annotation.*;

import com.zhixiang.health.common.http.abs.BaseHttp;

import javax.annotation.Resource;

/**
 * <p>
 * 军油工程加油卡日常管理登记 前端控制器
 * </p>
 *
 * @author Dingx
 * @since 2021-06-17
 */
@RestController
@RequestMapping("/t/lqbz/jygcjykrcgldj")
public class TLqbzJygcjykrcgldjApi extends BaseHttp {

    @Resource
    private ITLqbzJygcjykrcgldjService service;

    /**
     * 分页查询信息
     * @param page page
     * @param entity 实体信息
     * @return page
     */
    @GetMapping(value = "/page")
    public HttpPageResult<TLqbzJygcjykrcgldj> page(Page<TLqbzJygcjykrcgldj> page, TLqbzJygcjykrcgldj entity) {
        return new HttpPageResult<>(service.page(page));
    }

    /**
     * 保存实体信息
     * @param entity 实体信息
     * @return Boolean
     */
    @PostMapping(value = "/save")
    public HttpResult<Boolean> save(@RequestBody TLqbzJygcjykrcgldj entity ){
        return new HttpResult<>(service.save(entity));
    }

    /**
     * 修改实体信息
     * @param entity 实体信息
     * @return Boolean
     */
    @PutMapping(value = "/update")
    public HttpResult<Boolean> updateById( @RequestBody TLqbzJygcjykrcgldj entity ){
        return new HttpResult<>(service.updateById(entity));
    }

    /**
    * 删除实体信息
    * @param id 实体ID
    * @return Boolean
    */
    @DeleteMapping(value = "/remove/{id}")
    public HttpResult<Boolean> removeById( @PathVariable Integer id ){
        return new HttpResult<>(service.removeById(id));
    }

    /**
    * 查找实体信息
    * @param id 实体ID
    * @return HttpResult
    */
    @GetMapping(value = "/get/{id}")
    public HttpResult<TLqbzJygcjykrcgldj> getById(@PathVariable Integer id ){
        return new HttpResult<>(service.getById(id));
    }


    /**
     * 分页查询信息
     * @return page
     */
    @GetMapping(value = "/pageList")
    public HttpPageResult<TLqbzJygcjykrcgldjDto> pageList(TLqbzJygcjykrcgldjParam param) {
        return new HttpPageResult<>(service.pageList(param));
    }

}

