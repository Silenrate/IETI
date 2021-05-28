import React, { useEffect, useState } from 'react';
import Typography from '@material-ui/core/Typography';
import Button from '@material-ui/core/Button';
import Box from '@material-ui/core/Box';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import CardHeader from '@material-ui/core/CardHeader';
import CardMedia from '@material-ui/core/CardMedia';
import Rating from '@material-ui/core/Rating';
import { makeStyles } from '@material-ui/core/styles';
import moment from 'moment';
import axios from 'axios'

const useStyles = makeStyles((theme) => ({
    root: {
        width: 210,
        maxWidth: 300
    },
    media: {
        height: 0,
        paddingTop: '56.25%', // 16:9
    },
    expand: {
        transform: 'rotate(0deg)',
        marginLeft: 'auto',
        transition: theme.transitions.create('transform', {
            duration: theme.transitions.duration.shortest,
        }),
    },
    expandOpen: {
        transform: 'rotate(180deg)',
    }
}));

export const BookCard = (props) => {

    const classes = useStyles();

    const initialDate = moment(props.initialDate).format('MM/DD/YYYY')
    const finishDate = moment(props.finalDate).format('MM/DD/YYYY')

    console.log(props.channelId)
    const [user, setuser] = useState("")

    const channel=props.channelId.split('-')

    useEffect(() => {
        axios.get("https://enfiry-back-end.herokuapp.com/api/v1/users/id/" + channel[1])
            .then(res => {
                setuser(res.data.email);
            }).catch(error => {
                const response = error.response;
                if (response.status === 404) {
                    alert(response.data);
                } else {
                    alert("Fallo de Conexión con el BackEnd");
                }
            });
    }, [])

    

    return (
        <div>
            <Box m={1} p={1}>
                {/*<Link to={"/place?id=" + props.id}>*/}
                <Card variant="outlined" className={classes.root} >
                    <CardMedia
                        className={classes.media}
                        image={props.image}
                        title={props.name}
                    />
                    <CardHeader
                        title={props.name}
                        subheader={props.city + ", " + props.departament}
                    />
                    <CardContent>
                        <Rating
                            name="calification"
                            value={props.calification}
                            precision={0.5}
                            readOnly
                        />

                        {props.showOwner && <Typography gutterBottom>
                            {"Owner: " + props.owner}
                        </Typography>}

                        <Typography gutterBottom>
                            {`made by : ${user}`}
                        </Typography>

                        <Typography gutterBottom>
                            {`Initial date ${initialDate}`}
                        </Typography>

                        <Typography gutterBottom>
                            {`Finish date ${finishDate}`}
                        </Typography>

                        <Button variant="contained" color="primary" style={{ marginTop: "10px" }} onClick={event => window.location.href = `/place?id=${props.id}&&showReservation=${props.showReservation}&&showChat=${props.showChat}&&channelId=${channel[0]+"-"+user}`}>
                            View
                        </Button>

                    </CardContent>
                </Card>
                {/* </Link>*/}
            </Box>
        </div>
    );
};