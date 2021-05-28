import React, { useEffect, useState } from 'react';
import { PlaceResults } from './PlaceResults';
import axios from 'axios';

export const PlacesList = (props) => {


    const headers = {
        'X-Email': localStorage.getItem("emailUser")
    }

    const [places, setplaces] = useState([])
    
    useEffect(() => {
        axios.get("https://enfiry-back-end.herokuapp.com/api/v1/places/myplaces", {
            headers: headers
        })
        .then(res => {
                console.log(res);
                setplaces(res.data);
            }).catch(error => {
                const response = error.response;
                if(response.status === 404){
                    const message = response.data
                } else {
                    alert("Fallo de Conexión con el BackEnd");
                }
            });
    }, []);

    return (
        <>
            <h3 className="user-details-title">Your Places</h3>
            {places.length===0? <h3> There are not places created by you </h3>:<PlaceResults showReservation={true} items={places} showOwner={false} showEdit/>}
        </>
    );
};