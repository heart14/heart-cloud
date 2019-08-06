/*
 Navicat Premium Data Transfer

 Source Server         : MySQL
 Source Server Type    : MySQL
 Source Server Version : 50722
 Source Host           : localhost:3306
 Source Schema         : heart_cloud

 Target Server Type    : MySQL
 Target Server Version : 50722
 File Encoding         : 65001

 Date: 02/08/2019 17:55:58
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_cloud_dir
-- ----------------------------
DROP TABLE IF EXISTS `t_cloud_dir`;
CREATE TABLE `t_cloud_dir`  (
  `cloud_dir_id` int(11) NOT NULL,
  `cloud_dir_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `cloud_dir_size` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `cloud_dir_create_date` datetime(0) NULL DEFAULT NULL,
  `cloud_dir_update_date` datetime(0) NULL DEFAULT NULL,
  `cloud_dir_status` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `cloud_dir_parent_id` int(11) NULL DEFAULT 0,
  `cloud_dir_user_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`cloud_dir_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_cloud_file
-- ----------------------------
DROP TABLE IF EXISTS `t_cloud_file`;
CREATE TABLE `t_cloud_file`  (
  `cloud_file_id` int(11) NOT NULL,
  `cloud_file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `cloud_file_size` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `cloud_file_create_date` datetime(0) NULL DEFAULT NULL,
  `cloud_file_update_date` datetime(0) NULL DEFAULT NULL,
  `cloud_file_type` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '0-图片 1-文本文档 2-音乐 3-视频',
  `cloud_file_status` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `cloud_file_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `cloud_file_sell_flag` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '0-不出售 1-出售',
  `cloud_file_sell_price` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '出售价格，单位分',
  `cloud_file_dir_id` int(11) NULL DEFAULT NULL,
  `cloud_file_user_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`cloud_file_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_cloud_goods
-- ----------------------------
DROP TABLE IF EXISTS `t_cloud_goods`;
CREATE TABLE `t_cloud_goods`  (
  `cloud_goods_id` int(11) NOT NULL,
  `cloud_goods_file_id` int(11) NULL DEFAULT NULL,
  `cloud_goods_file_price` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `cloud_goods_status` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '0-无效 1-有效',
  `cloud_goods_create_date` datetime(0) NULL DEFAULT NULL,
  `cloud_goods_update_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`cloud_goods_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_cloud_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_cloud_permission`;
CREATE TABLE `t_cloud_permission`  (
  `permission_id` int(11) NOT NULL AUTO_INCREMENT,
  `permission_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限名 唯一标识',
  `permission_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限描述',
  `permission_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `permission_parent_id` int(11) NULL DEFAULT NULL COMMENT '父权限id',
  PRIMARY KEY (`permission_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_cloud_role
-- ----------------------------
DROP TABLE IF EXISTS `t_cloud_role`;
CREATE TABLE `t_cloud_role`  (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色名',
  `role_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色描述',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_cloud_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_cloud_role_permission`;
CREATE TABLE `t_cloud_role_permission`  (
  `role_id` int(11) NOT NULL,
  `permission_id` int(11) NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_cloud_user
-- ----------------------------
DROP TABLE IF EXISTS `t_cloud_user`;
CREATE TABLE `t_cloud_user`  (
  `user_id` int(11) NOT NULL COMMENT '用户表 主键',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `user_pass` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户密码',
  `user_salt` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户盐',
  `user_status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户状态',
  `user_create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `user_update_date` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_cloud_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_cloud_user_role`;
CREATE TABLE `t_cloud_user_role`  (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
