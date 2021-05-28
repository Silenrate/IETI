import React from 'react';
import { Link } from 'react-router-dom';
import { SearchModal } from './../searchModal';

export const Navbar = () => {

	let publicUrl = process.env.PUBLIC_URL + '/'
	let imgattr = 'logo'

	return (
		<nav className="navbar navbar-area navbar-expand-lg nav-style-01 viaje-go-top">
			<div className="container nav-container">
				<div className="responsive-mobile-menu">
					<div className="mobile-logo">
						<Link to="/">
							<img src={publicUrl + "assets/img/mainLogo.png"} alt={imgattr} />
						</Link>
					</div>
					<button className="navbar-toggler float-right" type="button" data-toggle="collapse" data-target="#tp_main_menu" aria-expanded="false" aria-label="Toggle navigation">
						<span className="navbar-toggle-icon">
							<span className="line" />
							<span className="line" />
							<span className="line" />
						</span>
					</button>
					<div className="nav-right-content">
						<ul className="pl-0">
							<li className="top-bar-btn-booking">
								<Link className="btn btn-yellow" to="/">Book Now <i className="fa fa-paper-plane" /></Link>
							</li>
							<li className="search" >
								<SearchModal />
							</li>
							<li className="notification">
								<a className="signUp-btn" href="#notification">
									<i className="fa fa-user-o" />
								</a>
							</li>
						</ul>
					</div>
				</div>
				<div className="collapse navbar-collapse" id="tp_main_menu">
					<div className="logo-wrapper desktop-logo">
						<Link to="/" className="main-logo">
							<img src={publicUrl + "assets/img/mainLogo.png"} alt="logo" />
						</Link>
						<Link to="/" className="sticky-logo">
							<img src={publicUrl + "assets/img/mainLogo.png"} alt="logo" />
						</Link>
					</div>
					<ul className="navbar-nav">
						<li>
							<Link to="/">Home</Link>
						</li>
						<li>
							<Link to="/about">About Us</Link>
						</li>
						{/*
			        <li className="menu-item-has-children">
			          <a href="#">Pages</a>
			          <ul className="sub-menu">
			            <li><Link to="/tour-list">Tours List</Link></li>
			            <li><Link to="/tour-list-v2">Tours List 02</Link></li>
			            <li><Link to="/tour-list-v3">Tours List 03</Link></li>
			            <li><Link to="/tour-details">Tours Details</Link></li>
			            <li><Link to="/destination-list">Destination List</Link></li>
			            <li><Link to="/destination-list-v2">Destination List 2</Link></li>
			            <li><Link to="/destination-details">Destination Details</Link></li>
			            <li><Link to="/gallery">Gallery</Link></li>
			            <li><Link to="/gallery-details">Gallery Details</Link></li>
			            <li><Link to="/comming-soon">Comming soon</Link></li>
			            <li><Link to="/error">404</Link></li>
			            <li><Link to="/faq">FAQ</Link></li>
			            <li><Link to="/user-profile">User Profile</Link></li>
			          </ul>
				</li>
				*/}
						<li>
							<Link to="/contact">Contact</Link>
						</li>
						<li hidden={localStorage.getItem("isLoggedIn")}>
							<Link to="/login">Log In / Sign Up</Link>
						</li>
						<li hidden={!localStorage.getItem("isLoggedIn")}>
							<Link to="/profile">Profile</Link>
						</li>
						<li hidden={!localStorage.getItem("isLoggedIn")}>
							<Link to="/create">New Lodging</Link>
						</li>
					</ul>
				</div>
				<div className="nav-right-content">
					<ul>
						<li>
							<Link className="btn btn-yellow" to="/">Book Now <i className="fa fa-paper-plane" /></Link>
						</li>
						<li >
							<SearchModal />
						</li>
					</ul>

				</div>
			</div>
		</nav>
	)
}