package edu.eci.ieti.envirify.model;

import edu.eci.ieti.envirify.controllers.dtos.BookDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Document Mapper Class For The Booking On Envirify App.
 *
 * @author Error 418
 */
@Document(collection = "books")
public class Book {

    @Id
    private String id;
    private Date initialDate;
    private Date finalDate;
    private String userId;
    private String placeId;

    /**
     * Basic Constructor For Book.
     */
    public Book() {
    }

    /**
     * Constructor For Book Based On His DTO.
     *
     * @param bookDTO The DTO OF The Book.
     */
    public Book(BookDTO bookDTO) {
        this.initialDate = bookDTO.getInitialDate();
        this.finalDate = bookDTO.getFinalDate();
        this.userId = bookDTO.getUserId();
        this.placeId = bookDTO.getPlaceId();
    }

    /**
     * Returns The Book Id.
     *
     * @return The Book Id.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets The Book Id.
     *
     * @param id The New Book Id.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Returns The Book Initial Date.
     *
     * @return The Book Initial Date.
     */
    public Date getInitialDate() {
        return initialDate;
    }

    /**
     * Sets The Book Initial Date.
     *
     * @param initialDate The New Book Initial Date.
     */
    public void setInitialDate(Date initialDate) {
        this.initialDate = initialDate;
    }

    /**
     * Returns The Book Final Date.
     *
     * @return The Book Final Date.
     */
    public Date getFinalDate() {
        return finalDate;
    }

    /**
     * Sets The Book Final Date.
     *
     * @param finalDate The New Book Final Date.
     */
    public void setFinalDate(Date finalDate) {
        this.finalDate = finalDate;
    }

    /**
     * Returns The Book User Id.
     *
     * @return The Book User Id.
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets The Book User Id.
     *
     * @param userId The New Book User Id.
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Returns The Place Id.
     *
     * @return The Place Id.
     */
    public String getPlaceId() {
        return placeId;
    }

    /**
     * Sets The Book Place Id.
     *
     * @param placeId The New Book Place Id.
     */
    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    /**
     * Determines If A Date Interval Could Clash With The Booking Date Interval.
     *
     * @param dateOfInit The Initial Date Of The Date Interval To Check.
     * @param dateOfEnd  The Initial Date Of The Date Interval To Check.
     * @return A Boolean Value That Determines If A Date Interval Could Clash With The Booking Date Interval.
     */
    public boolean hasConflict(Date dateOfInit, Date dateOfEnd) {
        return (dateOfInit.before(initialDate) && dateOfEnd.after(finalDate)) || isBetween(dateOfInit) || isBetween(dateOfEnd);
    }

    /**
     * Determines If A Date Is Between The Date Interval Of The Booking.
     *
     * @param date The Date To Check.
     * @return A Boolean Value That Determines If A Date Is Between The Date Interval Of The Booking.
     */
    private boolean isBetween(Date date) {
        return (date.after(initialDate) && date.before(finalDate)) || date.equals(initialDate) || date.equals(finalDate);
    }
}
