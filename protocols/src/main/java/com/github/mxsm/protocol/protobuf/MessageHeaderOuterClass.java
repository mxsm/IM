// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: RequestHeaders.proto

package com.github.mxsm.protocol.protobuf;

public final class MessageHeaderOuterClass {
  private MessageHeaderOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_github_mxsm_protocol_protobuf_MessageHeader_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_github_mxsm_protocol_protobuf_MessageHeader_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\023MessageHeader.proto\022!com.github.mxsm.p" +
      "rotocol.protobuf\"=\n\rMessageHeader\022\025\n\rsou" +
      "rceAddress\030\001 \001(\003\022\025\n\rremoteAddress\030\002 \001(\003B" +
      "%\n!com.github.mxsm.protocol.protobufP\001b\006" +
      "proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_com_github_mxsm_protocol_protobuf_MessageHeader_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_com_github_mxsm_protocol_protobuf_MessageHeader_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_github_mxsm_protocol_protobuf_MessageHeader_descriptor,
        new java.lang.String[] { "SourceAddress", "RemoteAddress", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
