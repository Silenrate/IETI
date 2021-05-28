import React from 'react';

export const PageHeader = ({HeaderTitle}) => {

	let publicUrl = process.env.PUBLIC_URL+'/'

	return (
		 <div className="breadcrumb-area jarallax" style={{backgroundImage: 'url('+publicUrl+'assets/img/bg/3.png)'}}>
				  <div className="container">
				    <div className="row">
				      <div className="col-lg-12">
				        <div className="breadcrumb-inner">
				          <h1 className="page-title">{ HeaderTitle }</h1>
				        </div>
				      </div>
				    </div>
				  </div>
				</div>
	)
}