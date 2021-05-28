import React, { useState, useEffect } from 'react';
import { NewTask } from './NewTask';
import { TaskList } from './TaskList';
import axios from 'axios';

export const Main = (props) => {

    const [items, setItems] = useState([]);

    useEffect(() => {
        axios.get("https://walteros-ieti-lab7.azurewebsites.net/api/list-tasks?code=sM7NyYnhWr15ivMRZKew7uITrksrV4pc1jPPOjWum5G8F8ivLVVYFQ==")
            .then(res => {
                setItems(res.data);
            }).catch(error => {
                alert("Fallo de Conexión con el BackEnd");
            });
    }, []);

    const addTaskHandler = (newTask) => {
        axios.post("https://walteros-ieti-lab7.azurewebsites.net/api/add-task?code=dtpM/jC4WbJepSlu3Iawk4gZ7fLB7MtqS1hXsZuD2GphEUjnBZeJNA==", newTask)
            .then(res => {
                const newTasks = [...items, res.data];
                setItems(newTasks);
            }).catch(error => {
                alert("Fallo de Conexión con el BackEnd");
            });
    }

    return (
        <div>
            <TaskList items={items} />
            <NewTask addTask={addTaskHandler} />
        </div>
    );
}