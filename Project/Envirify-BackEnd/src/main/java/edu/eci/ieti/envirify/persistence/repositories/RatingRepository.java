package edu.eci.ieti.envirify.persistence.repositories;

import edu.eci.ieti.envirify.model.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RatingRepository extends MongoRepository<Rating, String> {
}
