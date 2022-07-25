package grpc.client;

import com.proto.greeting.GreetingServiceGrpc;
import com.proto.greeting.HelloRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GreetingService {

    private GreetingServiceGrpc.GreetingServiceBlockingStub greetingServiceBlockingStub;

    public String receiveGreeting(final String name) {
        HelloRequest request = HelloRequest.newBuilder()
                .setName(name)
                .build();
        return greetingServiceBlockingStub.sayHello(request).getMessage();
    }

}
