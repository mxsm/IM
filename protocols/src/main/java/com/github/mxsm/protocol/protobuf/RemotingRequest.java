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
      "proto\032\024RequestHeaders.proto\032\025ResponseHea" +
      "ders.proto\"\356\002\n\017RemotingCommand\022I\n\016reques" +
      "tHeaders\030\001 \001(\01321.com.github.mxsm.protoco" +
      "l.protobuf.RequestHeaders\022K\n\017responseHea" +
      "ders\030\002 \001(\01322.com.github.mxsm.protocol.pr" +
      "otobuf.ResponseHeaders\022K\n\013commandType\030\003 " +
      "\001(\01626.com.github.mxsm.protocol.protobuf." +
      "RemotingCommandType\022\023\n\013requestCode\030\004 \001(\005" +
      "\022\024\n\014payloadCrc32\030\005 \001(\005\022\016\n\006oneway\030\006 \001(\010\022\021" +
      "\n\tcommandId\030\007 \001(\004\022\027\n\017createTimestamp\030\010 \001" +
      "(\004\022\017\n\007payload\030\t \001(\014B%\n!com.github.mxsm.p" +
      "rotocol.protobufP\001b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          com.github.mxsm.protocol.protobuf.RemotingCommandTypeOuterClass.getDescriptor(),
          com.github.mxsm.protocol.protobuf.RequestHeadersOuterClass.getDescriptor(),
          com.github.mxsm.protocol.protobuf.ResponseHeadersOuterClass.getDescriptor(),
        });
    internal_static_com_github_mxsm_protocol_protobuf_RemotingCommand_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_com_github_mxsm_protocol_protobuf_RemotingCommand_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_github_mxsm_protocol_protobuf_RemotingCommand_descriptor,
        new java.lang.String[] { "RequestHeaders", "ResponseHeaders", "CommandType", "RequestCode", "PayloadCrc32", "Oneway", "CommandId", "CreateTimestamp", "Payload", });
    com.github.mxsm.protocol.protobuf.RemotingCommandTypeOuterClass.getDescriptor();
    com.github.mxsm.protocol.protobuf.RequestHeadersOuterClass.getDescriptor();
    com.github.mxsm.protocol.protobuf.ResponseHeadersOuterClass.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
