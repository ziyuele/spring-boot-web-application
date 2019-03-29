/*
 * Copyright (c) 2019. website http://ziyuele.com
 * any questions you can  send mail 2429362606@qq.com
 */

package com.ziyue.fileserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.ziyue.fileserver.rpc.ServerHandler;
import com.ziyue.website.common.Commons;
import com.ziyue.website.common.rpc.GRPCServerImpl;
import com.ziyue.website.common.rpc.RPCServer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@ComponentScan(basePackages = "com.ziyue")
public class FileServer implements CommandLineRunner {

    private RPCServer fileServer;
    private Commons commons;
    private ServerHandler serverHandler;

    @Autowired
    public FileServer(Commons commons, ServerHandler serverHandler) {
       this.commons = commons;
       this.serverHandler = serverHandler;
    }

    private void init() {
        GRPCServerImpl.Args fileServerArg = new GRPCServerImpl.Args();
        fileServerArg.port = commons.getFILE_SERVER_RPC_PORT();
        fileServerArg.service = serverHandler;
        this.fileServer = new GRPCServerImpl(fileServerArg);
    }

    private void start() {
        log.info("start fileServer ...");
        this.fileServer.start();
    }

    private void waitForExit() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            this.fileServer.stop();
        }));
    }

    @Override
    public void run(String... args) throws Exception {
        init();
        start();
        waitForExit();
    }

    public static void main(String args[]) {
        SpringApplication.run(FileServer.class);
    }

}
