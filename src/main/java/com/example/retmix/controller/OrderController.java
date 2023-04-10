package com.example.retmix.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/order")
public class OrderController {

    @GetMapping
    public ResponseEntity<?> index(){
        return null;
    }
    @PostMapping
    public ResponseEntity<?> store(){
        return null;
    }
}
