# gRPC microservice using Vanilla Java

**FUTURE ME** this project demonstrates communication between two services using gRPC.
The two services are Order and Product. The Product service acts as the
server, while the Order service acts as the client. The idea is for the
Order service to call the Product service to retrieve product details (inventory).

# Getting started

### Prerequisites
1. Ensure you have Java 23+ installed on your machine.
2. Make sure you have Maven installed.
3. Clone this repository:
```bash
git clone https://github.com/iTchTheRightSpot/grpc
cd grpc
```

## Running the Product Service (Server)

To run the Product service, follow these steps:
- Open your terminal.
- Navigate to the product module directory and compile the service:
```bash
cd product/
mvn clean package -DskipTests
```
- Run the Product service: 
```bash
clear && java -jar target/product.jar
```
This will start the Product service on localhost:9090 (you can adjust the port if needed in the code).

## Running the Order Service (Client)

To run the Order service, follow these steps:
- Open another terminal window.
- Navigate to the order module directory and compile the service
```bash
cd order/
mvn clean package -DskipTests 
```
- Run the Order service:
```bash
clear && java -jar target/order.jar
```
The Order service will connect to the Product service, request inventory
details, and print the product information to the terminal.

## Troubleshooting
1. Ensure Product service is running before the Order service makes the gRPC call.

## Dev docs
1. [Intro to gRPC.](https://grpc.io/docs/what-is-grpc/introduction/)
2. [gRPC Java basics.](https://grpc.io/docs/languages/java/basics/)
3. [Simple gRPC server implementation.](https://github.com/grpc/grpc-java/tree/v1.71.x/examples/example-hostname)
4. [gRPC server & client doc-1](https://github.com/grpc/grpc-java/blob/v1.71.x/examples/src/main/java/io/grpc/examples/helloworld/HelloWorldClient.java)
5. [Java gRPC GitHub.](https://github.com/grpc/grpc-java/blob/master/README.md)