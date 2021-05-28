import React from 'react';

export const Counter = () => {

	let publicUrl = process.env.PUBLIC_URL+'/'

	return (
		<div className="counter-area bg-gray">
			  <div className="container">
			    <ul className="row">
			      <li className="col-lg-3 col-sm-6">
			        <div className="single-counting text-center">
			          <h2><img src={publicUrl+"assets/img/icons/10.png"} alt="img" /> <span className="count-nums">30</span><span>+</span></h2>
			          <span className="title">Cities</span>
			        </div>
			      </li>
			      <li className="col-lg-3 col-sm-6">
			        <div className="single-counting text-center">
			          <h2><img src={publicUrl+"assets/img/icons/10.png"} alt="img" /> <span className="count-nums">100</span><span>+</span></h2>
			          <span className="title">Hosts</span>
			        </div>
			      </li>
			      <li className="col-lg-3 col-sm-6">
			        <div className="single-counting text-center">
			          <h2><img src={publicUrl+"assets/img/icons/10.png"} alt="img" /><span className="count-nums">250</span><span>+</span></h2>
			          <span className="title">Sustainable lodgings</span>
			        </div>
			      </li>
			      <li className="col-lg-3 col-sm-6">
			        <div className="single-counting text-center">
			          <h2><img src={publicUrl+"assets/img/icons/10.png"} alt="img" /> <span className="count-nums">30</span><span>+</span></h2>
			          <span className="title">Activities</span>
			        </div>
			      </li>
			    </ul>
			  </div>
			</div>
	)
}
