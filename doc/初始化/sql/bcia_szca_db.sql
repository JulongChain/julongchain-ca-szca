/*
Navicat MySQL Data Transfer

Source Server         : localhost-bcia-bcia
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : bcia_szca_db

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-06-05 14:57:57
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for accessrulesdata
-- ----------------------------
DROP TABLE IF EXISTS `accessrulesdata`;
CREATE TABLE `accessrulesdata` (
  `pK` int(11) NOT NULL,
  `accessRule` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `isRecursive` tinyint(4) NOT NULL,
  `rowProtection` longtext,
  `rowVersion` int(11) NOT NULL,
  `rule` int(11) NOT NULL,
  `AdminGroupData_accessRules` int(11) DEFAULT NULL,
  PRIMARY KEY (`pK`),
  KEY `FKdaev24clq6yofynpsh4kuojno` (`AdminGroupData_accessRules`) USING BTREE,
  CONSTRAINT `FKdaev24clq6yofynpsh4kuojno` FOREIGN KEY (`AdminGroupData_accessRules`) REFERENCES `admingroupdata` (`pK`),
  CONSTRAINT `accessrulesdata_ibfk_1` FOREIGN KEY (`AdminGroupData_accessRules`) REFERENCES `admingroupdata` (`pK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of accessrulesdata
-- ----------------------------
INSERT INTO `accessrulesdata` VALUES ('659366689', '/', '1', null, '0', '1', '1');
INSERT INTO `accessrulesdata` VALUES ('2086400227', '/secureaudit/auditor/select', '1', null, '0', '1', '522715374');

-- ----------------------------
-- Table structure for adminentitydata
-- ----------------------------
DROP TABLE IF EXISTS `adminentitydata`;
CREATE TABLE `adminentitydata` (
  `pK` int(11) NOT NULL,
  `cAId` int(11) NOT NULL,
  `matchType` int(11) NOT NULL,
  `matchValue` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `matchWith` int(11) NOT NULL,
  `rowProtection` longtext,
  `rowVersion` int(11) NOT NULL,
  `tokenType` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `AdminGroupData_adminEntities` int(11) DEFAULT NULL,
  PRIMARY KEY (`pK`),
  KEY `FKgxaatpstfnr3sx8frhueq9q2d` (`AdminGroupData_adminEntities`) USING BTREE,
  CONSTRAINT `FKgxaatpstfnr3sx8frhueq9q2d` FOREIGN KEY (`AdminGroupData_adminEntities`) REFERENCES `admingroupdata` (`pK`),
  CONSTRAINT `adminentitydata_ibfk_1` FOREIGN KEY (`AdminGroupData_adminEntities`) REFERENCES `admingroupdata` (`pK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of adminentitydata
-- ----------------------------
INSERT INTO `adminentitydata` VALUES ('1381954217', '1443565835', '1000', 'BCIA Auditor', '8', null, '0', 'CertificateAuthenticationToken', '522715374');
INSERT INTO `adminentitydata` VALUES ('1649266446', '1443565835', '1000', 'BCIA Administrator', '8', null, '0', 'CertificateAuthenticationToken', '1');

-- ----------------------------
-- Table structure for admingroupdata
-- ----------------------------
DROP TABLE IF EXISTS `admingroupdata`;
CREATE TABLE `admingroupdata` (
  `pK` int(11) NOT NULL,
  `adminGroupName` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `rowProtection` longtext,
  `rowVersion` int(11) NOT NULL,
  PRIMARY KEY (`pK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admingroupdata
-- ----------------------------
INSERT INTO `admingroupdata` VALUES ('1', 'Super Administrator Role', null, '7');
INSERT INTO `admingroupdata` VALUES ('522715374', '监察员', null, '13');

-- ----------------------------
-- Table structure for adminpreferencesdata
-- ----------------------------
DROP TABLE IF EXISTS `adminpreferencesdata`;
CREATE TABLE `adminpreferencesdata` (
  `id` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `data` longblob NOT NULL,
  `rowProtection` longtext,
  `rowVersion` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of adminpreferencesdata
-- ----------------------------
INSERT INTO `adminpreferencesdata` VALUES ('default', 0xACED0005737200226F72672E63657365636F72652E7574696C2E426173653634476574486173684D617007156F73C047AEE9020000787200176A6176612E7574696C2E4C696E6B6564486173684D617034C04E5C106CC0FB0200015A000B6163636573734F72646572787200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000B74000776657273696F6E7372000F6A6176612E6C616E672E466C6F6174DAEDC9A2DB3CF0EC02000146000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B02000078703F80000074001070726566657265646C616E6775616765737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E0006000000097400117365636F6E646172796C616E677561676571007E000A74000E656E7472696573706572706167657371007E0009000000197400116C6F67656E7472696573706572706167657371007E0009000000197400057468656D6574000D64656661756C745F7468656D6574000B6C61737470726F66696C657371007E00090000000074000E6C61737466696C7465726D6F646571007E00137400116C6173746C6F6766696C7465726D6F646571007E001374001166726F6E74706167656361737461747573737200116A6176612E6C616E672E426F6F6C65616ECD207280D59CFAEE0200015A000576616C756578700174001366726F6E74706167657075627173746174757371007E00187800, null, '1');

-- ----------------------------
-- Table structure for api_call_config
-- ----------------------------
DROP TABLE IF EXISTS `api_call_config`;
CREATE TABLE `api_call_config` (
  `ip` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `uri` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ip`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of api_call_config
-- ----------------------------

-- ----------------------------
-- Table structure for api_call_log
-- ----------------------------
DROP TABLE IF EXISTS `api_call_log`;
CREATE TABLE `api_call_log` (
  `logId` int(11) NOT NULL,
  `authorization` longtext,
  `callTime` bigint(20) DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `logTime` bigint(20) DEFAULT NULL,
  `received` longtext,
  `result` longtext,
  `uri` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`logId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of api_call_log
-- ----------------------------

-- ----------------------------
-- Table structure for api_user_config
-- ----------------------------
DROP TABLE IF EXISTS `api_user_config`;
CREATE TABLE `api_user_config` (
  `username` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of api_user_config
-- ----------------------------

-- ----------------------------
-- Table structure for approvaldata
-- ----------------------------
DROP TABLE IF EXISTS `approvaldata`;
CREATE TABLE `approvaldata` (
  `id` int(11) NOT NULL,
  `approvalData` longtext NOT NULL,
  `approvalId` int(11) NOT NULL,
  `approvalType` int(11) NOT NULL,
  `cAId` int(11) NOT NULL,
  `endEntityProfileId` int(11) NOT NULL,
  `expireDate` bigint(20) NOT NULL,
  `remainingApprovals` int(11) NOT NULL,
  `reqAdminCertIssuerDn` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `reqAdminCertSn` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `requestData` longtext NOT NULL,
  `requestDate` bigint(20) NOT NULL,
  `rowProtection` longtext,
  `rowVersion` int(11) NOT NULL,
  `status` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of approvaldata
-- ----------------------------

-- ----------------------------
-- Table structure for auditrecorddata
-- ----------------------------
DROP TABLE IF EXISTS `auditrecorddata`;
CREATE TABLE `auditrecorddata` (
  `pk` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `additionalDetails` longtext,
  `authToken` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `customId` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `eventStatus` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `eventType` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `module` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `nodeId` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `rowProtection` longtext,
  `rowVersion` int(11) NOT NULL,
  `searchDetail1` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `searchDetail2` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `sequenceNumber` bigint(20) NOT NULL,
  `service` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `timeStamp` bigint(20) NOT NULL,
  PRIMARY KEY (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of auditrecorddata
-- ----------------------------
INSERT INTO `auditrecorddata` VALUES ('2fe16780c0a8760111f903ddce393bdf', '<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<java version=\"1.8.0_161\" class=\"java.beans.XMLDecoder\">\n <object class=\"org.cesecore.util.Base64PutHashMap\">\n  <void method=\"put\">\n   <string>resource0</string>\n   <string>/ca/1443565835</string>\n  </void>\n </object>\n</java>\n', 'CN=BCIA Administrator,C=CN', null, 'SUCCESS', 'ACCESS_CONTROL', 'ACCESSCONTROL', 'Think-About', null, '0', null, null, '0', 'CORE', '1528165972635');
INSERT INTO `auditrecorddata` VALUES ('eee58b95c0a8760148cc24de1ed9a332', '<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<java version=\"1.8.0_161\" class=\"java.beans.XMLDecoder\">\n <object class=\"org.cesecore.util.Base64PutHashMap\">\n  <void method=\"put\">\n   <string>resource0</string>\n   <string>/ca/1443565835</string>\n  </void>\n </object>\n</java>\n', 'CN=BCIA Administrator,C=CN', null, 'SUCCESS', 'ACCESS_CONTROL', 'ACCESSCONTROL', 'Think-About', null, '0', null, null, '1', 'CORE', '1528165973728');

-- ----------------------------
-- Table structure for authorizationtreeupdatedata
-- ----------------------------
DROP TABLE IF EXISTS `authorizationtreeupdatedata`;
CREATE TABLE `authorizationtreeupdatedata` (
  `pK` int(11) NOT NULL,
  `authorizationTreeUpdateNumber` int(11) NOT NULL,
  `rowProtection` longtext,
  `rowVersion` int(11) NOT NULL,
  PRIMARY KEY (`pK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of authorizationtreeupdatedata
-- ----------------------------
INSERT INTO `authorizationtreeupdatedata` VALUES ('1', '15', null, '14');

-- ----------------------------
-- Table structure for base64certdata
-- ----------------------------
DROP TABLE IF EXISTS `base64certdata`;
CREATE TABLE `base64certdata` (
  `fingerprint` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `base64Cert` longtext,
  `rowProtection` longtext,
  `rowVersion` int(11) NOT NULL,
  PRIMARY KEY (`fingerprint`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of base64certdata
-- ----------------------------

-- ----------------------------
-- Table structure for cadata
-- ----------------------------
DROP TABLE IF EXISTS `cadata`;
CREATE TABLE `cadata` (
  `cAId` int(11) NOT NULL,
  `data` longtext NOT NULL,
  `expireTime` bigint(20) NOT NULL,
  `name` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `rowProtection` longtext,
  `rowVersion` int(11) NOT NULL,
  `status` int(11) NOT NULL,
  `subjectDN` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `updateTime` bigint(20) NOT NULL,
  PRIMARY KEY (`cAId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cadata
-- ----------------------------
INSERT INTO `cadata` VALUES ('1443565835', '<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<java version=\"1.8.0_161\" class=\"java.beans.XMLDecoder\">\n <object class=\"org.cesecore.util.Base64PutHashMap\">\n  <void method=\"put\">\n   <string>validity</string>\n   <long>2158811991202</long>\n  </void>\n  <void method=\"put\">\n   <string>signedby</string>\n   <int>1</int>\n  </void>\n  <void method=\"put\">\n   <string>description</string>\n   <string></string>\n  </void>\n  <void method=\"put\">\n   <string>revokationreason</string>\n   <int>-1</int>\n  </void>\n  <void method=\"put\">\n   <string>certificateprofileid</string>\n   <int>3</int>\n  </void>\n  <void method=\"put\">\n   <string>crlperiod</string>\n   <long>12592000000</long>\n  </void>\n  <void method=\"put\">\n   <string>crlIssueInterval</string>\n   <long>0</long>\n  </void>\n  <void method=\"put\">\n   <string>crlOverlapTime</string>\n   <long>0</long>\n  </void>\n  <void method=\"put\">\n   <string>deltacrlperiod</string>\n   <long>0</long>\n  </void>\n  <void method=\"put\">\n   <string>crlpublishers</string>\n   <object class=\"java.util.ArrayList\"/>\n  </void>\n  <void method=\"put\">\n   <string>finishuser</string>\n   <boolean>false</boolean>\n  </void>\n  <void method=\"put\">\n   <string>includeinhealthcheck</string>\n   <boolean>false</boolean>\n  </void>\n  <void method=\"put\">\n   <string>doEnforceUniquePublicKeys</string>\n   <boolean>true</boolean>\n  </void>\n  <void method=\"put\">\n   <string>doEnforceUniqueDistinguishedName</string>\n   <boolean>false</boolean>\n  </void>\n  <void method=\"put\">\n   <string>doEnforceUniqueSubjectDNSerialnumber</string>\n   <boolean>true</boolean>\n  </void>\n  <void method=\"put\">\n   <string>useCertreqHistory</string>\n   <boolean>false</boolean>\n  </void>\n  <void method=\"put\">\n   <string>useUserStorage</string>\n   <boolean>true</boolean>\n  </void>\n  <void method=\"put\">\n   <string>useCertificateStorage</string>\n   <boolean>true</boolean>\n  </void>\n  <void method=\"put\">\n   <string>extendedcaservice3</string>\n   <object class=\"java.util.LinkedHashMap\">\n    <void method=\"put\">\n     <string>IMPLCLASS</string>\n     <string>org.ejbca.core.model.ca.caadmin.extendedcaservices.CmsCAService</string>\n    </void>\n    <void method=\"put\">\n     <string>extendedcaservicetype</string>\n     <int>3</int>\n    </void>\n    <void method=\"put\">\n     <string>keyspec</string>\n     <string>2048</string>\n    </void>\n    <void method=\"put\">\n     <string>keyalgorithm</string>\n     <string>RSA</string>\n    </void>\n    <void method=\"put\">\n     <string>subjectdn</string>\n     <string>Q049Q01TQ2VydGlmaWNhdGUsIENOPUJDSUEgTWFuYWdlbWVudCBDQSxDPUNO</string>\n    </void>\n    <void method=\"put\">\n     <string>subjectaltname</string>\n     <string></string>\n    </void>\n    <void method=\"put\">\n     <string>status</string>\n     <int>1</int>\n    </void>\n    <void method=\"put\">\n     <string>version</string>\n     <float>2.0</float>\n    </void>\n   </object>\n  </void>\n  <void method=\"put\">\n   <string>extendedcaservice4</string>\n   <object class=\"java.util.LinkedHashMap\">\n    <void method=\"put\">\n     <string>IMPLCLASS</string>\n     <string>org.ejbca.core.model.ca.caadmin.extendedcaservices.HardTokenEncryptCAService</string>\n    </void>\n    <void method=\"put\">\n     <string>extendedcaservicetype</string>\n     <int>4</int>\n    </void>\n    <void method=\"put\">\n     <string>version</string>\n     <float>1.0</float>\n    </void>\n    <void method=\"put\">\n     <string>status</string>\n     <int>2</int>\n    </void>\n   </object>\n  </void>\n  <void method=\"put\">\n   <string>extendedcaservice5</string>\n   <object class=\"java.util.LinkedHashMap\">\n    <void method=\"put\">\n     <string>IMPLCLASS</string>\n     <string>org.ejbca.core.model.ca.caadmin.extendedcaservices.KeyRecoveryCAService</string>\n    </void>\n    <void method=\"put\">\n     <string>extendedcaservicetype</string>\n     <int>5</int>\n    </void>\n    <void method=\"put\">\n     <string>version</string>\n     <float>1.0</float>\n    </void>\n    <void method=\"put\">\n     <string>status</string>\n     <int>2</int>\n    </void>\n   </object>\n  </void>\n  <void method=\"put\">\n   <string>extendedcaservices</string>\n   <object class=\"java.util.ArrayList\">\n    <void method=\"add\">\n     <int>3</int>\n    </void>\n    <void method=\"add\">\n     <int>4</int>\n    </void>\n    <void method=\"add\">\n     <int>5</int>\n    </void>\n   </object>\n  </void>\n  <void method=\"put\">\n   <string>approvalsettings</string>\n   <object class=\"java.util.ArrayList\"/>\n  </void>\n  <void method=\"put\">\n   <string>numberofreqapprovals</string>\n   <int>1</int>\n  </void>\n  <void method=\"put\">\n   <string>policies</string>\n   <object class=\"java.util.ArrayList\"/>\n  </void>\n  <void method=\"put\">\n   <string>subjectaltname</string>\n   <string></string>\n  </void>\n  <void method=\"put\">\n   <string>useauthoritykeyidentifier</string>\n   <boolean>true</boolean>\n  </void>\n  <void method=\"put\">\n   <string>authoritykeyidentifiercritical</string>\n   <boolean>false</boolean>\n  </void>\n  <void method=\"put\">\n   <string>usecrlnumber</string>\n   <boolean>true</boolean>\n  </void>\n  <void method=\"put\">\n   <string>crlnumbercritical</string>\n   <boolean>false</boolean>\n  </void>\n  <void method=\"put\">\n   <string>defaultcrldistpoint</string>\n   <string></string>\n  </void>\n  <void method=\"put\">\n   <string>defaultcrlissuer</string>\n   <string></string>\n  </void>\n  <void method=\"put\">\n   <string>cadefinedfreshestcrl</string>\n   <string></string>\n  </void>\n  <void method=\"put\">\n   <string>defaultocspservicelocator</string>\n   <string></string>\n  </void>\n  <void method=\"put\">\n   <string>useutf8policytext</string>\n   <boolean>false</boolean>\n  </void>\n  <void method=\"put\">\n   <string>useprintablestringsubjectdn</string>\n   <boolean>false</boolean>\n  </void>\n  <void method=\"put\">\n   <string>useldapdnorder</string>\n   <boolean>false</boolean>\n  </void>\n  <void method=\"put\">\n   <string>usecrldistributionpointoncrl</string>\n   <boolean>false</boolean>\n  </void>\n  <void method=\"put\">\n   <string>crldistributionpointoncrlcritical</string>\n   <boolean>false</boolean>\n  </void>\n  <void method=\"put\">\n   <string>cmpraauthsecret</string>\n   <string></string>\n  </void>\n  <void method=\"put\">\n   <string>authorityinformationaccess</string>\n   <object class=\"java.util.ArrayList\">\n    <void method=\"add\">\n     <null/>\n    </void>\n   </object>\n  </void>\n  <void method=\"put\">\n   <string>nameconstraintspermitted</string>\n   <object class=\"java.util.ArrayList\"/>\n  </void>\n  <void method=\"put\">\n   <string>nameconstraintsexcluded</string>\n   <object class=\"java.util.ArrayList\"/>\n  </void>\n  <void method=\"put\">\n   <string>catype</string>\n   <int>1</int>\n  </void>\n  <void method=\"put\">\n   <string>version</string>\n   <float>20.0</float>\n  </void>\n  <void method=\"put\">\n   <string>catoken</string>\n   <object class=\"java.util.LinkedHashMap\">\n    <void method=\"put\">\n     <string>version</string>\n     <float>8.0</float>\n    </void>\n    <void method=\"put\">\n     <string>cryptotokenid</string>\n     <string>2056667440</string>\n    </void>\n    <void method=\"put\">\n     <string>propertydata</string>\n     <string>hardTokenEncrypt=BICA Encrypt Key&#13;\ncertSignKey=BICA Sign Key&#13;\ndefaultKey=BICA Sign Key&#13;\nkeyEncryptKey=BICA Encrypt Key&#13;\ncrlSignKey=BICA Sign Key&#13;\ntestKey=BICA Sign Key&#13;\n</string>\n    </void>\n    <void method=\"put\">\n     <string>signaturealgorithm</string>\n     <string>SHA256WithRSA</string>\n    </void>\n    <void method=\"put\">\n     <string>encryptionalgorithm</string>\n     <string>SHA256WithRSA</string>\n    </void>\n    <void method=\"put\">\n     <string>sequenceformat</string>\n     <int>1</int>\n    </void>\n    <void method=\"put\">\n     <string>sequence</string>\n     <string>00000</string>\n    </void>\n   </object>\n  </void>\n  <void method=\"put\">\n   <string>certificatechain</string>\n   <object class=\"java.util.ArrayList\">\n    <void method=\"add\">\n     <string>MIIDOTCCAiGgAwIBAgIIX6DfxFmw6mswDQYJKoZIhvcNAQELBQAwKjELMAkGA1UE\nBhMCQ04xGzAZBgNVBAMMEkJDSUEgTWFuYWdlbWVudCBDQTAeFw0xODA2MDQwNTU5\nNTFaFw0zODA1MzAwNTU5NTFaMCoxCzAJBgNVBAYTAkNOMRswGQYDVQQDDBJCQ0lB\nIE1hbmFnZW1lbnQgQ0EwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQCc\n0B/OaCq8iiq0Idk8n2iscZyaN9MDP3ojzU47h2hpaNG8Hi1JgF9AoAs0ByNKOvf0\nZe/2oyBICd7FlyGH/ykHzdl3dzUu2qYitHDwr6Qts62JnHVfU5tOs2gmd66KZtUR\nX6vAWPb9vG1MxtUZ9tmOKDNHwsI6KJBR4cUAWpd1NeQS+ji7aVWtSgsUApVttq+q\nzZH3Z6gO7KqEZNIt99UCthoTEwh9Rx7/wtRrD3gYUiIuYeDtB2vpx+y2iCdi40on\niJazztmNU7nTLph4u3b6CEPuEDg19FvLW/dJIQBGWh9/2XUvvLidC7gHw20NH0TM\nXD7Z8oIEVgZcOw2wspgbAgMBAAGjYzBhMA8GA1UdEwEB/wQFMAMBAf8wHwYDVR0j\nBBgwFoAUQjeXS1fJMBuQw1Rap+rV7P3/HZAwHQYDVR0OBBYEFEI3l0tXyTAbkMNU\nWqfq1ez9/x2QMA4GA1UdDwEB/wQEAwIBhjANBgkqhkiG9w0BAQsFAAOCAQEAD/mZ\n2VGesG48TA34DYAe+Pg3ykLEqGrgi42LZrSYh5jlaV5UBWZOtDgUpWow9JstQMxc\nZDR8X3N/w7St6rdWvc+qMraRusK7F5ty7EddjjT+PpRQNadRaeWdGVaULx8vx+2Q\nuh5qxuOg8ZSf5PPc9zFj3LdtKq/xSJu4+XSE561BSjaS7vbm/V5yLKuec1w417J9\norA+M4abU9pDpTLSsTe5fmA8bWnH1L65go2bZgyubXdMaj9lf9vY5fY8SDAHJRwA\nZFaPO0n5ghbakO3yoQJr804XFpEKoClMv3fjoHdMVE+1O5OfWeLYsDMT93W8Aumh\nmr6i0+IRu7RszXr1Uw==</string>\n    </void>\n   </object>\n  </void>\n </object>\n</java>\n', '2158811991000', 'BICA Management CA', null, '1', '1', 'CN=BCIA Management CA,C=CN', '1528091991771');

-- ----------------------------
-- Table structure for certificatedata
-- ----------------------------
DROP TABLE IF EXISTS `certificatedata`;
CREATE TABLE `certificatedata` (
  `fingerprint` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `base64Cert` longtext,
  `cAFingerprint` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `certificateProfileId` int(11) NOT NULL,
  `expireDate` bigint(20) NOT NULL,
  `issuerDN` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `revocationDate` bigint(20) NOT NULL,
  `revocationReason` int(11) NOT NULL,
  `rowProtection` longtext,
  `rowVersion` int(11) NOT NULL,
  `serialNumber` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `status` int(11) NOT NULL,
  `subjectDN` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `subjectKeyId` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `tag` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `type` int(11) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `username` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`fingerprint`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of certificatedata
-- ----------------------------
INSERT INTO `certificatedata` VALUES ('4fb0f59148d6c00377238a00371a11f798211507', 'MIIDTzCCAjegAwIBAgIILPvaUOLkKvcwDQYJKoZIhvcNAQELBQAwKjELMAkGA1UE\nBhMCQ04xGzAZBgNVBAMMEkJDSUEgTWFuYWdlbWVudCBDQTAeFw0xODA2MDQwNjIy\nNTFaFw0yMDA2MDMwNjIyNTFaMCQxCzAJBgNVBAYTAkNOMRUwEwYDVQQDDAxCQ0lB\nIEF1ZGl0b3IwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQCu8Gldbq1u\ngoc1DlhJEFRs1aPrK8y5xBydEAdLsosED5VroQ/I86a1EuBwqZ88Ikm+8UF70vZz\nQMZD+P+5WGP3az8p9vsuy+MrwfBlTGaBwn+BXHHtA3t4v++DIfy7306GyUfitI0e\nAVly69Wj8iQJY+nVw8RAuRC+DnZvBv0GEwkpOoiPGZLAxmQdud5vgOsLrv3x+4Im\nnt/x9klwyAjeVxK+JKLPIc8vYJfMA4MK1XE17OGiJave9bxAJcldhXNpG1SEESoc\nh7qi+9m4UoV6XSStQP/N5WPZ0N+L5SiX4yBzi4g4iiD1xBKTQgdwSdhkbiK7wdQq\n7gZoEYeu5tzVAgMBAAGjfzB9MAwGA1UdEwEB/wQCMAAwHwYDVR0jBBgwFoAUQjeX\nS1fJMBuQw1Rap+rV7P3/HZAwHQYDVR0lBBYwFAYIKwYBBQUHAwIGCCsGAQUFBwME\nMB0GA1UdDgQWBBSfkhQVFbPJGLfnpIY455/M4X4lGTAOBgNVHQ8BAf8EBAMCBeAw\nDQYJKoZIhvcNAQELBQADggEBAFR4E6EXvMsMaMDy9lfxw9PvZ2mUGojAdz918gUX\nIcz7N+qJjeGljAEuWixdPzDKXEgoYVQ9t/IxOWULwi378i6B/IFbbt6f36YVy+5U\njLO7HI2xV1gzfZm0W6r7HIIRcU4islTj5yCP436jTyA2PMMwulhU6iT6OIL0nr3e\nODPzgut1xfFRNRCAXnZrtyAzhV2lx+si98fQlKtabHxK990+atQjMujquBPCXvG7\nsOOXPXjnj45Y/YMWzJ/QtLiZDsJfLIMKYNFLP+uB+JrGR2+yF/KgBP1mm9/yeRQP\nwtpel/ZdQW1TqaYGhbnaFlT/oW0oUzggci/CVJ42bikrdEI=', '8dd0fa387ee2929fb3baa05990d7f208b8a28a29', '1', '1591165371000', 'CN=BCIA Management CA,C=CN', '-1', '-1', null, '0', '3241424397762046711', '20', 'CN=BCIA Auditor,C=CN', 'n5IUFRWzyRi356SGOOefzOF+JRk=', null, '1', '1528093971303', 'auditor');
INSERT INTO `certificatedata` VALUES ('6e725c3d6125c51b9c525b847d5a02e64ec4b022', 'MIIDYDCCAkigAwIBAgIIdNCQqZVLucAwDQYJKoZIhvcNAQELBQAwKjELMAkGA1UE\nBhMCQ04xGzAZBgNVBAMMEkJDSUEgTWFuYWdlbWVudCBDQTAeFw0xODA2MDQwNjU4\nMjhaFw0yMDA2MDMwNjU4MjhaMCExCzAJBgNVBAYTAkNOMRIwEAYDVQQDDAlsb2Nh\nbGhvc3QwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQCXbbsVBIeke/JX\nL8yXoP0puELCvgCKhLeCLU3YXcgkh709YZ/A4b4hOa+3Kx/wvhfo1soMq2INoWCR\nf+svF4zLvVl3kMv2bGua0SXJNqPVemCvVT9nBvrzZANGskSpLDPioBMzdCYOYDMB\n5vqf46Wp9TncxerngKVfzhC+iRFMThL7UuHKZ7eTpFqyl0T5KmN9yLp5jp6NwucM\n4jOSXFF4sJcyid/NjPRkDJVSDZEDu4NeYo8mGSLlH6oHCltMOOUy17X4xcCzqvqr\nPWcxp/1yc+DLFVTmsLn0nEVgqPNrzk5FCSFbuASxxxJFN9nRmPVTaU7z2QCChdjJ\nYO1VPcdfAgMBAAGjgZIwgY8wDAYDVR0TAQH/BAIwADAfBgNVHSMEGDAWgBRCN5dL\nV8kwG5DDVFqn6tXs/f8dkDAaBgNVHREEEzARgglsb2NhbGhvc3SHBH8AAAEwEwYD\nVR0lBAwwCgYIKwYBBQUHAwEwHQYDVR0OBBYEFL9Mjjv/EVV36gJFAdr2v32H30IX\nMA4GA1UdDwEB/wQEAwIFoDANBgkqhkiG9w0BAQsFAAOCAQEAdk23dL46skrprjJv\nha+9Vq3ZiR8tYVk+pC4HjykAQTWoEjXLGDF9gSWM+EfkMJnqNCxBfTORHQMKwIAn\n9HBH2wy5HbIVQlUXym6TUzYPVZ15cjIcyk8iBc6uU4YGhD1yW+X/DpLsSpm+OiXL\nEiOU2ceWzbgB8b00/mjsrU/1z6llpkle6r8iNKMDS6oyJ+28ZBqiMqgSw1F7xPKl\nF8EyzUAV6AiG32bwnyaUq8FvaZ0sj1KwKhmKoBfDKFasQU2Gf1jB1TIeKePrC6L7\nXOvd8e6NZLjcYC7vxbkZn+STNRUaY0yL9DC9BqobPOMe1A19HYkFHAopcEjKfHOf\nODgKrg==', '8dd0fa387ee2929fb3baa05990d7f208b8a28a29', '9', '1591167508000', 'CN=BCIA Management CA,C=CN', '-1', '-1', null, '0', '8417386761584097728', '20', 'CN=localhost,C=CN', 'v0yOO/8RVXfqAkUB2va/fYffQhc=', null, '1', '1528096107276', 'ssl');
INSERT INTO `certificatedata` VALUES ('7b006a238b106addc12199feb4125b345c6d96ed', 'MIIDVTCCAj2gAwIBAgIIb4cp4LVakiIwDQYJKoZIhvcNAQELBQAwKjELMAkGA1UE\nBhMCQ04xGzAZBgNVBAMMEkJDSUEgTWFuYWdlbWVudCBDQTAeFw0xODA2MDQwNjE4\nNTFaFw0yMDA2MDMwNjE4NTFaMCoxCzAJBgNVBAYTAkNOMRswGQYDVQQDDBJCQ0lB\nIEFkbWluaXN0cmF0b3IwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQCe\n7b7E87WdrIdnMynB8vWmJOVu/lLW4sdHGSArXN5Y8GPVDxpD6oVCV4aqDWC3gpK+\nxONH/kRAZ6Wn5jEhEKhlUjhfMjELoD30n/rF+z95GD+AHh0Wot/Oxeq9siqcg2F9\nXN0IsPMX9gEkrja/V9HDtvGZ1T977eK3Jtdqkl98GAT+rs8PMfRErLo3QrHj6ONa\nrQZ0Zg65gecq1pt8fKGWoCr0t76pumhZd8mvuaYfYu8haw/Ebvn3qQJL5gw1J5Df\nMKHPufe+a87BRU8LJ6OvoIxsVGu63ux902ZPr722OzUAibfXHbU96pR8ihKL20a4\n0B6zvAvz4MdfaK9ujL0dAgMBAAGjfzB9MAwGA1UdEwEB/wQCMAAwHwYDVR0jBBgw\nFoAUQjeXS1fJMBuQw1Rap+rV7P3/HZAwHQYDVR0lBBYwFAYIKwYBBQUHAwIGCCsG\nAQUFBwMEMB0GA1UdDgQWBBTHLKNjmIDQFxt1Jr4EKsz8sWkfnTAOBgNVHQ8BAf8E\nBAMCBeAwDQYJKoZIhvcNAQELBQADggEBACrmKw/qyVa1BXTRfHneJt5v0xdMy28/\ncO8WXBxQc+OK+5G+vdA7QSpRcpKtoT4pvPzgGpAOQFTme7WWQOE9SsyQmFIvzJPs\npqprkDtSqaqS75tghaMDfr8pZeD80KOGuKdNSusGzjXXoV69HgZ22Hknqhjt2kFF\nYz1Hyulgt7aJpmzMwTCNvEot1xhuTq6++d4MC0YZmNERSRzo7yXzPnoAg5sa72Un\nfl80CoiegAJe64vu680KY8Yan1ATwHOhXEHm44J06GiHxzKzN4EDnAuZxvKnhjK+\nSc/dtjaCM/tDP4UJZ+ishQv2C5z1DYMX8Y+iGoMkgleXUC1pJ3bPvLw=', '8dd0fa387ee2929fb3baa05990d7f208b8a28a29', '1', '1591165131000', 'CN=BCIA Management CA,C=CN', '-1', '-1', null, '0', '8036438105157964322', '20', 'CN=BCIA Administrator,C=CN', 'xyyjY5iA0BcbdSa+BCrM/LFpH50=', null, '1', '1528093731015', 'administrator');
INSERT INTO `certificatedata` VALUES ('8dd0fa387ee2929fb3baa05990d7f208b8a28a29', 'MIIDOTCCAiGgAwIBAgIIX6DfxFmw6mswDQYJKoZIhvcNAQELBQAwKjELMAkGA1UE\nBhMCQ04xGzAZBgNVBAMMEkJDSUEgTWFuYWdlbWVudCBDQTAeFw0xODA2MDQwNTU5\nNTFaFw0zODA1MzAwNTU5NTFaMCoxCzAJBgNVBAYTAkNOMRswGQYDVQQDDBJCQ0lB\nIE1hbmFnZW1lbnQgQ0EwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQCc\n0B/OaCq8iiq0Idk8n2iscZyaN9MDP3ojzU47h2hpaNG8Hi1JgF9AoAs0ByNKOvf0\nZe/2oyBICd7FlyGH/ykHzdl3dzUu2qYitHDwr6Qts62JnHVfU5tOs2gmd66KZtUR\nX6vAWPb9vG1MxtUZ9tmOKDNHwsI6KJBR4cUAWpd1NeQS+ji7aVWtSgsUApVttq+q\nzZH3Z6gO7KqEZNIt99UCthoTEwh9Rx7/wtRrD3gYUiIuYeDtB2vpx+y2iCdi40on\niJazztmNU7nTLph4u3b6CEPuEDg19FvLW/dJIQBGWh9/2XUvvLidC7gHw20NH0TM\nXD7Z8oIEVgZcOw2wspgbAgMBAAGjYzBhMA8GA1UdEwEB/wQFMAMBAf8wHwYDVR0j\nBBgwFoAUQjeXS1fJMBuQw1Rap+rV7P3/HZAwHQYDVR0OBBYEFEI3l0tXyTAbkMNU\nWqfq1ez9/x2QMA4GA1UdDwEB/wQEAwIBhjANBgkqhkiG9w0BAQsFAAOCAQEAD/mZ\n2VGesG48TA34DYAe+Pg3ykLEqGrgi42LZrSYh5jlaV5UBWZOtDgUpWow9JstQMxc\nZDR8X3N/w7St6rdWvc+qMraRusK7F5ty7EddjjT+PpRQNadRaeWdGVaULx8vx+2Q\nuh5qxuOg8ZSf5PPc9zFj3LdtKq/xSJu4+XSE561BSjaS7vbm/V5yLKuec1w417J9\norA+M4abU9pDpTLSsTe5fmA8bWnH1L65go2bZgyubXdMaj9lf9vY5fY8SDAHJRwA\nZFaPO0n5ghbakO3yoQJr804XFpEKoClMv3fjoHdMVE+1O5OfWeLYsDMT93W8Aumh\nmr6i0+IRu7RszXr1Uw==', '8dd0fa387ee2929fb3baa05990d7f208b8a28a29', '0', '2158811991000', 'CN=BCIA Management CA,C=CN', '-1', '-1', null, '0', '6890753464288209515', '20', 'CN=BCIA Management CA,C=CN', 'QjeXS1fJMBuQw1Rap+rV7P3/HZA=', null, '8', '1528091991524', 'SYSTEMCA');

-- ----------------------------
-- Table structure for certificateprofiledata
-- ----------------------------
DROP TABLE IF EXISTS `certificateprofiledata`;
CREATE TABLE `certificateprofiledata` (
  `id` int(11) NOT NULL,
  `certificateProfileName` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `data` longblob NOT NULL,
  `rowProtection` longtext,
  `rowVersion` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of certificateprofiledata
-- ----------------------------

-- ----------------------------
-- Table structure for certreqhistorydata
-- ----------------------------
DROP TABLE IF EXISTS `certreqhistorydata`;
CREATE TABLE `certreqhistorydata` (
  `fingerprint` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `issuerDN` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `rowProtection` longtext,
  `rowVersion` int(11) NOT NULL,
  `serialNumber` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `timestamp` bigint(20) NOT NULL,
  `userDataVO` longtext NOT NULL,
  `username` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`fingerprint`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of certreqhistorydata
-- ----------------------------

-- ----------------------------
-- Table structure for crldata
-- ----------------------------
DROP TABLE IF EXISTS `crldata`;
CREATE TABLE `crldata` (
  `fingerprint` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `base64Crl` longtext NOT NULL,
  `cAFingerprint` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `cRLNumber` int(11) NOT NULL,
  `deltaCRLIndicator` int(11) NOT NULL,
  `issuerDN` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `nextUpdate` bigint(20) NOT NULL,
  `rowProtection` longtext,
  `rowVersion` int(11) NOT NULL,
  `thisUpdate` bigint(20) NOT NULL,
  PRIMARY KEY (`fingerprint`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of crldata
-- ----------------------------
INSERT INTO `crldata` VALUES ('144e5215e5455a160b553d2398a1d990d5c58fe3', 'MIIBpDCBjQIBATANBgkqhkiG9w0BAQsFADAqMQswCQYDVQQGEwJDTjEbMBkGA1UE\nAwwSQkNJQSBNYW5hZ2VtZW50IENBFw0xODA2MDQwNTU5NTFaFw0xODEwMjcyMzQ2\nMzFaoC8wLTAfBgNVHSMEGDAWgBRCN5dLV8kwG5DDVFqn6tXs/f8dkDAKBgNVHRQE\nAwIBATANBgkqhkiG9w0BAQsFAAOCAQEAHB75ReMOanUHY12R78IiABw12mCvCRE0\nAbclY09UPYHDidcCN+GL6zIJ5ZIWlGaJeUsDUv5UnFmlLqRfJgVS2Gu/1v08AdBh\n/FpBx0znZkWBt1qw3JiTAqzHE1te628QZ/eW9A1C5OdmpQY824dR7cb/LtMJsK/v\nWzNjGY6YZU6fhJemSNObdKC56TqgIAheUsk3Bvm1/wH8+z9JbeZXnVknOJJWfRu/\nQ8xNh4ijuxC5GqSIN0XnorPdUpVR67SQkWLAPdClbIJIELz35XKQb6fRJhS8ena7\nVeWtWGShK6qz+p3CPxnAtjuF5zPg517nXHrgN8zGFL8Ma/4qrWIuog==', '8dd0fa387ee2929fb3baa05990d7f208b8a28a29', '1', '-1', 'CN=BCIA Management CA,C=CN', '1540683991000', null, '0', '1528091991000');

-- ----------------------------
-- Table structure for crl_task_rel
-- ----------------------------
DROP TABLE IF EXISTS `crl_task_rel`;
CREATE TABLE `crl_task_rel` (
  `caId` int(11) NOT NULL,
  `createTime` datetime DEFAULT NULL,
  `jobId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`caId`),
  UNIQUE KEY `UK_egtq2w65axvoaqyaw0tnmd2sn` (`jobId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of crl_task_rel
-- ----------------------------

-- ----------------------------
-- Table structure for cryptotokendata
-- ----------------------------
DROP TABLE IF EXISTS `cryptotokendata`;
CREATE TABLE `cryptotokendata` (
  `id` int(11) NOT NULL,
  `lastUpdate` bigint(20) NOT NULL,
  `rowProtection` longtext,
  `rowVersion` int(11) NOT NULL,
  `tokenData` longtext,
  `tokenName` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `tokenProps` longtext,
  `tokenType` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `crtTime` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cryptotokendata
-- ----------------------------
INSERT INTO `cryptotokendata` VALUES ('2056667440', '1528096761850', null, '7', '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', 'ManagementCA', 'I01vbiBKdW4gMDQgMTU6MTk6MjEgQ1NUIDIwMTgNCnN0YXRlZHVtcC5rZXl0ZW1wbGF0ZXM9DQpwaW49Zm9vMTIzDQp0b2tlbk5hbWU9TWFuYWdlbWVudENBDQpOT0RFRkFVTFRQV0Q9dHJ1ZQ0K', 'SoftCryptoToken', '0');

-- ----------------------------
-- Table structure for endentityprofiledata
-- ----------------------------
DROP TABLE IF EXISTS `endentityprofiledata`;
CREATE TABLE `endentityprofiledata` (
  `id` int(11) NOT NULL,
  `data` longblob NOT NULL,
  `profileName` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `rowProtection` longtext,
  `rowVersion` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of endentityprofiledata
-- ----------------------------

-- ----------------------------
-- Table structure for globalconfigurationdata
-- ----------------------------
DROP TABLE IF EXISTS `globalconfigurationdata`;
CREATE TABLE `globalconfigurationdata` (
  `configurationId` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `data` longblob NOT NULL,
  `rowProtection` longtext,
  `rowVersion` int(11) NOT NULL,
  PRIMARY KEY (`configurationId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of globalconfigurationdata
-- ----------------------------
INSERT INTO `globalconfigurationdata` VALUES ('0', 0xACED0005737200226F72672E63657365636F72652E7574696C2E426173653634476574486173684D617007156F73C047AEE9020000787200176A6176612E7574696C2E4C696E6B6564486173684D617034C04E5C106CC0FB0200015A000B6163636573734F72646572787200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000607708000000800000003474000776657273696F6E7372000F6A6176612E6C616E672E466C6F6174DAEDC9A2DB3CF0EC02000146000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870404000007400057469746C65740014454A4243412041646D696E697374726174696F6E74001B656E64656E7469747970726F66696C656C696D69746174696F6E73737200116A6176612E6C616E672E426F6F6C65616ECD207280D59CFAEE0200015A000576616C756578700174001661757468656E7469636174656475736572736F6E6C797371007E000B00740011656E61626C656B65797265636F7665727971007E000C74001369737375656861726477617265746F6B656E737371007E000B00740016656E61626C656963616F63616E616D656368616E676571007E001174000E6E6F646573696E636C7573746572737200176A6176612E7574696C2E4C696E6B656448617368536574D86CD75A95DD2A1E020000787200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000043F40000000000002740011656A6263612D63652D362D74657374766D74000E504332303133303732313132343278740012737461746564756D705F6C6F636B646F776E71007E000E74000B726161646D696E7061746874000961646D696E7765622F740012617661696C61626C656C616E67756167657374001D656E2C62732C63732C64652C66722C6A612C70742C73762C756B2C7A6874000F617661696C61626C657468656D657374002264656661756C745F7468656D652E6373732C7365636F6E645F7468656D652E63737374000A7075626C6963706F72747400043830383074000B70726976617465706F72747400043834343374000E7075626C696370726F746F636F6C7400046874747074000F7072697661746570726F746F636F6C7400056874747073740012617574686F72697A6174696F6E5F7061746874002061646D696E7765622F61646D696E6973747261746F7270726976696C6567657374000C62616E6E6572735F7061746874000762616E6E65727374000763615F7061746874000B61646D696E7765622F6361740009646174615F7061746874001261646D696E7765622F737973636F6E66696774000968656C705F7061746874000468656C7074000B696D616765735F70617468740006696D6167657374000D6C616E67756167655F706174687400096C616E6775616765737400086C6F675F7061746874000C61646D696E7765622F6C6F6774000C7265706F7274735F7061746874001061646D696E7765622F7265706F72747374000772615F7061746874000B61646D696E7765622F726174000A7468656D655F706174687400067468656D657374000E68617264746F6B656E5F7061746874001261646D696E7765622F68617264746F6B656E7400106C616E677561676566696C656E616D6574000C6C616E677561676566696C6574000C6D61696E66696C656E616D657400086D61696E2E6A737074000D696E64657866696C656E616D65740009696E6465782E6A737074000C6D656E7566696C656E616D6574000D61646D696E6D656E752E6A73707400096572726F727061676574000D6572726F72706167652E6A7370740014696563737366696C656E616D65706F73746669787400095F69652D666978657374000A6865616462616E6E657274003161646D696E7765622F62616E6E6572732F61646D696E7765622F62616E6E6572732F686561645F62616E6E65722E6A737074000A666F6F7462616E6E65727400212F62616E6E6572732F2F62616E6E6572732F666F6F745F62616E6E65722E6A737074001268617264746F6B656E656E63727970746361737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E000600000000740018757365617070726F76616C6E6F74696669636174696F6E7371007E0011740019617070726F76616C61646D696E656D61696C6164647265737374000074001C617070726F76616C6E6F74696669636174696F6E66726F6D6164647271007E005574000E6175746F656E726F6C6C2E75736571007E001174000F6175746F656E726F6C6C2E636169647371007E0051B2BC413A7400186175746F656E726F6C6C2E73736C636F6E6E656374696F6E71007E00117400136175746F656E726F6C6C2E61647365727665727400116463312E636F6D70616E792E6C6F63616C7400116175746F656E726F6C6C2E6164706F727471007E00527400176175746F656E726F6C6C2E636F6E6E656374696F6E646E740028434E3D41445265616465722C434E3D55736572732C44433D636F6D70616E792C44433D6C6F63616C7400166175746F656E726F6C6C2E62617365646E2E7573657274001C434E3D55736572732C44433D636F6D70616E792C44433D6C6F63616C7400186175746F656E726F6C6C2E636F6E6E656374696F6E70776474001C4F42463A316A6732316C3138316B7535316B7170316B7875316A643874001A656E61626C65636F6D6D616E646C696E65696E7465726661636571007E000C740025656E61626C65636F6D6D616E646C696E65696E7465726661636564656661756C747573657271007E000C7400207075626C696377656263657274636861696E6F72646572726F6F74666972737471007E000C74000663746C6F67737371007E00023F4000000000000077080000001000000000787800, null, '4');
INSERT INTO `globalconfigurationdata` VALUES ('AVAILABLE_CUSTOM_CERT_EXTENSIONS', 0xACED0005737200176A6176612E7574696C2E4C696E6B6564486173684D617034C04E5C106CC0FB0200015A000B6163636573734F72646572787200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000776657273696F6E7372000F6A6176612E6C616E672E466C6F6174DAEDC9A2DB3CF0EC02000146000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870404000007800, null, '0');
INSERT INTO `globalconfigurationdata` VALUES ('AVAILABLE_EXTENDED_KEY_USAGES', 0xACED0005737200176A6176612E7574696C2E4C696E6B6564486173684D617034C04E5C106CC0FB0200015A000B6163636573734F72646572787200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000307708000000400000001F74000776657273696F6E7372000F6A6176612E6C616E672E466C6F6174DAEDC9A2DB3CF0EC02000146000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B02000078704040000074000B322E352E32392E33372E3074001C454B555F504B49585F414E59455854454E4445444B45595553414745740011312E332E362E312E352E352E372E332E31740013454B555F504B49585F53455256455241555448740011312E332E362E312E352E352E372E332E32740013454B555F504B49585F434C49454E5441555448740011312E332E362E312E352E352E372E332E33740014454B555F504B49585F434F44455349474E494E47740011312E332E362E312E352E352E372E332E34740018454B555F504B49585F454D41494C50524F54454354494F4E740011312E332E362E312E352E352E372E332E38740015454B555F504B49585F54494D455354414D50494E47740011312E332E362E312E352E352E372E332E39740014454B555F504B49585F4F4353505349474E494E47740012312E332E362E312E352E352E372E332E3133740013454B555F504B49585F4541504F564552505050740012312E332E362E312E352E352E372E332E3134740013454B555F504B49585F4541504F5645524C414E740012312E332E362E312E352E352E372E332E3135740013454B555F504B49585F53435650534552564552740012312E332E362E312E352E352E372E332E3136740013454B555F504B49585F53435650434C49454E54740012312E332E362E312E352E352E372E332E3137740011454B555F504B49585F4950534543494B45740012312E332E362E312E352E352E372E332E3230740012454B555F504B49585F534950444F4D41494E740012312E332E362E312E352E352E372E332E3231740012454B555F504B49585F535348434C49454E54740012312E332E362E312E352E352E372E332E3232740012454B555F504B49585F535348534552564552740016312E332E362E312E342E312E3331312E32302E322E32740015454B555F4D535F534D415254434152444C4F474F4E740017312E332E362E312E342E312E3331312E31302E332E3132740016454B555F4D535F444F43554D454E545349474E494E47740016312E332E362E312E342E312E3331312E322E312E3231740016454B555F4D535F434F44455349474E494E475F494E44740016312E332E362E312E342E312E3331312E322E312E3232740016454B555F4D535F434F44455349474E494E475F434F4D740016312E332E362E312E342E312E3331312E31302E332E34740010454B555F4D535F45465343525950544F740018312E332E362E312E342E312E3331312E31302E332E342E31740012454B555F4D535F4546535245434F56455259740017322E31362E3834302E312E3131333734312E312E322E3374000D454B555F494E54454C5F414D5474000E302E342E302E323233312E332E30740013454B555F455453495F54534C5349474E494E47740014312E322E3834302E3131333538332E312E312E35740014454B555F41444F42455F5044465349474E494E47740019312E322E3230332E373036342E312E312E3336393739312E31740011454B555F43534E5F544C53434C49454E54740019312E322E3230332E373036342E312E312E3336393739312E32740011454B555F43534E5F544C5353455256455274000F312E332E362E312E352E322E332E34740015454B555F4B52425F504B494E49545F434C49454E5474000F312E332E362E312E352E322E332E35740012454B555F4B52425F504B494E49545F4B444374000E322E32332E3133362E312E312E3374001A454B555F4943414F5F4D41535445524C4953545349474E494E47740014322E31362E3834302E312E3130312E332E362E38740014454B555F4E4953545F50495643415244415554487800, null, '0');
INSERT INTO `globalconfigurationdata` VALUES ('CESECORE_CONFIGURATION', 0xACED0005737200226F72672E63657365636F72652E7574696C2E426173653634476574486173684D617007156F73C047AEE9020000787200176A6176612E7574696C2E4C696E6B6564486173684D617034C04E5C106CC0FB0200015A000B6163636573734F72646572787200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000037708000000040000000274000776657273696F6E7372000F6A6176612E6C616E672E466C6F6174DAEDC9A2DB3CF0EC02000146000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870404000007400136D6178696D756D2E71756572792E636F756E74737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E0006000001F47800, null, '1');
INSERT INTO `globalconfigurationdata` VALUES ('UPGRADE', 0xACED0005737200176A6176612E7574696C2E4C696E6B6564486173684D617034C04E5C106CC0FB0200015A000B6163636573734F72646572787200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000067708000000080000000374000776657273696F6E7372000F6A6176612E6C616E672E466C6F6174DAEDC9A2DB3CF0EC02000146000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870404000007400117570677261646564546F56657273696F6E740007362E352E302E35740015706F73745570677261646564546F56657273696F6E740005362E342E307800, null, '8');

-- ----------------------------
-- Table structure for hardtokencertificatemap
-- ----------------------------
DROP TABLE IF EXISTS `hardtokencertificatemap`;
CREATE TABLE `hardtokencertificatemap` (
  `certificateFingerprint` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `rowProtection` longtext,
  `rowVersion` int(11) NOT NULL,
  `tokenSN` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`certificateFingerprint`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hardtokencertificatemap
-- ----------------------------

-- ----------------------------
-- Table structure for hardtokendata
-- ----------------------------
DROP TABLE IF EXISTS `hardtokendata`;
CREATE TABLE `hardtokendata` (
  `tokenSN` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `cTime` bigint(20) NOT NULL,
  `data` longblob,
  `mTime` bigint(20) NOT NULL,
  `rowProtection` longtext,
  `rowVersion` int(11) NOT NULL,
  `significantIssuerDN` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `tokenType` int(11) NOT NULL,
  `username` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `dataUnsafe` tinyblob,
  PRIMARY KEY (`tokenSN`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hardtokendata
-- ----------------------------

-- ----------------------------
-- Table structure for hardtokenissuerdata
-- ----------------------------
DROP TABLE IF EXISTS `hardtokenissuerdata`;
CREATE TABLE `hardtokenissuerdata` (
  `id` int(11) NOT NULL,
  `adminGroupId` int(11) NOT NULL,
  `alias` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `data` longblob NOT NULL,
  `rowProtection` longtext,
  `rowVersion` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hardtokenissuerdata
-- ----------------------------

-- ----------------------------
-- Table structure for hardtokenprofiledata
-- ----------------------------
DROP TABLE IF EXISTS `hardtokenprofiledata`;
CREATE TABLE `hardtokenprofiledata` (
  `id` int(11) NOT NULL,
  `data` longtext,
  `name` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `rowProtection` longtext,
  `rowVersion` int(11) NOT NULL,
  `updateCounter` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hardtokenprofiledata
-- ----------------------------

-- ----------------------------
-- Table structure for hardtokenpropertydata
-- ----------------------------
DROP TABLE IF EXISTS `hardtokenpropertydata`;
CREATE TABLE `hardtokenpropertydata` (
  `id` varchar(80) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `property` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `rowProtection` longtext,
  `rowVersion` int(11) NOT NULL,
  `value` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`,`property`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hardtokenpropertydata
-- ----------------------------

-- ----------------------------
-- Table structure for hibernate_sequence
-- ----------------------------
DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hibernate_sequence
-- ----------------------------
INSERT INTO `hibernate_sequence` VALUES ('6');

-- ----------------------------
-- Table structure for internalkeybindingdata
-- ----------------------------
DROP TABLE IF EXISTS `internalkeybindingdata`;
CREATE TABLE `internalkeybindingdata` (
  `id` int(11) NOT NULL,
  `certificateId` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `cryptoTokenId` int(11) NOT NULL,
  `keyBindingType` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `keyPairAlias` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `lastUpdate` bigint(20) NOT NULL,
  `name` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `rawData` longtext,
  `rowProtection` longtext,
  `rowVersion` int(11) NOT NULL,
  `status` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of internalkeybindingdata
-- ----------------------------

-- ----------------------------
-- Table structure for jpa_dept
-- ----------------------------
DROP TABLE IF EXISTS `jpa_dept`;
CREATE TABLE `jpa_dept` (
  `dept_id` varchar(255) NOT NULL,
  `dept_name` varchar(255) DEFAULT NULL,
  `dept_desc` varchar(255) DEFAULT NULL,
  `admin` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jpa_dept
-- ----------------------------

-- ----------------------------
-- Table structure for jpa_user
-- ----------------------------
DROP TABLE IF EXISTS `jpa_user`;
CREATE TABLE `jpa_user` (
  `id` varchar(255) NOT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jpa_user
-- ----------------------------

-- ----------------------------
-- Table structure for keyrecoverydata
-- ----------------------------
DROP TABLE IF EXISTS `keyrecoverydata`;
CREATE TABLE `keyrecoverydata` (
  `certSN` varchar(80) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `issuerDN` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `cryptoTokenId` int(11) NOT NULL,
  `keyAlias` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `keyData` longtext NOT NULL,
  `markedAsRecoverable` tinyint(4) NOT NULL,
  `publicKeyId` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `rowProtection` longtext,
  `rowVersion` int(11) NOT NULL,
  `username` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`certSN`,`issuerDN`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of keyrecoverydata
-- ----------------------------

-- ----------------------------
-- Table structure for peerdata
-- ----------------------------
DROP TABLE IF EXISTS `peerdata`;
CREATE TABLE `peerdata` (
  `id` int(11) NOT NULL,
  `connectorState` int(11) NOT NULL,
  `data` longtext,
  `name` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `rowProtection` longtext,
  `rowVersion` int(11) NOT NULL,
  `url` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of peerdata
-- ----------------------------

-- ----------------------------
-- Table structure for publisherdata
-- ----------------------------
DROP TABLE IF EXISTS `publisherdata`;
CREATE TABLE `publisherdata` (
  `id` int(11) NOT NULL,
  `data` longtext,
  `name` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `rowProtection` longtext,
  `rowVersion` int(11) NOT NULL,
  `updateCounter` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of publisherdata
-- ----------------------------

-- ----------------------------
-- Table structure for publisherqueuedata
-- ----------------------------
DROP TABLE IF EXISTS `publisherqueuedata`;
CREATE TABLE `publisherqueuedata` (
  `pk` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `fingerprint` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `lastUpdate` bigint(20) NOT NULL,
  `publishStatus` int(11) NOT NULL,
  `publishType` int(11) NOT NULL,
  `publisherId` int(11) NOT NULL,
  `rowProtection` longtext,
  `rowVersion` int(11) NOT NULL,
  `timeCreated` bigint(20) NOT NULL,
  `tryCounter` int(11) NOT NULL,
  `volatileData` longtext,
  PRIMARY KEY (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of publisherqueuedata
-- ----------------------------

-- ----------------------------
-- Table structure for servicedata
-- ----------------------------
DROP TABLE IF EXISTS `servicedata`;
CREATE TABLE `servicedata` (
  `id` int(11) NOT NULL,
  `data` longtext,
  `name` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `nextRunTimeStamp` bigint(20) NOT NULL,
  `rowProtection` longtext,
  `rowVersion` int(11) NOT NULL,
  `runTimeStamp` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of servicedata
-- ----------------------------

-- ----------------------------
-- Table structure for sys_basecode
-- ----------------------------
DROP TABLE IF EXISTS `sys_basecode`;
CREATE TABLE `sys_basecode` (
  `keyname` varchar(255) NOT NULL,
  `section` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`keyname`,`section`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_basecode
-- ----------------------------
INSERT INTO `sys_basecode` VALUES ('ADMIN_URL', 'URL', '后台管理系统的URL', 'http://localhost/admin/index.html');
INSERT INTO `sys_basecode` VALUES ('PUBLIC_URL', 'URL', '公共模块的URL', 'http://localhost/index.html');

-- ----------------------------
-- Table structure for sys_crl_strategy
-- ----------------------------
DROP TABLE IF EXISTS `sys_crl_strategy`;
CREATE TABLE `sys_crl_strategy` (
  `caid` int(11) NOT NULL,
  `cronExpr` varchar(255) DEFAULT NULL,
  `crttime` bigint(20) NOT NULL,
  `crtuser` varchar(255) DEFAULT NULL,
  `modtime` bigint(20) NOT NULL,
  `moduser` varchar(255) DEFAULT NULL,
  `running` bit(1) NOT NULL,
  `taskresult` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`caid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_crl_strategy
-- ----------------------------

-- ----------------------------
-- Table structure for task_schedule_job
-- ----------------------------
DROP TABLE IF EXISTS `task_schedule_job`;
CREATE TABLE `task_schedule_job` (
  `jobId` bigint(20) NOT NULL,
  `beanClass` varchar(255) DEFAULT NULL,
  `concurrentStatus` varchar(2) NOT NULL,
  `createTime` datetime DEFAULT NULL,
  `cronExpression` varchar(255) DEFAULT NULL,
  `description` longtext,
  `jobGroup` varchar(255) DEFAULT NULL,
  `jobName` longtext NOT NULL,
  `jobStatus` varchar(2) NOT NULL,
  `methodName` varchar(255) DEFAULT NULL,
  `springBeanId` varchar(255) DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`jobId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of task_schedule_job
-- ----------------------------

-- ----------------------------
-- Table structure for userdata
-- ----------------------------
DROP TABLE IF EXISTS `userdata`;
CREATE TABLE `userdata` (
  `username` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `cAId` int(11) NOT NULL,
  `cardNumber` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `certificateProfileId` int(11) NOT NULL,
  `clearPassword` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `endEntityProfileId` int(11) NOT NULL,
  `extendedInformationData` longtext,
  `hardTokenIssuerId` int(11) NOT NULL,
  `keyStorePassword` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `passwordHash` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `rowProtection` longtext,
  `rowVersion` int(11) NOT NULL,
  `status` int(11) NOT NULL,
  `subjectAltName` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `subjectDN` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `subjectEmail` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `timeCreated` bigint(20) NOT NULL,
  `timeModified` bigint(20) NOT NULL,
  `tokenType` int(11) NOT NULL,
  `type` int(11) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of userdata
-- ----------------------------
INSERT INTO `userdata` VALUES ('administrator', '1443565835', null, '1', null, '1', null, '0', null, '$2a$01$M5SvfhweIG0HawPi6IsS2ufAzDz4vpNzHlHwDnGxDhZzjWU2JCgl.', null, '1', '40', '', 'CN=BCIA Administrator,C=CN', '', '1528093714161', '1528093731527', '2', '1');
INSERT INTO `userdata` VALUES ('auditor', '1443565835', null, '1', null, '1', null, '0', null, '$2a$01$YRaDUoicF0OaftunFdst5.j45pWDUq1csihQoT9/hmjQzSvRa5MnC', null, '1', '40', '', 'CN=BCIA Auditor,C=CN', '', '1528093959324', '1528093971779', '2', '1');
INSERT INTO `userdata` VALUES ('ssl', '1443565835', null, '9', null, '1', null, '0', null, '$2a$01$eqA0LjqWYFX01ttg0busGOhLp8JDDJf3iNN8.C7bJhApkIfbCMYlC', null, '1', '40', 'dnsName=localhost,IPAddress=127.0.0.1', 'CN=localhost,C=CN', '', '1528096017093', '1528096108610', '3', '1');

-- ----------------------------
-- Table structure for userdatasourcedata
-- ----------------------------
DROP TABLE IF EXISTS `userdatasourcedata`;
CREATE TABLE `userdatasourcedata` (
  `id` int(11) NOT NULL,
  `data` longtext,
  `name` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `rowProtection` longtext,
  `rowVersion` int(11) NOT NULL,
  `updateCounter` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of userdatasourcedata
-- ----------------------------
