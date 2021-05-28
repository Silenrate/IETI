import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import {Task} from './Task';
import {NavBar} from './NavBar';

export const Main = (props) => {

    return(
        <div>
            <NavBar logout={props.logout} email={props.email}/>
            {props.items.map((item,i) => {
                return (<Task key={i}
                    description={item.description}
                    responsible={item.responsible}
                    status={item.status}
                    dueDate={item.dueDate}/>
                );
            })}
        </div>
    );
}