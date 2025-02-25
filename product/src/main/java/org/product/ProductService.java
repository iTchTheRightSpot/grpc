package org.product;

import io.grpc.stub.StreamObserver;

import java.util.Random;

final class ProductService extends ProductServiceGrpc.ProductServiceImplBase {
    @Override
    public void checkInventory(InventoryRequest request, StreamObserver<InventoryResponse> response) {
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