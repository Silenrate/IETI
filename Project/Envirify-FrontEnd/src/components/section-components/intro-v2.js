import React from 'react';

export const IntroV2 = () => {

	let publicUrl = process.env.PUBLIC_URL + '/'

	return (
		<div className="intro-area pd-top-75">
			<div className="container">
				<div className="row">
					<div className="col-lg-3 col-sm-6 single-intro-two bl-0">
						<div className="single-intro style-two">
							<div className="thumb">
								<img src={publicUrl + "assets/img/icons/9.png"} alt="img" />
							</div>
							<h4 className="intro-title">Discover Cultures</h4>
							<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin ut lorem ipsum. Vestibulum molestie id felis at vehicula. Morbi vel sem pellentesque, posuere lorem laoreet, commodo elit. Mauris placerat, sem in commodo hendrerit, tortor risus accumsan nisl, vel lacinia mi mauris eu ante.</p>
						</div>
					</div>
					<div className="col-lg-3 col-sm-6 single-intro-two">
						<div className="single-intro style-two">
							<div className="thumb">
								<img src={publicUrl + "assets/img/icons/10.png"} alt="img" />
							</div>
							<h4 className="intro-title">Diverse Destinations</h4>
							<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin ut lorem ipsum. Vestibulum molestie id felis at vehicula. Morbi vel sem pellentesque, posuere lorem laoreet, commodo elit. Mauris placerat, sem in commodo hendrerit, tortor risus accumsan nisl, vel lacinia mi mauris eu ante.</p>
						</div>
					</div>
					<div className="col-lg-3 col-sm-6 single-intro-two">
						<div className="single-intro style-two">
							<div className="thumb">
								<img src={publicUrl + "assets/img/icons/11.png"} alt="img" />
							</div>
							<h4 className="intro-title">Great Hotels</h4>
							<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin ut lorem ipsum. Vestibulum molestie id felis at vehicula. Morbi vel sem pellentesque, posuere lorem laoreet, commodo elit. Mauris placerat, sem in commodo hendrerit, tortor risus accumsan nisl, vel lacinia mi mauris eu ante.</p>
						</div>
					</div>
					<div className="col-lg-3 col-sm-6 single-intro-two">
						<div className="single-intro style-two">
							<div className="thumb">
								<img src={publicUrl + "assets/img/icons/12.png"} alt="img" />
							</div>
							<h4 className="intro-title">Fast Booking</h4>
							<p> Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin ut lorem ipsum. Vestibulum molestie id felis at vehicula. Morbi vel sem pellentesque, posuere lorem laoreet, commodo elit. Mauris placerat, sem in commodo hendrerit, tortor risus accumsan nisl, vel lacinia mi mauris eu ante.</p>
						</div>
					</div>
				</div>
			</div>
		</div>
	)
}
