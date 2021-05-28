import { Avatar, Button, FormControl, FormControlLabel, FormLabel, Grid, Paper, Radio, RadioGroup, Typography } from '@material-ui/core';
import TextField from '@material-ui/core/TextField';
import React, { useState } from 'react';
import AddCircleOutlineIcon from '@material-ui/icons/AddCircleOutline';
import Swal from 'sweetalert2';
import axios from 'axios';
import { makeStyles } from '@material-ui/core/styles';

const useStyles = makeStyles((theme) => ({
    root: {
        '& .MuiTextField-root': {
            margin: theme.spacing(1),
            width: 200,
        },
    },
}));

export const RegisterView = () => {

    const classes = useStyles();

    const paperStyle = { padding: '30px 20px', width: 310, margin: "0px auto" };

    const headerStyle = { margin: 0 };

    const avatarStyle = { backgroundColor: '#1bbd7e' }

    const [user, setuser] = useState({
        name: '',
        email: '',
        gender: '',
        phoneNumber: '',
        password: '',
        confirmPassword: ''
    })

    const handleChange = (e) => {
        const { name, value } = e.target;
        let newUser = { ...user, [name]: value }
        setuser(newUser)
    }

    const postUser = (newUser) => {
        axios.post("https://enfiry-back-end.herokuapp.com/api/v1/users", newUser)
            .then(response => {
                console.log("Success")
                Swal.fire({
                    title: 'Yeah!',
                    text: 'Register successful',
                    timer: 2000,
                    timerProgressBar: false,
                    icon: 'success',
                    showConfirmButton: false
                })
            }).catch(error => {
                alert("Fallo de Conexión con DB");
            });

    }
    const onSubmit = (e) => {
        e.preventDefault();
        if (user.password === user.confirmPassword) {
            postUser(user)
        }
        setuser({
            name: '',
            email: '',
            gender: '',
            phoneNumber: '',
            password: '',
            confirmPassword: ''
        }
        )
    }

    const validateEmail = (email) => {
        var re = /\S+@\S+\.\S+/;
        const rta = (re.test(email) || email === "");
        return rta;
    }

    return (
        <Grid>
            <Paper style={paperStyle}>
                <Grid align="center">
                    <Avatar style={avatarStyle}>
                        <AddCircleOutlineIcon />
                    </Avatar>
                    <h3 style={headerStyle}>Sign up</h3>
                    <Typography
                        variant='caption'
                        gutterBottom
                    >
                        Please fill this form to create an account
                    </Typography>
                </Grid>

                <form className={classes.root} onSubmit={onSubmit}>

                    <FormControl margin="normal" fullWidth>

                        <TextField required type="text" value={user.name} label="Name" name="name" autoFocus onChange={handleChange} variant="standard" />
                    </FormControl>

                    <FormControl margin="normal" required fullWidth>
                        <TextField required
                            error={!validateEmail(user.email)}
                            label="Email"
                            helperText={!validateEmail(user.email) ? "Incorrect email." : ""}
                            name={"email"}
                            value={user.email}
                            onChange={handleChange}
                            variant="standard"
                        />

                    </FormControl>
                    <FormControl margin="normal"  >

                        <TextField required fullWidth type="number" value={user.phoneNumber} label="Phone Number" name="phoneNumber" onChange={handleChange} variant="standard" />
                    </FormControl>

                    <FormControl component="fieldset" style={{ marginTop: 20 }}>
                        <FormLabel component="legend">Gender</FormLabel>
                        <RadioGroup aria-label="gender" name="gender"  >
                            <FormControlLabel value="female" onChange={handleChange} control={<Radio />} label="Female" />
                            <FormControlLabel value="male" onChange={handleChange} control={<Radio />} label="Male" />
                            <FormControlLabel value="other" onChange={handleChange} control={<Radio />} label="Other" />
                        </RadioGroup>
                    </FormControl>

                    <FormControl margin="normal" >
                        <TextField required fullWidth type="password" value={user.password} label="Password" name="password" onChange={handleChange} variant="standard" />
                    </FormControl>

                    <FormControl margin="normal" required fullWidth>
                        <TextField required fullWidth type="password" value={user.confirmPassword} label="Confirm Password" name="confirmPassword" onChange={handleChange} variant="standard" />
                    </FormControl>

                    <Button type='submit' variant='contained' color='primary'>
                        {"Sign up"}
                    </Button>

                </form>
            </Paper>
        </Grid>
    );

}
