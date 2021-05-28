import React from 'react';
import Typography from '@material-ui/core/Typography';
import Box from '@material-ui/core/Box';
import { UserCard } from './UserCard';

export const UsersList = (props) => {
    let results = (
        <Typography variant="h3" style={{ textAlign: "center" }}>Loading...</Typography>
    );
    if (props.list.length > 0) {
        results = props.list.map(user =>
        (<UserCard
            key={user.id}
            id={user.id}
            email={user.email}
            name={user.name}
            password={user.password}
        />));
    }
    return (
        <div>
            <Box justifyContent="center"
                alignItems="flex-start"
                display="flex"
                >
                {results}
            </Box>
        </div>
    );
};