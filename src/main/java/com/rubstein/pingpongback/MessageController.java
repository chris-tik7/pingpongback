package com.rubstein.pingpongback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/api/messages")
@CrossOrigin(origins = "*", allowedHeaders = "*") // WICHTIG: allowedHeaders hinzugefügt!
public class MessageController {

    @Autowired
    private MessageRepository repository;

    // Holt sich das Passwort aus der application.yml
    @Value("${app.secret-password}")
    private String serverPassword;

    // Unser kleiner Türsteher
    private void checkPassword(String clientPassword) {
        if (clientPassword == null || !clientPassword.equals(serverPassword)) {
            // Wirft den Fehler 401 (Unauthorized), wenn das Passwort falsch ist
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Falsches Passwort!");
        }
    }

    @GetMapping
    public List<Message> getMessages(@RequestHeader(value = "X-Secret-Password", required = false) String password) {
        checkPassword(password);
        return repository.findAll();
    }

    @PostMapping
    public Message saveMessage(@RequestBody Message message, @RequestHeader(value = "X-Secret-Password", required = false) String password) {
        checkPassword(password);
        return repository.save(message);
    }

    @DeleteMapping("/{id}")
    public void deleteMessage(@PathVariable Long id, @RequestHeader(value = "X-Secret-Password", required = false) String password) {
        checkPassword(password);
        repository.deleteById(id);
    }
}
