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
  `create_by` int(11) DEFAULT NULL COMMENT '创建人',
  `update_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_by` int(11) DEFAULT NULL COMMENT '修改人',
  `remark` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
  `delete_state` int(1) DEFAULT '0' COMMENT '使用状态 1已删除 0未删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='系统用户信息表';

CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '角色名称',
  `code` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '角色编码',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` int(11) DEFAULT NULL COMMENT '创建人',
  `update_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_by` int(11) DEFAULT NULL COMMENT '修改人',
  `remark` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
  `delete_state` int(1) DEFAULT '0' COMMENT '使用状态 1已删除 0未删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='系统角色信息表';

CREATE TABLE `sys_task` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '任务名',
  `group` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '任务组',
  `rule` varchar(40) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '定时规则',
  `class_name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '任务类',
  `used_state` int(1) DEFAULT '1' COMMENT '启用状态 1启用 0禁用',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` int(11) DEFAULT NULL COMMENT '创建人',
  `update_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_by` int(11) DEFAULT NULL COMMENT '修改人',
  `remark` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
  `delete_state` int(1) DEFAULT '0' COMMENT '使用状态 1已删除 0未删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='定时任务配置表';