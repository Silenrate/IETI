package edu.eci.ieti.envirify.model;

import edu.eci.ieti.envirify.controllers.dtos.RatingDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ratings")
public class Rating {

    @Id
    private String id;
    private String comment;
    private Integer qualification;
    private String owner;

    /**
     * Basic constructor
     */
    public Rating() {
    }

    public Rating(RatingDTO ratingDTO){
        this.qualification = ratingDTO.getQualification();
        this.comment= ratingDTO.getComment();
        this.owner = ratingDTO.getOwner();
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getQualification() {
        return qualification;
    }

    public void setQualification(Integer qualification) {
        this.qualification = qualification;
    }
}
