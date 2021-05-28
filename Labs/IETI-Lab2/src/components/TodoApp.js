import React, { useState } from 'react';
import DatePicker from 'react-datepicker';
import Button from '@material-ui/core/Button';
import Input from '@material-ui/core/Input';
import 'react-datepicker/dist/react-datepicker.css';
import moment from "moment";
import {TodoList} from "./TodoList";

export const TodoApp = (props) => {

    const [itemsState,setItemsState] = useState([]);
    const [textState,setTextState] = useState("");
    const [priorityState,setPriorityState] = useState(0);
    const [dueDateState,setDueDateState] = useState(moment());

    const handleTextChange = (e) => {
        setTextState(e.target.value);
    }

    const handlePriorityChange = (e) => {
        setPriorityState(e.target.value);
    }

    const handleDateChange = (date) => {
        setDueDateState(date);
    }

    const handleSubmit = (e) => {
        e.preventDefault();
        if (!textState.length || !priorityState.length || !dueDateState)
            return;

        const newItem = {
            text: textState,
            priority: priorityState,
            dueDate: dueDateState,
        };

        setItemsState([...itemsState,newItem]);
        setTextState("");
        setPriorityState("");
        setDueDateState("");
    }

    return(
        <div>
            <form onSubmit={handleSubmit} className="todo-form">
                <h3>New TODO</h3>
                <label htmlFor="text" className="right-margin">
                    Text:
                </label>

                 <Input
                    id="text"
                    onChange={handleTextChange}
                    value={textState}>
                 </Input>

                 <br/>
                 <br/>
                 <label htmlFor="priority" className="right-margin">
                    Priority:
                 </label>

                 <Input
                    id="priority"
                    type="number"
                    onChange={handlePriorityChange}
                    value={priorityState}>
                 </Input>
                 <br/>
                 <br/>

                 <DatePicker
                    id="due-date"
                    selected={dueDateState}
                    placeholderText="Due date"
                    onChange={handleDateChange}>
                 </DatePicker>
                 <br/>
                 <Button type="submit">
                    Add #{itemsState.length + 1}
                 </Button>
            </form>
            <br/>
            <br/>
            <TodoList todoList={itemsState}/>
        </div>
    );
}