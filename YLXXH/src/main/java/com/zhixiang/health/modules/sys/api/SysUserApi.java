package com.zhixiang.health.modules.sys.api;

import cn.hutool.core.util.StrUtil;
import com.zhixiang.health.common.http.abs.BaseHttp;
import com.zhixiang.health.common.http.result.HttpPageResult;
import com.zhixiang.health.common.http.result.HttpResult;
import com.zhixiang.health.common.model.annotation.Log;
import com.zhixiang.health.common.model.param.UserParam;
import com.zhixiang.health.common.utils.FileUtil;
import com.zhixiang.health.modules.log.model.enums.LogEventTypeEnum;
import com.zhixiang.health.modules.log.model.enums.LogModuleTypeEnum;
import com.zhixiang.health.modules.sys.model.dto.SysUserDto;
import com.zhixiang.health.modules.sys.model.entity.SysUser;
import com.zhixiang.health.modules.sys.model.param.SysUserParam;
import com.zhixiang.health.modules.sys.model.param.SysUserPasswordParam;
import com.zhixiang.health.modules.sys.model.param.SysUserSearchParam;
import com.zhixiang.health.modules.sys.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @Description: 系统用户管理 api
 * @Auther: HeJiawang
 * @Date: 2020-04-17
 */
@Slf4j
@RestController
@RequestMapping("/sys/user")
public class SysUserApi extends BaseHttp {

    @Value("${ylxxh.sys.user.photo}")
    private String userPhotoPath;

    /**
     * service
     */
    @Resource
    private ISysUserService service;

    /**
     * 分页查询信息
     * @param param 查询信息
     * @return page
     */
    @Log(value = "查看用户列表", eventType = LogEventTypeEnum.SELECT, moduleType = LogModuleTypeEnum.SYS)
    @GetMapping(value = "/page")
    public HttpPageResult<SysUserDto> page(SysUserSearchParam param) {
        return new HttpPageResult<>(service.page(param));
    }

    /**
     * 保存实体信息
     * @param param 实体信息
     * @return Boolean
     */
    @Log(value = "新增用户信息", eventType = LogEventTypeEnum.INSERT, moduleType = LogModuleTypeEnum.SYS)
    @PostMapping(value = "/save")
    public HttpResult<Boolean> save( @RequestBody SysUserParam param ){
        return new HttpResult<>(service.save(param));
    }

    /**
     * 修改实体信息
     * @param param 实体信息
     * @return Boolean
     */
    @Log(value = "修改用户信息", eventType = LogEventTypeEnum.UPDATE, moduleType = LogModuleTypeEnum.SYS)
    @PutMapping(value = "/update")
    public HttpResult<Boolean> updateById( @RequestBody SysUserParam param ){
        return new HttpResult<>(service.updateById(param));
    }

    /**
     * 修改个人信息
     * @param user 个人信息
     * @return boolean
     */
    @Log(value = "登录用户修改个人信息", eventType = LogEventTypeEnum.UPDATE, moduleType = LogModuleTypeEnum.SYS)
    @PutMapping(value = "/modify")
    public HttpResult<Boolean> modify(@RequestBody SysUser user) {
        return new HttpResult<>(service.updateById(user));
    }

    /**
     * 删除实体信息
     * @param id 实体ID
     * @return Boolean
     */
    @Log(value = "删除用户信息", eventType = LogEventTypeEnum.DELETE, moduleType = LogModuleTypeEnum.SYS)
    @DeleteMapping(value = "/remove/{id}")
    public HttpResult<Boolean> removeById( @PathVariable Integer id ){
        return new HttpResult<>(service.removeById(id));
    }

    /**
     * findById
     * @param id id
     * @return SysUser
     */
    @GetMapping(value = "/get/{id}")
    public HttpResult<SysUser> getById(@PathVariable Integer id ){
        return new HttpResult<>(service.getById(id));
    }

    /**
     * 停用用户
     * @param id 用户id
     * @return true
     */
    @Log(value = "停用用户信息", eventType = LogEventTypeEnum.UPDATE, moduleType = LogModuleTypeEnum.SYS)
    @GetMapping(value = "/pause/{id}")
    public HttpResult<Boolean> pauseById(@PathVariable Integer id ){
        return new HttpResult<>(service.pauseById(id));
    }

    /**
     * 启用用户
     * @param id 用户id
     * @return true
     */
    @Log(value = "启用用户信息", eventType = LogEventTypeEnum.UPDATE, moduleType = LogModuleTypeEnum.SYS)
    @GetMapping(value = "/resume/{id}")
    public HttpResult<Boolean> resumeById(@PathVariable Integer id ){
        return new HttpResult<>(service.resumeById(id));
    }

    /**
     * 校验用户登录名是否可用
     * @param user 用户id及登陆名称信息
     * @return true 登陆名称可用
     */
    @PostMapping(value = "/checkUserName")
    public HttpResult<Boolean> checkUserName(@RequestBody SysUser user) {
        return new HttpResult<>(service.checkUserName(user));
    }

    /**
     * 修改密码
     * @param user 用户id、登陆密码
     * @return true
     */
    @Log(value = "修改用户登录密码", eventType = LogEventTypeEnum.UPDATE, moduleType = LogModuleTypeEnum.SYS)
    @PostMapping(value = "/updatePassword")
    public HttpResult<Boolean> updatePassword(@RequestBody SysUser user) {
        return new HttpResult<>(service.updatePassword(user));
    }

    /**
     * 上传用户头像
     * @param uploadFile 文件信息
     * @return 头像链接地址
     */
    @PostMapping("/uploadPhoto")
    public HttpResult<String> uploadPhoto(@RequestParam(value = "file") MultipartFile uploadFile ){
        String fileUrl = FileUtil.uploadFile(uploadFile, userPhotoPath);
        return new HttpResult<>(fileUrl).setIsSuccess(StrUtil.isNotEmpty(fileUrl));
    }

    /**
     * 校验并修改当前登录人的登录密码
     * @param passwordParam 登录密码信息
     * @return boolean
     */
    @Log(value = "当前登录人修改个人登录密码", eventType = LogEventTypeEnum.UPDATE, moduleType = LogModuleTypeEnum.SYS)
    @PostMapping(value = "/modifyPassword")
    public HttpResult<Boolean> modifyPassword(@RequestBody SysUserPasswordParam passwordParam, UserParam userParam) {
        passwordParam.setId(userParam.getId());

        return service.modifyPassword(passwordParam).convert();
    }

    /**
     * 校验原始密码是否正确
     * @param passwordParam passwordParam
     * @param userParam userParam
     * @return true 原始密码正确
     */
    @PostMapping(value = "/checkPassword")
    public HttpResult<Boolean> checkPassword(@RequestBody SysUserPasswordParam passwordParam, UserParam userParam) {
        passwordParam.setId(userParam.getId());

        return new HttpResult<>(service.checkPassword(passwordParam));
    }
}
