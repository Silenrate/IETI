import { Avatar, Button, FormControl, Grid, Paper, TextField, Typography } from '@material-ui/core';
import React, { useState } from 'react';
import LockOutlinedIcon from '@material-ui/icons/LockOutlined';
import Swal from 'sweetalert2';
import axios from 'axios';
import { Link } from 'react-router-dom';



export const LoginView = ({ handleChange }) => {

    const paperStyle = { padding: 20, height: '70vh', width: 310, margin: "0px auto" }
    const btnStyle = { margin: '8px 0' }
    const avatarStyle = { backgroundColor: '#1bbd7e' }
    const marginTop = { marginTop: 5 }

    const [login, setLogin] = useState({
        email: '',
        password: '',
    })

    const validateEmail = (email) => {
        var re = /\S+@\S+\.\S+/;
        const rta = (re.test(email) || email === "");
        return rta;
    }

    const onSubmit = (e) => {
        e.preventDefault();
        postLogin(login)
        setLogin({
            email: '',
            password: '',
        }
        )
    }

    const handleValueChange = (e) => {
        const { name, value } = e.target;
        let newLogin = { ...login, [name]: value }
        setLogin(newLogin)
    }

    const postLogin = (newLogin) => {
        axios.post("https://enfiry-back-end.herokuapp.com/api/v1/users/login", newLogin)
            .then(response => {
                Swal.fire({
                    title: 'Yeah!',
                    text: 'Login successfull',
                    timer: 2000,
                    timerProgressBar: false,
                    icon: 'success',
                    showConfirmButton: false
                });
                localStorage.setItem('isLoggedIn', true);
                localStorage.setItem('Authentication', response.data.jwt);
                localStorage.setItem('idUser', response.data.id);
                localStorage.setItem('emailUser', response.data.email);
                window.location.href = "/profile";

            }).catch(error => {
                Swal.fire({
                    title: 'Bad credentials!',
                    text: 'Continue',
                    icon: 'error',
                    confirmButtonText: 'Cool'
                });
            });
    }

    return (
        <div>
            <Grid>
                <Paper style={paperStyle}>
                    <Grid align="center">
                        <Avatar style={avatarStyle}><LockOutlinedIcon /></Avatar>
                        <h3>Sign in</h3>
                    </Grid>
                    <FormControl margin="normal" required fullWidth>
                        <TextField required
                            error={!validateEmail(login.email)}
                            label="Email"
                            helperText={!validateEmail(login.email) ? "Incorrect email." : ""}
                            name={"email"}
                            value={login.email}
                            onChange={handleValueChange}
                            variant="standard"
                        />
                    </FormControl>
                    <br />
                    <TextField
                        label='Password'
                        type='password'
                        name={"password"}
                        fullWidth required
                        style={marginTop}
                        onChange={handleValueChange}
                        variant="standard"
                    />
                    <Button
                        type='submit'
                        color='primary'
                        style={btnStyle}
                        variant='contained'
                        fullWidth required
                        onClick={onSubmit}
                    >
                        Sign in
                </Button>
                    <Typography align="left">
                        <Link to="/login">
                            Forgot password?
                </Link>
                    </Typography >
                    <Typography align="left"> Don't you have an account? &nbsp;
                <Link href="#" to="/login" style={{ color: 'blue' }} onClick={() => handleChange("event", 1)}>
                            Sign up
                </Link>
                    </Typography>
                </Paper>
            </Grid>
        </div>
    );
}