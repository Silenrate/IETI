import React, { useState, useEffect } from 'react';
import { NewTask } from './NewTask';
import { TaskList } from './TaskList';
import axios from 'axios';

export const Main = (props) => {

    const [items, setItems] = useState([]);

    useEffect(() => {
        axios.get("http://localhost:8080/api/todo")
            .then(res => {
                setItems(res.data);
            }).catch(error => {
                console.log(error);
                alert("Fallo de Conexión con el BackEnd");
            });
    }, []);

    const addTaskHandler = (newTask, imageURL) => {
        newTask.fileUrl = imageURL;
        axios.post("http://localhost:8080/api/todo", newTask)
            .then(res => {
                const newTasks = [...items, res.data];
                setItems(newTasks);
            }).catch(error => {
                console.log(error);
                alert("Fallo de Conexión con el BackEnd");
            });
    }

    const addHandler = (newFile, newTask) => {
        axios.post("http://localhost:8080/api/files", newFile)
            .then(res => {
                const staticUrl = res.data;
                console.log("file uploaded!", "Static URL: " + staticUrl);
                addTaskHandler(newTask,staticUrl);
            }).catch(error => {
                console.log(error);
                alert("Fallo de Conexión con el BackEnd");
            });
    }

    return (
        <div>
            <TaskList items={items} />
            <NewTask add={addHandler} />
        </div>
    );
}