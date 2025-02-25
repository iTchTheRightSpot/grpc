package org.product;

import io.grpc.Grpc;
import io.grpc.InsecureServerCredentials;
import io.grpc.Server;
import io.grpc.health.v1.HealthCheckResponse;
import io.grpc.protobuf.services.HealthStatusManager;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(final String[] args) throws IOException, InterruptedException {
        final int port = 9090;
        final var health = new HealthStatusManager();
        final Server server = Grpc.newServerBuilderForPort(port, InsecureServerCredentials.create())
                .addService(new ProductService())
                .addService(health.getHealthService())
                .build()
                .start();

        System.out.println("product module listening on port " + port);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            server.shutdown();
            try {
                server.awaitTermination(30, TimeUnit.SECONDS);
            } catch (final InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            server.shutdownNow();
        }));
        health.setStatus("", HealthCheckResponse.ServingStatus.SERVING);
        server.awaitTermination();
    }
}
