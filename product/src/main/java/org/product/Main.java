package org.product;

import io.grpc.Grpc;
import io.grpc.InsecureServerCredentials;
import io.grpc.Server;
import io.grpc.health.v1.HealthCheckResponse;
import io.grpc.protobuf.services.HealthStatusManager;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(final String[] args) throws IOException, InterruptedException {
        final int port = 9090;
        final var health = new HealthStatusManager();
        final Server server = Grpc.newServerBuilderForPort(port, InsecureServerCredentials.create())
                .addService(new ProductService())
                .addService(health.getHealthService())
                .build()
                .start();

        logger.info("product module listening on port " + port);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            server.shutdown();
            try {
                logger.info("product module awaiting termination");
                server.awaitTermination(30, TimeUnit.SECONDS);
            } catch (final InterruptedException ex) {
                logger.log(Level.WARNING, "Runtime.addShutdownHook: {0}", ex.getMessage());
                Thread.currentThread().interrupt();
            }
            logger.info("shutting server down");
            server.shutdownNow();
        }));
        health.setStatus("", HealthCheckResponse.ServingStatus.SERVING);
        server.awaitTermination();
    }
}
