import React from 'react'
import { ReviewCard } from './ReviewCard';

export const ReviewView = (props) => {


    return (
        <div>
                {props.reviews.map(review => (
                    <ReviewCard 
                    key = {review.id}
                    review={review}
                    />
                ))}
        </div>
    )
}
