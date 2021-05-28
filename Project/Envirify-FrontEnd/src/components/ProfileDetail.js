import { Typography } from '@material-ui/core'
import React, { useEffect, useState } from 'react'
import { FooterV1 } from './global-components/footer'
import { Navbar } from './global-components/navbar'
import { PageHeader } from './global-components/page-header'

export const ProfileDetail = () => {

    const [user, setuser] = useState({
        name: "",
        email: "",
        phoneNumber: ""
    })

    const getParameterByName = (name) => {
        name = name.replace(/[[]/, "\\[").replace(/[\]]/, "\\]");
        var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
            results = regex.exec(window.location.href);
        return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
    }

    

    useEffect(() => {
        async function fetchMyAPI() {
            const emailUser = await getParameterByName("user");
            let response = await fetch(`https://enfiry-back-end.herokuapp.com/api/v1/users/${emailUser}`)
            response = await response.json()
            setuser(response)
        }

        
        fetchMyAPI()

    }, [])

    return <div>
        <Navbar />
        <PageHeader HeaderTitle="About Us" />
        <br></br>
        <div style={{ textAlign: "center" }}>
            <Typography variant="h3">User</Typography>
            <form style={{ width: "50%", display: "inline-block" }}>
                <div className="row">
                    <div className="col">
                        <label className="single-input-wrap style-two">
                            <span className="single-input-title" style={{ textAlign: "left" }}>User Name</span>
                            <input type="text" name="first-name" value={user.name} />
                        </label>
                    </div>
                </div>
                <div className="row">
                    <div className="col">
                        <label className="single-input-wrap style-two">
                            <span className="single-input-title" style={{ textAlign: "left" }}>User Phone</span>
                            <input type="text" name="phone" value={user.phoneNumber} />
                        </label>
                    </div>
                </div>
                <div className="row">
                    <div className="col"  >
                        <label className="single-input-wrap style-two">
                            <span className="single-input-title" style={{ textAlign: "left" }}>User Email Address</span>
                            <input type="text" name="email" value={user.email} />
                        </label>
                    </div>
                </div>
            </form>
        </div>
        <FooterV1 />
    </div>
}
