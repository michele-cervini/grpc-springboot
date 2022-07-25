package grpc.service;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.proto.chat.ChatMessage;
import com.proto.chat.ChatMessageFromServer;
import com.proto.chat.ChatServiceGrpc;
import io.grpc.stub.StreamObserver;

import com.google.protobuf.Timestamp;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class ChatServiceImpl extends ChatServiceGrpc.ChatServiceImplBase {


    private static final Set<StreamObserver<ChatMessageFromServer>> observers = ConcurrentHashMap.newKeySet();

    @Override
    public StreamObserver<ChatMessage> chat(StreamObserver<ChatMessageFromServer> responseObserver) {
        observers.add(responseObserver);

        return new StreamObserver<>() {
            @Override
            public void onNext(ChatMessage value) {
                System.out.println("From: " + value.getFrom());
                System.out.println("Message: " + value.getMessage());
                ChatMessageFromServer message = ChatMessageFromServer.newBuilder().setMessage(value)
                        .setTimestamp(Timestamp.newBuilder().setSeconds(System.currentTimeMillis() / 1000)).build();

                for (StreamObserver<ChatMessageFromServer> observer : observers) {
                    observer.onNext(message);
                }
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("Error: " + t.getMessage());
                // do something;
                for (StreamObserver<ChatMessageFromServer> observer : observers) {
                    observer.onError(t);

                }
            }

            @Override
            public void onCompleted() {
                System.out.println("On completed");
                responseObserver.onCompleted();
                observers.remove(responseObserver);
            }
        };
    }
}