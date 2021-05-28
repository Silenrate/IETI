import React from 'react';

export const Todo = (props) => {

    return (
        <tr>
            <td>{props.text}</td>
            <td>{props.priority}</td>
            <td>{props.dueDate.format('DD-MM-YYYY')}</td>
        </tr>
    );
}