import React from 'react';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import Typography from '@material-ui/core/Typography';
import Box from '@material-ui/core/Box';
import './Task.css';

export const Task = (props) => {

    let borderStyle = "2px solid ";
    if (props.status === "ready") {
        borderStyle = borderStyle + "blue";
    } else if (props.status === "in progress") {
        borderStyle = borderStyle + "yellow";
    } else if (props.status === "done") {
        borderStyle = borderStyle + "green";
    }

    return (
        <Box m={1} p={1}>
            <Card style={{ border: borderStyle, minWidth: 275 }}>
                <CardContent>
                    <Typography variant="h5" component="h2">
                        {props.description}
                    </Typography>
                    <Typography className="pos" color="textSecondary">
                        {new Date(props.dueDate).toLocaleString()}
                    </Typography>
                    <Typography className="title" color="textSecondary" gutterBottom>
                        {props.status}
                    </Typography>
                    <Typography variant="body1" component="p">
                        {props.responsible.name + " - " + props.responsible.email}
                    </Typography>
                </CardContent>
            </Card>
        </Box>
    );
}