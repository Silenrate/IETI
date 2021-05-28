import React, { useState } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import FormControl from '@material-ui/core/FormControl';
import Input from '@material-ui/core/Input';
import InputLabel from '@material-ui/core/InputLabel';
import Typography from '@material-ui/core/Typography';
import CheckRoundedIcon from '@material-ui/icons/CheckRounded';
import CloseRoundedIcon from '@material-ui/icons/CloseRounded';
import Fab from '@material-ui/core/Fab';
import AddIcon from '@material-ui/icons/Add';
import Select from '@material-ui/core/Select';
import MenuItem from '@material-ui/core/MenuItem';
import TextField from '@material-ui/core/TextField';
import AdapterDateFns from '@material-ui/lab/AdapterDateFns';
import LocalizationProvider from '@material-ui/lab/LocalizationProvider';
import DatePicker from '@material-ui/lab/DatePicker';
import Dialog from '@material-ui/core/Dialog';
import DialogContent from '@material-ui/core/DialogContent';
import DialogTitle from '@material-ui/core/DialogTitle';

const useStyles = makeStyles((theme) => ({
    root: {
        '& > *': {
            margin: theme.spacing(3),
        },
    }
}));

export const NewTask = (props) => {

    const classes = useStyles();

    const [openState, setOpenState] = useState(false);
    const [descriptionState, setDescriptionState] = useState("");
    const [responsibleState, setResponsibleState] = useState("");
    const [emailState, setEmailState] = useState("");
    const [statusState, setStatusState] = useState("");
    const [dueDateState, setDueDateState] = useState(null);

    const handleOpenDialog = () => {
        setOpenState(true);
    };

    const handleCloseDialog = () => {
        setOpenState(false);
    };

    const handleDescriptionChange = (e) => {
        setDescriptionState(e.target.value);
    }

    const handleResponsibleChange = (e) => {
        setResponsibleState(e.target.value);
    }

    const handleEmailChange = (e) => {
        setEmailState(e.target.value);
    }

    const handleStatusChange = (e) => {
        setStatusState(e.target.value);
    }

    const handleDueDateChange = (date) => {
        setDueDateState(date.getTime());
    }

    const handleAdd = (e) => {
        e.preventDefault();
        if (dueDateState === null || descriptionState === "" || statusState === "" || responsibleState === "") {
            alert("There Is Some Empty Fields.");
        } else {
            const newTask = {
                "description": descriptionState,
                "responsible": {
                    "name": responsibleState,
                    "email": emailState
                },
                "status": statusState,
                "dueDate": dueDateState
            };
            props.addTask(newTask);
            setOpenState(false);
        }
    }

    return (
        <div>
            <div style={{ textAlign: "left", padding: "15px" }}>
                <Fab color="primary" aria-label="add" onClick={handleOpenDialog}>
                    <AddIcon />
                </Fab>
            </div>
            <Dialog open={openState} onClose={handleCloseDialog} aria-labelledby="form-dialog-title">
                <DialogTitle id="form-dialog-title" disableTypography>
                    <Typography variant="h3" style={{ textAlign: "center" }}>New Task</Typography>
                </DialogTitle>
                <DialogContent>
                    <form className="form">
                        <FormControl margin="normal" required fullWidth>
                            <InputLabel htmlFor="description">Description</InputLabel>
                            <Input
                                onChange={handleDescriptionChange}
                                id="description"
                                name="description"
                                autoComplete="description"
                                autoFocus
                            />
                        </FormControl>
                        <FormControl margin="normal" required fullWidth>
                            <InputLabel htmlFor="responsible">Responsible</InputLabel>
                            <Input
                                onChange={handleResponsibleChange}
                                id="responsible"
                                name="responsible"
                                autoComplete="responsible"
                                autoFocus
                            />
                        </FormControl>
                        <FormControl margin="normal" required fullWidth>
                            <InputLabel htmlFor="description">Email</InputLabel>
                            <Input
                                onChange={handleEmailChange}
                                id="email"
                                name="email"
                                autoComplete="email"
                                autoFocus
                            />
                        </FormControl>
                        <FormControl margin="normal" required fullWidth>
                            <InputLabel htmlFor="status">Status</InputLabel>
                            <Select
                                labelId="status"
                                id="status"
                                value={statusState}
                                onChange={handleStatusChange}
                            >
                                <MenuItem value={"ready"}>Ready</MenuItem>
                                <MenuItem value={"in progress"}>In Progress</MenuItem>
                                <MenuItem value={"done"}>Done</MenuItem>
                            </Select>
                        </FormControl>
                        <FormControl margin="normal" required fullWidth>
                            <LocalizationProvider dateAdapter={AdapterDateFns}>
                                <DatePicker
                                    label="dueDate"
                                    value={dueDateState}
                                    onChange={handleDueDateChange}
                                    renderInput={(params) => <TextField {...params} />}
                                />
                            </LocalizationProvider>
                        </FormControl>
                        <FormControl margin="normal" required fullWidth>
                            <div className={classes.root} style={{ textAlign: "center" }}>
                                <Fab style={{ backgroundColor: "red" }} aria-label="Add" onClick={handleCloseDialog}>
                                    <CloseRoundedIcon />
                                </Fab>
                                <Fab style={{ backgroundColor: "green" }} aria-label="Cancel" onClick={handleAdd}>
                                    <CheckRoundedIcon />
                                </Fab>
                            </div>
                        </FormControl>
                    </form>
                </DialogContent>
            </Dialog>
        </div>
    );
};