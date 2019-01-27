package github.ant.mxsm.protocol.message;

public enum  MessageType {

    /**
     * 功能性消息类型
     */
    CONN, //用户连接服务消息类型
    ACK , //服务器收到消息和客户端收到消息的确认消息类型
    DISCONN, // 断开与服务器的消息类型


    /**
     * 数据传输消息类型
     */
    SINGLE,  //单聊
    GROUP   //群聊
    ;
}
