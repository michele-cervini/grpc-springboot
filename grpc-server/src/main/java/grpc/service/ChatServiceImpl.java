package grpc.service;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.proto.chat.ChatMessage;
import com.proto.chat.ChatMessageFromServer;
import com.proto.chat.ChatServiceGrpc;
import io.grpc.stub.StreamObserver;

import com.google.protobuf.Timestamp;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcService
public class ChatServiceImpl extends ChatServiceGrpc.ChatServiceImplBase {

    Logger log = LoggerFactory.getLogger(ChatServiceImpl.class);

    private static final Set<StreamObserver<ChatMessageFromServer>> observers = ConcurrentHashMap.newKeySet();

    @Override
    public StreamObserver<ChatMessage> chat(StreamObserver<ChatMessageFromServer> responseObserver) {
        observers.add(responseObserver);

        return new StreamObserver<>() {
            @Override
            public void onNext(ChatMessage value) {
                log.info("From: " + value.getFrom());
                log.info("Message: " + value.getMessage());
                ChatMessageFromServer message = ChatMessageFromServer.newBuilder().setMessage(value)
                        .setTimestamp(Timestamp.newBuilder().setSeconds(System.currentTimeMillis() / 1000)).build();

                observers.stream().forEach(o -> o.onNext(message));
            }

            @Override
            public void onError(Throwable t) {
                log.error("Error: " + t.getMessage());
                // do something;
                observers.stream().forEach(o -> onError(t));
            }

            @Override
            public void onCompleted() {
                log.info("On completed");
                responseObserver.onCompleted();
                observers.remove(responseObserver);
            }
        };
    }
}