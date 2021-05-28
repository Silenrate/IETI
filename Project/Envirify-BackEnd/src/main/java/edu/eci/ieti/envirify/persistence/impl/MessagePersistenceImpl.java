package edu.eci.ieti.envirify.persistence.impl;

import edu.eci.ieti.envirify.exceptions.EnvirifyPersistenceException;
import edu.eci.ieti.envirify.model.Message;
import edu.eci.ieti.envirify.model.User;
import edu.eci.ieti.envirify.persistence.MessagePersistence;
import edu.eci.ieti.envirify.persistence.UserPersistence;
import edu.eci.ieti.envirify.persistence.repositories.MessageRepository;
import reactor.core.publisher.Flux;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class That Implements The Message Persistence Methods For Envirify App.
 *
 * @author Error 418
 */
@Service
public class MessagePersistenceImpl implements MessagePersistence {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserPersistence userPersistence;

    /**
     * Adds a new message to the Envirify Message
     * @param message message to be added
     * @param email email of the user that adds the message
     * @throws EnvirifyPersistenceException in case messages are sent or received from users that do not exist
     */
    @Override
    public void addMessage(Message message, String email) throws EnvirifyPersistenceException {
        User userSender = userPersistence.getUserByEmail(message.getSender());
        User userAuthenticated = userPersistence.getUserByEmail(email);
        if(!userSender.equals(userAuthenticated)){
            throw new EnvirifyPersistenceException("The authenticated user: "+email+" is not the same as the user who sent the message :"+message.getSender());
        }
        userPersistence.getUserByEmail(message.getSender());
        messageRepository.save(message).subscribe();
    }

    /**
     * Get the messages of a user
     * @param email The user messages that be searched
     * @throws EnvirifyPersistenceException When the user does not exist
     */
	@Override
	public Flux<Message> getChatsByEmail(String email) throws EnvirifyPersistenceException {
		return messageRepository.findWithTailableCursorByChannelId(email);
	}

}
