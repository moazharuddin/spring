package com.spring.security.springsecurity.resource;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rest")
public class SpringResource {

    @GetMapping("/msg")
    public String hello(){
        return "Hello";
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/secured/msg")
    public String securedContent(){
        return "Hello Secured";
    }
}
