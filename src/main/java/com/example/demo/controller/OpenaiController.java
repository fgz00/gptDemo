package com.example.demo.controller;

import com.example.demo.service.OpenaiService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/openAiApi")
public class OpenaiController {

    @Autowired
    private OpenAiChatClient openAiChatClient;
    @Autowired
    private OpenaiService openaiService;

    @RequestMapping("/chat")
    public String chat(){
        return openaiService.chat();
    }

    @RequestMapping("/stream")
    public SseEmitter stream(HttpServletResponse response){
        return openaiService.stream(response);
    }
}