package edu.eci.ieti.envirify.persistence.repositories;

import edu.eci.ieti.envirify.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Interface That Connects The User Document Mapper With The User MongoDB Document.
 *
 * @author Error 418
 */
public interface UserRepository extends MongoRepository<User, String> {
    /**
     * Finds a User Based On The Email.
     *
     * @param email The email value to search.
     * @return The User with that email, returns null if does not exist.
     */
    User findByEmail(String email);
}
