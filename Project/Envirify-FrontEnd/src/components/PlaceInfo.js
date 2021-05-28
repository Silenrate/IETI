import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Typography from '@material-ui/core/Typography';
import CssBaseline from '@material-ui/core/CssBaseline';
import Rating from '@material-ui/core/Rating';
import Avatar from '@material-ui/core/Avatar';
import { ReservationModal } from './ReservationModal';
import { Navbar } from './global-components/navbar';
import { FooterV1 } from './global-components/footer';
import { BannerV2 } from './section-components/banner-v2';
import { ReviewModal } from './ReviewModal';
import { Link } from "react-router-dom";
import Swal from 'sweetalert2';
import { ReviewView } from './ReviewView';
import { Box } from '@material-ui/core';
import Button from '@material-ui/core/Button';
import { Widget, addResponseMessage} from 'react-chat-widget';
import 'react-chat-widget/lib/styles.css';

export const PlaceInfo = () => {

    const [info, setInfo] = useState({});

    const [ratings, setRatings] = useState([]);

    const [count,setCount] = useState(0);

    const [openChat, setopenChat] = useState(false)

    const getParameterByName = (name) => {
        name = name.replace(/[[]/, "\\[").replace(/[\]]/, "\\]");
        var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
            results = regex.exec(window.location.href);
        return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
    }

    const channelId1 = getParameterByName("channelId")
    console.log(channelId1)
    const channel = channelId1.split('-');

    useEffect(() => {
        axios.get("https://enfiry-back-end.herokuapp.com/api/v1/places/" + getParameterByName("id"))
            .then(res => {
                setInfo(res.data);
            }).catch(error => {
                const response = error.response;
                if (response.status === 404) {
                    alert(response.data);
                } else {
                    alert("Fallo de Conexión con el BackEnd");
                }
            });
    }, []);

    const [listening, setListening] = useState(false);
    let eventSource = undefined;

    useEffect(() => {
        if (!listening) {
            eventSource = new EventSource("https://enfiry-back-end.herokuapp.com/api/v1/messages/"+channelId1+"/chats");
            eventSource.onmessage = (event) => {
                const data = JSON.parse(event.data);
                if(data.receiver===localStorage.getItem("emailUser")){
                    console.log(data.receiver)
                    addResponseMessage(data.message)
                }
                
            }
            eventSource.onerror = (err) => {
                console.error("EventSource failed:", err);
                eventSource.close();
            }
            setListening(true)
        }
        return () => {
                eventSource.close();
                console.log("event closed")
        }

    }, [])

    const handleNewUserMessage = (newMessage) => {
        console.log(`New message incomig! ${newMessage}`);
        const headers = {
            "X-Email": localStorage.getItem("emailUser"),
            "Authorization": "Bearer " + localStorage.getItem("Authentication")
        }
        const receiver = localStorage.getItem("emailUser")===channel[0] ? channel[1]:channel[0]
        const messagePost={
            messageDTO:newMessage,
            channelIdDTO:channelId1,
            senderDTO:localStorage.getItem("emailUser"),
            receiverDTO:receiver
        }
        axios.post("https://enfiry-back-end.herokuapp.com/api/v1/messages", messagePost, { headers: headers })
            .then(res => {
                console.log("siii")
                
            })
            .catch(error => {
                console.error(error)
            });
        // Now send the message throught the backend API
      }


    const id = getParameterByName("id");
    const showReservation = (getParameterByName("showReservation") === 'true');
    const showChat = (getParameterByName("showChat") === 'true');

    const handleOpenChange=(e)=>{
        setopenChat(!openChat)

    }


    
    let calification = 0;

    useEffect(() => {
        axios.get("https://enfiry-back-end.herokuapp.com/api/v1/ratings?placeId=" + getParameterByName("id"))
            .then(res => {
                setRatings(res.data);
                res.data.map(rating => {
                    calification = calification+rating.qualification;
                });
                setCount(calification/res.data.length);
            }).catch(error => {
                const response = error.response;
                if (response.status === 404) {
                    alert(response.data);
                } else {
                    alert("Fallo de Conexión con el BackEnd");
                }
            });
    }, []);

    const sumbitBookHandler = (start, end) => {
        //FORMATO YYYY-MM-DD
        const newBooking = {
            initialDate: start.toISOString().substring(0, 10),
            finalDate: end.toISOString().substring(0, 10),
            placeId: id
        };
        const headers = {
            "X-Email": localStorage.getItem("emailUser"),
            "Authorization": "Bearer " + localStorage.getItem("Authentication")
        }
        axios.post("https://enfiry-back-end.herokuapp.com/api/v1/books", newBooking, { headers: headers })
            .then(res => {
                Swal.fire({
                    title: 'Yeah!',
                    text: 'Booking Created',
                    timer: 2000,
                    timerProgressBar: false,
                    icon: 'success',
                    showConfirmButton: false
                });
            })
            .catch(error => {
                Swal.fire({
                    title: 'Booking Failed',
                    text: error.response.data,
                    icon: 'error',
                    confirmButtonText: 'Ok'
                });
            });

    };

    let PlaceInformation = (
        <Typography variant="h4">
            Non Existent Place!
        </Typography>
    );

    if (info.id !== undefined) {
        //LA CALIFICACION ES EL PROMEDIO DE LSO PUNTOS DE LOS COMENTARIOS, CAMBIAR EL 3.5 QUEMADO.
        PlaceInformation = (
            <div >
                <CssBaseline />
                <div className="video-area tp-video-area pd-top-110">
                    <div className="container viaje-go-top">
                        <div className="row">
                            <div className="col-xl-5 col-lg-6 align-self-center wow animated fadeInRight" data-wow-duration="1s" data-wow-delay="0.3s">
                                <div className="section-title mb-lg-0 mb-4 text-center text-lg-left">
                                    <h2 className="title">{info.name}</h2>
                                    <h3>{info.city + ", " + info.department}</h3>
                                    <Typography variant="h4">Calification: <Rating
                                        name="calification"
                                        value={count}
                                        precision={0.5}
                                        size="large"
                                        readOnly
                                    /></Typography>
                                    <Typography variant="h4">Description:</Typography>
                                    <Typography variant="h5">{info.description}</Typography>
                                    <Typography variant="h4">Owner:</Typography>

                                    <Link to={"/user?user=" + info.owner} style={{ color: "black" }}>
                                        <Typography variant="h5" >{info.owner} </Typography>
                                    </Link>

                                    <br></br>
                                    {(showReservation && localStorage.getItem('isLoggedIn')) && <ReservationModal sumbitBook={sumbitBookHandler} />}
                                    <br></br>
                                    {localStorage.getItem('isLoggedIn') && <ReviewModal placeId={getParameterByName("id")}></ReviewModal>}
                                    <br></br>
                                    {(localStorage.getItem('isLoggedIn') && showChat)&& <Button variant="contained" color="primary" onClick={handleOpenChange}>
                                         Chat
                                         </Button>}
                                </div>
                            </div>

                            <div className="col-xl-5 col-lg-6 offset-xl-1 wow animated fadeInLeft" data-wow-duration="1s" data-wow-delay="0.3s">
                                <div className="video-popup-wrap">
                                    <div>
                                        <Avatar variant="square"
                                            style={{ width: "500px", height: "400px" }}
                                            src={info.urlImage} />
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }

    let ReviewsInformation = (
        <div>
            <Box
                justifyContent="center"
            >
                <h2 style={{"text-align":"center"}}> Reviews</h2>
                {ratings.length > 0 ? <ReviewView
                    reviews={ratings}>
                </ReviewView> : <h1 style={{"text-align":"center"}}>No reviews found</h1>}
            </Box>
        </div>
    );


    return (
        <div>
            <Navbar />
            <BannerV2 />
            {PlaceInformation}
            <br></br>
            <br></br>
            {ReviewsInformation}
            <br></br>
            {openChat&&<Widget
          handleNewUserMessage={handleNewUserMessage}
          title="Envirify"
          subtitle="Chat"
        />}
            <FooterV1 />
        </div>
    );
};

