package com.ziyue.website.master;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.ziyue.website.common.Commons;
import com.ziyue.website.common.rpc.GRPCServerImpl;
import com.ziyue.website.common.rpc.RPCServer;
import com.ziyue.website.master.rpc.MasterServerHandler;
import com.ziyue.website.master.rpc.MasterWorkerHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@ComponentScan(basePackages = "com.ziyue")
public class Master implements CommandLineRunner {

   private RPCServer masterHttpServer;
   private RPCServer masterWorkerServer;
   private Commons commons;

    @Autowired
    public Master(Commons commons) {
        this.commons = commons;
    }

    private void init() {
        // init master-http server
        GRPCServerImpl.Args MasterServerArgs = new GRPCServerImpl.Args();
        MasterServerArgs.port = commons.getMASTER_RPC_SERVER_PORT();
        MasterServerArgs.service = new MasterServerHandler();
        this.masterHttpServer = new GRPCServerImpl(MasterServerArgs);

        // init master-worker server
        GRPCServerImpl.Args masterWorkerArgs = new GRPCServerImpl.Args();
        masterWorkerArgs.port = commons.getMASTER_PRC_WORKER_PORT();
        masterWorkerArgs.service = new MasterWorkerHandler();
        this.masterWorkerServer = new GRPCServerImpl(masterWorkerArgs);

    }

    private void start() {
        try {
            log.info("try to start masterWorkerServer");
            this.masterWorkerServer.start();
            log.info("try to start masterHttpServer");
            this.masterHttpServer.start();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            exitSystem(-1);
        }
    }
    /**
     *  this mathod used to init all services include RPCService RPCClient
     *  thread-pool and son on
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        init();
        start();
    }

    /**
     *  this function used to stop all service and exit System
     */
    public void exitSystem(int status) {
        log.warn("stop all system");
        this.masterHttpServer.stop();
        this.masterWorkerServer.stop();
        System.exit(status);
    }

    public static void main(String args[]) {
        SpringApplication.run(Master.class);
    }
}