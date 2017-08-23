/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50106
Source Host           : localhost:3306
Source Database       : jdpay

Target Server Type    : MYSQL
Target Server Version : 50106
File Encoding         : 65001

Date: 2016-10-12 23:15:37
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `risk_black_list`
-- ----------------------------
DROP TABLE IF EXISTS `risk_black_list`;
CREATE TABLE `risk_black_list` (
  `ID` bigint(20) NOT NULL auto_increment,
  `INTERFACE_NAME` varchar(200) default NULL,
  `TYPE` varchar(100) default NULL,
  `VALUE` varchar(200) default NULL,
  `STATUS` int(11) default NULL,
  `CREATE_TIME` timestamp NULL default NULL,
  `REASON` varchar(200) default NULL,
  `OPERATOR` varchar(200) default NULL,
  `KEY_VERSION` varchar(100) default NULL,
  `UPDATE_TIME` timestamp NULL default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of risk_black_list
-- ----------------------------

-- ----------------------------
-- Table structure for `risk_interface`
-- ----------------------------
DROP TABLE IF EXISTS `risk_interface`;
CREATE TABLE `risk_interface` (
  `ID` bigint(20) NOT NULL auto_increment,
  `SYSTEM_NAME` varchar(100) default NULL,
  `INTERFACE_NAME` varchar(200) default NULL,
  `DESCRIPTION` varchar(200) default NULL,
  `EVENT_DESCRIPTION` varchar(200) default NULL,
  `CREATE_TIME` timestamp NULL default NULL,
  `STATUS` int(11) default NULL,
  `UPDATE_TIME` timestamp NULL default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of risk_interface
-- ----------------------------
INSERT INTO `risk_interface` VALUES ('1', null, 'RULE_MENU2_I1', 'RULE_MENU2_I1_DESC', 'RULE_MENU2_I1', '2016-10-11 00:35:23', '1', '2016-10-11 00:36:25');

-- ----------------------------
-- Table structure for `risk_interface_log_field`
-- ----------------------------
DROP TABLE IF EXISTS `risk_interface_log_field`;
CREATE TABLE `risk_interface_log_field` (
  `ID` bigint(20) NOT NULL auto_increment,
  `FIELD_ID` bigint(20) default NULL,
  `INTERFACE_NAME` varchar(200) default NULL,
  `FIELD_NAME` varchar(200) default NULL,
  `FIELD_DESCRIPTION` varchar(200) default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of risk_interface_log_field
-- ----------------------------

-- ----------------------------
-- Table structure for `risk_menu`
-- ----------------------------
DROP TABLE IF EXISTS `risk_menu`;
CREATE TABLE `risk_menu` (
  `ID` bigint(20) NOT NULL auto_increment,
  `NAME` varchar(100) default NULL,
  `CODE` varchar(200) default NULL,
  `INTERFACE_NAME` varchar(200) default NULL,
  `PARENT_ID` int(11) default NULL,
  `CREATE_TIME` timestamp NULL default NULL,
  `NUMBER` varchar(200) default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of risk_menu
-- ----------------------------
INSERT INTO `risk_menu` VALUES ('1', 'RULE_MENU1??', 'RULE_MENU1', null, '0', '2016-10-10 23:47:34', '01');
INSERT INTO `risk_menu` VALUES ('2', 'RULE_MENU2??', 'RULE_MENU2', 'RULE_MENU2_I1', '1', '2016-10-11 00:35:23', '01');

-- ----------------------------
-- Table structure for `risk_register_server`
-- ----------------------------
DROP TABLE IF EXISTS `risk_register_server`;
CREATE TABLE `risk_register_server` (
  `ID` bigint(20) NOT NULL auto_increment,
  `NAME` varchar(100) default NULL,
  `IP` varchar(200) default NULL,
  `PORT` int(11) default NULL,
  `PARAMS` varchar(200) default NULL,
  `LOGIN_TIME` timestamp NULL default NULL,
  `LOGOUT_TIME` timestamp NULL default NULL on update CURRENT_TIMESTAMP,
  `ACTIVE` smallint(6) default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of risk_register_server
-- ----------------------------

-- ----------------------------
-- Table structure for `risk_rule`
-- ----------------------------
DROP TABLE IF EXISTS `risk_rule`;
CREATE TABLE `risk_rule` (
  `ID` bigint(20) NOT NULL auto_increment,
  `NO` varchar(200) default NULL,
  `NAME` varchar(100) default NULL,
  `START_TIME` timestamp NULL default NULL,
  `END_TIME` timestamp NULL default NULL on update CURRENT_TIMESTAMP,
  `STATUS` int(11) default NULL,
  `PRIORITY` int(11) default NULL,
  `LEVEL` int(11) default NULL,
  `CREATE_PERSON` varchar(100) default NULL,
  `CREATE_TIME` timestamp NULL default NULL,
  `INTERFACE_NAME` varchar(200) default NULL,
  `START_INTERVAL` varchar(200) default NULL,
  `END_INTERVAL` varchar(200) default NULL,
  `UPDATE_TIME` timestamp NULL default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of risk_rule
-- ----------------------------

-- ----------------------------
-- Table structure for `risk_rule_condition`
-- ----------------------------
DROP TABLE IF EXISTS `risk_rule_condition`;
CREATE TABLE `risk_rule_condition` (
  `ID` bigint(20) NOT NULL auto_increment,
  `RULE_ID` bigint(20) default NULL,
  `RISK_FACTOR` varchar(200) default NULL,
  `CHECK_CONDITION` varchar(200) default NULL,
  `CHECK_VALUE` varchar(200) default NULL,
  `LOGICAL_OPERATOR` int(11) default NULL,
  `CONNECTOR` varchar(50) default NULL,
  `RISK_FACTOR_PARAM` varchar(200) default NULL,
  `RISK_CONVERTOR` varchar(200) default NULL,
  `EXTERNAL_PARAM` varchar(200) default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of risk_rule_condition
-- ----------------------------

-- ----------------------------
-- Table structure for `risk_rule_condition_draft`
-- ----------------------------
DROP TABLE IF EXISTS `risk_rule_condition_draft`;
CREATE TABLE `risk_rule_condition_draft` (
  `ID` bigint(20) NOT NULL auto_increment,
  `RULE_ID` bigint(20) default NULL,
  `RISK_FACTOR` varchar(200) default NULL,
  `CHECK_CONDITION` varchar(200) default NULL,
  `CHECK_VALUE` varchar(200) default NULL,
  `LOGICAL_OPERATOR` int(11) default NULL,
  `CONNECTOR` varchar(50) default NULL,
  `RISK_FACTOR_PARAM` varchar(200) default NULL,
  `EXTERNAL_PARAM` varchar(200) default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of risk_rule_condition_draft
-- ----------------------------

-- ----------------------------
-- Table structure for `risk_rule_draft`
-- ----------------------------
DROP TABLE IF EXISTS `risk_rule_draft`;
CREATE TABLE `risk_rule_draft` (
  `ID` bigint(20) NOT NULL auto_increment,
  `NO` varchar(200) default NULL,
  `NAME` varchar(100) default NULL,
  `START_TIME` timestamp NULL default NULL,
  `END_TIME` timestamp NULL default NULL on update CURRENT_TIMESTAMP,
  `STATUS` int(11) default NULL,
  `PRIORITY` int(11) default NULL,
  `LEVEL` int(11) default NULL,
  `CREATE_PERSON` varchar(100) default NULL,
  `CREATE_TIME` timestamp NULL default NULL,
  `INTERFACE_NAME` varchar(200) default NULL,
  `AUDIT_STATUS` int(11) default NULL,
  `OPERATE_TYPE` int(11) default NULL,
  `START_INTERVAL` varchar(200) default NULL,
  `END_INTERVAL` varchar(200) default NULL,
  `REFUSE_REASON` varchar(200) default NULL,
  `UPDATE_TIME` timestamp NULL default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of risk_rule_draft
-- ----------------------------

-- ----------------------------
-- Table structure for `risk_rule_field`
-- ----------------------------
DROP TABLE IF EXISTS `risk_rule_field`;
CREATE TABLE `risk_rule_field` (
  `ID` bigint(20) NOT NULL auto_increment,
  `CATEGORY` varchar(100) default NULL,
  `NAME` varchar(100) default NULL,
  `DESCRIPTION` varchar(200) default NULL,
  `CODES` varchar(200) default NULL,
  `CODEINFOS` varchar(200) default NULL,
  `CREATE_TIME` timestamp NULL default NULL on update CURRENT_TIMESTAMP,
  `OPERATOR` varchar(100) default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of risk_rule_field
-- ----------------------------

-- ----------------------------
-- Table structure for `risk_rule_handler`
-- ----------------------------
DROP TABLE IF EXISTS `risk_rule_handler`;
CREATE TABLE `risk_rule_handler` (
  `ID` bigint(20) NOT NULL auto_increment,
  `RULE_ID` bigint(20) default NULL,
  `COMMAND` varchar(100) default NULL,
  `COMMAND_VALUE` varchar(200) default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of risk_rule_handler
-- ----------------------------

-- ----------------------------
-- Table structure for `risk_rule_handler_draft`
-- ----------------------------
DROP TABLE IF EXISTS `risk_rule_handler_draft`;
CREATE TABLE `risk_rule_handler_draft` (
  `ID` bigint(20) NOT NULL auto_increment,
  `RULE_ID` bigint(20) default NULL,
  `COMMAND` varchar(100) default NULL,
  `COMMAND_VALUE` varchar(200) default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of risk_rule_handler_draft
-- ----------------------------

-- ----------------------------
-- Table structure for `risk_system`
-- ----------------------------
DROP TABLE IF EXISTS `risk_system`;
CREATE TABLE `risk_system` (
  `SYSTEM_NAME` varchar(100) NOT NULL,
  `DESCRIPTION` varchar(200) default NULL,
  `STATUS` int(11) default NULL,
  PRIMARY KEY  (`SYSTEM_NAME`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of risk_system
-- ----------------------------
INSERT INTO `risk_system` VALUES ('C2B', 'C2B System', '1');

-- ----------------------------
-- Table structure for `risk_white_list`
-- ----------------------------
DROP TABLE IF EXISTS `risk_white_list`;
CREATE TABLE `risk_white_list` (
  `ID` bigint(20) NOT NULL auto_increment,
  `INTERFACE_NAME` varchar(200) default NULL,
  `TYPE` varchar(100) default NULL,
  `VALUE` varchar(200) default NULL,
  `STATUS` int(11) default NULL,
  `CREATE_TIME` timestamp NULL default NULL,
  `REASON` varchar(200) default NULL,
  `OPERATOR` varchar(200) default NULL,
  `KEY_VERSION` varchar(100) default NULL,
  `UPDATE_TIME` timestamp NULL default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of risk_white_list
-- ----------------------------
