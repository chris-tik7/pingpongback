package com.rubstein.pingpongback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/messages")
@CrossOrigin(origins = "*") // EXTREM WICHTIG für Frontend-Tests!
public class MessageController {

    @Autowired
    private MessageRepository repository;

    @GetMapping
    public List<Message> getMessages() {
        return repository.findAll();
    }

    @PostMapping
    public Message saveMessage(@RequestBody Message message) {
        return repository.save(message);
    }

    @DeleteMapping("/{id}")
    public void deleteMessage(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
