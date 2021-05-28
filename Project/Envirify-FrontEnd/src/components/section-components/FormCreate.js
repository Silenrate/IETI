import React, { useEffect, useState } from 'react'
import Swal from 'sweetalert2'
import axios from 'axios'
import MenuItem from '@material-ui/core/MenuItem';
import InputLabel from '@material-ui/core/InputLabel';
import Select from '@material-ui/core/Select';
import { storage } from '../firebase'


export const FormCreate = () => {

    const [place, setplace] = useState({
        name: "",
        department: "",
        city: "",
        direction: "",
        description: "",
        urlImage: "",
        capacity: 0,
        habitations: 0,
        bathrooms: 0
    })

    const headers = {
        'X-Email': localStorage.getItem("emailUser")
    }


    const handleChange = (e) => {
        const { name, value } = e.target;
        let newPlace = { ...place, [name]: value }
        console.log(newPlace)
        setplace(newPlace)
    }

    const postPlace = (place) => {
        console.log("place", place)
        axios.post("https://enfiry-back-end.herokuapp.com/api/v1/places", place, {
            headers: headers
        })
            .then(response => {

                Swal.fire(
                    'Created!',
                    'Your lodging has been created',
                    'success'
                ).then(function () {
                    window.location.href = "/"
                })


            }).catch(error => {
                alert("Fallo de Conexión con DB");
            });
    }

    const onSubmit = (e) => {
        e.preventDefault();

        Swal.fire({
            title: 'Confirm',
            icon: 'question',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, create it!'
        }).then((result) => {
            if (result.isConfirmed) {
                postPlace(place)
            }
        })

        setplace({
            name: "",
            department: "",
            city: "",
            direction: "",
            description: "",
            urlImage: "",
            capacity: 0,
            habitations: 0,
            bathrooms: 0
        })
    }

    const [image, setimage] = useState(null)

    const handleImageChange = (e) => {
        if (e.target.files[0]) {
            setimage(e.target.files[0])

        }
    }

    const handleUpload = async () => {
        const uploadTask = storage.ref(`images/${image.name}`).put(image);
        await uploadTask.on(
            "state_changed",
            snapshot => { },
            error => {
                console.log(error)
            },
            () => {
                storage
                    .ref("images")
                    .child(image.name)
                    .getDownloadURL()
                    .then(url => {
                        let newPlace = { ...place, ["urlImage"]: url }
                        setplace(newPlace)
                        Swal.fire({
                            title: 'Success',
                            icon: 'success',
                            confirmButtonColor: '#3085d6',
                            confirmButtonText: 'Ok'
                        })
                    })
            }
        )

    }

    const [Departments, setDepartments] = useState([])
    useEffect(() => {
        axios.get("https://raw.githubusercontent.com/marcovega/colombia-json/master/colombia.json")
        .then(res => {
                setDepartments(res.data);
            }).catch(error => {
                const response = error.response;
                if(response.status === 404){
                    const message = response.data
                } else {
                    alert("Fallo de Conexión con el BackEnd");
                }
            });
    }, [])

   const [cities, setcities] = useState([])

    const handleChangeSelect = (e) =>{
        let newPlace = { ...place, ["department"]: e.target.value }
        setplace(newPlace)
        setcities(Departments.filter(item => item.departamento===e.target.value)[0].ciudades)
    }

    const handleChangeCity = (e) =>{
        let newPlace = { ...place, ["city"]: e.target.value }
        setplace(newPlace)
    }

    return (
        <div>
            <div style={{ textAlign: "center" }}>
                <form style={{ width: "50%", display: "inline-block", textAlign: "left" }}>
                    <div className="row">
                        <div className="col-md-6">
                            <label className="single-input-wrap style-two">
                                <span className="single-input-title">Name</span>
                                <input type="text" name="name" value={place.name} onChange={handleChange} />
                            </label>
                        </div>
                        <div className="col-md-6">
                           
                            <InputLabel id="demo-simple-select-label">Department</InputLabel>
                            <Select
                            labelId="demo-simple-select-label"
                            id="demo-simple-select"
                            onChange={handleChangeSelect}
                            style={{ minWidth:"100%"}}
                            >
                                {
                                    Departments.map(i=><MenuItem value={i.departamento}>{i.departamento}</MenuItem>)

                                }
                           
                            </Select>
                                
                           
                        </div>
                        <div className="col-md-6">
                        <InputLabel id="demo-simple-select-label">City</InputLabel>
                            <Select
                            labelId="demo-simple-select-label"
                            id="demo-simple-select"
                            style={{ minWidth:"100%"}}
                            onChange={handleChangeCity}
                            >
                                {
                                    cities.map(i=><MenuItem value={i}>{i}</MenuItem>)

                                }
                           
                            </Select>
                        </div>
                        <div className="col-md-6">
                            <label className="single-input-wrap style-two">
                                <span className="single-input-title">Direction</span>
                                <input type="text" name="direction" value={place.direction} onChange={handleChange} />
                            </label>
                        </div>
                        <div className="col-md-4">
                            <label className="single-input-wrap style-two">
                                <span className="single-input-title">Capacity</span>
                                <input type="number" name="capacity" value={place.capacity} onChange={handleChange} />
                            </label>
                        </div>
                        <div className="col-md-4">
                            <label className="single-input-wrap style-two">
                                <span className="single-input-title"># Habitations</span>
                                <input type="number" name="habitations" value={place.habitations} onChange={handleChange} />
                            </label>
                        </div>
                        <div className="col-md-4">
                            <label className="single-input-wrap style-two">
                                <span className="single-input-title"># Bathrooms</span>
                                <input type="number" name="bathrooms" value={place.bathrooms} onChange={handleChange} />
                            </label>
                        </div>
                        <div className="col-lg-12">
                            <label className="single-input-wrap style-two">
                                <span className="single-input-title">Description</span>
                                <textarea name="description" value={place.description} onChange={handleChange} />
                            </label>
                        </div>


                        <div className="col-lg-12">
                            <label className="single-input-wrap style-two">
                                <span className="single-input-title">Image</span>
                                <input type="file" onChange={handleImageChange} />

                            </label>
                        </div>

                    </div>
                </form>

                <div style={{ textAlign: "center" }}>
                    <div style={{ width: "50%", display: "inline-block", textAlign: "left" }}>
                        <button className="btn btn-yellow mt-3 text-center" onClick={handleUpload}>Upload image</button>
                        <br></br>
                        <button className="btn btn-yellow mt-3 text-center" onClick={onSubmit}>Add</button>
                    </div>
                </div>

            </div>

        </div>
    )
}