import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Typography from '@material-ui/core/Typography';
import { UsersList } from './UsersList';

export const UsersPage = (props) => {

    const [users, setUsers] = useState([]);

    useEffect(() => {
        axios.get("http://walteros-taskplanner-back.westus.azurecontainer.io:8080/users")
            .then(response => {
                let result = response.data;
                setUsers(result);
            }).catch(error => {
                alert("Fallo de Conexión con el BackEnd");
            });
    }, []);

    return (
        <div>
            <Typography variant="h3" style={{ textAlign: "center" }}>Users Page</Typography>
            <br></br>
            <div
                style={{
                    display: "flex",
                    justifyContent: "center",
                    alignItems: "center"
                }}
            >
                <UsersList list={users}></UsersList>
            </div>
        </div>
    );
}