import React, { useState } from 'react';
import Button from '@material-ui/core/Button';
import Modal from '@material-ui/core/Modal';
import Backdrop from '@material-ui/core/Backdrop';
import Fade from '@material-ui/core/Fade';
import { makeStyles } from '@material-ui/core/styles';
import ReactStars from "react-rating-stars-component";
import { Typography } from '@material-ui/core';
import Swal from 'sweetalert2';
import axios from 'axios'



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

export const ReviewModal = ({placeId}) => {

    const classes = useStyles();

    const [openModal, setOpenModal] = useState(false);

    const [comment, setComment] = useState("");

    const [qualification, setqualification] = useState(null)


    const ratingChanged = (newRating) => {
    setqualification(newRating);
    };

    const handleCommentChange = (e) => {
        setComment(e.target.value)
    }


    const openModalHandler = () => {
        setOpenModal(true);
    };

    const closeModalHandler = () => {
        setOpenModal(false);
    };

    const headers = {
        'X-Email': localStorage.getItem("emailUser")
    }

    const postRating = (rating) =>{

        axios.post("https://enfiry-back-end.herokuapp.com/api/v1/ratings/?placeId="+placeId, rating, {
            headers: headers
        })
            .then(response => {

                Swal.fire(
                    'Posted!',
                    'Your review has been posted',
                    'success'
                ).then(function () {
                    window.location.href = "/"
                })


            }).catch(error => {
                alert("Fallo de Conexión con DB");
            });
    }
    

    const handleCommentSubmit = () => {
        
        const rating ={
            "qualification":qualification,
            "comment":comment
        }
        Swal.fire({
            title: 'Confirm',
            icon: 'question',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, post it!'
        }).then((result) => {
            if (result.isConfirmed) {
                postRating(rating)
            }
        })

        setComment('');
        closeModalHandler();
    }

    return (
        <div>
            <Button variant="contained" color="primary" onClick={openModalHandler}>
            Tell us about your experience            
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
                <Fade style={{width:600}} in={openModal}>
                    <div className={classes.paper}>
                        <h3>Rate your experience!</h3>
                        <Typography component="legend">Choose how many stars you rate your experience with!
                        </Typography>
                        <ReactStars
                            count={5}
                            onChange={ratingChanged}
                            size={24}
                            activeColor="#ffd700"
                        />
                            <br></br>
                            <form className="form">
                            <div>
                                <label className="single-input-wrap style-two">
                                    <span className="single-input-title">Tell us how your experience was.</span>
                                    <textarea value={comment} onChange={handleCommentChange} />
                                </label>
                            </div>
                            </form>
                            <Button variant="outlined" onClick={handleCommentSubmit}>
                                Submit review!          
                            </Button>
                    </div>
                </Fade>
            </Modal>
        </div>
    );
};