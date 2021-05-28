const Todo = (props) => {
    return (
        <div>
            <h2>{props.text}</h2>
            <p><strong>Priority:</strong>{props.priority} <strong>Date:</strong>{props.dueDate.toString()}</p>
        </div>
    );
}

export default Todo;