package com.ziyue.website.httpserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.ziyue.website.common.Commons;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@ComponentScan("com.ziyue")
public class HttpServer implements CommandLineRunner {

    private Commons commons;

    @Autowired
    public HttpServer(Commons commons) {
        this.commons = commons;
    }

    private void init() {
    }

    private void start() {
    }

    private void exit(int staus) {
        log.warn("httpServer try to stop");
        System.exit(staus);
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            init();
            start();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            exit(-1);
        }
    }

    public static void main(String[] args) {
        try {
            log.info("start service");
            SpringApplication.run(HttpServer.class, args);
        } catch (Exception e){
            log.error("start service failed");
            System.exit(-1);
        }
    }
}

