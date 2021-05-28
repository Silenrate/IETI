import React, { useEffect, useState } from 'react'
import { BookingToMeResult } from './BookingToMeResult';
import axios from 'axios'

export const BooksToMe = () => {

    let email = localStorage.getItem('emailUser');


    const [lista, setlista] = useState([])



    useEffect(() => {
        axios.get("https://enfiry-back-end.herokuapp.com/api/v1/users/" + email + "/books")
            .then(response => {
                let result = response.data;
                setlista(result);
            }).catch(error => {
                
            });
    }, [])
    

    return (
        <div>
            <h3 className="user-details-title">Your Bookings</h3>
            {lista.length===0? <h3>No bookings </h3>: <BookingToMeResult  items={lista} showReservation={false} showChat={true} showOwner={false} showEdit={false} />}
        </div>
    )
}
