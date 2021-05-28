package edu.eci.ieti.envirify.controllers.dtos;

import edu.eci.ieti.envirify.model.Book;
import edu.eci.ieti.envirify.model.Place;

import java.io.Serializable;
import java.util.Date;

public class BookPlaceDTO implements Serializable {

    private String id;
    private Date initialDate;
    private Date finalDate;
    private String userId;
    private Place place;

    public BookPlaceDTO(){

    }

    public BookPlaceDTO(Book book, Place place){
        this.id=book.getId();
        this.initialDate=book.getInitialDate();
        this.finalDate=book.getFinalDate();
        this.userId= book.getUserId();
        this.place=place;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(Date initialDate) {
        this.initialDate = initialDate;
    }

    public Date getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(Date finalDate) {
        this.finalDate = finalDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }
}
