package com.maurigvs.bank.account.config;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Context;
import ch.qos.logback.core.read.ListAppender;
import io.grpc.Server;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@SpringBootTest(classes = {GrpcServer.class})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class GrpcServerTest {

    @Autowired
    GrpcServer grpcServer;

    @MockBean
    Server server;

    private static ListAppender<ILoggingEvent> loggingAppender;

    private static final Logger logger = (Logger) LoggerFactory.getLogger(GrpcServer.class);

    @BeforeEach
    void setUp() {
        loggingAppender = new ListAppender<>();
        loggingAppender.setContext((Context) LoggerFactory.getILoggerFactory());
        logger.addAppender(loggingAppender);
    }

    @Nested
    class startServer {

        @Test
        void should_call_server_start() throws Exception {
            grpcServer.startServer();

            then(server).should().start();
        }

        @Test
        void should_log_error_when_IOException_is_thrown() throws Exception {
            loggingAppender.start();
            given(server.start()).willThrow(new IOException());

            grpcServer.startServer();

            var logEvent = loggingAppender.list.get(0);
            assertEquals(Level.ERROR, logEvent.getLevel());
            assertEquals("Error while starting Grpc server", logEvent.getMessage());
        }

        @Test
        void should_log_error_when_RuntimeException_is_thrown() throws Exception {
            loggingAppender.start();
            given(server.start()).willThrow(new RuntimeException());

            grpcServer.startServer();

            var logEvent = loggingAppender.list.get(0);
            assertEquals(Level.ERROR, logEvent.getLevel());
            assertEquals("Grpc server is either already started or in shutdown mode", logEvent.getMessage());
        }
    }

    @Nested
    class stopServer {
        
        @Test
        void should_call_server_shutdown() throws InterruptedException {
            grpcServer.stopServer();

            then(server).should().shutdown();
            then(server).should().awaitTermination(1L, TimeUnit.SECONDS);
        }

        @Test
        void should_log_error_when_InterruptedException_is_thrown() throws InterruptedException {
            loggingAppender.start();
            given(server.awaitTermination(1, TimeUnit.SECONDS)).willThrow(new InterruptedException());

            grpcServer.stopServer();

            var logEvent = loggingAppender.list.get(0);
            assertEquals(Level.ERROR, logEvent.getLevel());
            assertEquals("Grpc thread server was unexpectedly interrupted", logEvent.getMessage());
        }
    }
}
