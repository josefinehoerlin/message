package com.example.message.controller;

import com.example.message.model.Message;
import com.example.message.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("message")
// Controller-klassen håndterer HTTP-anmodninger fra klienten.
// Den bruger @Controller i stedet for @RestController og returnerer data via ResponseEntity.
public class MessageController {
    private final MessageService service;

    public MessageController(MessageService messageService) {
        this.service = messageService;
    }

    @GetMapping()
    public ResponseEntity<List<Message>> getMessages() {
        List<Message> messages = service.getMessages();
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable int id, @RequestParam(required = false) String caps) {
       Message message = service.findMessageById(id, caps);
       if(message == null) {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }
       return new ResponseEntity<>(message, HttpStatus.OK);

    }

    @PostMapping("/add") // denne metode svarer på en POST-request til /message/add.
    public ResponseEntity<Message> addMessage(@RequestBody Message message) {
        Message newMessage = service.addMessage(message);
        return new ResponseEntity<>(newMessage, HttpStatus.CREATED);
    }
}