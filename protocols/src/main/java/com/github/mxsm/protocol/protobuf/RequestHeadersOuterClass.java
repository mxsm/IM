// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: RequestHeader.proto

package com.github.mxsm.protocol.protobuf;

public final class RequestHeadersOuterClass {
  private RequestHeadersOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_github_mxsm_protocol_protobuf_RequestHeaders_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_github_mxsm_protocol_protobuf_RequestHeaders_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\024RequestHeaders.proto\022!com.github.mxsm." +
      "protocol.protobuf\032\022TerminalType.proto\"\364\001" +
      "\n\016RequestHeaders\022\020\n\010protocol\030\001 \001(\t\022\017\n\007ve" +
      "rsion\030\002 \001(\t\022\025\n\rsourceAddress\030\003 \001(\003\022\025\n\rre" +
      "moteAddress\030\004 \001(\003\022\023\n\013contentType\030\006 \001(\t\022E" +
      "\n\014terminalType\030\007 \001(\0162/.com.github.mxsm.p" +
      "rotocol.protobuf.TerminalType\022\023\n\013deviceT" +
      "oken\030\010 \001(\t\022\016\n\006sender\030\t \001(\t\022\020\n\010receiver\030\n" +
      " \001(\tB%\n!com.github.mxsm.protocol.protobu" +
      "fP\001b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          com.github.mxsm.protocol.protobuf.constant.TerminalTypeOuterClass.getDescriptor(),
        });
    internal_static_com_github_mxsm_protocol_protobuf_RequestHeaders_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_com_github_mxsm_protocol_protobuf_RequestHeaders_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_github_mxsm_protocol_protobuf_RequestHeaders_descriptor,
        new java.lang.String[] { "Protocol", "Version", "SourceAddress", "RemoteAddress", "ContentType", "TerminalType", "DeviceToken", "Sender", "Receiver", });
    com.github.mxsm.protocol.protobuf.constant.TerminalTypeOuterClass.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
