import React from 'react';
import Box from '@material-ui/core/Box';
import Typography from '@material-ui/core/Typography';
import { Task } from './Task';

export const TaskList = (props) => {

    let results = (
        <Typography variant="h3" style={{ textAlign: "center" }}>Loading...</Typography>
    );

    if (props.items.length > 0) {
        results = props.items.map(item =>
        (<Task key={item.id}
            description={item.description}
            responsible={item.responsible}
            status={item.status}
            dueDate={item.dueDate}
            fileUrl = {item.fileUrl}
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