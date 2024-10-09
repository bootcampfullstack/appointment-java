package com.abutua.agenda.web.resources;


import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
public class Hello {

    @GetMapping("hello")
    public String getMethodName() {
        return "V3.0";
    }
    
    
}
