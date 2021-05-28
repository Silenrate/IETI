package edu.eci.ieti.envirify.services;

import edu.eci.ieti.envirify.exceptions.EnvirifyPersistenceException;
import edu.eci.ieti.envirify.model.Message;
import reactor.core.publisher.Flux;

public interface MessageServices {

    /**
     * Adds a new message to the Envirify Message
     * @param message message to be added
     * @param email email of the user that adds the message
     * @throws EnvirifyPersistenceException in case messages are sent or received from users that do not exist
     */
    public void addMessage(Message message, String email) throws EnvirifyPersistenceException;

    /**
     * Get the messages of a user
     * @param email The user messages that be searched
     * @throws EnvirifyPersistenceException When the user does not exist
     */
	public Flux<Message> getChatsByEmail(String email) throws EnvirifyPersistenceException;
}
