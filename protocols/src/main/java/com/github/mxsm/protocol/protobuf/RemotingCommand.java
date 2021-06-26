// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: RemotingRequest.proto

package com.github.mxsm.protocol.protobuf;

/**
 * Protobuf type {@code com.github.mxsm.protocol.protobuf.RemotingCommand}
 */
public final class RemotingCommand extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:com.github.mxsm.protocol.protobuf.RemotingCommand)
    RemotingCommandOrBuilder {
private static final long serialVersionUID = 0L;
  // Use RemotingCommand.newBuilder() to construct.
  private RemotingCommand(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private RemotingCommand() {
    commandType_ = 0;
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new RemotingCommand();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private RemotingCommand(
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
          case 8: {
            int rawValue = input.readEnum();

            commandType_ = rawValue;
            break;
          }
          case 16: {

            requestCode_ = input.readInt32();
            break;
          }
          case 24: {

            oneway_ = input.readBool();
            break;
          }
          case 32: {

            commandId_ = input.readUInt64();
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
    return com.github.mxsm.protocol.protobuf.RemotingRequest.internal_static_com_github_mxsm_protocol_protobuf_RemotingCommand_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.github.mxsm.protocol.protobuf.RemotingRequest.internal_static_com_github_mxsm_protocol_protobuf_RemotingCommand_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.github.mxsm.protocol.protobuf.RemotingCommand.class, com.github.mxsm.protocol.protobuf.RemotingCommand.Builder.class);
  }

  public static final int COMMANDTYPE_FIELD_NUMBER = 1;
  private int commandType_;
  /**
   * <pre>
   *命令类型
   * </pre>
   *
   * <code>.com.github.mxsm.protocol.protobuf.RemotingCommandType commandType = 1;</code>
   * @return The enum numeric value on the wire for commandType.
   */
  @java.lang.Override public int getCommandTypeValue() {
    return commandType_;
  }
  /**
   * <pre>
   *命令类型
   * </pre>
   *
   * <code>.com.github.mxsm.protocol.protobuf.RemotingCommandType commandType = 1;</code>
   * @return The commandType.
   */
  @java.lang.Override public com.github.mxsm.protocol.protobuf.RemotingCommandType getCommandType() {
    @SuppressWarnings("deprecation")
    com.github.mxsm.protocol.protobuf.RemotingCommandType result = com.github.mxsm.protocol.protobuf.RemotingCommandType.valueOf(commandType_);
    return result == null ? com.github.mxsm.protocol.protobuf.RemotingCommandType.UNRECOGNIZED : result;
  }

  public static final int REQUESTCODE_FIELD_NUMBER = 2;
  private int requestCode_;
  /**
   * <code>int32 requestCode = 2;</code>
   * @return The requestCode.
   */
  @java.lang.Override
  public int getRequestCode() {
    return requestCode_;
  }

  public static final int ONEWAY_FIELD_NUMBER = 3;
  private boolean oneway_;
  /**
   * <code>bool oneway = 3;</code>
   * @return The oneway.
   */
  @java.lang.Override
  public boolean getOneway() {
    return oneway_;
  }

  public static final int COMMANDID_FIELD_NUMBER = 4;
  private long commandId_;
  /**
   * <code>uint64 commandId = 4;</code>
   * @return The commandId.
   */
  @java.lang.Override
  public long getCommandId() {
    return commandId_;
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
    if (commandType_ != com.github.mxsm.protocol.protobuf.RemotingCommandType.REQUEST.getNumber()) {
      output.writeEnum(1, commandType_);
    }
    if (requestCode_ != 0) {
      output.writeInt32(2, requestCode_);
    }
    if (oneway_ != false) {
      output.writeBool(3, oneway_);
    }
    if (commandId_ != 0L) {
      output.writeUInt64(4, commandId_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (commandType_ != com.github.mxsm.protocol.protobuf.RemotingCommandType.REQUEST.getNumber()) {
      size += com.google.protobuf.CodedOutputStream
        .computeEnumSize(1, commandType_);
    }
    if (requestCode_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(2, requestCode_);
    }
    if (oneway_ != false) {
      size += com.google.protobuf.CodedOutputStream
        .computeBoolSize(3, oneway_);
    }
    if (commandId_ != 0L) {
      size += com.google.protobuf.CodedOutputStream
        .computeUInt64Size(4, commandId_);
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
    if (!(obj instanceof com.github.mxsm.protocol.protobuf.RemotingCommand)) {
      return super.equals(obj);
    }
    com.github.mxsm.protocol.protobuf.RemotingCommand other = (com.github.mxsm.protocol.protobuf.RemotingCommand) obj;

    if (commandType_ != other.commandType_) return false;
    if (getRequestCode()
        != other.getRequestCode()) return false;
    if (getOneway()
        != other.getOneway()) return false;
    if (getCommandId()
        != other.getCommandId()) return false;
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
    hash = (37 * hash) + COMMANDTYPE_FIELD_NUMBER;
    hash = (53 * hash) + commandType_;
    hash = (37 * hash) + REQUESTCODE_FIELD_NUMBER;
    hash = (53 * hash) + getRequestCode();
    hash = (37 * hash) + ONEWAY_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(
        getOneway());
    hash = (37 * hash) + COMMANDID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        getCommandId());
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.github.mxsm.protocol.protobuf.RemotingCommand parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.github.mxsm.protocol.protobuf.RemotingCommand parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.github.mxsm.protocol.protobuf.RemotingCommand parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.github.mxsm.protocol.protobuf.RemotingCommand parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.github.mxsm.protocol.protobuf.RemotingCommand parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.github.mxsm.protocol.protobuf.RemotingCommand parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.github.mxsm.protocol.protobuf.RemotingCommand parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.github.mxsm.protocol.protobuf.RemotingCommand parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.github.mxsm.protocol.protobuf.RemotingCommand parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static com.github.mxsm.protocol.protobuf.RemotingCommand parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.github.mxsm.protocol.protobuf.RemotingCommand parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.github.mxsm.protocol.protobuf.RemotingCommand parseFrom(
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
  public static Builder newBuilder(com.github.mxsm.protocol.protobuf.RemotingCommand prototype) {
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
   * Protobuf type {@code com.github.mxsm.protocol.protobuf.RemotingCommand}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:com.github.mxsm.protocol.protobuf.RemotingCommand)
      com.github.mxsm.protocol.protobuf.RemotingCommandOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.github.mxsm.protocol.protobuf.RemotingRequest.internal_static_com_github_mxsm_protocol_protobuf_RemotingCommand_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.github.mxsm.protocol.protobuf.RemotingRequest.internal_static_com_github_mxsm_protocol_protobuf_RemotingCommand_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.github.mxsm.protocol.protobuf.RemotingCommand.class, com.github.mxsm.protocol.protobuf.RemotingCommand.Builder.class);
    }

    // Construct using com.github.mxsm.protocol.protobuf.RemotingCommand.newBuilder()
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
      commandType_ = 0;

      requestCode_ = 0;

      oneway_ = false;

      commandId_ = 0L;

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.github.mxsm.protocol.protobuf.RemotingRequest.internal_static_com_github_mxsm_protocol_protobuf_RemotingCommand_descriptor;
    }

    @java.lang.Override
    public com.github.mxsm.protocol.protobuf.RemotingCommand getDefaultInstanceForType() {
      return com.github.mxsm.protocol.protobuf.RemotingCommand.getDefaultInstance();
    }

    @java.lang.Override
    public com.github.mxsm.protocol.protobuf.RemotingCommand build() {
      com.github.mxsm.protocol.protobuf.RemotingCommand result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.github.mxsm.protocol.protobuf.RemotingCommand buildPartial() {
      com.github.mxsm.protocol.protobuf.RemotingCommand result = new com.github.mxsm.protocol.protobuf.RemotingCommand(this);
      result.commandType_ = commandType_;
      result.requestCode_ = requestCode_;
      result.oneway_ = oneway_;
      result.commandId_ = commandId_;
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
      if (other instanceof com.github.mxsm.protocol.protobuf.RemotingCommand) {
        return mergeFrom((com.github.mxsm.protocol.protobuf.RemotingCommand)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.github.mxsm.protocol.protobuf.RemotingCommand other) {
      if (other == com.github.mxsm.protocol.protobuf.RemotingCommand.getDefaultInstance()) return this;
      if (other.commandType_ != 0) {
        setCommandTypeValue(other.getCommandTypeValue());
      }
      if (other.getRequestCode() != 0) {
        setRequestCode(other.getRequestCode());
      }
      if (other.getOneway() != false) {
        setOneway(other.getOneway());
      }
      if (other.getCommandId() != 0L) {
        setCommandId(other.getCommandId());
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
      com.github.mxsm.protocol.protobuf.RemotingCommand parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (com.github.mxsm.protocol.protobuf.RemotingCommand) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private int commandType_ = 0;
    /**
     * <pre>
     *命令类型
     * </pre>
     *
     * <code>.com.github.mxsm.protocol.protobuf.RemotingCommandType commandType = 1;</code>
     * @return The enum numeric value on the wire for commandType.
     */
    @java.lang.Override public int getCommandTypeValue() {
      return commandType_;
    }
    /**
     * <pre>
     *命令类型
     * </pre>
     *
     * <code>.com.github.mxsm.protocol.protobuf.RemotingCommandType commandType = 1;</code>
     * @param value The enum numeric value on the wire for commandType to set.
     * @return This builder for chaining.
     */
    public Builder setCommandTypeValue(int value) {
      
      commandType_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     *命令类型
     * </pre>
     *
     * <code>.com.github.mxsm.protocol.protobuf.RemotingCommandType commandType = 1;</code>
     * @return The commandType.
     */
    @java.lang.Override
    public com.github.mxsm.protocol.protobuf.RemotingCommandType getCommandType() {
      @SuppressWarnings("deprecation")
      com.github.mxsm.protocol.protobuf.RemotingCommandType result = com.github.mxsm.protocol.protobuf.RemotingCommandType.valueOf(commandType_);
      return result == null ? com.github.mxsm.protocol.protobuf.RemotingCommandType.UNRECOGNIZED : result;
    }
    /**
     * <pre>
     *命令类型
     * </pre>
     *
     * <code>.com.github.mxsm.protocol.protobuf.RemotingCommandType commandType = 1;</code>
     * @param value The commandType to set.
     * @return This builder for chaining.
     */
    public Builder setCommandType(com.github.mxsm.protocol.protobuf.RemotingCommandType value) {
      if (value == null) {
        throw new NullPointerException();
      }
      
      commandType_ = value.getNumber();
      onChanged();
      return this;
    }
    /**
     * <pre>
     *命令类型
     * </pre>
     *
     * <code>.com.github.mxsm.protocol.protobuf.RemotingCommandType commandType = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearCommandType() {
      
      commandType_ = 0;
      onChanged();
      return this;
    }

    private int requestCode_ ;
    /**
     * <code>int32 requestCode = 2;</code>
     * @return The requestCode.
     */
    @java.lang.Override
    public int getRequestCode() {
      return requestCode_;
    }
    /**
     * <code>int32 requestCode = 2;</code>
     * @param value The requestCode to set.
     * @return This builder for chaining.
     */
    public Builder setRequestCode(int value) {
      
      requestCode_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int32 requestCode = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearRequestCode() {
      
      requestCode_ = 0;
      onChanged();
      return this;
    }

    private boolean oneway_ ;
    /**
     * <code>bool oneway = 3;</code>
     * @return The oneway.
     */
    @java.lang.Override
    public boolean getOneway() {
      return oneway_;
    }
    /**
     * <code>bool oneway = 3;</code>
     * @param value The oneway to set.
     * @return This builder for chaining.
     */
    public Builder setOneway(boolean value) {
      
      oneway_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>bool oneway = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearOneway() {
      
      oneway_ = false;
      onChanged();
      return this;
    }

    private long commandId_ ;
    /**
     * <code>uint64 commandId = 4;</code>
     * @return The commandId.
     */
    @java.lang.Override
    public long getCommandId() {
      return commandId_;
    }
    /**
     * <code>uint64 commandId = 4;</code>
     * @param value The commandId to set.
     * @return This builder for chaining.
     */
    public Builder setCommandId(long value) {
      
      commandId_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>uint64 commandId = 4;</code>
     * @return This builder for chaining.
     */
    public Builder clearCommandId() {
      
      commandId_ = 0L;
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


    // @@protoc_insertion_point(builder_scope:com.github.mxsm.protocol.protobuf.RemotingCommand)
  }

  // @@protoc_insertion_point(class_scope:com.github.mxsm.protocol.protobuf.RemotingCommand)
  private static final com.github.mxsm.protocol.protobuf.RemotingCommand DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.github.mxsm.protocol.protobuf.RemotingCommand();
  }

  public static com.github.mxsm.protocol.protobuf.RemotingCommand getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<RemotingCommand>
      PARSER = new com.google.protobuf.AbstractParser<RemotingCommand>() {
    @java.lang.Override
    public RemotingCommand parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new RemotingCommand(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<RemotingCommand> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<RemotingCommand> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.github.mxsm.protocol.protobuf.RemotingCommand getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

