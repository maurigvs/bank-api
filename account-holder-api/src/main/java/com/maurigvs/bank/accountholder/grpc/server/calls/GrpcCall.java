package com.maurigvs.bank.accountholder.grpc.server.calls;

import com.google.protobuf.GeneratedMessageV3;

public interface GrpcCall<T extends GeneratedMessageV3, V extends GeneratedMessageV3> {

    V processCall(T t);
}
