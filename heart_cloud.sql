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

 Date: 12/09/2019 16:27:04
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
  `cloud_goods_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `cloud_goods_status` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '0-无效 1-有效',
  `cloud_goods_create_date` datetime(0) NULL DEFAULT NULL,
  `cloud_goods_update_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`cloud_goods_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_cloud_goods
-- ----------------------------
INSERT INTO `t_cloud_goods` VALUES (396654958, 1058903621, '100', 'E:\\HEARTCLOUD\\heart777\\图片\\9fe68e1569e1c8a5f98ec582f291b2d5cbd828790985c161ef8d2c5293af82f5.jpg', '0', '2019-08-05 12:37:29', '2019-08-21 13:33:13');
INSERT INTO `t_cloud_goods` VALUES (873453215, 1746917819, '300', 'E:\\HEARTCLOUD\\heart777\\视频\\dnfvideo 2017-06-29 03-26-06-777.avi', '0', '2019-08-05 12:28:45', '2019-08-05 12:28:50');
INSERT INTO `t_cloud_goods` VALUES (967927878, 862177529, '100', 'E:\\HEARTCLOUD\\heart777\\图片\\842c9429f4ea974365dbf49c65562b34b707109382b05be89d2c27fe9c98c2db.jpg', '1', '2019-08-05 12:37:22', NULL);
INSERT INTO `t_cloud_goods` VALUES (1415433494, 1502676435, '240', 'E:\\HEARTCLOUD\\heart777\\图片\\ea088c90jw1f6dhutqtktj21hc0u0dyb.jpg', '1', '2019-08-21 13:33:26', NULL);
INSERT INTO `t_cloud_goods` VALUES (1517349546, 1744289088, '200', 'E:\\HEARTCLOUD\\heart777\\视频\\dnfvideo 2017-06-29 03-26-06-777_9yFBKqLi.avi', '0', '2019-08-05 12:28:37', '2019-08-05 12:36:30');
INSERT INTO `t_cloud_goods` VALUES (1961139590, 1502676435, '100', 'E:\\HEARTCLOUD\\heart777\\图片\\ea088c90jw1f6dhutqtktj21hc0u0dyb.jpg', '0', '2019-08-05 12:37:35', '2019-08-21 13:33:07');
INSERT INTO `t_cloud_goods` VALUES (2120659840, 1746917819, '300', 'E:\\HEARTCLOUD\\heart777\\视频\\dnfvideo 2017-06-29 03-26-06-777.avi', '0', '2019-08-05 12:28:57', '2019-08-05 12:36:35');

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
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_cloud_role
-- ----------------------------
INSERT INTO `t_cloud_role` VALUES (1, 'Manager', '管理员用户');

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
-- Records of t_cloud_user
-- ----------------------------
INSERT INTO `t_cloud_user` VALUES (134523096, 'heart777', 'bfb9a2ab9b154b23ff7487dca2d0765a', 'heart777_123456', '1', '2019-07-15 14:35:29', NULL);

-- ----------------------------
-- Table structure for t_cloud_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_cloud_user_role`;
CREATE TABLE `t_cloud_user_role`  (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_cloud_user_role
-- ----------------------------
INSERT INTO `t_cloud_user_role` VALUES (134523096, 1);

SET FOREIGN_KEY_CHECKS = 1;
