package org.order;

import io.grpc.*;
import org.product.InventoryRequest;
import org.product.InventoryResponse;
import org.product.ProductServiceGrpc;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    private final ProductServiceGrpc.ProductServiceBlockingStub stub;

    public Main(final Channel channel) {
        stub = ProductServiceGrpc.newBlockingStub(channel);
    }

    public void greet() {
        final var productId = "product-123";
        final InventoryRequest req = InventoryRequest.newBuilder().setProductId(productId).build();
        try {
            final InventoryResponse resp = stub.checkInventory(req);

            logger.info("inventory check response product_id: " + resp.getProductId());
            logger.info("inventory check product_id request equals response: " + resp.getProductId().equals(productId));
            logger.info("inventory check response name: " + resp.getName());
            logger.info("inventory check response qty: " + resp.getQty());
        } catch (final StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
        }
    }

    public static void main(final String[] args) throws InterruptedException {
        logger.info("starting order server...");
        final var channel = Grpc.newChannelBuilder("localhost:9090", InsecureChannelCredentials.create()).build();
        try {
            new Main(channel).greet();
        } finally {
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}
