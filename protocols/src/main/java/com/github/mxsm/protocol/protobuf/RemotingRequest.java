// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: RemotingRequest.proto

package com.github.mxsm.protocol.protobuf;

public final class RemotingRequest {
  private RemotingRequest() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_github_mxsm_protocol_protobuf_RemotingCommand_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_github_mxsm_protocol_protobuf_RemotingCommand_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\025RemotingRequest.proto\022!com.github.mxsm" +
      ".protocol.protobuf\032\031RemotingCommandType." +
      "proto\"^\n\017RemotingCommand\022K\n\013commandType\030" +
      "\001 \001(\01626.com.github.mxsm.protocol.protobu" +
      "f.RemotingCommandTypeB%\n!com.github.mxsm" +
      ".protocol.protobufP\001b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          com.github.mxsm.protocol.protobuf.RemotingCommandTypeOuterClass.getDescriptor(),
        });
    internal_static_com_github_mxsm_protocol_protobuf_RemotingCommand_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_com_github_mxsm_protocol_protobuf_RemotingCommand_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_github_mxsm_protocol_protobuf_RemotingCommand_descriptor,
        new java.lang.String[] { "CommandType", });
    com.github.mxsm.protocol.protobuf.RemotingCommandTypeOuterClass.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
