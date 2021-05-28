import React, { useState, useEffect } from 'react';
import { BookingsList } from './../BookingsList';
import { PlacesList } from './../PlacesList';
import Swal from 'sweetalert2';
import axios from 'axios';
import { BooksToMe } from '../BooksToMe';

export const UserProfile = () => {

	let publicUrl = process.env.PUBLIC_URL + '/';

	let oldEmail = localStorage.getItem('emailUser');

	const [user, setuser] = useState({
		name: "",
		email: "",
		phoneNumber: "",
		gender: ""
	})

	const [newuser, setNewUser] = useState({
		id: "",
		name: "",
		email: "",
		phoneNumber: "",
		gender: "",
		places: [],
		books: [],
		chats: []
	})

	useEffect(() => {
		async function fetchMyAPI() {

			let email = await localStorage.getItem('emailUser');

			let response = await fetch("https://enfiry-back-end.herokuapp.com/api/v1/users/" + email)
			response = await response.json();
			setuser(response);
			setNewUser(response);
		}
		fetchMyAPI()

	}, [])

	const handleOnChange = (e) => {
		const { name, value } = e.target;
		const userNew = { ...newuser, [name]: value };
		console.log(userNew);
		setNewUser(userNew);
	}

	const handleSubmit = (e) => {

		e.preventDefault()

		axios.put("https://enfiry-back-end.herokuapp.com/api/v1/users/" + oldEmail, newuser)
			.then(response => {
				Swal.fire({
					title: 'Yeah!',
					text: 'User Updated',
					timer: 2000,
					timerProgressBar: false,
					icon: 'success',
					showConfirmButton: false
				});
				console.log(response.data);
				console.log("hola", oldEmail + newuser)
			}).catch(error => {
				console.log(error);
				Swal.fire({
					title: 'Refused!',
					text: 'Continue',
					icon: 'error',
					confirmButtonText: 'Cool'
				});
			});

	}

	const logOut = (e) => {
		localStorage.removeItem('isLoggedIn');
		localStorage.removeItem('Authentication');
		localStorage.removeItem('idUser');
		window.location.href = "/profile";
	}

	return (
		<div className="user-profile-area pd-top-120">
			<div className="container">
				<div className="row">
					<div className="col-xl-10 col-lg-12">
						<div className="row">
							<div className="col-lg-4">
								<ul className="nav nav-tabs tp-tabs style-two">
									<li className="nav-item">
										<a className="nav-link active" data-toggle="tab" href="#tabs_1"><i className="fa fa-user" />Profile</a>
									</li>
									<li className="nav-item">
										<a className="nav-link" data-toggle="tab" href="#tabs_3"><i className="fa fa-cog" />Edit Profile</a>
									</li>
									<li className="nav-item">
										<a className="nav-link" data-toggle="tab" href="#tabs_7"><i className="fa fa-user" />Your Places</a>
									</li>
									<li className="nav-item">
										<a className="nav-link" data-toggle="tab" href="#tabs_8"><i className="fa fa-user" />Your Bookings</a>
									</li>
									<li className="nav-item">
										<a className="nav-link" data-toggle="tab" href="#tabs_9"><i className="fa fa-user" />Books to me</a>
									</li>
									<li className="text-center">
										<a className="btn btn-yellow" href="#text-center" onClick={logOut}><i className="fa fa-sign-in" aria-hidden="true" /> <span>Log Out</span></a>
									</li>
								</ul>
							</div>
							<div className="col">
								<div className="tab-content user-tab-content">
									<div className="tab-pane fade show active" id="tabs_1">
										<div className="user-details">
											<h3 className="user-details-title">Your Profile</h3>
											<div className="tp-img-upload">
											</div>
											<form className="tp-form-wrap">
												<div className="row">
													<div className="col-md-6">
														<label className="single-input-wrap style-two">
															<span className="single-input-title">Your Name</span>
															<input type="text" value={user.name} />
														</label>
													</div>
													<div className="col-md-6">
														<label className="single-input-wrap style-two">
															<span className="single-input-title">Your Phone</span>
															<input type="text" value={user.phoneNumber} />
														</label>
													</div>
													<div className="col-md-6">
														<label className="single-input-wrap style-two">
															<span className="single-input-title">Your Email Address</span>
															<input type="text" value={user.email} />
														</label>
													</div>
													<div className="col-md-6">
														<label className="single-input-wrap style-two">
															<span className="single-input-title">Your Gender</span>
															<input type="text" value={user.gender} />
														</label>
													</div>
												</div>
											</form>
										</div>
									</div>
									<div className="tab-pane fade" id="tabs_3">
										<div className="user-settings">
											<h3 className="user-details-title">Edit Your Profile</h3>
											<form className="tp-form-wrap" onSubmit={handleSubmit}>
												<div className="row">
													<div className="col-md-6">
														<label className="single-input-wrap style-two">
															<span className="single-input-title">Change your Name</span>
															<input type="text" name="name" value={newuser.name} onChange={handleOnChange} required />
														</label>
													</div>
													<div className="col-md-6">
														<label className="single-input-wrap style-two">
															<span className="single-input-title">Change your Phone</span>
															<input type="text" name="phoneNumber" value={newuser.phoneNumber} onChange={handleOnChange} required />
														</label>
													</div>
													<div className="col-md-6">
														<label className="single-input-wrap style-two">
															<span className="single-input-title">Change your Email Address</span>
															<input type="text" name="email" value={newuser.email} onChange={handleOnChange} required />
														</label>
													</div>
													<div className="col-md-6">
														<label className="single-input-wrap style-two">
															<span className="single-input-title"> Change your Gender</span>
															<input type="text" name="gender" value={newuser.gender} onChange={handleOnChange} required />
														</label>
													</div>
													<div className="col-12">
														<button className="btn btn-yellow mt-3 text-center">Editar</button>
													</div>
												</div>
											</form>
											<br></br>

										</div>
									</div>
									<div className="tab-pane fade" id="tabs_7">
										<PlacesList />
									</div>
									<div className="tab-pane fade" id="tabs_8">
										<BookingsList />
									</div>

									<div className="tab-pane fade" id="tabs_9">
										<BooksToMe/>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	)
}