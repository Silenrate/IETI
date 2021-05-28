import React, { useState } from 'react';
import Button from '@material-ui/core/Button';
import Modal from '@material-ui/core/Modal';
import Backdrop from '@material-ui/core/Backdrop';
import Fade from '@material-ui/core/Fade';
import { makeStyles } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';
import Typography from '@material-ui/core/Typography';
import FormControl from '@material-ui/core/FormControl';
import AdapterDateFns from '@material-ui/lab/AdapterDateFns';
import LocalizationProvider from '@material-ui/lab/LocalizationProvider';
import DatePicker from '@material-ui/lab/DatePicker';

const useStyles = makeStyles((theme) => ({
    modal: {
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
    },
    paper: {
        backgroundColor: theme.palette.background.paper,
        border: '2px solid #000',
        boxShadow: theme.shadows[5],
        padding: theme.spacing(2, 4, 3),
    },
}));

export const ReservationModal = (props) => {

    const classes = useStyles();

    const [openModal, setOpenModal] = useState(false);
    const [startDate, setStartDate] = useState(null);
    const [finishDate, setFinishDate] = useState(null);

    const openModalHandler = () => {
        setOpenModal(true);
    };

    const closeModalHandler = () => {
        setOpenModal(false);
    };

    const changeStartDateHandler = (date) => {
        setStartDate(date);
    };

    const changeFinishDateHandler = (date) => {
        setFinishDate(date);
    };

    const sumbitHandler = () => {
        if (startDate === null || finishDate === null) {
            alert("Hay parametros faltantes");
        } else {
            props.sumbitBook(startDate, finishDate);
            closeModalHandler();
        }
    }

    return (
        <div>
            <Button variant="contained" color="primary" onClick={openModalHandler}>
                Make Reservation
            </Button>
            <Modal
                aria-labelledby="transition-modal-title"
                aria-describedby="transition-modal-description"
                className={classes.modal}
                open={openModal}
                onClose={closeModalHandler}
                closeAfterTransition
                BackdropComponent={Backdrop}
                BackdropProps={{
                    timeout: 500,
                }}
            >
                <Fade in={openModal}>
                    <div className={classes.paper}>
                        <Typography variant="h4" >Make A Reservation</Typography>
                        <br></br>
                        <form className="form">
                            <FormControl margin="normal" required fullWidth>
                                <Typography variant="h5" >Start Date</Typography>
                                <LocalizationProvider dateAdapter={AdapterDateFns}>
                                    <DatePicker
                                        value={startDate}
                                        onChange={changeStartDateHandler}
                                        renderInput={(params) => <TextField {...params} />}
                                    />
                                </LocalizationProvider>
                            </FormControl>
                            <FormControl margin="normal" required fullWidth>
                                <Typography variant="h5" >Finish Date</Typography>
                                <LocalizationProvider dateAdapter={AdapterDateFns}>
                                    <DatePicker
                                        value={finishDate}
                                        onChange={changeFinishDateHandler}
                                        renderInput={(params) => <TextField {...params} />}
                                    />
                                </LocalizationProvider>
                            </FormControl>
                        </form>
                        <Button variant="contained" color="primary" onClick={sumbitHandler}>
                            Book
                        </Button>
                    </div>
                </Fade>
            </Modal>
        </div>
    );
};