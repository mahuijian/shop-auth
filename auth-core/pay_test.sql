/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50717
Source Host           : 127.0.0.1:3306
Source Database       : pay_test

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2019-11-14 17:09:12
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(20) unsigned NOT NULL AUTO_INCREMENT,
  `nick_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '用户昵称',
  `real_name` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '真实姓名',
  `union_id` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '微信unionId',
  `open_id` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '微信openId',
  `avatar_url` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '头像url',
  `gender` tinyint(1) unsigned DEFAULT '0' COMMENT '0未知 男1 女2',
  `phone` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '手机',
  `status` tinyint(1) unsigned DEFAULT '0' COMMENT '用户状态',
  `create_time` int(50) DEFAULT NULL COMMENT '创建时间',
  `update_time` int(50) DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint(1) DEFAULT NULL,
  `create_user` int(50) DEFAULT NULL,
  `update_user` int(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'xiao', 'ma', '', 'oJbOH5LTgY8kMlsWT3Lr5nEpCHkI', '', '0', '', '0', null, '1573204218', null, null, '1');
