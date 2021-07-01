// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: RemotingRequest.proto

package com.github.mxsm.protocol.protobuf;

public interface RemotingCommandOrBuilder extends
    // @@protoc_insertion_point(interface_extends:com.github.mxsm.protocol.protobuf.RemotingCommand)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   *请求头部
   * </pre>
   *
   * <code>.com.github.mxsm.protocol.protobuf.RequestHeaders requestHeaders = 1;</code>
   * @return Whether the requestHeaders field is set.
   */
  boolean hasRequestHeaders();
  /**
   * <pre>
   *请求头部
   * </pre>
   *
   * <code>.com.github.mxsm.protocol.protobuf.RequestHeaders requestHeaders = 1;</code>
   * @return The requestHeaders.
   */
  com.github.mxsm.protocol.protobuf.RequestHeaders getRequestHeaders();
  /**
   * <pre>
   *请求头部
   * </pre>
   *
   * <code>.com.github.mxsm.protocol.protobuf.RequestHeaders requestHeaders = 1;</code>
   */
  com.github.mxsm.protocol.protobuf.RequestHeadersOrBuilder getRequestHeadersOrBuilder();

  /**
   * <pre>
   *返回头部
   * </pre>
   *
   * <code>.com.github.mxsm.protocol.protobuf.ResponseHeaders responseHeaders = 2;</code>
   * @return Whether the responseHeaders field is set.
   */
  boolean hasResponseHeaders();
  /**
   * <pre>
   *返回头部
   * </pre>
   *
   * <code>.com.github.mxsm.protocol.protobuf.ResponseHeaders responseHeaders = 2;</code>
   * @return The responseHeaders.
   */
  com.github.mxsm.protocol.protobuf.ResponseHeaders getResponseHeaders();
  /**
   * <pre>
   *返回头部
   * </pre>
   *
   * <code>.com.github.mxsm.protocol.protobuf.ResponseHeaders responseHeaders = 2;</code>
   */
  com.github.mxsm.protocol.protobuf.ResponseHeadersOrBuilder getResponseHeadersOrBuilder();

  /**
   * <pre>
   *命令类型
   * </pre>
   *
   * <code>.com.github.mxsm.protocol.protobuf.RemotingCommandType commandType = 3;</code>
   * @return The enum numeric value on the wire for commandType.
   */
  int getCommandTypeValue();
  /**
   * <pre>
   *命令类型
   * </pre>
   *
   * <code>.com.github.mxsm.protocol.protobuf.RemotingCommandType commandType = 3;</code>
   * @return The commandType.
   */
  com.github.mxsm.protocol.protobuf.RemotingCommandType getCommandType();

  /**
   * <pre>
   *请求码
   * </pre>
   *
   * <code>int32 requestCode = 4;</code>
   * @return The requestCode.
   */
  int getRequestCode();

  /**
   * <code>int32 payloadCrc32 = 5;</code>
   * @return The payloadCrc32.
   */
  int getPayloadCrc32();

  /**
   * <pre>
   *是否单向
   * </pre>
   *
   * <code>bool oneway = 6;</code>
   * @return The oneway.
   */
  boolean getOneway();

  /**
   * <pre>
   *命令ID
   * </pre>
   *
   * <code>uint64 commandId = 7;</code>
   * @return The commandId.
   */
  long getCommandId();

  /**
   * <pre>
   *创建时间
   * </pre>
   *
   * <code>uint64 createTimestamp = 8;</code>
   * @return The createTimestamp.
   */
  long getCreateTimestamp();

  /**
   * <pre>
   *消息体
   * </pre>
   *
   * <code>bytes payload = 9;</code>
   * @return The payload.
   */
  com.google.protobuf.ByteString getPayload();
}
