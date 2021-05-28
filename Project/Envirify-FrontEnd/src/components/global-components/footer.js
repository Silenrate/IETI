import React, { useEffect } from 'react';
import { Link } from 'react-router-dom';

export const FooterV1 = () => {

	let publicUrl = process.env.PUBLIC_URL+'/'

	useEffect(() => {
		let publicUrl = process.env.PUBLIC_URL+'/'
        const minscript = document.createElement("script");
        minscript.async = true;
        minscript.src = publicUrl + "assets/js/main.js";

        document.body.appendChild(minscript);
	}, [])

	return (
		<footer className="footer-area" style={{backgroundImage: 'url('+publicUrl+'assets/img/bg/10.png)'}}>
				  <div className="container-fluid">
				    <div className="row" >
				      <div className="col">
				        <div className="footer-widget widget">
				          <div className="about_us_widget">
				            <Link to="/" className="footer-logo"> 
				              <img src={publicUrl+"assets/img/mainLogo2.png"} alt="footer logo" />
				            </Link>
				            <p>The perfect place to find sustainable accommodation and live with the different local cultures, if you have a rural home, don't wait to publish it and earn money.</p>
				          </div>
				        </div>
				      </div>
				      <div className="col">
				        <div className="footer-widget widget ">
				          <div className="widget-contact">
				            <h4 className="widget-title">Contact us</h4>
				            <p>
				              <i className="fa fa-map-marker" /> 
				              <span>AK. 45 No. 205 - 59, Autopista Norte. PBX: +57(1) 668 3600 - Bogotá.</span>
				            </p>
				            <p className="location"> 
				              <i className="fa fa-envelope-o" />
				              <span>envirify@gmail.com</span>
				            </p>
				            <p className="telephone">
				              <i className="fa fa-phone base-color" /> 
				              <span>
				                +57 3007340944
				              </span>
				            </p>
				          </div>
				        </div>
				      </div>
				    </div>
				  </div>
				</footer>
	)
}
