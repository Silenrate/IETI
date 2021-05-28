import React, { useState , useEffect } from 'react';
import Drawer from '@material-ui/core/Drawer';
import Button from '@material-ui/core/Button';
import List from '@material-ui/core/List';
import Divider from '@material-ui/core/Divider';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import PersonIcon from '@material-ui/icons/Person';
import ExitToAppIcon from '@material-ui/icons/ExitToApp';
import {FilterModal} from './FilterModal';
import './NavBar.css';

export const NavBar = (props) => {

    const [state, setState] = useState({
        top: false,
        left: false,
        bottom: false,
        right: false,
    });

    const [openModalState, setOpenModalState] = useState(false);

    const handleOpenModal = () => {
        setOpenModalState(true);
    };

    const handleCloseModal = () => {
        setOpenModalState(false);
    };

    const toggleDrawer = (anchor, open) => (event) => {
        if (event.type === 'keydown' && (event.key === 'Tab' || event.key === 'Shift')) {
            return;
        }
        setState({ ...state, [anchor]: open });
    };

    const handleUserProfileView = () => {
        window.location.href = "/update";
    };

    const usersPageHandler = () => {
        window.location.href = "/users";
    }

    const list = (anchor) => (
            <div
                className="list"
                role="presentation"
                onClick={toggleDrawer(anchor, false)}
                onKeyDown={toggleDrawer(anchor, false)}>
                <List>
                    <ListItem button onClick={handleUserProfileView}>
                        <ListItemIcon>
                            <PersonIcon style={{ fontSize: 50 }}/>
                        </ListItemIcon>
                        <ListItemText
                            primary={props.userData.fullName}
                            secondary={props.userData.username}
                        />
                    </ListItem>
                </List>
                <Divider />
                <List>
                    <ListItem onClick={props.logout}button>
                        <ListItemIcon>
                            <ExitToAppIcon style={{ fontSize: 50 }}/>
                        </ListItemIcon>
                        <ListItemText
                            primary="Log Out"
                        />
                    </ListItem>
                </List>
            </div>
    );

    return (
        <div>
            <div>
                <Button onClick={toggleDrawer("left", true)}>Menu</Button>
                <Button onClick={usersPageHandler}>Users</Button>
                <Button onClick={handleOpenModal}>
                    Filter
                </Button>
                <FilterModal open={openModalState} closeAction={handleCloseModal} applyFilters={props.applyFilters}/>
            </div>
            <Drawer anchor={"left"} open={state["left"]} onClose={toggleDrawer("left", false)}>
                {list("left")}
            </Drawer>
        </div>
    );
}