/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50721
Source Host           : localhost:3306
Source Database       : jmonkey3

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2020-07-10 13:33:02
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for log_login
-- ----------------------------
DROP TABLE IF EXISTS `log_login`;
CREATE TABLE `log_login` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `year` int(4) NOT NULL COMMENT '年',
  `month` int(2) NOT NULL COMMENT '月',
  `day` int(2) NOT NULL COMMENT '日',
  `user_name` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户登陆名称',
  `result` int(1) DEFAULT '0' COMMENT '登录结果 0失败 1成功',
  `code` int(5) DEFAULT '0' COMMENT '结果编码',
  `message` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '结果信息',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建人',
  `update_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_by` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '修改人',
  `remark` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
  `delete_state` int(1) DEFAULT '0' COMMENT '使用状态 1已删除 0未删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='登录日志';

-- ----------------------------
-- Records of log_login
-- ----------------------------

-- ----------------------------
-- Table structure for sys_button
-- ----------------------------
DROP TABLE IF EXISTS `sys_button`;
CREATE TABLE `sys_button` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '按钮名称',
  `permission` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '权限标识',
  `url` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '请求链接',
  `method` int(1) DEFAULT '1' COMMENT '请求方法  1:Get，2:Post，3:Put，4:Delete',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建人',
  `update_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '修改人',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
  `delete_state` int(1) DEFAULT '0' COMMENT '使用状态 1已删除 0未删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='按钮及访问权限配置';

-- ----------------------------
-- Records of sys_button
-- ----------------------------
INSERT INTO `sys_button` VALUES ('6', 'GET', '', '/sys/**', '1', '2020-07-02 13:49:56', 'admin', '2020-07-03 17:04:41', null, '', '0');
INSERT INTO `sys_button` VALUES ('7', 'POST', '', '/sys/**', '2', '2020-07-02 13:50:08', 'admin', '2020-07-03 17:04:42', null, '', '0');
INSERT INTO `sys_button` VALUES ('8', 'DELETE', '', '/sys/**', '4', '2020-07-02 13:50:25', 'admin', '2020-07-03 17:04:44', null, '', '0');
INSERT INTO `sys_button` VALUES ('9', 'PUT', '', '/sys/**', '3', '2020-07-02 13:50:38', 'admin', '2020-07-03 17:04:45', null, '', '0');
INSERT INTO `sys_button` VALUES ('10', '导入导出用户权限', 'auth_sys_user', '/sys/user/save', '2', '2020-07-02 13:51:16', 'admin', '2020-07-06 09:57:53', 'admin', '', '0');
INSERT INTO `sys_button` VALUES ('11', 'GET', '', '/log/**', '1', '2020-07-08 11:28:05', 'admin', null, null, '', '0');
INSERT INTO `sys_button` VALUES ('12', 'POST', '', '/log/**', '1', '2020-07-08 11:28:16', 'admin', null, null, '', '0');
INSERT INTO `sys_button` VALUES ('13', 'PUT', '', '/log/**', '1', '2020-07-08 11:28:23', 'admin', null, null, '', '0');
INSERT INTO `sys_button` VALUES ('14', 'DELETE', '', '/log/**', '4', '2020-07-08 11:28:35', 'admin', null, null, '', '0');

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `parent_id` int(11) DEFAULT '0' COMMENT '上级部门ID, 0为顶级部门',
  `name` varchar(255) COLLATE utf8mb4_bin NOT NULL COMMENT '部门名称',
  `simple_name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '机构部门简称',
  `code` varchar(64) COLLATE utf8mb4_bin NOT NULL COMMENT '部门编码',
  `sort` int(11) DEFAULT NULL COMMENT '排序值',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建人',
  `update_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_by` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '修改人',
  `remark` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
  `delete_state` int(1) DEFAULT '0' COMMENT '使用状态 1已删除 0未删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='部门信息';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES ('1', '0', '北京研发中心', '北京研发', '1', '1', '2020-05-03 18:55:01', null, '2020-07-10 09:51:52', 'admin', '', '0');
INSERT INTO `sys_dept` VALUES ('2', '0', '沈阳研发中心', '沈阳研发中心', '2', '2', '2020-05-03 18:55:12', null, '2020-07-10 09:47:05', null, '', '0');
INSERT INTO `sys_dept` VALUES ('3', '1', '产品评定组', '产品评定组', '1-1', '1', '2020-05-03 18:55:30', null, '2020-07-10 09:47:06', null, '', '1');
INSERT INTO `sys_dept` VALUES ('4', '1', '研发组', '研发组', '1001', '1', '2020-07-10 09:16:41', 'admin', '2020-07-10 09:47:08', null, '', '0');
INSERT INTO `sys_dept` VALUES ('5', '1', '测试组', '测试组', '1002', '2', '2020-07-10 09:16:52', 'admin', '2020-07-10 09:47:09', null, '', '0');
INSERT INTO `sys_dept` VALUES ('6', '1', '产品组', '产品组', '1003', '3', '2020-07-10 09:17:06', 'admin', '2020-07-10 09:47:10', null, '', '0');
INSERT INTO `sys_dept` VALUES ('7', '2', '销售组', '销售组', '2001', '1', '2020-07-10 09:17:19', 'admin', '2020-07-10 09:47:12', null, '', '0');

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `parent_id` int(11) DEFAULT '0' COMMENT '上级字典ID, 0为顶级字典定义',
  `label` varchar(255) COLLATE utf8mb4_bin NOT NULL COMMENT '字典标签',
  `value` varchar(64) COLLATE utf8mb4_bin NOT NULL COMMENT '字典键值',
  `sort` int(11) DEFAULT '0' COMMENT '排序值',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建人',
  `update_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_by` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '修改人',
  `remark` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
  `delete_state` int(1) DEFAULT '0' COMMENT '使用状态 1已删除 0未删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='字典表';

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES ('1', '0', '系统设置-定时任务分组', 'sys_task_group', '1', '2020-05-04 17:15:51', null, null, null, '', '0');
INSERT INTO `sys_dict` VALUES ('2', '1', '系统任务', 'sys_task_group_1', '1', '2020-05-04 17:15:51', null, null, null, '系统设置模块中的任务', '0');
INSERT INTO `sys_dict` VALUES ('3', '0', '系统设置-系统类型', 'sys_system_type', '2', '2020-06-26 16:32:32', null, null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('4', '3', '基础支撑', 'sys_system_type_1', '1', '2020-06-26 16:33:05', null, null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('5', '3', '数据分析', 'sys_system_type_2', '2', '2020-06-26 16:33:22', null, '2020-07-02 13:33:36', 'admin', null, '0');
INSERT INTO `sys_dict` VALUES ('6', '3', '医疗保障', 'sys_system_type_3', '3', '2020-06-26 16:33:38', null, '2020-07-02 13:33:17', null, null, '1');
INSERT INTO `sys_dict` VALUES ('7', '3', '演示页面', 'sys_system_type_3', '3', '2020-07-07 14:48:41', 'admin', null, null, null, '0');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '菜单名称',
  `icon` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '图标',
  `path` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'router路径',
  `component` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '组件路径',
  `is_index` int(1) DEFAULT '0' COMMENT '是否是主页，1是，0否',
  `show_type` int(1) DEFAULT '0' COMMENT '页面展示方式  0 HOME  1 SCREEN',
  `sort` int(11) DEFAULT NULL COMMENT '排序值',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建人',
  `update_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '修改人',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
  `delete_state` int(1) DEFAULT '0' COMMENT '使用状态 1已删除 0未删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='菜单配置';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('9', '字典管理', '/menu/sys/dict.png', '/sys/dict', '/sys/dict', '1', '0', '1', '2020-07-02 13:40:43', 'admin', null, null, '', '0');
INSERT INTO `sys_menu` VALUES ('10', '系统配置', '/menu/sys/sys.png', '/sysset', '', '0', '0', '2', '2020-07-02 13:41:35', 'admin', '2020-07-03 15:12:33', null, '', '0');
INSERT INTO `sys_menu` VALUES ('11', '系统管理', '/menu/sys/system.png', '/sysset/system', '/sys/system', '0', '0', '3', '2020-07-02 13:42:02', 'admin', '2020-07-03 16:22:51', 'admin', '', '0');
INSERT INTO `sys_menu` VALUES ('12', '菜单管理', '/menu/sys/menu.png', '/sysset/menu', '/sys/menu', '0', '0', '2', '2020-07-02 13:42:26', 'admin', '2020-07-03 15:12:43', null, '', '0');
INSERT INTO `sys_menu` VALUES ('13', '按钮权限', '/menu/sys/button.png', '/sysset/button', '/sys/button', '0', '0', '1', '2020-07-02 13:42:44', 'admin', '2020-07-03 16:23:03', 'admin', '', '0');
INSERT INTO `sys_menu` VALUES ('14', '用户管理', '/menu/sys/user.png', '/sys/user', '/sys/user', '1', '0', '1', '2020-07-02 13:43:17', 'admin', null, null, '', '0');
INSERT INTO `sys_menu` VALUES ('15', '角色管理', '/menu/sys/role.png', '/sys/role', '/sys/role', '0', '0', '2', '2020-07-02 13:43:38', 'admin', null, null, '', '0');
INSERT INTO `sys_menu` VALUES ('16', '机构部门', '/menu/sys/dept.png', '/sys/dept', '/sys/dept', '0', '0', '3', '2020-07-02 13:43:57', 'admin', null, null, '', '0');
INSERT INTO `sys_menu` VALUES ('17', '定时任务', '/menu/sys/task.png', '/sys/task', '/sys/task', '1', '0', '1', '2020-07-02 13:44:21', 'admin', '2020-07-06 08:31:34', 'admin', '', '0');
INSERT INTO `sys_menu` VALUES ('18', '系统分析', '/menu/sys/tjfx.png', '/sys/home', '/sys/home', '1', '0', '1', '2020-07-02 13:45:48', 'admin', '2020-07-09 10:02:31', 'admin', '', '0');
INSERT INTO `sys_menu` VALUES ('19', '首页示例', '/menu/ys/ys.png', '/example/dashboard', '', '1', '0', '1', '2020-07-07 14:55:07', 'admin', '2020-07-07 16:18:53', 'admin', '', '0');
INSERT INTO `sys_menu` VALUES ('20', '分析页', '/menu/ys/ys.png', '/example/dashboard/analysis', '/example/dashboard/analysis', '1', '0', '3', '2020-07-07 14:55:42', 'admin', '2020-07-07 15:03:51', 'admin', '', '0');
INSERT INTO `sys_menu` VALUES ('21', '工作台', '/menu/ys/ys.png', '/example/dashboard/workplace', '/example/dashboard/workplace', '0', '0', '2', '2020-07-07 14:56:08', 'admin', null, null, '', '0');
INSERT INTO `sys_menu` VALUES ('22', '监控页', '/menu/ys/ys.png', '/example/dashboard/monitor', '/example/dashboard/monitor', '0', '0', '1', '2020-07-07 15:03:03', 'admin', '2020-07-07 15:03:46', 'admin', '', '0');
INSERT INTO `sys_menu` VALUES ('23', '表单页', '/menu/ys/ys.png', '/example/form', '', '0', '0', '2', '2020-07-07 15:05:15', 'admin', null, null, '', '0');
INSERT INTO `sys_menu` VALUES ('24', '基础表单', '/menu/ys/ys.png', '/example/form/basic-form', '/example/form/basic-form', '0', '0', '1', '2020-07-07 15:05:37', 'admin', null, null, '', '0');
INSERT INTO `sys_menu` VALUES ('25', '分步表单', '/menu/ys/ys.png', '/example/form/step-form', '/example/form/step-form', '0', '0', '2', '2020-07-07 15:06:00', 'admin', null, null, '', '0');
INSERT INTO `sys_menu` VALUES ('26', '高级表单', '/menu/ys/ys.png', '/example/form/advanced-form', '/example/form/advanced-form', '0', '0', '3', '2020-07-07 15:06:26', 'admin', null, null, '', '0');
INSERT INTO `sys_menu` VALUES ('27', '列表页', '/menu/ys/ys.png', '/example/list', '', '0', '0', '3', '2020-07-07 15:07:48', 'admin', null, null, '', '0');
INSERT INTO `sys_menu` VALUES ('28', '搜索列表', '/menu/ys/ys.png', '/example/list/search', '/example/list/search', '0', '0', '1', '2020-07-07 15:08:26', 'admin', null, null, '', '0');
INSERT INTO `sys_menu` VALUES ('29', '搜索列表（文章）', '/menu/ys/ys.png', '/example/list/search/articles', '/example/list/search/articles', '0', '0', '1', '2020-07-07 15:08:49', 'admin', null, null, '', '0');
INSERT INTO `sys_menu` VALUES ('30', '搜索列表（项目）', '/menu/ys/ys.png', '/example/list/search/projects', '/example/list/search/projects', '0', '0', '2', '2020-07-07 15:09:12', 'admin', null, null, '', '0');
INSERT INTO `sys_menu` VALUES ('31', '搜索列表（应用）', '/menu/ys/ys.png', '/example/list/search/applications', '/example/list/search/applications', '0', '0', '3', '2020-07-07 15:09:32', 'admin', null, null, '', '0');
INSERT INTO `sys_menu` VALUES ('32', '查询表格', '/menu/ys/ys.png', '/example/list/table-list', '/example/list/table-list', '0', '0', '2', '2020-07-07 15:10:26', 'admin', null, null, '', '0');
INSERT INTO `sys_menu` VALUES ('33', '标准列表', '/menu/ys/ys.png', '/example/list/basic-list', '/example/list/basic-list', '0', '0', '3', '2020-07-07 15:10:49', 'admin', null, null, '', '0');
INSERT INTO `sys_menu` VALUES ('34', '卡片列表', '/menu/ys/ys.png', '/example/list/card-list', '/example/list/card-list', '0', '0', '4', '2020-07-07 15:11:06', 'admin', null, null, '', '0');
INSERT INTO `sys_menu` VALUES ('35', '详情页', '/menu/ys/ys.png', '/example/profile', '', '0', '0', '4', '2020-07-07 15:15:43', 'admin', null, null, '', '0');
INSERT INTO `sys_menu` VALUES ('36', '基础详情页', '/menu/ys/ys.png', '/example/profile/basic', '/example/profile/basic', '0', '0', '1', '2020-07-07 15:16:00', 'admin', null, null, '', '0');
INSERT INTO `sys_menu` VALUES ('37', '高级详情页', '/menu/ys/ys.png', '/example/profile/advanced', '/example/profile/advanced', '0', '0', '2', '2020-07-07 15:16:17', 'admin', null, null, '', '0');
INSERT INTO `sys_menu` VALUES ('38', '结果页', '/menu/ys/ys.png', '/example/result', '', '0', '0', '5', '2020-07-07 15:17:00', 'admin', null, null, '', '0');
INSERT INTO `sys_menu` VALUES ('39', '成功页', '/menu/ys/ys.png', '/example/result/success', '/example/result/success', '0', '0', '1', '2020-07-07 15:17:24', 'admin', null, null, '', '0');
INSERT INTO `sys_menu` VALUES ('40', '失败页', '/menu/ys/ys.png', '/example/result/fail', '/example/result/fail', '0', '0', '2', '2020-07-07 15:17:45', 'admin', null, null, '', '0');
INSERT INTO `sys_menu` VALUES ('41', '异常页', '/menu/ys/ys.png', '/example/exception', '', '0', '0', '6', '2020-07-07 15:18:34', 'admin', null, null, '', '0');
INSERT INTO `sys_menu` VALUES ('42', '403', '/menu/ys/ys.png', '/example/exception/403', '/example/exception/403', '0', '0', '1', '2020-07-07 15:18:49', 'admin', null, null, '', '0');
INSERT INTO `sys_menu` VALUES ('43', '404', '/menu/ys/ys.png', '/example/exception/404', '/example/exception/404', '0', '0', '2', '2020-07-07 15:19:10', 'admin', null, null, '', '0');
INSERT INTO `sys_menu` VALUES ('44', '500', '/menu/ys/ys.png', '/example/exception/500', '/example/exception/500', '0', '0', '3', '2020-07-07 15:19:31', 'admin', null, null, '', '0');
INSERT INTO `sys_menu` VALUES ('45', '个人页', '/menu/ys/ys.png', '/example/account', '', '0', '0', '7', '2020-07-07 15:20:43', 'admin', null, null, '', '0');
INSERT INTO `sys_menu` VALUES ('46', '个人中心', '/menu/ys/ys.png', '/example/account/center', '/example/account/center', '0', '0', '1', '2020-07-07 15:20:57', 'admin', null, null, '', '0');
INSERT INTO `sys_menu` VALUES ('47', '个人设置', '/menu/ys/ys.png', '/example/account/settings', '/example/account/settings', '0', '0', '2', '2020-07-07 15:21:16', 'admin', null, null, '', '0');
INSERT INTO `sys_menu` VALUES ('48', '图形编辑器', '/menu/ys/ys.png', '/example/editor', '', '0', '0', '8', '2020-07-07 15:21:43', 'admin', null, null, '', '0');
INSERT INTO `sys_menu` VALUES ('49', '流程编辑器', '/menu/ys/ys.png', '/example/editor/flow', '/example/editor/flow', '0', '0', '1', '2020-07-07 15:22:32', 'admin', null, null, '', '0');
INSERT INTO `sys_menu` VALUES ('50', '脑图编辑器', '/menu/ys/ys.png', '/example/editor/mind', '/example/editor/mind', '0', '0', '2', '2020-07-07 15:23:14', 'admin', null, null, '', '0');
INSERT INTO `sys_menu` VALUES ('51', '拓扑编辑器', '/menu/ys/ys.png', '/example/editor/koni', '/example/editor/koni', '0', '0', '3', '2020-07-07 15:23:28', 'admin', null, null, '', '0');
INSERT INTO `sys_menu` VALUES ('52', '操作日志', '/menu/log/handle.png', '/log/handle', '/log/handle', '1', '0', '1', '2020-07-08 11:27:17', 'admin', null, null, '', '0');
INSERT INTO `sys_menu` VALUES ('53', '登录日志', '/menu/log/login.png', '/log/login', '/log/login', '0', '0', '2', '2020-07-08 11:27:49', 'admin', null, null, '', '0');

-- ----------------------------
-- Table structure for sys_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_resource`;
CREATE TABLE `sys_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `parent_id` int(11) DEFAULT '0' COMMENT '父资源ID，指向sys_resource.id',
  `type` int(1) DEFAULT '1' COMMENT '资源类型 1:System 2:Menu 3:Button',
  `resource_id` int(11) DEFAULT NULL COMMENT '资源id 例 type==System r_id==sys_system.id',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建人',
  `update_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '修改人',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
  `delete_state` int(1) DEFAULT '0' COMMENT '使用状态 1已删除 0未删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=86 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='系统资源表';

-- ----------------------------
-- Records of sys_resource
-- ----------------------------
INSERT INTO `sys_resource` VALUES ('25', '0', '1', '13', '2020-07-02 13:07:32', 'admin', null, null, null, '0');
INSERT INTO `sys_resource` VALUES ('26', '0', '1', '14', '2020-07-02 13:21:12', 'admin', null, null, null, '0');
INSERT INTO `sys_resource` VALUES ('27', '0', '1', '15', '2020-07-02 13:32:56', 'admin', null, null, null, '0');
INSERT INTO `sys_resource` VALUES ('28', '0', '1', '16', '2020-07-02 13:34:39', 'admin', null, null, null, '0');
INSERT INTO `sys_resource` VALUES ('29', '25', '2', '9', '2020-07-02 13:40:43', 'admin', null, null, null, '0');
INSERT INTO `sys_resource` VALUES ('30', '25', '2', '10', '2020-07-02 13:41:35', 'admin', null, null, null, '0');
INSERT INTO `sys_resource` VALUES ('31', '30', '2', '11', '2020-07-02 13:42:02', 'admin', null, null, null, '0');
INSERT INTO `sys_resource` VALUES ('32', '30', '2', '12', '2020-07-02 13:42:26', 'admin', null, null, null, '0');
INSERT INTO `sys_resource` VALUES ('33', '30', '2', '13', '2020-07-02 13:42:44', 'admin', null, null, null, '0');
INSERT INTO `sys_resource` VALUES ('34', '26', '2', '14', '2020-07-02 13:43:17', 'admin', null, null, null, '0');
INSERT INTO `sys_resource` VALUES ('35', '26', '2', '15', '2020-07-02 13:43:38', 'admin', null, null, null, '0');
INSERT INTO `sys_resource` VALUES ('36', '26', '2', '16', '2020-07-02 13:43:57', 'admin', null, null, null, '0');
INSERT INTO `sys_resource` VALUES ('37', '27', '2', '17', '2020-07-02 13:44:21', 'admin', null, null, null, '0');
INSERT INTO `sys_resource` VALUES ('38', '28', '2', '18', '2020-07-02 13:45:48', 'admin', null, null, null, '0');
INSERT INTO `sys_resource` VALUES ('39', '25', '3', '6', '2020-07-02 13:49:56', 'admin', null, null, null, '0');
INSERT INTO `sys_resource` VALUES ('40', '25', '3', '7', '2020-07-02 13:50:08', 'admin', null, null, null, '0');
INSERT INTO `sys_resource` VALUES ('41', '25', '3', '8', '2020-07-02 13:50:25', 'admin', null, null, null, '0');
INSERT INTO `sys_resource` VALUES ('42', '25', '3', '9', '2020-07-02 13:50:38', 'admin', null, null, null, '0');
INSERT INTO `sys_resource` VALUES ('43', '34', '3', '10', '2020-07-02 13:51:16', 'admin', null, null, null, '0');
INSERT INTO `sys_resource` VALUES ('44', '0', '1', '17', '2020-07-03 15:55:52', 'admin', null, null, null, '0');
INSERT INTO `sys_resource` VALUES ('45', '0', '1', '18', '2020-07-07 14:51:06', 'admin', null, null, null, '0');
INSERT INTO `sys_resource` VALUES ('46', '45', '2', '19', '2020-07-07 14:55:07', 'admin', null, null, null, '0');
INSERT INTO `sys_resource` VALUES ('47', '46', '2', '20', '2020-07-07 14:55:42', 'admin', null, null, null, '0');
INSERT INTO `sys_resource` VALUES ('48', '46', '2', '21', '2020-07-07 14:56:08', 'admin', null, null, null, '0');
INSERT INTO `sys_resource` VALUES ('49', '46', '2', '22', '2020-07-07 15:03:03', 'admin', null, null, null, '0');
INSERT INTO `sys_resource` VALUES ('50', '45', '2', '23', '2020-07-07 15:05:15', 'admin', null, null, null, '0');
INSERT INTO `sys_resource` VALUES ('51', '50', '2', '24', '2020-07-07 15:05:37', 'admin', null, null, null, '0');
INSERT INTO `sys_resource` VALUES ('52', '50', '2', '25', '2020-07-07 15:06:00', 'admin', null, null, null, '0');
INSERT INTO `sys_resource` VALUES ('53', '50', '2', '26', '2020-07-07 15:06:26', 'admin', null, null, null, '0');
INSERT INTO `sys_resource` VALUES ('54', '45', '2', '27', '2020-07-07 15:07:48', 'admin', null, null, null, '0');
INSERT INTO `sys_resource` VALUES ('55', '54', '2', '28', '2020-07-07 15:08:26', 'admin', null, null, null, '0');
INSERT INTO `sys_resource` VALUES ('56', '55', '2', '29', '2020-07-07 15:08:49', 'admin', null, null, null, '0');
INSERT INTO `sys_resource` VALUES ('57', '55', '2', '30', '2020-07-07 15:09:12', 'admin', null, null, null, '0');
INSERT INTO `sys_resource` VALUES ('58', '55', '2', '31', '2020-07-07 15:09:32', 'admin', null, null, null, '0');
INSERT INTO `sys_resource` VALUES ('59', '54', '2', '32', '2020-07-07 15:10:26', 'admin', null, null, null, '0');
INSERT INTO `sys_resource` VALUES ('60', '54', '2', '33', '2020-07-07 15:10:49', 'admin', null, null, null, '0');
INSERT INTO `sys_resource` VALUES ('61', '54', '2', '34', '2020-07-07 15:11:06', 'admin', null, null, null, '0');
INSERT INTO `sys_resource` VALUES ('62', '45', '2', '35', '2020-07-07 15:15:43', 'admin', null, null, null, '0');
INSERT INTO `sys_resource` VALUES ('63', '62', '2', '36', '2020-07-07 15:16:00', 'admin', null, null, null, '0');
INSERT INTO `sys_resource` VALUES ('64', '62', '2', '37', '2020-07-07 15:16:17', 'admin', null, null, null, '0');
INSERT INTO `sys_resource` VALUES ('65', '45', '2', '38', '2020-07-07 15:17:00', 'admin', null, null, null, '0');
INSERT INTO `sys_resource` VALUES ('66', '65', '2', '39', '2020-07-07 15:17:24', 'admin', null, null, null, '0');
INSERT INTO `sys_resource` VALUES ('67', '65', '2', '40', '2020-07-07 15:17:45', 'admin', null, null, null, '0');
INSERT INTO `sys_resource` VALUES ('68', '45', '2', '41', '2020-07-07 15:18:34', 'admin', null, null, null, '0');
INSERT INTO `sys_resource` VALUES ('69', '68', '2', '42', '2020-07-07 15:18:49', 'admin', null, null, null, '0');
INSERT INTO `sys_resource` VALUES ('70', '68', '2', '43', '2020-07-07 15:19:10', 'admin', null, null, null, '0');
INSERT INTO `sys_resource` VALUES ('71', '68', '2', '44', '2020-07-07 15:19:31', 'admin', null, null, null, '0');
INSERT INTO `sys_resource` VALUES ('72', '45', '2', '45', '2020-07-07 15:20:43', 'admin', null, null, null, '0');
INSERT INTO `sys_resource` VALUES ('73', '72', '2', '46', '2020-07-07 15:20:57', 'admin', null, null, null, '0');
INSERT INTO `sys_resource` VALUES ('74', '72', '2', '47', '2020-07-07 15:21:16', 'admin', null, null, null, '0');
INSERT INTO `sys_resource` VALUES ('75', '45', '2', '48', '2020-07-07 15:21:43', 'admin', null, null, null, '0');
INSERT INTO `sys_resource` VALUES ('76', '75', '2', '49', '2020-07-07 15:22:32', 'admin', null, null, null, '0');
INSERT INTO `sys_resource` VALUES ('77', '75', '2', '50', '2020-07-07 15:23:14', 'admin', null, null, null, '0');
INSERT INTO `sys_resource` VALUES ('78', '75', '2', '51', '2020-07-07 15:23:28', 'admin', null, null, null, '0');
INSERT INTO `sys_resource` VALUES ('79', '0', '1', '19', '2020-07-08 11:26:35', 'admin', null, null, null, '0');
INSERT INTO `sys_resource` VALUES ('80', '79', '2', '52', '2020-07-08 11:27:17', 'admin', null, null, null, '0');
INSERT INTO `sys_resource` VALUES ('81', '79', '2', '53', '2020-07-08 11:27:49', 'admin', null, null, null, '0');
INSERT INTO `sys_resource` VALUES ('82', '79', '3', '11', '2020-07-08 11:28:06', 'admin', null, null, null, '0');
INSERT INTO `sys_resource` VALUES ('83', '79', '3', '12', '2020-07-08 11:28:16', 'admin', null, null, null, '0');
INSERT INTO `sys_resource` VALUES ('84', '79', '3', '13', '2020-07-08 11:28:23', 'admin', null, null, null, '0');
INSERT INTO `sys_resource` VALUES ('85', '79', '3', '14', '2020-07-08 11:28:35', 'admin', null, null, null, '0');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '角色名称',
  `code` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '角色编码',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建人',
  `update_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_by` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '修改人',
  `remark` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
  `delete_state` int(1) DEFAULT '0' COMMENT '使用状态 1已删除 0未删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='系统角色信息表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '超级管理员', 'admin', '2020-05-03 15:32:23', null, '2020-07-01 10:45:25', null, '系统权限最高的角色，为开发人员配置系统使用，禁止修改，禁止用户使用。', '0');
INSERT INTO `sys_role` VALUES ('2', '测试角色', 'test', '2020-05-04 19:19:17', null, '2020-06-24 16:07:19', null, '查看 ant design pro 原生页面', '0');
INSERT INTO `sys_role` VALUES ('3', 'sdf ', 'gfdsg', '2020-06-30 13:37:29', 'admin', '2020-06-30 13:39:04', null, '', '1');
INSERT INTO `sys_role` VALUES ('4', '测试角色2', 'tt', '2020-07-01 10:30:23', 'admin', '2020-07-08 09:20:44', null, '', '1');

-- ----------------------------
-- Table structure for sys_role_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_resource`;
CREATE TABLE `sys_role_resource` (
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `resource_id` int(11) NOT NULL COMMENT '资源ID',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建人',
  `update_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '修改人',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
  `delete_state` int(1) DEFAULT '0' COMMENT '使用状态 1已删除 0未删除',
  PRIMARY KEY (`role_id`,`resource_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='角色权限表';

-- ----------------------------
-- Records of sys_role_resource
-- ----------------------------
INSERT INTO `sys_role_resource` VALUES ('1', '25', '2020-07-08 11:28:57', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1', '26', '2020-07-08 11:28:57', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1', '27', '2020-07-08 11:28:57', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1', '28', '2020-07-08 11:28:57', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1', '29', '2020-07-08 11:28:57', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1', '30', '2020-07-08 11:28:57', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1', '31', '2020-07-08 11:28:57', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1', '32', '2020-07-08 11:28:57', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1', '33', '2020-07-08 11:28:57', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1', '34', '2020-07-08 11:28:57', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1', '35', '2020-07-08 11:28:57', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1', '36', '2020-07-08 11:28:57', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1', '37', '2020-07-08 11:28:57', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1', '38', '2020-07-08 11:28:57', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1', '39', '2020-07-08 11:28:57', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1', '40', '2020-07-08 11:28:57', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1', '41', '2020-07-08 11:28:57', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1', '42', '2020-07-08 11:28:57', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1', '43', '2020-07-08 11:28:57', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1', '44', '2020-07-08 11:28:57', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1', '45', '2020-07-08 11:28:57', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1', '46', '2020-07-08 11:28:57', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1', '47', '2020-07-08 11:28:57', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1', '48', '2020-07-08 11:28:57', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1', '49', '2020-07-08 11:28:57', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1', '50', '2020-07-08 11:28:57', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1', '51', '2020-07-08 11:28:57', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1', '52', '2020-07-08 11:28:57', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1', '53', '2020-07-08 11:28:57', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1', '54', '2020-07-08 11:28:57', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1', '55', '2020-07-08 11:28:57', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1', '56', '2020-07-08 11:28:57', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1', '57', '2020-07-08 11:28:57', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1', '58', '2020-07-08 11:28:57', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1', '59', '2020-07-08 11:28:57', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1', '60', '2020-07-08 11:28:57', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1', '61', '2020-07-08 11:28:57', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1', '62', '2020-07-08 11:28:57', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1', '63', '2020-07-08 11:28:57', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1', '64', '2020-07-08 11:28:57', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1', '65', '2020-07-08 11:28:57', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1', '66', '2020-07-08 11:28:57', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1', '67', '2020-07-08 11:28:57', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1', '68', '2020-07-08 11:28:57', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1', '69', '2020-07-08 11:28:57', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1', '70', '2020-07-08 11:28:57', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1', '71', '2020-07-08 11:28:57', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1', '72', '2020-07-08 11:28:57', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1', '73', '2020-07-08 11:28:57', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1', '74', '2020-07-08 11:28:57', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1', '75', '2020-07-08 11:28:57', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1', '76', '2020-07-08 11:28:57', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1', '77', '2020-07-08 11:28:57', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1', '78', '2020-07-08 11:28:57', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1', '79', '2020-07-08 11:28:57', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1', '80', '2020-07-08 11:28:57', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1', '81', '2020-07-08 11:28:57', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1', '82', '2020-07-08 11:28:57', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1', '83', '2020-07-08 11:28:57', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1', '84', '2020-07-08 11:28:57', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1', '85', '2020-07-08 11:28:57', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('2', '39', '2020-07-07 16:25:09', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('2', '40', '2020-07-07 16:25:09', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('2', '41', '2020-07-07 16:25:09', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('2', '42', '2020-07-07 16:25:09', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('2', '45', '2020-07-07 16:25:09', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('2', '46', '2020-07-07 16:25:09', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('2', '47', '2020-07-07 16:25:09', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('2', '48', '2020-07-07 16:25:09', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('2', '49', '2020-07-07 16:25:09', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('2', '50', '2020-07-07 16:25:09', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('2', '51', '2020-07-07 16:25:09', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('2', '52', '2020-07-07 16:25:09', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('2', '53', '2020-07-07 16:25:09', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('2', '54', '2020-07-07 16:25:09', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('2', '55', '2020-07-07 16:25:09', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('2', '56', '2020-07-07 16:25:09', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('2', '57', '2020-07-07 16:25:09', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('2', '58', '2020-07-07 16:25:09', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('2', '59', '2020-07-07 16:25:09', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('2', '60', '2020-07-07 16:25:09', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('2', '61', '2020-07-07 16:25:09', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('2', '62', '2020-07-07 16:25:09', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('2', '63', '2020-07-07 16:25:09', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('2', '64', '2020-07-07 16:25:09', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('2', '65', '2020-07-07 16:25:09', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('2', '66', '2020-07-07 16:25:09', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('2', '67', '2020-07-07 16:25:09', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('2', '68', '2020-07-07 16:25:09', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('2', '69', '2020-07-07 16:25:09', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('2', '70', '2020-07-07 16:25:09', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('2', '71', '2020-07-07 16:25:09', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('2', '72', '2020-07-07 16:25:09', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('2', '73', '2020-07-07 16:25:09', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('2', '74', '2020-07-07 16:25:09', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('2', '75', '2020-07-07 16:25:09', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('2', '76', '2020-07-07 16:25:09', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('2', '77', '2020-07-07 16:25:09', 'admin', null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('2', '78', '2020-07-07 16:25:09', 'admin', null, null, null, '0');

-- ----------------------------
-- Table structure for sys_system
-- ----------------------------
DROP TABLE IF EXISTS `sys_system`;
CREATE TABLE `sys_system` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `type` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '系统类型，关联字典表，sys_system_type',
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '系统名称',
  `describe` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '系统简介',
  `icon` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '图标',
  `is_guide` int(1) DEFAULT '0' COMMENT '是否需要引导页，1是，0否',
  `show_type` int(1) DEFAULT '0' COMMENT '进入系统方式，0 _SELF，1 _BLANK',
  `blank_path` varchar(255) DEFAULT NULL COMMENT '系统进入方式为blank时的路径',
  `sort` int(11) DEFAULT '0' COMMENT '排序值',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建人',
  `update_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '修改人',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
  `delete_state` int(1) DEFAULT '0' COMMENT '使用状态 1已删除 0未删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='系统配置';

-- ----------------------------
-- Records of sys_system
-- ----------------------------
INSERT INTO `sys_system` VALUES ('13', 'sys_system_type_1', '系统设置', '对系统、菜单、按钮以及访问路径等系统资源进行配置。并维护系统字典信息。', '/system/xtsz.png', '1', '0', null, '1', '2020-07-02 13:07:32', 'admin', '2020-07-06 11:18:32', 'admin', null, '0');
INSERT INTO `sys_system` VALUES ('14', 'sys_system_type_1', '权限管理', '对登录用户的管理，以及登录用户归属的角色、部门信息，并对角色进行授权。', '/system/qxgl.png', '0', '0', null, '2', '2020-07-02 13:21:12', 'admin', '2020-07-03 16:19:07', 'admin', null, '0');
INSERT INTO `sys_system` VALUES ('15', 'sys_system_type_1', '程序任务', '对系统内的定时任务进行管理', '/system/cxrw.png', '0', '0', null, '3', '2020-07-02 13:32:56', 'admin', '2020-07-02 16:30:43', 'admin', null, '0');
INSERT INTO `sys_system` VALUES ('16', 'sys_system_type_2', '数据分析', '对数据的分析展示', '/system/sjfx.png', '0', '0', '/sys/home', '1', '2020-07-02 13:34:39', 'admin', '2020-07-09 10:02:23', 'admin', null, '0');
INSERT INTO `sys_system` VALUES ('17', 'sys_system_type_2', '百度链接', '', '/system/sjfx.png', '0', '1', 'http://www.baidu.com', '2', '2020-07-03 15:55:52', 'admin', '2020-07-06 09:00:34', 'admin', null, '0');
INSERT INTO `sys_system` VALUES ('18', 'sys_system_type_3', '演示页面', 'antd pro 前端框架带的静态页面，可作为开发的参考页面', '/system/ys.png', '0', '0', '', '1', '2020-07-07 14:51:06', 'admin', '2020-07-08 10:51:25', 'admin', null, '0');
INSERT INTO `sys_system` VALUES ('19', 'sys_system_type_2', '日志调阅', '', '/system/rz.png', '0', '0', '', '3', '2020-07-08 11:26:35', 'admin', '2020-07-08 15:25:33', 'admin', null, '0');

-- ----------------------------
-- Table structure for sys_task
-- ----------------------------
DROP TABLE IF EXISTS `sys_task`;
CREATE TABLE `sys_task` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '任务名',
  `group` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '任务组，关联字典表，sys_task_group',
  `rule` varchar(40) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '定时规则',
  `class_name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '任务类',
  `used_state` int(1) DEFAULT '1' COMMENT '启用状态 1启用 0禁用',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建人',
  `update_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_by` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '修改人',
  `remark` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
  `delete_state` int(1) DEFAULT '0' COMMENT '使用状态 1已删除 0未删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='定时任务配置表';

-- ----------------------------
-- Records of sys_task
-- ----------------------------
INSERT INTO `sys_task` VALUES ('1', '测试2', 'sys_task_group_1', '0 0 23 * * ?', 'com.wang.jmonkey.modules.sys.task.SysTestTask', '1', '2020-05-03 19:19:28', null, '2020-05-04 17:49:58', null, null, '0');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_name` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户登陆名称',
  `password` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户登陆密码',
  `real_name` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户真实姓名',
  `phone` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '手机号码',
  `birthday` date DEFAULT NULL COMMENT '出生日期',
  `sex` int(1) DEFAULT NULL COMMENT '性别 1男 0女',
  `photo` varchar(500) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户头像',
  `used_state` int(1) DEFAULT '1' COMMENT '启用状态 1启用 0禁用',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建人',
  `update_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_by` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '修改人',
  `remark` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
  `delete_state` int(1) DEFAULT '0' COMMENT '使用状态 1已删除 0未删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='系统用户信息表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', '$2a$10$lKOBjPD4ayNIzMKNsyaRU.IfcwNljdBMekeutyxfOZ96ofhMnaD6q', '超级管理员', '13333333333', '2020-05-05', '1', '', '1', '2020-05-03 15:32:23', null, '2020-07-07 14:35:18', 'admin', null, '0');
INSERT INTO `sys_user` VALUES ('2', 'test', '$2a$10$VcHwIZOZXLZB9xU5fwWCvuD5YVMVIxnNL7huYGvry5tkC.GWgUSAe', '测试', '13333333333', '2020-06-24', '1', '/assets/sys/user/photo/6ec78d04-1880-4d55-8605-a1ff28de16d2.png', '1', '2020-06-24 16:07:43', null, '2020-07-01 08:54:06', 'admin', null, '0');
INSERT INTO `sys_user` VALUES ('3', '1', '$2a$10$Wu/1JMTOKN/UsBGd8O8P6ON8ZOpAKPTzfwNhfoK2ug2HMZN6d42ku', '123456', '11111111111', '2020-06-30', '1', '', '1', '2020-06-30 13:37:56', 'admin', '2020-06-30 13:39:00', 'admin', null, '0');
INSERT INTO `sys_user` VALUES ('4', 't', '$2a$10$tMImwndWaBOilYF7.UUnZeLZn9xAtK7GHMx9cY9wElyx2MsdH5W/G', 't', '11111111111', '2020-07-01', '1', '', '0', '2020-07-01 10:30:43', 'admin', '2020-07-10 08:24:33', 'admin', null, '0');
INSERT INTO `sys_user` VALUES ('5', 'yy4', '$2a$10$Zhw7NDf5vCCoyI1OIjJ8d.OlIEx/qgg.RenYt6W8A0pEQF3XNM8lK', '测试4', '11111111111', '2020-07-10', '1', '', '1', '2020-07-10 08:24:22', 'admin', null, null, null, '0');

-- ----------------------------
-- Table structure for sys_user_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_dept`;
CREATE TABLE `sys_user_dept` (
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `dept_id` int(11) NOT NULL COMMENT '部门ID',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建人',
  `update_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '修改人',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
  `delete_state` int(1) DEFAULT '0' COMMENT '使用状态 1已删除 0未删除',
  PRIMARY KEY (`user_id`,`dept_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户部门表';

-- ----------------------------
-- Records of sys_user_dept
-- ----------------------------
INSERT INTO `sys_user_dept` VALUES ('1', '1', '2020-05-17 09:29:39', null, null, null, null, '0');
INSERT INTO `sys_user_dept` VALUES ('2', '1', '2020-07-10 10:50:56', 'admin', null, null, null, '0');
INSERT INTO `sys_user_dept` VALUES ('3', '2', '2020-06-30 14:06:43', 'admin', null, null, null, '0');
INSERT INTO `sys_user_dept` VALUES ('4', '1', '2020-07-01 17:57:16', 'admin', null, null, null, '0');
INSERT INTO `sys_user_dept` VALUES ('5', '2', '2020-07-10 08:24:22', 'admin', null, null, null, '0');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建人',
  `update_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '修改人',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
  `delete_state` int(1) DEFAULT '0' COMMENT '使用状态 1已删除 0未删除',
  PRIMARY KEY (`user_id`,`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户角色表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '1', '2020-05-17 09:29:39', null, null, null, null, '0');
INSERT INTO `sys_user_role` VALUES ('2', '2', '2020-07-10 10:50:56', 'admin', null, null, null, '0');
INSERT INTO `sys_user_role` VALUES ('3', '2', '2020-06-30 14:06:43', 'admin', null, null, null, '0');
INSERT INTO `sys_user_role` VALUES ('4', '2', '2020-07-01 17:57:16', 'admin', null, null, null, '0');
INSERT INTO `sys_user_role` VALUES ('5', '2', '2020-07-10 08:24:22', 'admin', null, null, null, '0');
