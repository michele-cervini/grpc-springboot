package grpc;

import com.proto.greeting.GreetingServiceGrpc;
import grpc.client.GreetingService;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import net.devh.boot.grpc.client.channelfactory.GrpcChannelConfigurer;
import net.devh.boot.grpc.client.inject.GrpcClient;
import net.devh.boot.grpc.client.inject.GrpcClientBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@GrpcClientBean(
        clazz = GreetingServiceGrpc.GreetingServiceBlockingStub.class,
        beanName = "greeetingServiceBlockingStub",
        client = @GrpcClient("greetingServiceClient"))
public class AppConfiguration {

    public GreetingService greetingService(
            @Autowired GreetingServiceGrpc.GreetingServiceBlockingStub greeetingServiceBlockingStub) {
        return new GreetingService(greeetingServiceBlockingStub);
    }

//    @Bean
//    public GrpcChannelConfigurer keepAliveClientConfigurer() {
//        return (channelBuilder, name) -> {
//            if (channelBuilder instanceof NettyChannelBuilder) {
//                ((NettyChannelBuilder) channelBuilder)
//                        .usePlaintext();
////                        .keepAliveTime(30, TimeUnit.SECONDS)
////                        .keepAliveTimeout(5, TimeUnit.SECONDS);
//            }
//        };
//    }
}
