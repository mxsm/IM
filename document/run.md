### 1 如何启动注册中心(Register)

```shell
usage: register [-c <arg>] [-h] [-p <arg>]
 -c,--config <arg>   register config file path
 -h,--help           Print help
 -p,--port <arg>     register bind port
```

#### 1.1 IDEA如何启动注册中心

![](https://github.com/mxsm/picture/blob/main/IM/%E6%B3%A8%E5%86%8C%E4%B8%AD%E5%BF%83IDEA%E5%90%AF%E5%8A%A8%E9%85%8D%E7%BD%AE.png?raw=true)

配置好项目的启动参数。-c 的参数根据个人具体情况配置。主要看register.conf文件的位置

```shell
-c xxxxpath\register.conf -p 8888
example: -c E:\develop\github\IM\distribution\conf\register.conf -p 8888
```



> 注意:配置的是项目的启动参数，而不是JVM参数或者环境变量

#### 1.2 Windows平台如何运行注册中心

```shell
git clone https://github.com/mxsm/IM.git
cd IM
mvn -Prelease-all -DskipTests clean install -U
cd distribution/target/im-0.1-SNAPSHOT/im-0.1-SNAPSHOT
bin\imregister.cmd -c E:\develop\github\IM\distribution\conf\register.conf -p 8888
```

#### 1.3 Linux平台如何运行注册中心

待编写..................

### 2 如何启动鹊桥

```shell
usage: MagpieBridge [-c <arg>] [-h] [-p <arg>] [-r <arg>]
 -c,--config <arg>             MagpieBridge config file path
 -h,--help                     Print help
 -p,--port <arg>               register bind port
 -r,--register-address <arg>   register address,example:127.0.0.1:8080,192.168.10.16:8080
```

#### 2.1 IDEA如何启动鹊桥