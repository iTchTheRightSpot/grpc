package org.product;

import io.grpc.stub.StreamObserver;

import java.util.Random;
import java.util.logging.Logger;

final class ProductService extends ProductServiceGrpc.ProductServiceImplBase {
    private static final Logger logger = Logger.getLogger(ProductService.class.getName());

    @Override
    public void checkInventory(InventoryRequest request, StreamObserver<InventoryResponse> response) {
        logger.info("processing inventory request with id: " + request.getProductId());
        final int rand = new Random().nextInt(10);
        final var build = InventoryResponse.newBuilder()
                .setName("product-name-" + rand)
                .setProductId(request.getProductId())
                .setQty(rand)
                .build();

        // send the response to the client
        response.onNext(build);
        response.onCompleted();
    }
}