import React, { useState, useEffect } from 'react'
import { BookingResult } from './BookingResult';
import Swal from 'sweetalert2';
import axios from 'axios';

export const BookingsList = () => {

    let email = localStorage.getItem('emailUser');

    const [lista, setlista] = useState([])
    useEffect(() => {
        axios.get("https://enfiry-back-end.herokuapp.com/api/v1/users/" + email + "/bookings")
            .then(response => {
                let result = response.data;
                console.log(result)
                let list = [];
                setlista(result);
            }).catch(error => {
                
            });
    }, [])
    console.log(lista)

    return (
        <div>
            <h3 className="user-details-title">Your Bookings</h3>
            <BookingResult items={lista} showReservation={false} showChat={true} showOwner={false} showEdit={false} />
        </div>
    )
}
