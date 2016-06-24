  * [SSO之CAS单点登录实例演示](http://www.micmiu.com/enterprise-app/sso/sso-cas-sample/)
  * [单点登录之Jasig CAS初体验](http://liunancun.iteye.com/blog/2176225)
  * [cas-server](https://developer.jasig.org/cas/)
  * [cas-client](https://developer.jasig.org/cas-clients/)

1. 配置host

  *127.0.0.1	demo.yeall.me
  *127.0.0.1	app1.yeall.me
  *127.0.0.1	app2.yeall.me
 
2. 生成证书

```keytool -genkey -alias ssodemo -keyalg RSA -keysize 1024 -keypass ssoSSOPwd -validity 365 -keystore D:\devs\sso\ssodemo.keystore -storepass ssoSSOPwd```

>生成证书过程中，名字和姓氏同hosts 保持一致
>keypass 和 storepass 两个密码要一致，否则下面tomcat 配置https 访问失败；

3. 导出证书

```keytool -export -alias ssodemo -keystore D:\devs\sso\ssodemo.keystore -file d:\devs\sso\ssodemo.crt```

>  -storepass ssoSSOPwd 导出密码和生成证书密码一致

4. 客户端导入证书

```keytool -import -keystore %JAVA_HOME%\jre\lib\security\cacerts -file d:\devs\sso\ssodemo.crt -alias ssodemo```

> 密码:changeit

5. 配置tomcat

修改server.xml
```<Connector port="8443" protocol="org.apache.coyote.http11.Http11Protocol"
               maxThreads="150" SSLEnabled="true" scheme="https" secure="true"
			   keystoreFile="D:/devs/sso/ssodemo.keystore" keystorePass="ssoSSOPwd" URIEncoding="UTF-8"
               clientAuth="false" sslProtocol="TLS" />```

> 增加 keystoreFile="D:/devs/sso/ssodemo.keystore" keystorePass="ssoSSOPwd" URIEncoding="UTF-8"

6. 验证https配置

https://demo.yeall.me:8443


>用户名密码配置在Spring配置文件 
>打开cas/WEB-INF/deployerConfigContext.xml 
>找到primaryAuthenticationHandler的bean配置 
>可以看到初始用户名为casuser密码为Mellon 

[Shiro & CAS 实现单点登录](http://howiefh.github.io/2015/05/19/shiro-cas-single-sign-on/)
![Cas](http://fh-1.qiniudn.com/cas-clip.jpg)


![流程](http://fh-1.qiniudn.com/cas-noproxy.png)
