**聚龙链CA-全面支持国际通用算法和国密算法的数字证书(CA)管理系统**

**一、系统模块结构**<br/>

1.通用代码模块bcia-ca-szca-common<br/>
2.在线证书状态查询服务（OCSP）bcia-ca-szca-ocsp-server<br/>
3.对外公共服务模块 bcia-ca-szca-publicweb<br/>
CA系统的前端公共网站，包含证书业务API接口，注册实体、申请证书、CA证书下载、CRL下载、实体证书下载、CSR生成工具、CSR解析工具等功能<br/>
4.CA后台管理模块 bcia-ca-szca-adminweb<br/>
CA后台管理系统，包含证书模板管理、CA生成和配置管理、密钥配置管理、终端实体模板管理、终端用户和证书管理、实体证书流程管理等模块。<br/>



**二、数据库;**
1.数据库连接配置文件是bcia-ca-szca-publicweb/src/main/webapp/META-INF/context.xml
2.建议使用MySql5.7或者以上版本,Linux下设置表名大小写不敏感.
3.请参考 doc -> 初始化->sql的脚本初始化相关数据库。

**三、应用服务器**
1.建议使用Tomcat 8.5版本
2.后台管理系统需要使用HTTPS，证书参考doc -> 初始化 -> cert
服务器证书 bica_serv.jks，密码12345678，
管理员证书 bcia_truststore.jks，密码changeit

**四、API接口**
1.证书业务API接口发布于bcia-ca-szca-publicweb,若发布路径是 http://ip:port/publicweb,则各个接口具体对应如下，请使用POST方式调用下面接口：
a). enrollOrUpdate: http://ip:port/publicweb/enrollOrUpdate.html
    提供用户注册、证书申请和证书更新的服务接口，实现实时用户注册/更新并签发数字证书的功能
b). revoke: http://ip:port/publicweb/revoke.html
    提供撤销用户证书的服务接口，实现实时撤销用户或指定证书的的功能
