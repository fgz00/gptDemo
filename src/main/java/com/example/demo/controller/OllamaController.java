package com.example.demo.controller;

import com.example.demo.service.OllamaService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/ollamaApi")
public class OllamaController {

    @Autowired
    private OllamaService ollamaService;

    @RequestMapping("/chat")
    public String chat(){
        return ollamaService.chat();
    }

        
    @RequestMapping("/stream")
    public SseEmitter stream(HttpServletResponse response){
        return ollamaService.stream(response);

    }
}