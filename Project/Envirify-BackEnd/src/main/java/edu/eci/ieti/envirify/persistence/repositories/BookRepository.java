package edu.eci.ieti.envirify.persistence.repositories;

import edu.eci.ieti.envirify.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Interface That Connects The Book Document Mapper With The Book MongoDB Document.
 *
 * @author Error 418
 */
public interface BookRepository extends MongoRepository<Book, String> {
}
