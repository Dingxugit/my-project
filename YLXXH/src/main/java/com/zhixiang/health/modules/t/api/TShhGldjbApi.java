package com.zhixiang.health.modules.t.api;

import com.zhixiang.health.common.http.pagination.Page;
import com.zhixiang.health.common.http.result.HttpPageResult;
import com.zhixiang.health.common.http.result.HttpResult;
import com.zhixiang.health.common.model.param.UserParam;
import com.zhixiang.health.modules.t.model.dto.TLqbzJygcjykrcgldjDto;
import com.zhixiang.health.modules.t.model.dto.TShhGldjbDto;
import com.zhixiang.health.modules.t.model.entity.TLqbzJygcjykrcgldj;
import com.zhixiang.health.modules.t.model.entity.TShhGldjb;
import com.zhixiang.health.modules.t.model.param.TLqbzJygcjykrcgldjParam;
import com.zhixiang.health.modules.t.model.param.TShhGldjbParam;
import com.zhixiang.health.modules.t.service.ITShhGldjbService;
import org.springframework.web.bind.annotation.*;

import com.zhixiang.health.common.http.abs.BaseHttp;

import javax.annotation.Resource;

/**
 * <p>
 * 油料社会化保障加油卡日常管理登记表 前端控制器
 * </p>
 *
 * @author Dingx
 * @since 2021-06-20
 */
@RestController
@RequestMapping("/t/shh/gldjb")
public class TShhGldjbApi extends BaseHttp {

    @Resource
    private ITShhGldjbService service;

    /**
     * 分页查询信息
     * @param page page
     * @param entity 实体信息
     * @return page
     */
    @GetMapping(value = "/page")
    public HttpPageResult<TShhGldjb> page(Page<TShhGldjb> page, TShhGldjb entity) {
        return new HttpPageResult<>(service.page(page));
    }

    /**
     * 保存实体信息
     * @param entity 实体信息
     * @return Boolean
     */
    @PostMapping(value = "/save")
    public HttpResult<Boolean> save(@RequestBody TShhGldjb entity ){
        return new HttpResult<>(service.save(entity));
    }

    /**
     * 修改实体信息
     * @param entity 实体信息
     * @return Boolean
     */
    @PutMapping(value = "/update")
    public HttpResult<Boolean> updateById( @RequestBody TShhGldjb entity ){
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
    public HttpResult<TShhGldjb> getById(@PathVariable Integer id ){
        return new HttpResult<>(service.getById(id));
    }

    /**
     * 分页查询信息
     * @return page
     */
    @GetMapping(value = "/pageList")
    public HttpPageResult<TShhGldjbDto> pageList(TShhGldjbParam param) {
        return new HttpPageResult<>(service.pageList(param));
    }

    /**
     * 保存实体信息
     * @param entity 实体信息
     * @return Boolean
     */
    @PostMapping(value = "/sjdwSave")
    public HttpResult<Boolean> sjdwSave(@RequestBody TShhGldjb entity, UserParam param){
        return new HttpResult<>(service.sjdwSave(entity,param));
    }

    /**
     * 修改实体信息
     * @param entity 实体信息
     * @return Boolean
     */
    @PutMapping(value = "/sjdwUpdate")
    public HttpResult<Boolean> sjdwUpdate( @RequestBody TShhGldjb entity, UserParam param){
        return new HttpResult<>(service.sjdwUpdate(entity,param));
    }

}

