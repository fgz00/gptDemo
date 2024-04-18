package com.example.demo.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface OpenaiService {
    String chat();

    SseEmitter stream(HttpServletResponse response);
}
