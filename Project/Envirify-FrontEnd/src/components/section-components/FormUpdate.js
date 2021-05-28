import React, { useState } from 'react'

export const FormUpdate = ({ placeInfo, handleOnClick }) => {

    const[place , setPlace] = useState({
        id:placeInfo.id,
        name:placeInfo.name,
        urlImage:placeInfo.image,
        department:placeInfo.department,
        city:placeInfo.city,
        description:placeInfo.description,
        
    });

    const {name,urlImage,department,city,description} = place;

    const handleNameChange = (e) => {
        setPlace({
            ...place,
            name : e.target.value
        });
    }

    const handleDepartmentChange = (e) => {
        setPlace({
            ...place,
            department : e.target.value
        });
    }

    const handleCityChange = (e) => {
        setPlace({
            ...place,
            city : e.target.value
        });
    }

    const handleDescriptionChange = (e) => {
        setPlace({
            ...place,
            description : e.target.value
        });
    }

    const handleImageChange = (e) => {
        setPlace({
            ...place,
            urlImage : e.target.value
        });
    }

    const handleSubmit = () => {
        setPlace({
            id:"",
            name:"",
            urlImage:"",
            department:"",
            city:"",
            description:"",
            
        });
    }


    const handleClick = () => {
        handleOnClick(place);
        handleSubmit();

    }

    return (
        <div>
            <div class="row">
            <div class="col-md-4">
                <label className="single-input-wrap style-two">
                    <span className="single-input-title">Name</span>
                    <input type="text" value={name} onChange={handleNameChange} />                </label>
                </div>
                <div class="col-md-4">
                <label className="single-input-wrap style-two">
                    <span className="single-input-title">Department</span>
                    <input type="text" value={department} onChange={handleDepartmentChange} />
                </label>
                </div>
                <div class="col-md-4">
                <label className="single-input-wrap style-two">
                                <span className="single-input-title">City</span>
                                <input type="text" value={city} onChange={handleCityChange} />
                            </label>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12">
                <label className="single-input-wrap style-two">
                    <span className="single-input-title">Url Image</span>
                    <input type="text" value={urlImage} onChange={handleImageChange} /> </label>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12">
                <label className="single-input-wrap style-two">
                                <span className="single-input-title">Description</span>
                                <textarea value={description} onChange={handleDescriptionChange} />
                            </label>
                </div>

            </div>

            <div style={{ textAlign: "center" }}>
                    <div style={{ width: "50%", display: "inline-block", textAlign: "left" }}>
                        <button className="btn btn-yellow mt-3 text-center" onClick={handleClick}>Submit Changes</button>
                    </div>
                </div>
        </div>
    )
}
