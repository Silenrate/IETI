package edu.eci.ieti.envirify.persistence.repositories;

import edu.eci.ieti.envirify.model.Place;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


/**
 * Interface That Connects The Place Document Mapper With The Place MongoDB Document.
 *
 * @author Error 418
 */
public interface PlaceRepository extends MongoRepository<Place, String> {

    /**
     * find the places with a direction
     *
     * @param direction the direction
     * @return a list of places
     */
    List<Place> findByDirection(String direction);

    /**
     * Find The Places By The City Name.
     *
     * @param city The City Name.
     * @return A List Of Places In A City.
     */
    List<Place> findByCity(String city);

    /**
     * Find The Places By The Department Name.
     *
     * @param department The Department Name.
     * @return A List Of Places In A Department.
     */
    List<Place> findByDepartment(String department);

	void deleteById(String id, String email);


}
