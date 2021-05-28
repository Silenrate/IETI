import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import Typography from '@material-ui/core/Typography';
import Box from '@material-ui/core/Box';

const useStyles = makeStyles({
    root: {
        minWidth: 275,
    },
    pos: {
        marginBottom: 12,
    },
});

export const UserCard = (props) => {

    const classes = useStyles();
    
    return (
        <Box m={1} p={1}>
            <Card className={classes.root} variant="outlined">
                <CardContent>
                    <Typography variant="h5" component="h2">
                        {props.name}
                    </Typography>
                    <Typography className={classes.pos} color="textSecondary">
                        {props.email}
                    </Typography>
                    <Typography variant="body2" component="p" gutterBottom>
                         {props.password}
                    </Typography>
                </CardContent>
            </Card>
        </Box>
    );
};