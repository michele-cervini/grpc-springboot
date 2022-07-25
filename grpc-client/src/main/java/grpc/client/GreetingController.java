package grpc.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    @Autowired GreetingService greetingService;

    @GetMapping("/")
    public String receiveGreeting(@RequestParam final String name) {
        return greetingService.receiveGreeting(name);
    }
}
