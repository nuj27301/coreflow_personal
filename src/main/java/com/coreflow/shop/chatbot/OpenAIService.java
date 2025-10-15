package com.coreflow.shop.chatbot;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class OpenAIService {

    @Value("${openai.api-key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public String askChatGPT(String prompt) {
        String url = "https://api.openai.com/v1/chat/completions";

        ChatRequest request = new ChatRequest(
                "gpt-3.5-turbo",
                Collections.singletonList(
                        new ChatRequest.Message("user", prompt)
                )
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        HttpEntity<ChatRequest> entity = new HttpEntity<>(request, headers);
        
        ResponseEntity<ChatResponse> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                ChatResponse.class
        );

        return response.getBody()
                .getChoices()
                .get(0)
                .getMessage()
                .getContent();
    }
}

