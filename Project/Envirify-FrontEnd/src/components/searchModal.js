import React, { useState } from 'react';
import IconButton from '@material-ui/core/IconButton';
import SearchIcon from '@material-ui/icons/Search';
import { makeStyles } from '@material-ui/core/styles';
import Modal from '@material-ui/core/Modal';
import Backdrop from '@material-ui/core/Backdrop';
import Fade from '@material-ui/core/Fade';
import Input from '@material-ui/core/Input';
import { Link } from 'react-router-dom';

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

export const SearchModal = (props) => {

    const classes = useStyles();

    const [openModal, setOpenModal] = useState(false);

    const [place, setPlace] = useState("");

    const openModalHandler = () => {
        setOpenModal(true);
    };

    const closeModalHandler = () => {
        setOpenModal(false);
    };

    const placeChangeHandler = (e) => {
        setPlace(e.target.value);
    }

    return (
        <div>
            <IconButton className="materialIcon" type="submit" aria-label="search" onClick={openModalHandler}>
                <SearchIcon />
            </IconButton>
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
                        <form className="form">
                            <Input
                                onChange={placeChangeHandler}
                                id="Search"
                                name="Search"
                                autoComplete="Search"
                                autoFocus
                            ></Input>
                            <Link to={"/search?value=" + place}>
                                <IconButton type="submit" aria-label="search">
                                    <SearchIcon />
                                </IconButton>
                            </Link>
                        </form>
                    </div>
                </Fade>
            </Modal>
        </div>

    );
};
