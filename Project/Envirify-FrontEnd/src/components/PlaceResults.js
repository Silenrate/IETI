import React from 'react';
import Box from '@material-ui/core/Box';
import { PlaceCard } from './PlaceCard';


export const PlaceResults = (props) => {

    return (
        <>
            <Box justifyContent="center"
                flexDirection="row"
                alignContent="flex-start"
                display="flex"
                flexWrap="wrap"
                >
                {props.items.map(item => (
                    <PlaceCard showReservation={props.showReservation}
                        key={item.id}
                        id={item.id}
                        name={item.name}
                        city={item.city}
                        department={item.department}
                        calification={item.calification}
                        description={item.description}
                        owner={item.owner} 
                        image={item.urlImage}
                        showOwner={props.showOwner}
                        showEdit={props.showEdit}/>
                ))}
            </Box>
        </>
    );
};