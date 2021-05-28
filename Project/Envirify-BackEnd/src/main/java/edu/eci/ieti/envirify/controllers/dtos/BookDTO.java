package edu.eci.ieti.envirify.controllers.dtos;

import java.io.Serializable;
import java.util.Date;

/**
 * Data Transfer Object For Book Object.
 *
 * @author Error 418
 */
public class BookDTO implements Serializable {

    private String id;
    private Date initialDate;
    private Date finalDate;
    private String userId;
    private String placeId;

    /**
     * Basic Constructor For BookDTO.
     */
    public BookDTO() {
    }

    /**
     * Constructor For BookDTO.
     *
     * @param initialDate The Initial Date Of The BookDTO.
     * @param finalDate   The Final Date Of The BookDTO.
     * @param placeId     The User Id Of The BookDTO.
     */
    public BookDTO(Date initialDate, Date finalDate, String placeId) {
        this.initialDate = initialDate;
        this.finalDate = finalDate;
        this.placeId = placeId;
    }

    /**
     * Returns The Id Of The BookDTO.
     *
     * @return The Id Of The BookDTO.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets The BookDTO Id.
     *
     * @param id The New BookDTO Id.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Returns The Initial Date Of The BookDTO.
     *
     * @return The Initial Date Of The BookDTO.
     */
    public Date getInitialDate() {
        return initialDate;
    }

    /**
     * Sets The BookDTO Initial Date.
     *
     * @param initialDate The New BookDTO Initial Date.
     */
    public void setInitialDate(Date initialDate) {
        this.initialDate = initialDate;
    }

    /**
     * Returns The Final Date Of The BookDTO.
     *
     * @return The Final Date Of The BookDTO.
     */
    public Date getFinalDate() {
        return finalDate;
    }

    /**
     * Sets The BookDTO Final Date.
     *
     * @param finalDate The New BookDTO Final Date.
     */
    public void setFinalDate(Date finalDate) {
        this.finalDate = finalDate;
    }

    /**
     * Returns The User Id Of The BookDTO.
     *
     * @return The User Id Of The BookDTO.
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets The BookDTO User Id.
     *
     * @param userId The New BookDTO User Id.
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Returns The Place Id Of The BookDTO.
     *
     * @return The Place Id Of The BookDTO.
     */
    public String getPlaceId() {
        return placeId;
    }

    /**
     * Sets The BookDTO Place Id.
     *
     * @param placeId The New BookDTO Place Id.
     */
    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }
}
