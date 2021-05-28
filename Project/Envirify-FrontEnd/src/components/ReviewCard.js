import React, { useEffect, useState } from 'react';
import { Box, Rating } from '@material-ui/core';
import Card from '@material-ui/core/Card';
import CardHeader from '@material-ui/core/CardHeader';
import CardContent from '@material-ui/core/CardContent';
import Avatar from '@material-ui/core/Avatar';
import IconButton from '@material-ui/core/IconButton';
import Typography from '@material-ui/core/Typography';
import MoreVertIcon from '@material-ui/icons/MoreVert';
import { useStyles } from '@material-ui/pickers/views/Calendar/SlideTransition';



export const ReviewCard = (props) => {


    const [user, setUser] = useState("");

    useEffect(() => {
        async function fetchMyAPI() {
            if (props.review.owner != null) {
                let response = await fetch(`https://enfiry-back-end.herokuapp.com/api/v1/users/${props.review.owner}`)
                response = await response.json()
                setUser(response.name)
            }
        }
        fetchMyAPI();
    }, []);


    return (
            <Box
                display = "flex"
                justifyContent="center"
                alignItems="center"
                minHeight="30vh"
            >
                <Card style={{width:"50%"}}>
                    <CardHeader
                        avatar={
                            <Avatar aria-label="recipe" >
                                {user.substring(0, 1).toUpperCase()}
                            </Avatar>
                        }
                        action={
                            <IconButton aria-label="settings">
                                <MoreVertIcon />
                            </IconButton>
                        }
                        title={user}
                        subheader={props.review.date}
                    />
                    <CardContent>
                        <Rating
                            name="calification"
                            value={props.review.qualification}
                            precision={0.5}
                            readOnly
                        />
                        <Typography variant="body2" color="textSecondary">
                            {props.review.comment}
                        </Typography>
                    </CardContent>
                </Card>
            </Box>
    );
}
