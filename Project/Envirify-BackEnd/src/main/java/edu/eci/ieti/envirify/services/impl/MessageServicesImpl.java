package edu.eci.ieti.envirify.services.impl;

import edu.eci.ieti.envirify.exceptions.EnvirifyPersistenceException;
import edu.eci.ieti.envirify.model.Message;
import edu.eci.ieti.envirify.persistence.MessagePersistence;
import edu.eci.ieti.envirify.services.MessageServices;
import reactor.core.publisher.Flux;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServicesImpl implements MessageServices {
    
    @Autowired
    private MessagePersistence persistence;

    /**
     * Adds a new message to the Envirify Message
     * @param message message to be added
     * @param email email of the user that adds the message
     * @throws EnvirifyPersistenceException in case messages are sent or received from users that do not exist
     */
    @Override
    public void addMessage(Message message, String email) throws EnvirifyPersistenceException {
        persistence.addMessage(message , email);
    }

    /**
     * Get the messages of a user
     * @param email The user messages that be searched
     * @throws EnvirifyPersistenceException When the user does not exist
     */
	@Override
	public Flux<Message> getChatsByEmail(String email) throws EnvirifyPersistenceException {
		 return persistence.getChatsByEmail(email);
	}
}
