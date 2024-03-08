package com.maurigvs.bank.accountholder.grpc.server;

import io.grpc.Server;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Configuration
public class GrpcServer {

    private static final Logger log = LoggerFactory.getLogger(GrpcServer.class);

    private final Server server;

    public GrpcServer(Server server) {
        this.server = server;
    }

    @PostConstruct
    public void startServer(){
        try{
            server.start();
            log.info("Grpc Server started, listening on port " + server.getPort());

        } catch (IOException exception){
            log.error("Error while starting Grpc server", exception);

        } catch (RuntimeException exception) {
            log.error("Grpc server is either already started or in shutdown mode", exception);
        }
    }

    @PreDestroy
    public void stopServer(){
        try{
            server.shutdown();
            server.awaitTermination(1, TimeUnit.SECONDS);
            log.info("Grpc Server was shutdown");

        } catch (Exception exception){
            Thread.currentThread().interrupt();
            log.error("Grpc thread server was unexpectedly interrupted", exception);
        }
    }
}
