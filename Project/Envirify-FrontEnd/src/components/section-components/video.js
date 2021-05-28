import React from 'react';

export const Video = () => {

	let publicUrl = process.env.PUBLIC_URL + '/'

	return (
		<div className="video-area tp-video-area pd-top-110" style={{ backgroundImage: 'url(' + publicUrl + 'assets/img/bg/7.png)' }}>
			<div className="container viaje-go-top">
				<div className="row">
					<div className="col-xl-5 col-lg-6 align-self-center wow animated fadeInRight" data-wow-duration="1s" data-wow-delay="0.3s">
						<div className="section-title mb-lg-0 mb-4 text-center text-lg-left">
							<h2 className="title">About US</h2>
							<p>The perfect place to find sustainable accommodation and live with the different local cultures, if you have a rural home, don't wait to publish it and earn money.</p>
						</div>
					</div>
					<div className="col-xl-5 col-lg-6 offset-xl-1 wow animated fadeInLeft" data-wow-duration="1s" data-wow-delay="0.3s">
						<div className="video-popup-wrap">
							<div>
								<img src={publicUrl + "assets/img/vid.png"} alt="video" />
							</div>
							<div className="video-popup-btn">
								<a href="https://www.youtube.com/watch?v=tcniZK-jYHY" className="video-play-btn mfp-iframe"><i className="fa fa-play" /></a>
							</div>
						</div>
						<br></br>
					</div>
				</div>
			</div>
		</div>
	)
}

