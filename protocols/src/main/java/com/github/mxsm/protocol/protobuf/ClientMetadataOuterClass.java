// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: ClientMetadata.proto

package com.github.mxsm.protocol.protobuf;

public final class ClientMetadataOuterClass {
  private ClientMetadataOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_github_mxsm_protocol_protobuf_ClientMetadata_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_github_mxsm_protocol_protobuf_ClientMetadata_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\024ClientMetadata.proto\022!com.github.mxsm." +
      "protocol.protobuf\"0\n\016ClientMetadata\022\020\n\010s" +
      "erverIp\030\001 \001(\r\022\014\n\004name\030\002 \001(\tB%\n!com.githu" +
      "b.mxsm.protocol.protobufP\001b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_com_github_mxsm_protocol_protobuf_ClientMetadata_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_com_github_mxsm_protocol_protobuf_ClientMetadata_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_github_mxsm_protocol_protobuf_ClientMetadata_descriptor,
        new java.lang.String[] { "ServerIp", "Name", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}