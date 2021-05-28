import React from 'react';

export const Contact = () => {

  let publicUrl = process.env.PUBLIC_URL + '/'

  return (
    <div>
      <div className="contact-area pd-top-108">
        <div className="container">
          <div className="row justify-content-center">
            <div className="col-lg-6">
              <div className="section-title text-lg-center text-left">
                <h2 className="title">Get In Touch!</h2>
                <p>Vestibulum blandit viverra convallis. Pellentesque ligula urna, fermentum ut semper in, tincidunt nec dui. Morbi mauris lacus, consequat eget justo in</p>
              </div>
            </div>
          </div>
          <div className="row">
            <div className="col-xl-5 offset-xl-1 col-lg-6">
              <div className="thumb">
                <img src={publicUrl + "assets/img/others/11.png"} alt="img" />
              </div>
            </div>
            <div className="col-xl-5 col-lg-6">
              <form className="tp-form-wrap">
                <div className="row">
                  <div className="col-md-6">
                    <label className="single-input-wrap style-two">
                      <span className="single-input-title">Name</span>
                      <input type="text" name="name" />
                    </label>
                  </div>
                  <div className="col-md-6">
                    <label className="single-input-wrap style-two">
                      <span className="single-input-title">Number</span>
                      <input type="text" name="number" />
                    </label>
                  </div>
                  <div className="col-lg-12">
                    <label className="single-input-wrap style-two">
                      <span className="single-input-title">Email</span>
                      <input type="text" name="email" />
                    </label>
                  </div>
                  <div className="col-lg-12">
                    <label className="single-input-wrap style-two">
                      <span className="single-input-title">Message</span>
                      <textarea defaultValue={""} name="message" />
                    </label>
                  </div>
                  <div className="col-12">
                    <input type="submit" className="btn btn-yellow" value="Send Message" />
                  </div>
                </div>
              </form>
              <br></br>
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}
