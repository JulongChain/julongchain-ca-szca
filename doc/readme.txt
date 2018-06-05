一、此CA系统的系统模块结构如下：
1.一个公共模块bcia-ca-szca-common
2.一个客户端工具模块bcia-ca-szca-client
3.两个webapp模块：
a)bcia-ca-szca-publicweb
CA系统的公共网站，包含对外公共API接口，注册实体、申请证书、下载CA证书、下载CRL、下载实体证书、CSR生成工具、CSR解析工具等功能
b)bcia-ca-szca-adminweb
CA系统的后台配置管理应用，CA系统所有配置、管理功能都由此应用提供。

二、数据库;
1.数据库连接配置文件是bcia-ca-szca-publicweb/src/main/webapp/META-INF/context.xml
2.建议使用MySql5.7或者以上版本.
3.正确设置数据库连接参数后，启动bcia-ca-szca-adminweb系统时会自动创建数据库对象（表、序列、视图、过程等），同时导入必要的初始化数据。

三、应用服务器
建议使用Tomcat 8.5版本

四、API接口
1.CA系统的对外API接口发布于bcia-ca-szca-publicweb,若发布路径是 http://ip:port/context,则各个接口具体对应如下，请使用POST方式调用下面接口：
a). getcacert: http://ip:port/context/cainfo
b). register: http://ip:port/context/register
c). enroll: http://ip:port/context/enroll
d). reenroll: http://ip:port/context/reenroll
e). revoke: http://ip:port/context/revoke
2.bcia-ca-szca-publicweb也提供相应的功能，用户可以直接使用页面功能，实现上面接口的对应的所有功能，具体对应如下：
a). getcacert: CA证书
b). register: 注册实体
c). enroll: 申请证书
d). reenroll: 申请证书
e). revoke: 撤销证书
3.在尚未提供客户端证书工具时，可以先使用Fabric-CA-Client连接此模块，实现相关功能。注意，若要使用Fabric-CA-Client调用此模块的接口，则此模块必须发布为根应用(即Tomcat的ROOT应用)