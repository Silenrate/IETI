import React, { useState } from 'react';
import {BrowserRouter as Router, Link, Route} from 'react-router-dom';
import logo from './logo.svg';
import './App.css';
import {Login} from "./components/Login";
import {TodoApp} from "./components/TodoApp";

const App = (props) => {

    localStorage.setItem("Username", "daniel@gmail.com");
    localStorage.setItem("Password", "12345");

    let initialLoggedInState = localStorage.getItem("isLoggedIn");
	if(initialLoggedInState === "false"){
		initialLoggedInState = false;
	} else if (initialLoggedInState === "true"){
		initialLoggedInState = true;
	}

    const[isLoggedInState,setIsLoggedInState] = useState(initialLoggedInState);

    const handleSuccessfullyLogin = (e) => {
        setIsLoggedInState(true);
        localStorage.setItem("isLoggedIn", true);
    }

    const handleFailedLogin = (e) => {
        alert("Usuario o Clave Incorrectos");
        setIsLoggedInState(false);
        localStorage.setItem("isLoggedIn", false);
    }

    const LoginView = () => (
        <Login successful={handleSuccessfullyLogin} failed={handleFailedLogin}/>
    );

    const TodoAppView = () => (
        <TodoApp/>
    );

    return (
        <Router>
            <div className="App">
                <header className="App-header">
                    <img src={logo} className="App-logo" alt="logo"/>
                    <h1 className="App-title">TODO React App</h1>
                </header>

                <br/>
                <br/>

                <ul>
                    <li><Link to="/">Login</Link></li>
                    {isLoggedInState && (<li><Link to="/todo">Todo</Link></li>)}
                </ul>
                <div>
                    <Route exact path="/" component={LoginView}/>
                    {isLoggedInState && (<Route path="/todo" component={TodoAppView}/>)}
                </div>
            </div>
        </Router>
    );
}

export default App;
