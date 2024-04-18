package com.example.demo.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import com.example.demo.service.OllamaService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ai.chat.Generation;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.ollama.OllamaChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@Slf4j
@Service
public class OllamaServiceImpl implements OllamaService {
    @Autowired
    private OllamaChatClient ollamaChatClient;
    @Override
    public String chat() {
        String systemPrompt = "{prompt}";
        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(systemPrompt);

        String userPrompt = "中国有哪些名胜古迹？";
        Message userMessage = new UserMessage(userPrompt);

        Message systemMessage = systemPromptTemplate.createMessage(MapUtil.of("prompt", "you are a helpful AI assistant"));

        Prompt prompt = new Prompt(List.of(userMessage, systemMessage));

        List<Generation> response = ollamaChatClient.call(prompt).getResults();

        String result = "";

        for (Generation generation : response){
            String content = generation.getOutput().getContent();
            result += content;
        }

        System.out.println(result);
        return result;
    }

    @Override
    public SseEmitter stream(HttpServletResponse response) {
        response.setContentType("text/event-stream");
        response.setCharacterEncoding("UTF-8");
        SseEmitter emitter = new SseEmitter();


        String systemPrompt = "{prompt}";
        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(systemPrompt);

        String userPrompt = "中国有哪些名胜古迹？";
        Message userMessage = new UserMessage(userPrompt);

        Message systemMessage = systemPromptTemplate.createMessage(MapUtil.of("prompt", "you are a helpful AI assistant"));
        Prompt prompt = new Prompt(List.of(userMessage, systemMessage));

        ollamaChatClient.stream(prompt).subscribe(x -> {
            try {
                log.info("response: {}",x);
                List<Generation> generations = x.getResults();
                if(CollUtil.isNotEmpty(generations)){
                    for(Generation generation:generations){
                        AssistantMessage assistantMessage =  generation.getOutput();
                        String content = assistantMessage.getContent();
                        if(StringUtils.isNotEmpty(content)){
                            emitter.send(content);
                        }else{
                            if(StringUtils.equals(content,"null"))
                                emitter.complete(); // Complete the SSE connection
                        }
                    }
                }


            } catch (Exception e) {
                emitter.complete();
                log.error("流式返回结果异常",e);
            }
        });

        return emitter;
    }
}
