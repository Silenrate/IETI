import React from 'react';
import {Todo} from './Todo';

export const TodoList = (props) => {

    const todoList = props.todoList.map((todo, i) => {
        return (
            <Todo key={i} text={todo.text} priority={todo.priority} dueDate={todo.dueDate}/>
        );
    });

    return (
        <table>
        <thead>
        <tr>
            <th>Task</th>
            <th>Priority</th>
            <th>Due Date</th>
        </tr>
        </thead>
        <tbody>
            {todoList}
        </tbody>
        </table>
    );
}