#CLH2.1

<h1>mybatis 自动生成 以下是必须修改的内容</h1>

generatorConfig.properties文件 4到7行 修改为自己的数据库连接

jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/quick4j?useUnicode=true&characterEncoding=utf-8
jdbc.username=root
jdbc.password=root


generatorConfig.xml 的第48行 tableName 为数据表名，domainObjectName为对应的实体类名


<h1>除以上以外不可修改</h1>

修改完成后，如下图所示启动插件
![输入图片说明](http://git.oschina.net/uploads/images/2017/0316/172648_01228327_753096.png "mybatis")