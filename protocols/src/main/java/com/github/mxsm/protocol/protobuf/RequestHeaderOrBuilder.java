// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: RequestHeader.proto

package com.github.mxsm.protocol.protobuf;

public interface RequestHeaderOrBuilder extends
    // @@protoc_insertion_point(interface_extends:com.github.mxsm.protocol.protobuf.RequestHeader)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   *protocol
   * </pre>
   *
   * <code>string protocol = 1;</code>
   * @return The protocol.
   */
  java.lang.String getProtocol();
  /**
   * <pre>
   *protocol
   * </pre>
   *
   * <code>string protocol = 1;</code>
   * @return The bytes for protocol.
   */
  com.google.protobuf.ByteString
      getProtocolBytes();

  /**
   * <pre>
   *protocol version
   * </pre>
   *
   * <code>string version = 2;</code>
   * @return The version.
   */
  java.lang.String getVersion();
  /**
   * <pre>
   *protocol version
   * </pre>
   *
   * <code>string version = 2;</code>
   * @return The bytes for version.
   */
  com.google.protobuf.ByteString
      getVersionBytes();

  /**
   * <pre>
   *source ip
   * </pre>
   *
   * <code>uint32 sourceAddress = 3;</code>
   * @return The sourceAddress.
   */
  int getSourceAddress();

  /**
   * <pre>
   *remote ip
   * </pre>
   *
   * <code>uint32 remoteAddress = 4;</code>
   * @return The remoteAddress.
   */
  int getRemoteAddress();

  /**
   * <pre>
   *content type like http content type
   * </pre>
   *
   * <code>string contentType = 6;</code>
   * @return The contentType.
   */
  java.lang.String getContentType();
  /**
   * <pre>
   *content type like http content type
   * </pre>
   *
   * <code>string contentType = 6;</code>
   * @return The bytes for contentType.
   */
  com.google.protobuf.ByteString
      getContentTypeBytes();

  /**
   * <pre>
   *Terminal Type
   * </pre>
   *
   * <code>.com.github.mxsm.protocol.protobuf.TerminalType terminalType = 7;</code>
   * @return The enum numeric value on the wire for terminalType.
   */
  int getTerminalTypeValue();
  /**
   * <pre>
   *Terminal Type
   * </pre>
   *
   * <code>.com.github.mxsm.protocol.protobuf.TerminalType terminalType = 7;</code>
   * @return The terminalType.
   */
  com.github.mxsm.protocol.protobuf.constant.TerminalType getTerminalType();

  /**
   * <pre>
   *send message device token
   * </pre>
   *
   * <code>string deviceToken = 8;</code>
   * @return The deviceToken.
   */
  java.lang.String getDeviceToken();
  /**
   * <pre>
   *send message device token
   * </pre>
   *
   * <code>string deviceToken = 8;</code>
   * @return The bytes for deviceToken.
   */
  com.google.protobuf.ByteString
      getDeviceTokenBytes();

  /**
   * <pre>
   *message sender
   * </pre>
   *
   * <code>string sender = 9;</code>
   * @return The sender.
   */
  java.lang.String getSender();
  /**
   * <pre>
   *message sender
   * </pre>
   *
   * <code>string sender = 9;</code>
   * @return The bytes for sender.
   */
  com.google.protobuf.ByteString
      getSenderBytes();

  /**
   * <pre>
   *message receiver
   * </pre>
   *
   * <code>string receiver = 10;</code>
   * @return The receiver.
   */
  java.lang.String getReceiver();
  /**
   * <pre>
   *message receiver
   * </pre>
   *
   * <code>string receiver = 10;</code>
   * @return The bytes for receiver.
   */
  com.google.protobuf.ByteString
      getReceiverBytes();
}
