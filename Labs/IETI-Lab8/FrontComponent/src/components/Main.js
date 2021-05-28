import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Task } from './Task';
import { NavBar } from './NavBar';
import { NewTask } from './NewTask';

export const Main = (props) => {

    const module = axios.create({
        baseURL: 'http://localhost:8080',
        timeout: 1000,
        headers: { 'Authorization': 'Bearer ' + localStorage.getItem("JWTToken") }
    });

    const [itemsState, setItemsState] = useState([]);

    useEffect(() => {
        module.get("/api/tasks"
        ).then(response => {
            setItemsState(response.data);
        }).catch(error => {
            alert("Fallo de Conexión con BackEnd");
        });
    }, []);

    const [filtersState, setFiltersState] = useState({
        dueDate: null,
        status: "",
        responsible: ""
    });

    const handleFilters = (filters) => {
        setFiltersState(filters);
    };

    const handleAddNewTask = (newItem) => {
        module.post("/api/tasks", newItem
        ).then(response => {
            const newItems = [...itemsState, newItem];
            setItemsState(newItems);
        }).catch(error => {
            alert("Fallo de Conexión con DB");
        });
    }

    let taskList = itemsState;

    if (filtersState.dueDate !== null) {
        taskList = taskList.filter(item => item.dueDate === filtersState.dueDate);
    }
    if (filtersState.status !== "") {
        taskList = taskList.filter(item => item.status === filtersState.status);
    }
    if (filtersState.responsible !== "") {
        taskList = taskList.filter(item => item.responsible === filtersState.responsible);
    }

    return (
        <div>
            <NavBar logout={props.logout} userData={props.userData} applyFilters={handleFilters} />
            {taskList.map((item, i) => {
                return (<Task key={i}
                    description={item.text}
                    responsible={item.responsible}
                    status={item.state}
                    dueDate={item.dueDate} />
                );
            })}
            <NewTask email={props.userData ? props.userData.username : "email"} addTask={handleAddNewTask} />
        </div>
    );
}