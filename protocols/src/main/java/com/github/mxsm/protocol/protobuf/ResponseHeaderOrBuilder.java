// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: ResponseHeader.proto

package com.github.mxsm.protocol.protobuf;

public interface ResponseHeaderOrBuilder extends
    // @@protoc_insertion_point(interface_extends:com.github.mxsm.protocol.protobuf.ResponseHeader)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>string protocol = 1;</code>
   * @return The protocol.
   */
  java.lang.String getProtocol();
  /**
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
   *response content type
   * </pre>
   *
   * <code>string contentType = 3;</code>
   * @return The contentType.
   */
  java.lang.String getContentType();
  /**
   * <pre>
   *response content type
   * </pre>
   *
   * <code>string contentType = 3;</code>
   * @return The bytes for contentType.
   */
  com.google.protobuf.ByteString
      getContentTypeBytes();

  /**
   * <pre>
   *response status
   * </pre>
   *
   * <code>uint32 status = 4;</code>
   * @return The status.
   */
  int getStatus();

  /**
   * <pre>
   *sender
   * </pre>
   *
   * <code>string sender = 5;</code>
   * @return The sender.
   */
  java.lang.String getSender();
  /**
   * <pre>
   *sender
   * </pre>
   *
   * <code>string sender = 5;</code>
   * @return The bytes for sender.
   */
  com.google.protobuf.ByteString
      getSenderBytes();

  /**
   * <pre>
   *receiver
   * </pre>
   *
   * <code>string receiver = 6;</code>
   * @return The receiver.
   */
  java.lang.String getReceiver();
  /**
   * <pre>
   *receiver
   * </pre>
   *
   * <code>string receiver = 6;</code>
   * @return The bytes for receiver.
   */
  com.google.protobuf.ByteString
      getReceiverBytes();
}
