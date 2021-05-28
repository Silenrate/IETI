import React, { useState } from 'react';
import axios from 'axios';
import { BrowserRouter as Router, Route } from 'react-router-dom';
import './App.css';
import { Login } from './components/Login';
import { UserProfile } from './components/UserProfile';
import { Main } from './components/Main';

const App = () => {

    const module = axios.create({
        baseURL: 'http://localhost:8080',
        timeout: 1000,
        headers: { 'Authorization': 'Bearer ' + localStorage.getItem("JWTToken") }
    });

    const [isLoggedInState, setIsLoggedInState] = useState(localStorage.getItem("JWTToken") !== "");

    const [userState, setUserState] = useState({
        username: "",
        password: "",
        fullName: ""
    });

    const handleSuccessfullyLogin = (e) => {
        setIsLoggedInState(true);
        window.location.href = "/home";
    }

    const handleFailedLogin = (e) => {
        alert("Usuario o Clave Incorrectos");
        setIsLoggedInState(false);
    }

    const handleLogout = () => {
        setIsLoggedInState(false);
        localStorage.setItem("JWTToken", "");
        window.location.href = "/";
    }

    //ESTE METODO NO SIRVE
    const handleUpdateProfile = (newFullName, newPassword) => {
        const email = userState.username;
        const newUserData = {
            username: email,
            password: newPassword,
            fullName: newFullName
        };
        module.put("https://taskplannerieti-default-rtdb.firebaseio.com/user/-MTSd3d_vKkT1Wew3yV3.json", newUserData)
            .then(response => {
                setUserState(newUserData);
                window.location.href = "/home";
            }).catch(error => {
                alert("Fallo de Conexión con DB");
            });
    };

    const LoginView = () => (<Login
        successful={handleSuccessfullyLogin}
        failed={handleFailedLogin}
    />);

    const MainView = () => (<Main
        logout={handleLogout}
        userData={userState}
    />);

    const UserView = () => (<UserProfile
        userData={userState}
        updateUserData={handleUpdateProfile}
    />);

    return (
        <Router>
            <div className="App">
                <Route exact path="/" component={isLoggedInState ? MainView : LoginView} />
                <Route path="/home" component={isLoggedInState ? MainView : LoginView} />
                <Route path="/update" component={isLoggedInState ? UserView : LoginView} />
            </div>
        </Router>
    );
}

export default App;
