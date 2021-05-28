import { Box } from '@material-ui/core'
import React, { useEffect } from 'react'
import { BookingCard } from './BookingCard';

export const BookingResult = (props) => {

    
    return (
        <div>
            <Box justifyContent="center"
                flexDirection="row"
                alignContent="flex-start"
                display="flex"
                flexWrap="wrap"
            >
                {props.items.map(item => (

                    <BookingCard 
                        showReservation={props.showReservation}
                        showChat={props.showChat}
                        key={item.place.id}
                        id={item.place.id}
                        name={item.place.name}
                        image={item.place.urlImage}
                        city={item.place.city}
                        departament={item.place.department}
                        calification={item.place.calification}
                        description={item.place.description}
                        owner={item.place.owner}
                        channelId={item.place.owner+"-"+item.userId}
                        showOwner={props.showOwner}
                        showEdit={props.showEdit} 
                        initialDate={item.initialDate}
                        finalDate={item.finalDate}/>
                ))}
            </Box>

        </div>
    )
}
