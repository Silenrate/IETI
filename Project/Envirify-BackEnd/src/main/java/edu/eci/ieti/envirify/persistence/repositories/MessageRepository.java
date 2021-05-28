package edu.eci.ieti.envirify.persistence.repositories;

import edu.eci.ieti.envirify.model.Message;
import reactor.core.publisher.Flux;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends ReactiveMongoRepository<Message, String> {
	
	@Tailable
	public Flux<Message> findWithTailableCursorByChannelId(String email);

}