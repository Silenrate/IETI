import React, { useState } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Drawer from '@material-ui/core/Drawer';
import Button from '@material-ui/core/Button';
import List from '@material-ui/core/List';
import Divider from '@material-ui/core/Divider';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import PersonIcon from '@material-ui/icons/Person';
import ExitToAppIcon from '@material-ui/icons/ExitToApp';
import Typography from '@material-ui/core/Typography';
import './NavBar.css';

export const NavBar = (props) => {

    const [state, setState] = useState({
        top: false,
        left: false,
        bottom: false,
        right: false,
    });

    const toggleDrawer = (anchor, open) => (event) => {
        if (event.type === 'keydown' && (event.key === 'Tab' || event.key === 'Shift')) {
            return;
        }
        setState({ ...state, [anchor]: open });
    };

    const list = (anchor) => (
        <div
            className="list"
            role="presentation"
            onClick={toggleDrawer(anchor, false)}
            onKeyDown={toggleDrawer(anchor, false)}>
            <List>
                <ListItem button>
                    <ListItemIcon>
                        <PersonIcon style={{ fontSize: 50 }}/>
                    </ListItemIcon>
                    <ListItemText
                        primary="Daniel Walteros"
                        secondary={
                            <div>
                                <Typography
                                    component="span"
                                    variant="body2"
                                    color="textPrimary"
                                >{props.email}
                                </Typography>
                            </div>
                    }/>
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
            <Button onClick={toggleDrawer("left", true)}>Menu</Button>
            <Drawer anchor={"left"} open={state["left"]} onClose={toggleDrawer("left", false)}>
                {list("left")}
            </Drawer>
        </div>
    );
}