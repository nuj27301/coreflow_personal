package com.coreflow.shop.chatbot;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/chatapi/*")
@CrossOrigin(origins = "*")
public class ChatController {

    private final OpenAIService openAIService;
    
    
    
    @GetMapping("/chatForm")
    public void chatForm() {
    	
    }
    

    @GetMapping("/chat")
    public ResponseEntity<String> ask(@RequestParam String question) {
    	ResponseEntity<String> entity = null;
    	
    	entity = new ResponseEntity<String>(openAIService.askChatGPT(question), HttpStatus.OK);
    	
        return entity;
    }
}

