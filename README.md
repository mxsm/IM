# 即时通讯-IM
![Deploy Status](https://travis-ci.com/mxsm/IM.svg?branch=develop)
[![IM](https://codecov.io/gh/mxsm/IM/branch/develop/graph/badge.svg)](https://codecov.io/gh/mxsm/IM)
[![Coverage Status](https://coveralls.io/repos/github/mxsm/IM/badge.svg?branch=develop)](https://coveralls.io/github/mxsm/IM?branch=develop)
[![Average time to resolve an issue](http://isitmaintained.com/badge/resolution/mxsm/IM.svg)](http://isitmaintained.com/project/mxsm/IM "Average time to resolve an issue")
[![Percentage of issues still open](http://isitmaintained.com/badge/open/mxsm/IM.svg)](http://isitmaintained.com/project/mxsm/IM "Percentage of issues still open")  

### 模块

IM 基于Netty的即时通讯
![](https://github.com/mxsm/picture/blob/main/project/%E8%81%8A%E5%A4%A9%E5%B7%A5%E5%85%B7.png?raw=true)

消息的转发模块叫鹊桥(取自中国航天探月工程的鹊桥中继卫星)

### 线程模型

![](https://github.com/mxsm/picture/blob/main/IM/%E6%9C%8D%E5%8A%A1%E7%AB%AF%E7%BA%BF%E7%A8%8B%E6%A8%A1%E5%9E%8B.png?raw=true)

### 从源码构建

```shell
git clone https://github.com/mxsm/IM.git
cd IM
mvn -Prelease-all -DskipTests clean install -U
cd distribution/target/im-0.1-SNAPSHOT/im-0.1-SNAPSHOT
```

### 鹊桥(MagpieBridge)

在鹊桥的集群中，只有一个Master,多个SLAVE。一个集群名称唯一，集群中的节点的名称可以相同。节点ID集群内必须唯一。

- 节点ID最小的为MASTER，当Master掉线或者失去作用的时候回将节点ID最小的为MASTER。
- 一个集群内节点名称必须相同。不同的节点名称可以不相同



### 工程目录结构说明

- **common**    包含了工程的通用工具类
- **container**   包含了启动容器(当前只有spring容器的实现)
- **core**   处理消息的逻辑
- **distribute** 服务器分发消息(当前只有akka的实现)
- **protocols**  IM的消息协议(当前用的protobuf)
- **server**  IM通讯协议的服务(现在就实现了TCP)

### 协议文件结构
结构的设计参考了HTTP的方式
```
syntax = "proto3";

package com.github.mxsm.protocol.protobuf;

option java_package = "com.github.mxsm.protocol.protobuf";
option java_outer_classname = "Message";

message MessageProtobuf{

	 Header header = 1;
	 message Header{
		 string protocol = 1; //协议
		 string version = 2; //协议版本号
		 string sender = 3; //消息发送者
		 string receiver = 4; //消息接收者
		 string messageType = 5; //消息类型
		 string contentType = 6; //参照http content-type类型
		 string terminalType = 7; //发送消息的终端
		 string deviceToken = 8; //发送消息的终端设备号
	 }
	 Auth auth = 2;
	 message Auth{
		 string token = 1;
	 }
	 string messageUnique = 3; //消息唯一值
	 int64 messageTime = 4; //消息的创建时间
	 bytes payload = 5;//消息体
}
```


### 编译protobuf.proto文件命令
```
protoc -I=$SRC_DIR --java_out=$DST_DIR $SRC_DIR/addressbook.proto
```

### 消息类型

及时通讯中消息类型大致在这里为两类  
#### **1. 功能控制类型**
主要用来控制与服务器之前的消息确认和服务器与客户端的连接关系

```
CONN //用户连接服务消息类型
ACK  //服务器收到消息和客户端收到消息的确认消息类型
DISCONN // 断开与服务器的消息类型
```

#### **2. 数据传输类型**
对于聊天来说就只是单聊和群聊
```
SINGLE  //单聊
GROUP   //群聊
```
