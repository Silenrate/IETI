import React, { useState } from 'react';
import Button from '@material-ui/core/Button';
import Modal from '@material-ui/core/Modal';
import Backdrop from '@material-ui/core/Backdrop';
import Fade from '@material-ui/core/Fade';
import { makeStyles } from '@material-ui/core/styles';
import axios from 'axios';
import { FormUpdate } from './section-components/FormUpdate';
import Swal from 'sweetalert2';



 const headers = {
    'X-Email': localStorage.getItem("emailUser")
}

const useStyles = makeStyles((theme) => ({
    modal: {
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
    },
    paper: {
        backgroundColor: theme.palette.background.paper,
        border: '2px solid #000',
        boxShadow: theme.shadows[5],
        padding: theme.spacing(2, 4, 3),
    },
}));

export const EditPlaceModal = ({place}) => {

    const classes = useStyles();

    const [openModal, setOpenModal] = useState(false);


    const openModalHandler = () => {
        setOpenModal(true);
    };

    const closeModalHandler = () => {
        setOpenModal(false);
    };

    const handleSubmit = () => {
        closeModalHandler();
        Swal.fire({
            title: 'Place updated!',
            text: 'Continue',
            icon: 'success',
            confirmButtonText: 'Cool'
        })
        .then( () => {
            window.location.reload(true);
        })
    
    }


    const handleOnClick = (place) => {
        axios.put("https://enfiry-back-end.herokuapp.com/api/v1/places/" + place.id , place , {
            headers: headers
          })
            .then(res => {
                handleSubmit();
            }).catch(error => {
                const response = error.response;
                if (response.status === 404) {
                    alert(response.data);
                } else {
                    alert("Fallo de Conexión con el BackEnd");
                }
            })
        };

    return (
        <div>
            <Button variant="contained" color="primary" onClick={openModalHandler}>
            Edit        
            </Button>
            <Modal
                aria-labelledby="transition-modal-title"
                aria-describedby="transition-modal-description"
                className={classes.modal}
                open={openModal}
                onClose={closeModalHandler}
                closeAfterTransition
                BackdropComponent={Backdrop}
                BackdropProps={{
                    timeout: 500,
                }}
            >
                <Fade style={{width:800}} in={openModal}>
                    <div className={classes.paper}>
                    <h3>Edit your place</h3>
                            <br></br>
                            <FormUpdate placeInfo={place} handleOnClick={handleOnClick}></FormUpdate>
                    </div>
                </Fade>
            </Modal>
        </div>
    );
};