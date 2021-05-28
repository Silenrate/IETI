import React from 'react'
import { FooterV1 } from './global-components/footer'
import { Navbar } from './global-components/navbar'
import { PageHeader } from './global-components/page-header'
import { FormUpdate } from './section-components/FormUpdate'


export const UpdateLodging = () => {


    return (
        <div>
            <Navbar />
            <PageHeader HeaderTitle={"Update Lodging"} />
            <br></br>
            <FormUpdate />
            <br></br>
            <FooterV1 />
        </div>
    )
}
