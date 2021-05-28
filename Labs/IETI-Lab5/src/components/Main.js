import React, { useState } from 'react';
import Card from '@material-ui/core/Card';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import {Task} from './Task';
import {NavBar} from './NavBar';
import {NewTask} from './NewTask';

export const Main = (props) => {

    const[filtersState,setFiltersState] = useState({
        dueDate:null,
        status:"",
        responsible:""
    });

    const handleFilters = (filters) => {
        setFiltersState(filters);
    };

    let taskList = props.items;

    if(filtersState.dueDate !== null){
        taskList = taskList.filter(item => item.dueDate === filtersState.dueDate);
    }
    if(filtersState.status !== ""){
        taskList = taskList.filter(item => item.status === filtersState.status);
    }
    if(filtersState.responsible !== ""){
        taskList = taskList.filter(item => item.responsible === filtersState.responsible);
    }

    return(
        <div>
            <NavBar logout={props.logout} userData={props.userData} applyFilters={handleFilters}/>
            {taskList.map((item,i) => {
                return (<Task key={i}
                    description={item.description}
                    responsible={item.responsible}
                    status={item.status}
                    dueDate={item.dueDate}/>
                );
            })}
            <NewTask email={props.userData ? props.userData.username : "email"} addTask={props.addTask}/>
        </div>
    );
}