/*
 * Copyright (c) 2019. website http://ziyuele.com
 * any questions you can  send mail 2429362606@qq.com
 */

package com.ziyue.website.common.rpc;

public interface RPCServer {

    public abstract AbstractRPCServer.rpcType getServiceType();

    public abstract void stop();

    public abstract void start();
}