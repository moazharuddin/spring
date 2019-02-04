package com.microservice.client.clientservice.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rest")
public class ControllerRest {

    @GetMapping("/msg")
    public String getMessage(){
        return "Hello World";
    }
}
