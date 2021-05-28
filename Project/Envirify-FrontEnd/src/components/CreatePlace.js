import React from 'react'
import { FooterV1 } from './global-components/footer'
import { Navbar } from './global-components/navbar'
import { PageHeader } from './global-components/page-header';
import { FormCreate } from './section-components/FormCreate';

export const CreatePlace = () => {

    return (
        <div>
            <Navbar />
            <PageHeader HeaderTitle={"New Lodging"} />
            <br></br>
            <FormCreate />
            <br></br>
            <FooterV1 />
        </div>
    )
}
