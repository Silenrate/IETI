import Todo from './Todo';

const TodoList = (props) => {
    return (
        <div>
        {props.todoList.map((t,i) => {
            return <Todo key={"Todo "+i} text={t.text} priority={t.priority} dueDate={t.dueDate}/>
        })}
        </div>
    );
}

export default TodoList;