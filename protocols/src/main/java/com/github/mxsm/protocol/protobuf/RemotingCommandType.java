// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: RemotingCommandType.proto

package com.github.mxsm.protocol.protobuf;

/**
 * Protobuf enum {@code com.github.mxsm.protocol.protobuf.RemotingCommandType}
 */
public enum RemotingCommandType
    implements com.google.protobuf.ProtocolMessageEnum {
  /**
   * <pre>
   *请求
   * </pre>
   *
   * <code>REQUEST = 0;</code>
   */
  REQUEST(0),
  /**
   * <pre>
   *响应
   * </pre>
   *
   * <code>RESPONSE = 1;</code>
   */
  RESPONSE(1),
  UNRECOGNIZED(-1),
  ;

  /**
   * <pre>
   *请求
   * </pre>
   *
   * <code>REQUEST = 0;</code>
   */
  public static final int REQUEST_VALUE = 0;
  /**
   * <pre>
   *响应
   * </pre>
   *
   * <code>RESPONSE = 1;</code>
   */
  public static final int RESPONSE_VALUE = 1;


  public final int getNumber() {
    if (this == UNRECOGNIZED) {
      throw new java.lang.IllegalArgumentException(
          "Can't get the number of an unknown enum value.");
    }
    return value;
  }

  /**
   * @param value The numeric wire value of the corresponding enum entry.
   * @return The enum associated with the given numeric wire value.
   * @deprecated Use {@link #forNumber(int)} instead.
   */
  @java.lang.Deprecated
  public static RemotingCommandType valueOf(int value) {
    return forNumber(value);
  }

  /**
   * @param value The numeric wire value of the corresponding enum entry.
   * @return The enum associated with the given numeric wire value.
   */
  public static RemotingCommandType forNumber(int value) {
    switch (value) {
      case 0: return REQUEST;
      case 1: return RESPONSE;
      default: return null;
    }
  }

  public static com.google.protobuf.Internal.EnumLiteMap<RemotingCommandType>
      internalGetValueMap() {
    return internalValueMap;
  }
  private static final com.google.protobuf.Internal.EnumLiteMap<
      RemotingCommandType> internalValueMap =
        new com.google.protobuf.Internal.EnumLiteMap<RemotingCommandType>() {
          public RemotingCommandType findValueByNumber(int number) {
            return RemotingCommandType.forNumber(number);
          }
        };

  public final com.google.protobuf.Descriptors.EnumValueDescriptor
      getValueDescriptor() {
    if (this == UNRECOGNIZED) {
      throw new java.lang.IllegalStateException(
          "Can't get the descriptor of an unrecognized enum value.");
    }
    return getDescriptor().getValues().get(ordinal());
  }
  public final com.google.protobuf.Descriptors.EnumDescriptor
      getDescriptorForType() {
    return getDescriptor();
  }
  public static final com.google.protobuf.Descriptors.EnumDescriptor
      getDescriptor() {
    return com.github.mxsm.protocol.protobuf.RemotingCommandTypeOuterClass.getDescriptor().getEnumTypes().get(0);
  }

  private static final RemotingCommandType[] VALUES = values();

  public static RemotingCommandType valueOf(
      com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
    if (desc.getType() != getDescriptor()) {
      throw new java.lang.IllegalArgumentException(
        "EnumValueDescriptor is not for this type.");
    }
    if (desc.getIndex() == -1) {
      return UNRECOGNIZED;
    }
    return VALUES[desc.getIndex()];
  }

  private final int value;

  private RemotingCommandType(int value) {
    this.value = value;
  }

  // @@protoc_insertion_point(enum_scope:com.github.mxsm.protocol.protobuf.RemotingCommandType)
}

