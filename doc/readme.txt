此CA系统的系统模块结构如下：
1.一个公共模块bcia-ca-szca-common
2.一个客户端工具模块bcia-ca-szca-client
3.两个webapp模块：
a)bcia-ca-szca-publicweb
CA系统的公共网站，包含对外公共API接口，注册实体、申请证书、下载CA证书、下载CRL、下载实体证书、CSR生成工具、CSR解析工具等功能
b)bcia-ca-szca-adminweb
CA系统的后台配置管理应用，CA系统所有配置、管理功能都由此应用提供。

数据库;
1.数据库连接配置文件是bcia-ca-szca-publicweb/src/main/webapp/META-INF/context.xml
2.建议使用MySql5.7或者以上版本.
