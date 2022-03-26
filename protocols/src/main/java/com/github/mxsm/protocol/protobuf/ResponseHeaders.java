// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: ResponseHeader.proto

package com.github.mxsm.protocol.protobuf;

/**
 * Protobuf type {@code com.github.mxsm.protocol.protobuf.ResponseHeaders}
 */
public final class ResponseHeaders extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:com.github.mxsm.protocol.protobuf.ResponseHeaders)
    ResponseHeadersOrBuilder {
private static final long serialVersionUID = 0L;
  // Use ResponseHeaders.newBuilder() to construct.
  private ResponseHeaders(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private ResponseHeaders() {
    protocol_ = "";
    version_ = "";
    contentType_ = "";
    sender_ = "";
    receiver_ = "";
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new ResponseHeaders();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private ResponseHeaders(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          case 10: {
            java.lang.String s = input.readStringRequireUtf8();

            protocol_ = s;
            break;
          }
          case 18: {
            java.lang.String s = input.readStringRequireUtf8();

            version_ = s;
            break;
          }
          case 26: {
            java.lang.String s = input.readStringRequireUtf8();

            contentType_ = s;
            break;
          }
          case 32: {

            status_ = input.readUInt32();
            break;
          }
          case 42: {
            java.lang.String s = input.readStringRequireUtf8();

            sender_ = s;
            break;
          }
          case 50: {
            java.lang.String s = input.readStringRequireUtf8();

            receiver_ = s;
            break;
          }
          default: {
            if (!parseUnknownField(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return com.github.mxsm.protocol.protobuf.ResponseHeadersOuterClass.internal_static_com_github_mxsm_protocol_protobuf_ResponseHeaders_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.github.mxsm.protocol.protobuf.ResponseHeadersOuterClass.internal_static_com_github_mxsm_protocol_protobuf_ResponseHeaders_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.github.mxsm.protocol.protobuf.ResponseHeaders.class, com.github.mxsm.protocol.protobuf.ResponseHeaders.Builder.class);
  }

  public static final int PROTOCOL_FIELD_NUMBER = 1;
  private volatile java.lang.Object protocol_;
  /**
   * <pre>
   *协议
   * </pre>
   *
   * <code>string protocol = 1;</code>
   * @return The protocol.
   */
  @java.lang.Override
  public java.lang.String getProtocol() {
    java.lang.Object ref = protocol_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      protocol_ = s;
      return s;
    }
  }
  /**
   * <pre>
   *协议
   * </pre>
   *
   * <code>string protocol = 1;</code>
   * @return The bytes for protocol.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getProtocolBytes() {
    java.lang.Object ref = protocol_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      protocol_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int VERSION_FIELD_NUMBER = 2;
  private volatile java.lang.Object version_;
  /**
   * <pre>
   *协议版本号
   * </pre>
   *
   * <code>string version = 2;</code>
   * @return The version.
   */
  @java.lang.Override
  public java.lang.String getVersion() {
    java.lang.Object ref = version_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      version_ = s;
      return s;
    }
  }
  /**
   * <pre>
   *协议版本号
   * </pre>
   *
   * <code>string version = 2;</code>
   * @return The bytes for version.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getVersionBytes() {
    java.lang.Object ref = version_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      version_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int CONTENTTYPE_FIELD_NUMBER = 3;
  private volatile java.lang.Object contentType_;
  /**
   * <pre>
   *内容类型
   * </pre>
   *
   * <code>string contentType = 3;</code>
   * @return The contentType.
   */
  @java.lang.Override
  public java.lang.String getContentType() {
    java.lang.Object ref = contentType_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      contentType_ = s;
      return s;
    }
  }
  /**
   * <pre>
   *内容类型
   * </pre>
   *
   * <code>string contentType = 3;</code>
   * @return The bytes for contentType.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getContentTypeBytes() {
    java.lang.Object ref = contentType_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      contentType_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int STATUS_FIELD_NUMBER = 4;
  private int status_;
  /**
   * <pre>
   *状态
   * </pre>
   *
   * <code>uint32 status = 4;</code>
   * @return The status.
   */
  @java.lang.Override
  public int getStatus() {
    return status_;
  }

  public static final int SENDER_FIELD_NUMBER = 5;
  private volatile java.lang.Object sender_;
  /**
   * <pre>
   *消息发送者
   * </pre>
   *
   * <code>string sender = 5;</code>
   * @return The sender.
   */
  @java.lang.Override
  public java.lang.String getSender() {
    java.lang.Object ref = sender_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      sender_ = s;
      return s;
    }
  }
  /**
   * <pre>
   *消息发送者
   * </pre>
   *
   * <code>string sender = 5;</code>
   * @return The bytes for sender.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getSenderBytes() {
    java.lang.Object ref = sender_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      sender_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int RECEIVER_FIELD_NUMBER = 6;
  private volatile java.lang.Object receiver_;
  /**
   * <pre>
   *消息接收者
   * </pre>
   *
   * <code>string receiver = 6;</code>
   * @return The receiver.
   */
  @java.lang.Override
  public java.lang.String getReceiver() {
    java.lang.Object ref = receiver_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      receiver_ = s;
      return s;
    }
  }
  /**
   * <pre>
   *消息接收者
   * </pre>
   *
   * <code>string receiver = 6;</code>
   * @return The bytes for receiver.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getReceiverBytes() {
    java.lang.Object ref = receiver_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      receiver_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (!getProtocolBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, protocol_);
    }
    if (!getVersionBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 2, version_);
    }
    if (!getContentTypeBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 3, contentType_);
    }
    if (status_ != 0) {
      output.writeUInt32(4, status_);
    }
    if (!getSenderBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 5, sender_);
    }
    if (!getReceiverBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 6, receiver_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!getProtocolBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, protocol_);
    }
    if (!getVersionBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, version_);
    }
    if (!getContentTypeBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, contentType_);
    }
    if (status_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeUInt32Size(4, status_);
    }
    if (!getSenderBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(5, sender_);
    }
    if (!getReceiverBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(6, receiver_);
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof com.github.mxsm.protocol.protobuf.ResponseHeaders)) {
      return super.equals(obj);
    }
    com.github.mxsm.protocol.protobuf.ResponseHeaders other = (com.github.mxsm.protocol.protobuf.ResponseHeaders) obj;

    if (!getProtocol()
        .equals(other.getProtocol())) return false;
    if (!getVersion()
        .equals(other.getVersion())) return false;
    if (!getContentType()
        .equals(other.getContentType())) return false;
    if (getStatus()
        != other.getStatus()) return false;
    if (!getSender()
        .equals(other.getSender())) return false;
    if (!getReceiver()
        .equals(other.getReceiver())) return false;
    if (!unknownFields.equals(other.unknownFields)) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + PROTOCOL_FIELD_NUMBER;
    hash = (53 * hash) + getProtocol().hashCode();
    hash = (37 * hash) + VERSION_FIELD_NUMBER;
    hash = (53 * hash) + getVersion().hashCode();
    hash = (37 * hash) + CONTENTTYPE_FIELD_NUMBER;
    hash = (53 * hash) + getContentType().hashCode();
    hash = (37 * hash) + STATUS_FIELD_NUMBER;
    hash = (53 * hash) + getStatus();
    hash = (37 * hash) + SENDER_FIELD_NUMBER;
    hash = (53 * hash) + getSender().hashCode();
    hash = (37 * hash) + RECEIVER_FIELD_NUMBER;
    hash = (53 * hash) + getReceiver().hashCode();
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.github.mxsm.protocol.protobuf.ResponseHeaders parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.github.mxsm.protocol.protobuf.ResponseHeaders parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.github.mxsm.protocol.protobuf.ResponseHeaders parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.github.mxsm.protocol.protobuf.ResponseHeaders parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.github.mxsm.protocol.protobuf.ResponseHeaders parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.github.mxsm.protocol.protobuf.ResponseHeaders parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.github.mxsm.protocol.protobuf.ResponseHeaders parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.github.mxsm.protocol.protobuf.ResponseHeaders parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.github.mxsm.protocol.protobuf.ResponseHeaders parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static com.github.mxsm.protocol.protobuf.ResponseHeaders parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.github.mxsm.protocol.protobuf.ResponseHeaders parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.github.mxsm.protocol.protobuf.ResponseHeaders parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(com.github.mxsm.protocol.protobuf.ResponseHeaders prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code com.github.mxsm.protocol.protobuf.ResponseHeaders}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:com.github.mxsm.protocol.protobuf.ResponseHeaders)
      com.github.mxsm.protocol.protobuf.ResponseHeadersOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.github.mxsm.protocol.protobuf.ResponseHeadersOuterClass.internal_static_com_github_mxsm_protocol_protobuf_ResponseHeaders_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.github.mxsm.protocol.protobuf.ResponseHeadersOuterClass.internal_static_com_github_mxsm_protocol_protobuf_ResponseHeaders_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.github.mxsm.protocol.protobuf.ResponseHeaders.class, com.github.mxsm.protocol.protobuf.ResponseHeaders.Builder.class);
    }

    // Construct using com.github.mxsm.protocol.protobuf.ResponseHeaders.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      protocol_ = "";

      version_ = "";

      contentType_ = "";

      status_ = 0;

      sender_ = "";

      receiver_ = "";

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.github.mxsm.protocol.protobuf.ResponseHeadersOuterClass.internal_static_com_github_mxsm_protocol_protobuf_ResponseHeaders_descriptor;
    }

    @java.lang.Override
    public com.github.mxsm.protocol.protobuf.ResponseHeaders getDefaultInstanceForType() {
      return com.github.mxsm.protocol.protobuf.ResponseHeaders.getDefaultInstance();
    }

    @java.lang.Override
    public com.github.mxsm.protocol.protobuf.ResponseHeaders build() {
      com.github.mxsm.protocol.protobuf.ResponseHeaders result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.github.mxsm.protocol.protobuf.ResponseHeaders buildPartial() {
      com.github.mxsm.protocol.protobuf.ResponseHeaders result = new com.github.mxsm.protocol.protobuf.ResponseHeaders(this);
      result.protocol_ = protocol_;
      result.version_ = version_;
      result.contentType_ = contentType_;
      result.status_ = status_;
      result.sender_ = sender_;
      result.receiver_ = receiver_;
      onBuilt();
      return result;
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof com.github.mxsm.protocol.protobuf.ResponseHeaders) {
        return mergeFrom((com.github.mxsm.protocol.protobuf.ResponseHeaders)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.github.mxsm.protocol.protobuf.ResponseHeaders other) {
      if (other == com.github.mxsm.protocol.protobuf.ResponseHeaders.getDefaultInstance()) return this;
      if (!other.getProtocol().isEmpty()) {
        protocol_ = other.protocol_;
        onChanged();
      }
      if (!other.getVersion().isEmpty()) {
        version_ = other.version_;
        onChanged();
      }
      if (!other.getContentType().isEmpty()) {
        contentType_ = other.contentType_;
        onChanged();
      }
      if (other.getStatus() != 0) {
        setStatus(other.getStatus());
      }
      if (!other.getSender().isEmpty()) {
        sender_ = other.sender_;
        onChanged();
      }
      if (!other.getReceiver().isEmpty()) {
        receiver_ = other.receiver_;
        onChanged();
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      com.github.mxsm.protocol.protobuf.ResponseHeaders parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (com.github.mxsm.protocol.protobuf.ResponseHeaders) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private java.lang.Object protocol_ = "";
    /**
     * <pre>
     *协议
     * </pre>
     *
     * <code>string protocol = 1;</code>
     * @return The protocol.
     */
    public java.lang.String getProtocol() {
      java.lang.Object ref = protocol_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        protocol_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <pre>
     *协议
     * </pre>
     *
     * <code>string protocol = 1;</code>
     * @return The bytes for protocol.
     */
    public com.google.protobuf.ByteString
        getProtocolBytes() {
      java.lang.Object ref = protocol_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        protocol_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <pre>
     *协议
     * </pre>
     *
     * <code>string protocol = 1;</code>
     * @param value The protocol to set.
     * @return This builder for chaining.
     */
    public Builder setProtocol(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      protocol_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     *协议
     * </pre>
     *
     * <code>string protocol = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearProtocol() {
      
      protocol_ = getDefaultInstance().getProtocol();
      onChanged();
      return this;
    }
    /**
     * <pre>
     *协议
     * </pre>
     *
     * <code>string protocol = 1;</code>
     * @param value The bytes for protocol to set.
     * @return This builder for chaining.
     */
    public Builder setProtocolBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      protocol_ = value;
      onChanged();
      return this;
    }

    private java.lang.Object version_ = "";
    /**
     * <pre>
     *协议版本号
     * </pre>
     *
     * <code>string version = 2;</code>
     * @return The version.
     */
    public java.lang.String getVersion() {
      java.lang.Object ref = version_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        version_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <pre>
     *协议版本号
     * </pre>
     *
     * <code>string version = 2;</code>
     * @return The bytes for version.
     */
    public com.google.protobuf.ByteString
        getVersionBytes() {
      java.lang.Object ref = version_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        version_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <pre>
     *协议版本号
     * </pre>
     *
     * <code>string version = 2;</code>
     * @param value The version to set.
     * @return This builder for chaining.
     */
    public Builder setVersion(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      version_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     *协议版本号
     * </pre>
     *
     * <code>string version = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearVersion() {
      
      version_ = getDefaultInstance().getVersion();
      onChanged();
      return this;
    }
    /**
     * <pre>
     *协议版本号
     * </pre>
     *
     * <code>string version = 2;</code>
     * @param value The bytes for version to set.
     * @return This builder for chaining.
     */
    public Builder setVersionBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      version_ = value;
      onChanged();
      return this;
    }

    private java.lang.Object contentType_ = "";
    /**
     * <pre>
     *内容类型
     * </pre>
     *
     * <code>string contentType = 3;</code>
     * @return The contentType.
     */
    public java.lang.String getContentType() {
      java.lang.Object ref = contentType_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        contentType_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <pre>
     *内容类型
     * </pre>
     *
     * <code>string contentType = 3;</code>
     * @return The bytes for contentType.
     */
    public com.google.protobuf.ByteString
        getContentTypeBytes() {
      java.lang.Object ref = contentType_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        contentType_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <pre>
     *内容类型
     * </pre>
     *
     * <code>string contentType = 3;</code>
     * @param value The contentType to set.
     * @return This builder for chaining.
     */
    public Builder setContentType(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      contentType_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     *内容类型
     * </pre>
     *
     * <code>string contentType = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearContentType() {
      
      contentType_ = getDefaultInstance().getContentType();
      onChanged();
      return this;
    }
    /**
     * <pre>
     *内容类型
     * </pre>
     *
     * <code>string contentType = 3;</code>
     * @param value The bytes for contentType to set.
     * @return This builder for chaining.
     */
    public Builder setContentTypeBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      contentType_ = value;
      onChanged();
      return this;
    }

    private int status_ ;
    /**
     * <pre>
     *状态
     * </pre>
     *
     * <code>uint32 status = 4;</code>
     * @return The status.
     */
    @java.lang.Override
    public int getStatus() {
      return status_;
    }
    /**
     * <pre>
     *状态
     * </pre>
     *
     * <code>uint32 status = 4;</code>
     * @param value The status to set.
     * @return This builder for chaining.
     */
    public Builder setStatus(int value) {
      
      status_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     *状态
     * </pre>
     *
     * <code>uint32 status = 4;</code>
     * @return This builder for chaining.
     */
    public Builder clearStatus() {
      
      status_ = 0;
      onChanged();
      return this;
    }

    private java.lang.Object sender_ = "";
    /**
     * <pre>
     *消息发送者
     * </pre>
     *
     * <code>string sender = 5;</code>
     * @return The sender.
     */
    public java.lang.String getSender() {
      java.lang.Object ref = sender_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        sender_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <pre>
     *消息发送者
     * </pre>
     *
     * <code>string sender = 5;</code>
     * @return The bytes for sender.
     */
    public com.google.protobuf.ByteString
        getSenderBytes() {
      java.lang.Object ref = sender_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        sender_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <pre>
     *消息发送者
     * </pre>
     *
     * <code>string sender = 5;</code>
     * @param value The sender to set.
     * @return This builder for chaining.
     */
    public Builder setSender(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      sender_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     *消息发送者
     * </pre>
     *
     * <code>string sender = 5;</code>
     * @return This builder for chaining.
     */
    public Builder clearSender() {
      
      sender_ = getDefaultInstance().getSender();
      onChanged();
      return this;
    }
    /**
     * <pre>
     *消息发送者
     * </pre>
     *
     * <code>string sender = 5;</code>
     * @param value The bytes for sender to set.
     * @return This builder for chaining.
     */
    public Builder setSenderBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      sender_ = value;
      onChanged();
      return this;
    }

    private java.lang.Object receiver_ = "";
    /**
     * <pre>
     *消息接收者
     * </pre>
     *
     * <code>string receiver = 6;</code>
     * @return The receiver.
     */
    public java.lang.String getReceiver() {
      java.lang.Object ref = receiver_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        receiver_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <pre>
     *消息接收者
     * </pre>
     *
     * <code>string receiver = 6;</code>
     * @return The bytes for receiver.
     */
    public com.google.protobuf.ByteString
        getReceiverBytes() {
      java.lang.Object ref = receiver_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        receiver_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <pre>
     *消息接收者
     * </pre>
     *
     * <code>string receiver = 6;</code>
     * @param value The receiver to set.
     * @return This builder for chaining.
     */
    public Builder setReceiver(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      receiver_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     *消息接收者
     * </pre>
     *
     * <code>string receiver = 6;</code>
     * @return This builder for chaining.
     */
    public Builder clearReceiver() {
      
      receiver_ = getDefaultInstance().getReceiver();
      onChanged();
      return this;
    }
    /**
     * <pre>
     *消息接收者
     * </pre>
     *
     * <code>string receiver = 6;</code>
     * @param value The bytes for receiver to set.
     * @return This builder for chaining.
     */
    public Builder setReceiverBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      receiver_ = value;
      onChanged();
      return this;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:com.github.mxsm.protocol.protobuf.ResponseHeaders)
  }

  // @@protoc_insertion_point(class_scope:com.github.mxsm.protocol.protobuf.ResponseHeaders)
  private static final com.github.mxsm.protocol.protobuf.ResponseHeaders DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.github.mxsm.protocol.protobuf.ResponseHeaders();
  }

  public static com.github.mxsm.protocol.protobuf.ResponseHeaders getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<ResponseHeaders>
      PARSER = new com.google.protobuf.AbstractParser<ResponseHeaders>() {
    @java.lang.Override
    public ResponseHeaders parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new ResponseHeaders(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<ResponseHeaders> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<ResponseHeaders> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.github.mxsm.protocol.protobuf.ResponseHeaders getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

