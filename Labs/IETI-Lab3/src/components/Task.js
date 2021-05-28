import React from 'react';
import Card from '@material-ui/core/Card';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import './Task.css';

export const Task = (props) => {

    let borderStyle = "2px solid ";
    if (props.status === "Ready"){
        borderStyle = borderStyle + "blue";
    } else if (props.status === "In Progress"){
        borderStyle = borderStyle + "yellow";
    } else if (props.status === "Completed"){
        borderStyle = borderStyle + "green";
    }

    return(
        <div>
            <Card style={{border: borderStyle}}>
                <CardContent>
                    <Typography variant="h5" component="h2">
                        {props.description}
                    </Typography>
                    <Typography className="title" color="textSecondary" gutterBottom>
                        {props.status}
                    </Typography>
                    <Typography className="pos" color="textSecondary">
                        {new Date(props.dueDate).toLocaleString()}
                    </Typography>
                    <Typography variant="body2" component="p">
                        {props.responsible.name}
                        <br />
                        {props.responsible.email}
                    </Typography>
                </CardContent>
            </Card>
            <br></br>
        </div>
    );
}