import React from 'react';

export const BannerV2 = () => {

	let publicUrl = process.env.PUBLIC_URL + '/'

	return (
		<div className="main-banner-area jarallax" style={{ backgroundImage: 'url(' + publicUrl + 'assets/img/banner/4.png)' }}>
			<div className="content">
				<div className="container">
					<h2>Explore, Discover, Travel</h2>
					<h1>ADVENTURE</h1>
					<h1 className="shadow">ADVENTURE</h1>
				</div>
			</div>
		</div>
	)
}
