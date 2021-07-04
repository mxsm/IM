// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: MagpieBridge.proto

package com.github.mxsm.protocol.protobuf.magpiebridge;

/**
 * Protobuf type {@code com.github.mxsm.protocol.protobuf.magpiebridge.MagpieBridge}
 */
public final class MagpieBridge extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:com.github.mxsm.protocol.protobuf.magpiebridge.MagpieBridge)
    MagpieBridgeOrBuilder {
private static final long serialVersionUID = 0L;
  // Use MagpieBridge.newBuilder() to construct.
  private MagpieBridge(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private MagpieBridge() {
    magpieBridgeName_ = "";
    magpieBridgeAddress_ = "";
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new MagpieBridge();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private MagpieBridge(
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

            magpieBridgeName_ = s;
            break;
          }
          case 18: {
            java.lang.String s = input.readStringRequireUtf8();

            magpieBridgeAddress_ = s;
            break;
          }
          case 24: {

            magpieBridgeId_ = input.readUInt32();
            break;
          }
          case 32: {

            magpieBridgeCreateTimestamp_ = input.readUInt64();
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
    return com.github.mxsm.protocol.protobuf.magpiebridge.MagpieBridgeOuterClass.internal_static_com_github_mxsm_protocol_protobuf_magpiebridge_MagpieBridge_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.github.mxsm.protocol.protobuf.magpiebridge.MagpieBridgeOuterClass.internal_static_com_github_mxsm_protocol_protobuf_magpiebridge_MagpieBridge_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.github.mxsm.protocol.protobuf.magpiebridge.MagpieBridge.class, com.github.mxsm.protocol.protobuf.magpiebridge.MagpieBridge.Builder.class);
  }

  public static final int MAGPIEBRIDGENAME_FIELD_NUMBER = 1;
  private volatile java.lang.Object magpieBridgeName_;
  /**
   * <pre>
   *名称
   * </pre>
   *
   * <code>string magpieBridgeName = 1;</code>
   * @return The magpieBridgeName.
   */
  @java.lang.Override
  public java.lang.String getMagpieBridgeName() {
    java.lang.Object ref = magpieBridgeName_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      magpieBridgeName_ = s;
      return s;
    }
  }
  /**
   * <pre>
   *名称
   * </pre>
   *
   * <code>string magpieBridgeName = 1;</code>
   * @return The bytes for magpieBridgeName.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getMagpieBridgeNameBytes() {
    java.lang.Object ref = magpieBridgeName_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      magpieBridgeName_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int MAGPIEBRIDGEADDRESS_FIELD_NUMBER = 2;
  private volatile java.lang.Object magpieBridgeAddress_;
  /**
   * <code>string magpieBridgeAddress = 2;</code>
   * @return The magpieBridgeAddress.
   */
  @java.lang.Override
  public java.lang.String getMagpieBridgeAddress() {
    java.lang.Object ref = magpieBridgeAddress_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      magpieBridgeAddress_ = s;
      return s;
    }
  }
  /**
   * <code>string magpieBridgeAddress = 2;</code>
   * @return The bytes for magpieBridgeAddress.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getMagpieBridgeAddressBytes() {
    java.lang.Object ref = magpieBridgeAddress_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      magpieBridgeAddress_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int MAGPIEBRIDGEID_FIELD_NUMBER = 3;
  private int magpieBridgeId_;
  /**
   * <code>uint32 magpieBridgeId = 3;</code>
   * @return The magpieBridgeId.
   */
  @java.lang.Override
  public int getMagpieBridgeId() {
    return magpieBridgeId_;
  }

  public static final int MAGPIEBRIDGECREATETIMESTAMP_FIELD_NUMBER = 4;
  private long magpieBridgeCreateTimestamp_;
  /**
   * <code>uint64 magpieBridgeCreateTimestamp = 4;</code>
   * @return The magpieBridgeCreateTimestamp.
   */
  @java.lang.Override
  public long getMagpieBridgeCreateTimestamp() {
    return magpieBridgeCreateTimestamp_;
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
    if (!getMagpieBridgeNameBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, magpieBridgeName_);
    }
    if (!getMagpieBridgeAddressBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 2, magpieBridgeAddress_);
    }
    if (magpieBridgeId_ != 0) {
      output.writeUInt32(3, magpieBridgeId_);
    }
    if (magpieBridgeCreateTimestamp_ != 0L) {
      output.writeUInt64(4, magpieBridgeCreateTimestamp_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!getMagpieBridgeNameBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, magpieBridgeName_);
    }
    if (!getMagpieBridgeAddressBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, magpieBridgeAddress_);
    }
    if (magpieBridgeId_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeUInt32Size(3, magpieBridgeId_);
    }
    if (magpieBridgeCreateTimestamp_ != 0L) {
      size += com.google.protobuf.CodedOutputStream
        .computeUInt64Size(4, magpieBridgeCreateTimestamp_);
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
    if (!(obj instanceof com.github.mxsm.protocol.protobuf.magpiebridge.MagpieBridge)) {
      return super.equals(obj);
    }
    com.github.mxsm.protocol.protobuf.magpiebridge.MagpieBridge other = (com.github.mxsm.protocol.protobuf.magpiebridge.MagpieBridge) obj;

    if (!getMagpieBridgeName()
        .equals(other.getMagpieBridgeName())) return false;
    if (!getMagpieBridgeAddress()
        .equals(other.getMagpieBridgeAddress())) return false;
    if (getMagpieBridgeId()
        != other.getMagpieBridgeId()) return false;
    if (getMagpieBridgeCreateTimestamp()
        != other.getMagpieBridgeCreateTimestamp()) return false;
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
    hash = (37 * hash) + MAGPIEBRIDGENAME_FIELD_NUMBER;
    hash = (53 * hash) + getMagpieBridgeName().hashCode();
    hash = (37 * hash) + MAGPIEBRIDGEADDRESS_FIELD_NUMBER;
    hash = (53 * hash) + getMagpieBridgeAddress().hashCode();
    hash = (37 * hash) + MAGPIEBRIDGEID_FIELD_NUMBER;
    hash = (53 * hash) + getMagpieBridgeId();
    hash = (37 * hash) + MAGPIEBRIDGECREATETIMESTAMP_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        getMagpieBridgeCreateTimestamp());
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.github.mxsm.protocol.protobuf.magpiebridge.MagpieBridge parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.github.mxsm.protocol.protobuf.magpiebridge.MagpieBridge parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.github.mxsm.protocol.protobuf.magpiebridge.MagpieBridge parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.github.mxsm.protocol.protobuf.magpiebridge.MagpieBridge parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.github.mxsm.protocol.protobuf.magpiebridge.MagpieBridge parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.github.mxsm.protocol.protobuf.magpiebridge.MagpieBridge parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.github.mxsm.protocol.protobuf.magpiebridge.MagpieBridge parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.github.mxsm.protocol.protobuf.magpiebridge.MagpieBridge parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.github.mxsm.protocol.protobuf.magpiebridge.MagpieBridge parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static com.github.mxsm.protocol.protobuf.magpiebridge.MagpieBridge parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.github.mxsm.protocol.protobuf.magpiebridge.MagpieBridge parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.github.mxsm.protocol.protobuf.magpiebridge.MagpieBridge parseFrom(
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
  public static Builder newBuilder(com.github.mxsm.protocol.protobuf.magpiebridge.MagpieBridge prototype) {
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
   * Protobuf type {@code com.github.mxsm.protocol.protobuf.magpiebridge.MagpieBridge}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:com.github.mxsm.protocol.protobuf.magpiebridge.MagpieBridge)
      com.github.mxsm.protocol.protobuf.magpiebridge.MagpieBridgeOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.github.mxsm.protocol.protobuf.magpiebridge.MagpieBridgeOuterClass.internal_static_com_github_mxsm_protocol_protobuf_magpiebridge_MagpieBridge_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.github.mxsm.protocol.protobuf.magpiebridge.MagpieBridgeOuterClass.internal_static_com_github_mxsm_protocol_protobuf_magpiebridge_MagpieBridge_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.github.mxsm.protocol.protobuf.magpiebridge.MagpieBridge.class, com.github.mxsm.protocol.protobuf.magpiebridge.MagpieBridge.Builder.class);
    }

    // Construct using com.github.mxsm.protocol.protobuf.magpiebridge.MagpieBridge.newBuilder()
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
      magpieBridgeName_ = "";

      magpieBridgeAddress_ = "";

      magpieBridgeId_ = 0;

      magpieBridgeCreateTimestamp_ = 0L;

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.github.mxsm.protocol.protobuf.magpiebridge.MagpieBridgeOuterClass.internal_static_com_github_mxsm_protocol_protobuf_magpiebridge_MagpieBridge_descriptor;
    }

    @java.lang.Override
    public com.github.mxsm.protocol.protobuf.magpiebridge.MagpieBridge getDefaultInstanceForType() {
      return com.github.mxsm.protocol.protobuf.magpiebridge.MagpieBridge.getDefaultInstance();
    }

    @java.lang.Override
    public com.github.mxsm.protocol.protobuf.magpiebridge.MagpieBridge build() {
      com.github.mxsm.protocol.protobuf.magpiebridge.MagpieBridge result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.github.mxsm.protocol.protobuf.magpiebridge.MagpieBridge buildPartial() {
      com.github.mxsm.protocol.protobuf.magpiebridge.MagpieBridge result = new com.github.mxsm.protocol.protobuf.magpiebridge.MagpieBridge(this);
      result.magpieBridgeName_ = magpieBridgeName_;
      result.magpieBridgeAddress_ = magpieBridgeAddress_;
      result.magpieBridgeId_ = magpieBridgeId_;
      result.magpieBridgeCreateTimestamp_ = magpieBridgeCreateTimestamp_;
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
      if (other instanceof com.github.mxsm.protocol.protobuf.magpiebridge.MagpieBridge) {
        return mergeFrom((com.github.mxsm.protocol.protobuf.magpiebridge.MagpieBridge)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.github.mxsm.protocol.protobuf.magpiebridge.MagpieBridge other) {
      if (other == com.github.mxsm.protocol.protobuf.magpiebridge.MagpieBridge.getDefaultInstance()) return this;
      if (!other.getMagpieBridgeName().isEmpty()) {
        magpieBridgeName_ = other.magpieBridgeName_;
        onChanged();
      }
      if (!other.getMagpieBridgeAddress().isEmpty()) {
        magpieBridgeAddress_ = other.magpieBridgeAddress_;
        onChanged();
      }
      if (other.getMagpieBridgeId() != 0) {
        setMagpieBridgeId(other.getMagpieBridgeId());
      }
      if (other.getMagpieBridgeCreateTimestamp() != 0L) {
        setMagpieBridgeCreateTimestamp(other.getMagpieBridgeCreateTimestamp());
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
      com.github.mxsm.protocol.protobuf.magpiebridge.MagpieBridge parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (com.github.mxsm.protocol.protobuf.magpiebridge.MagpieBridge) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private java.lang.Object magpieBridgeName_ = "";
    /**
     * <pre>
     *名称
     * </pre>
     *
     * <code>string magpieBridgeName = 1;</code>
     * @return The magpieBridgeName.
     */
    public java.lang.String getMagpieBridgeName() {
      java.lang.Object ref = magpieBridgeName_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        magpieBridgeName_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <pre>
     *名称
     * </pre>
     *
     * <code>string magpieBridgeName = 1;</code>
     * @return The bytes for magpieBridgeName.
     */
    public com.google.protobuf.ByteString
        getMagpieBridgeNameBytes() {
      java.lang.Object ref = magpieBridgeName_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        magpieBridgeName_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <pre>
     *名称
     * </pre>
     *
     * <code>string magpieBridgeName = 1;</code>
     * @param value The magpieBridgeName to set.
     * @return This builder for chaining.
     */
    public Builder setMagpieBridgeName(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      magpieBridgeName_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     *名称
     * </pre>
     *
     * <code>string magpieBridgeName = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearMagpieBridgeName() {
      
      magpieBridgeName_ = getDefaultInstance().getMagpieBridgeName();
      onChanged();
      return this;
    }
    /**
     * <pre>
     *名称
     * </pre>
     *
     * <code>string magpieBridgeName = 1;</code>
     * @param value The bytes for magpieBridgeName to set.
     * @return This builder for chaining.
     */
    public Builder setMagpieBridgeNameBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      magpieBridgeName_ = value;
      onChanged();
      return this;
    }

    private java.lang.Object magpieBridgeAddress_ = "";
    /**
     * <code>string magpieBridgeAddress = 2;</code>
     * @return The magpieBridgeAddress.
     */
    public java.lang.String getMagpieBridgeAddress() {
      java.lang.Object ref = magpieBridgeAddress_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        magpieBridgeAddress_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string magpieBridgeAddress = 2;</code>
     * @return The bytes for magpieBridgeAddress.
     */
    public com.google.protobuf.ByteString
        getMagpieBridgeAddressBytes() {
      java.lang.Object ref = magpieBridgeAddress_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        magpieBridgeAddress_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string magpieBridgeAddress = 2;</code>
     * @param value The magpieBridgeAddress to set.
     * @return This builder for chaining.
     */
    public Builder setMagpieBridgeAddress(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      magpieBridgeAddress_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string magpieBridgeAddress = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearMagpieBridgeAddress() {
      
      magpieBridgeAddress_ = getDefaultInstance().getMagpieBridgeAddress();
      onChanged();
      return this;
    }
    /**
     * <code>string magpieBridgeAddress = 2;</code>
     * @param value The bytes for magpieBridgeAddress to set.
     * @return This builder for chaining.
     */
    public Builder setMagpieBridgeAddressBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      magpieBridgeAddress_ = value;
      onChanged();
      return this;
    }

    private int magpieBridgeId_ ;
    /**
     * <code>uint32 magpieBridgeId = 3;</code>
     * @return The magpieBridgeId.
     */
    @java.lang.Override
    public int getMagpieBridgeId() {
      return magpieBridgeId_;
    }
    /**
     * <code>uint32 magpieBridgeId = 3;</code>
     * @param value The magpieBridgeId to set.
     * @return This builder for chaining.
     */
    public Builder setMagpieBridgeId(int value) {
      
      magpieBridgeId_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>uint32 magpieBridgeId = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearMagpieBridgeId() {
      
      magpieBridgeId_ = 0;
      onChanged();
      return this;
    }

    private long magpieBridgeCreateTimestamp_ ;
    /**
     * <code>uint64 magpieBridgeCreateTimestamp = 4;</code>
     * @return The magpieBridgeCreateTimestamp.
     */
    @java.lang.Override
    public long getMagpieBridgeCreateTimestamp() {
      return magpieBridgeCreateTimestamp_;
    }
    /**
     * <code>uint64 magpieBridgeCreateTimestamp = 4;</code>
     * @param value The magpieBridgeCreateTimestamp to set.
     * @return This builder for chaining.
     */
    public Builder setMagpieBridgeCreateTimestamp(long value) {
      
      magpieBridgeCreateTimestamp_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>uint64 magpieBridgeCreateTimestamp = 4;</code>
     * @return This builder for chaining.
     */
    public Builder clearMagpieBridgeCreateTimestamp() {
      
      magpieBridgeCreateTimestamp_ = 0L;
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


    // @@protoc_insertion_point(builder_scope:com.github.mxsm.protocol.protobuf.magpiebridge.MagpieBridge)
  }

  // @@protoc_insertion_point(class_scope:com.github.mxsm.protocol.protobuf.magpiebridge.MagpieBridge)
  private static final com.github.mxsm.protocol.protobuf.magpiebridge.MagpieBridge DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.github.mxsm.protocol.protobuf.magpiebridge.MagpieBridge();
  }

  public static com.github.mxsm.protocol.protobuf.magpiebridge.MagpieBridge getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<MagpieBridge>
      PARSER = new com.google.protobuf.AbstractParser<MagpieBridge>() {
    @java.lang.Override
    public MagpieBridge parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new MagpieBridge(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<MagpieBridge> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<MagpieBridge> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.github.mxsm.protocol.protobuf.magpiebridge.MagpieBridge getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

