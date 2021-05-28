import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Typography from '@material-ui/core/Typography';
import { PlaceResults } from './PlaceResults';
import { Navbar } from './global-components/navbar';
import { PageHeader } from './global-components/page-header';
import { FooterV1 } from './global-components/footer';

export const SearchPlace = (props) => {

    const [items, setItems] = useState([]);

    const getParameterByName = (name) => {
        name = name.replace(/[[]/, "\\[").replace(/[\]]/, "\\]");
        var regex = new RegExp("[\\?&#]" + name + "=([^&#]*)"),
            results = regex.exec(window.location.href);
        return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
    }

    useEffect(() => {
        axios.get("https://enfiry-back-end.herokuapp.com/api/v1/places?search=" + getParameterByName("value"))
            .then(res => {
                console.log(res);
                setItems(res.data);
            }).catch(error => {
                const response = error.response;
                if(response.status === 404){
                    console.log(response.data);
                } else {
                    alert("Fallo de Conexión con el BackEnd");
                }
            });
    }, []);

    let resultComponent = (
        <Typography variant="h4">
            No results Found!
        </Typography>
    );

    if (items.length > 0) {
        resultComponent = (<PlaceResults items={items} showOwner={true} showEdit={false} showReservation={true} />);
    }

    return (
        <div>
            <Navbar />
            <PageHeader HeaderTitle={"Results for: " + getParameterByName("value")} />
            <br></br>
            <br></br>
            {resultComponent}
            <FooterV1 />
        </div>
    );
};
