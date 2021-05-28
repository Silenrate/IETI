import { useState } from 'react';
import TodoList from './TodoList';

const TodoApp = (props) => {

  const [itemsState,setItemsState] = useState({
    items: []
  });

  const [textState,setTextState] = useState({
    text: ""
  });

  const handleChange = (e) => {
    setTextState({ text: e.target.value });
  }

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!textState.text.length) {
        return;
    }
    const newItem = {
        text: textState.text,
        dueDate: new Date(),
        priority: 1
    };
    let newItems = [...itemsState.items,newItem];
    setItemsState({
        items: newItems
    });
    setTextState({ text: "" });
  }

  return (
    <div style={{ textAlign: "center" }}>
        <h3>TODO</h3>
        <TodoList todoList={itemsState.items} />
        <form onSubmit={handleSubmit.bind(this)}>
            <label htmlFor="new-todo">
                What needs to be done? 
            </label>
            <input
                id="new-todo"
                onChange={handleChange.bind(this)}
                value={textState.text}
            />
            <button>
                Add #{itemsState.items.length + 1}
            </button>
        </form>
    </div>
  );
}

export default TodoApp;