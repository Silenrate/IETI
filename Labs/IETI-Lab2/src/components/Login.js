import React, { useState } from 'react';
import Avatar from '@material-ui/core/Avatar';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import FormControl from '@material-ui/core/FormControl';
import Input from '@material-ui/core/Input';
import InputLabel from '@material-ui/core/InputLabel';
import LockIcon from '@material-ui/icons/LockOutlined';
import Paper from '@material-ui/core/Paper';
import Typography from '@material-ui/core/Typography';
import './Login.css';


export const Login = (props) =>{

    const[usernameState,setUsernameState] = useState("");
    const[passwordState,setPasswordState] = useState("");

    const handleUsernameChange = (e) => {
        setUsernameState(e.target.value);
    }

    const handlePasswordChange = (e) => {
        setPasswordState(e.target.value);
    }

    const handleLogin = (e) => {
        e.preventDefault();
        if(usernameState === localStorage.getItem("Username") && passwordState === localStorage.getItem("Password")){
            props.successful();
        } else {
            props.failed();
        }
    }

    return (
        <div>
            <CssBaseline />
            <main className="layout">
                <Paper className="paper">
                    <Avatar className="avatar">
                        <LockIcon />
                    </Avatar>
                    <Typography variant="h2">Sign in</Typography>
                    <form className="form" onSubmit={handleLogin}>
                        <FormControl margin="normal" required fullWidth>
                            <InputLabel htmlFor="email">Email Address</InputLabel>
                            <Input
                                onChange={handleUsernameChange}
                                id="email"
                                name="email"
                                autoComplete="email"
                                autoFocus
                            />
                        </FormControl>
                        <FormControl margin="normal" required fullWidth>
                            <InputLabel htmlFor="password">Password</InputLabel>
                            <Input
                                name="password"
                                type="password"
                                id="password"
                                autoComplete="current-password"
                                onChange={handlePasswordChange}
                            />
                        </FormControl>
                         <Button
                            type="submit"
                            fullWidth
                            variant="contained"
                            color="primary"
                            className="submit"
                         >
                            Sign in
                         </Button>
                    </form>
                </Paper>
            </main>
        </div>
    );
}