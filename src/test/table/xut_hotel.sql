/*
Navicat MySQL Data Transfer

Source Server         : MYSQL
Source Server Version : 80011
Source Host           : localhost:3306
Source Database       : xut_hotel

Target Server Type    : MYSQL
Target Server Version : 80011
File Encoding         : 65001

Date: 2021-02-07 16:56:55
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `xut_client`
-- ----------------------------
DROP TABLE IF EXISTS `xut_client`;
CREATE TABLE `xut_client` (
  `xut_id` int(11) NOT NULL AUTO_INCREMENT,
  `xut_order_id` int(11) NOT NULL,
  `xut_realname` varchar(20) NOT NULL,
  `xut_identification_id` char(18) NOT NULL,
  `xut_phone` char(11) NOT NULL,
  PRIMARY KEY (`xut_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xut_client
-- ----------------------------
INSERT INTO `xut_client` VALUES ('5', '1', 'jjjj', '111111111111111', '15845637852');
INSERT INTO `xut_client` VALUES ('6', '1', 'gw', '12222222222222', '15845637853');
INSERT INTO `xut_client` VALUES ('7', '1', 'jjjj', '111111111111111', '15845637852');
INSERT INTO `xut_client` VALUES ('8', '1', 'gw', '12222222222222', '15845637853');
INSERT INTO `xut_client` VALUES ('9', '1', 'jjjj', '111111111111111', '15845637852');
INSERT INTO `xut_client` VALUES ('10', '1', 'gw', '12222222222222', '15845637853');

-- ----------------------------
-- Table structure for `xut_comment`
-- ----------------------------
DROP TABLE IF EXISTS `xut_comment`;
CREATE TABLE `xut_comment` (
  `xut_id` int(11) NOT NULL AUTO_INCREMENT,
  `xut_description` mediumtext NOT NULL,
  `xut_user_id` int(11) NOT NULL,
  `xut_created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `xut_updated_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `xut_type_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`xut_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xut_comment
-- ----------------------------
INSERT INTO `xut_comment` VALUES ('1', 'test', '1', '2020-12-05 19:15:02', '2020-12-05 19:15:02', '1');
INSERT INTO `xut_comment` VALUES ('3', 'fwwg', '1', '2020-12-13 23:11:00', '2020-12-13 23:11:00', '1');
INSERT INTO `xut_comment` VALUES ('4', 'nihao', '1', '2020-12-13 23:16:50', '2020-12-13 23:16:50', '4');
INSERT INTO `xut_comment` VALUES ('5', 'hhh', '1', '2020-12-20 14:09:00', '2020-12-20 14:09:00', '1');
INSERT INTO `xut_comment` VALUES ('6', 'test', '1', '2020-12-20 19:45:58', '2020-12-20 19:45:58', '1');
INSERT INTO `xut_comment` VALUES ('7', 'test', '1', '2020-12-25 00:05:23', '2020-12-25 00:05:23', '1');
INSERT INTO `xut_comment` VALUES ('8', '111', '1', '2021-01-13 23:03:59', '2021-01-13 23:03:59', '1');

-- ----------------------------
-- Table structure for `xut_order`
-- ----------------------------
DROP TABLE IF EXISTS `xut_order`;
CREATE TABLE `xut_order` (
  `xut_id` int(11) NOT NULL AUTO_INCREMENT,
  `xut_uuid` varchar(64) NOT NULL,
  `xut_user_id` int(11) NOT NULL,
  `xut_room_type_id` int(11) NOT NULL,
  `xut_status` int(2) NOT NULL DEFAULT '2' COMMENT '订单状态0--已取消 1--已完成 2--交易中',
  `xut_price` double NOT NULL,
  `xut_note` varchar(100) DEFAULT NULL,
  `xut_room_id` int(11) DEFAULT NULL,
  `xut_check_in_time` date DEFAULT NULL,
  `xut_check_out_time` date DEFAULT NULL,
  `xut_created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `xut_updated_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `xut_finished_time` datetime DEFAULT NULL,
  PRIMARY KEY (`xut_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xut_order
-- ----------------------------
INSERT INTO `xut_order` VALUES ('1', '8d0ba338-1115-44f6-99d8-99d6a4fa262b', '1', '1', '2', '299.99', 'jjjj', null, '2020-12-14', '2020-12-19', '2020-12-15 00:52:27', '2020-12-15 00:52:27', null);
INSERT INTO `xut_order` VALUES ('2', 'c080b87f-af33-47ef-abaf-daba47cb8278', '1', '1', '2', '299.99', 'jjjj', null, '2020-12-14', '2020-12-19', '2020-12-20 19:46:02', '2020-12-20 19:46:02', null);
INSERT INTO `xut_order` VALUES ('3', '9223c0a0-9570-46ce-add6-caf68b24a111', '1', '1', '2', '299.99', 'jjjj', null, '2020-12-14', '2020-12-19', '2020-12-25 00:05:25', '2020-12-25 00:05:25', null);

-- ----------------------------
-- Table structure for `xut_reply`
-- ----------------------------
DROP TABLE IF EXISTS `xut_reply`;
CREATE TABLE `xut_reply` (
  `xut_id` int(11) NOT NULL AUTO_INCREMENT,
  `xut_description` mediumtext NOT NULL,
  `xut_user_id` int(11) NOT NULL,
  `xut_comment_id` int(11) NOT NULL,
  `xut_reply_user_name` varchar(32) DEFAULT NULL,
  `xut_created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `xut_updated_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `reply_user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`xut_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xut_reply
-- ----------------------------
INSERT INTO `xut_reply` VALUES ('1', '这是一条回复', '1', '1', 'xut', '2020-12-10 19:41:13', '2020-12-10 19:41:13', '1');
INSERT INTO `xut_reply` VALUES ('3', 'nihao', '1', '3', 'admin1', '2020-12-13 23:11:50', '2020-12-13 23:11:50', '1');
INSERT INTO `xut_reply` VALUES ('4', 'nhjk', '1', '1', 'admin1', '2020-12-20 14:09:13', '2020-12-20 14:09:13', '1');
INSERT INTO `xut_reply` VALUES ('5', '111', '1', '1', 'admin1', '2021-01-13 23:05:15', '2021-01-13 23:05:15', '1');
INSERT INTO `xut_reply` VALUES ('6', '1', '1', '3', 'admin1', '2021-01-13 23:05:35', '2021-01-13 23:05:35', '1');

-- ----------------------------
-- Table structure for `xut_room`
-- ----------------------------
DROP TABLE IF EXISTS `xut_room`;
CREATE TABLE `xut_room` (
  `xut_id` int(11) NOT NULL AUTO_INCREMENT,
  `xut_room_number` varchar(10) NOT NULL,
  `xut_type_id` int(11) DEFAULT NULL,
  `xut_floor_num` int(11) DEFAULT NULL,
  PRIMARY KEY (`xut_id`),
  UNIQUE KEY `xut_room_xut_room_number_index` (`xut_room_number`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xut_room
-- ----------------------------
INSERT INTO `xut_room` VALUES ('2', '101', '1', '1');
INSERT INTO `xut_room` VALUES ('4', '102', '1', '2');
INSERT INTO `xut_room` VALUES ('6', '205', '10', '10');

-- ----------------------------
-- Table structure for `xut_room_setting`
-- ----------------------------
DROP TABLE IF EXISTS `xut_room_setting`;
CREATE TABLE `xut_room_setting` (
  `xut_id` int(11) NOT NULL AUTO_INCREMENT,
  `xut_type_id` int(11) NOT NULL,
  `xut_wifi` varchar(100) DEFAULT NULL,
  `xut_food` varchar(100) DEFAULT NULL,
  `xut_air_conditioner` varchar(50) DEFAULT NULL,
  `xut_spot` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '閰掑簵鍛ㄨ竟鐨勫悕鑳?',
  `xut_facilities` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`xut_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xut_room_setting
-- ----------------------------
INSERT INTO `xut_room_setting` VALUES ('1', '0', '免费wifi', '中餐、西餐、各种特色菜', '有空调（中央空调）', '周围有景点，距离酒店很近', '有淋浴、书桌、阳台，酒店内有各种娱乐设施，如KTV');

-- ----------------------------
-- Table structure for `xut_room_type`
-- ----------------------------
DROP TABLE IF EXISTS `xut_room_type`;
CREATE TABLE `xut_room_type` (
  `xut_id` int(11) NOT NULL AUTO_INCREMENT,
  `xut_type` varchar(20) NOT NULL,
  `xut_imgs` mediumtext,
  `xut_price` double NOT NULL DEFAULT '0',
  `xut_max_people` int(11) NOT NULL DEFAULT '1',
  `xut_size` int(11) DEFAULT NULL,
  `xut_hasFood` int(1) DEFAULT NULL COMMENT '0:无，1：有',
  `xut_bed` varchar(100) DEFAULT NULL,
  `xut_floor` varchar(20) DEFAULT NULL,
  `xut_hasWindow` int(1) DEFAULT NULL,
  `xut_smoke` int(1) DEFAULT NULL,
  PRIMARY KEY (`xut_id`),
  UNIQUE KEY `xut_room_type_xut_type_uindex` (`xut_type`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xut_room_type
-- ----------------------------
INSERT INTO `xut_room_type` VALUES ('1', '情趣房', null, '299.99', '2', null, null, '一张大床', null, '1', null);
INSERT INTO `xut_room_type` VALUES ('3', '双人房', null, '199.99', '2', null, '0', null, null, '1', '1');
INSERT INTO `xut_room_type` VALUES ('4', '大床房', '[\"http://qkjs7zxyw.hn-bkt.clouddn.com/xut_160731641800063_1571399780081_079F4FB55B755F6F198BEE97D7C95390.png\"]', '199.99', '2', null, null, null, null, null, null);
INSERT INTO `xut_room_type` VALUES ('6', '大床房1', '[\"http://qkjs7zxyw.hn-bkt.clouddn.com/xut_160731641800063_1571399780081_079F4FB55B755F6F198BEE97D7C95390.png\"]', '199.99', '2', null, null, null, null, null, null);
INSERT INTO `xut_room_type` VALUES ('8', '儿童房', '[\"http://qkjs7zxyw.hn-bkt.clouddn.com/xut_1607404033000EXo9mSKU0AABu2E.jpeg\",\"http://qkjs7zxyw.hn-bkt.clouddn.com/xut_1607404034000timg.jpeg\"]', '200.99', '1', '15', null, '一张1.5m的床', '2-3', null, null);
INSERT INTO `xut_room_type` VALUES ('9', '大床房3', '[\"http://xuxuxuonezero.top/xut_1610545871000index3.jpeg\",\"http://xuxuxuonezero.top/xut_1610545911000index2.jpeg\"]', '299.99', '2', '20', '1', '一张1.5m的床', '7-9', '1', '0');
INSERT INTO `xut_room_type` VALUES ('10', '总统房', '[\"http://qkjs7zxyw.hn-bkt.clouddn.com/xut_1607867987000u=3270617100,1904621490&fm=26&gp=0.jpg\"]', '2999.99', '4', '90', '1', null, null, '1', '0');

-- ----------------------------
-- Table structure for `xut_user`
-- ----------------------------
DROP TABLE IF EXISTS `xut_user`;
CREATE TABLE `xut_user` (
  `xut_id` int(11) NOT NULL AUTO_INCREMENT,
  `xut_password` varchar(30) NOT NULL,
  `xut_identification_id` char(18) NOT NULL,
  `xut_sex` int(1) DEFAULT '0' COMMENT '0:男，1:女',
  `xut_type` int(1) NOT NULL DEFAULT '1' COMMENT '0:管理员，1：用户',
  `xut_user_name` varchar(32) NOT NULL,
  `xut_real_name` varchar(20) NOT NULL,
  `xut_phone` char(20) NOT NULL,
  `xut_avatar` varchar(100) DEFAULT NULL,
  `xut_introduction` varchar(300) DEFAULT NULL,
  `xut_created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `xut_updated_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `xut_last_login_time` datetime DEFAULT NULL,
  PRIMARY KEY (`xut_id`),
  UNIQUE KEY `xut_user_xut_identification_id_uindex` (`xut_identification_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xut_user
-- ----------------------------
INSERT INTO `xut_user` VALUES ('1', '123456', '320211199908263256', '0', '0', 'admin1', 'admin', '11125693257', null, null, '2020-11-24 23:08:02', '2020-12-03 00:41:48', null);
INSERT INTO `xut_user` VALUES ('2', 'Aa123456', '32021119990826325', null, '1', 'xutian', 'xutian', '158123569852', null, null, '2020-12-03 00:41:15', '2020-12-03 00:41:15', null);

-- ----------------------------
-- Procedure structure for `comment_get_by_type_id`
-- ----------------------------
DROP PROCEDURE IF EXISTS `comment_get_by_type_id`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `comment_get_by_type_id`(
  IN in_type_id INT,
  IN in_offset INT,
  IN in_page_size INT,
  OUT out_total_count INT
)
BEGIN
    SELECT SQL_CALC_FOUND_ROWS * FROM xut_comment WHERE xut_type_id = in_type_id
    LIMIT in_offset, in_page_size;
    SET out_total_count = FOUND_ROWS();
END
;;
DELIMITER ;
