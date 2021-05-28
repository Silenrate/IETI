package edu.eci.ieti.envirify.controllers;

import edu.eci.ieti.envirify.controllers.dtos.MessageDTO;
import edu.eci.ieti.envirify.exceptions.EnvirifyException;
import edu.eci.ieti.envirify.exceptions.EnvirifyPersistenceException;
import edu.eci.ieti.envirify.model.Message;
import edu.eci.ieti.envirify.persistence.repositories.MessageRepository;
import edu.eci.ieti.envirify.services.MessageServices;
import reactor.core.publisher.Flux;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST API Controller for Messages.
 */
@RestController
@RequestMapping(value = "api/v1/messages")
@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET})
public class MessageController {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    private MessageServices services;

    @PostMapping()
    public ResponseEntity<Object> postMessage(@RequestBody MessageDTO messageDTO , @RequestHeader("X-Email") String email) throws EnvirifyPersistenceException {
        Message message = new Message(messageDTO);
        services.addMessage(message , email);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    /**
     * Returns the chats of a user with a email.
     *
     * @param email The email to search the user.
     * @return The Response Entity With The User Information Or The Error Message.
     * @throws EnvirifyException When the user can not be Searched.
     */
    @GetMapping(value = "/{email}/chats", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Message> getChatsByEmail(@PathVariable String email) throws EnvirifyPersistenceException {
    	return services.getChatsByEmail(email);
    }

    /*@GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Message> streamMessagesR(@RequestParam String receiver){
        return messageRepository.findWithTailableCursorByReceiver(receiver);
    }*/
}
