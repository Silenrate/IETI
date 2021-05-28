import React, { useState } from 'react';
import Avatar from '@material-ui/core/Avatar';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import FormControl from '@material-ui/core/FormControl';
import Input from '@material-ui/core/Input';
import InputLabel from '@material-ui/core/InputLabel';
import AssignmentIndIcon from '@material-ui/icons/AssignmentInd';
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
                    <Typography variant="h3">Task Planner</Typography>
                    <AssignmentIndIcon style={{ fontSize: 80 }} />
                    <form className="form" onSubmit={handleLogin}>
                        <FormControl margin="normal" required fullWidth>
                            <InputLabel htmlFor="email">Username</InputLabel>
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
                        <FormControl margin="normal" required fullWidth>
                            <Button
                                type="submit"
                                fullWidth
                                variant="contained"
                                color="primary"
                                className="submit"
                            >Log In
                            </Button>
                        </FormControl>
                         <br></br>
                         <Button
                            variant="contained" disabled
                         >Create Account
                         </Button>
                    </form>
                </Paper>
            </main>
        </div>
    );
}