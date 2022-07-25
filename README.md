[![License](https://img.shields.io/github/license/italia/bootstrap-italia.svg)](https://github.com/italia/bootstrap-italia/blob/master/LICENSE)

# gRPC with SpringBoot

This is an example project of instrumenting gRPC with springboot.
<br>
It contains two service definitions and their respective implementation.
- greeting.proto: request-response
- chat.proto: bidirectional streaming

<br>
A client for the greeting service is provided and used by a simple rest controller.
<br>
A UI to test the chat service is provided in repo:
<a href="git@github.com:michele-cervini/grpc-client-springboot-vaadin-example.git">michele-cervini/grpc-client-springboot-vaadin-example</a>

## How to run this example

Start the server
```bash
./gradlew :grpc-server:bootRun
```

Start the client
```bash
./gradlew :grpc-client:bootRun
```