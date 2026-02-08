package pl.teo.realworldapp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    @GetMapping("/")
    public String sayHello() {
        return "<h1>Hello, Spring!</h1>";
    }
}
