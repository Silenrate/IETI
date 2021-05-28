import React from 'react';
import { Navbar } from './global-components/navbar';
import { PageHeader } from './global-components/page-header';
import { Contact } from './section-components/contact';
import { FooterV1 } from './global-components/footer';

export const ContactPage = () => {
    return <div>
        <Navbar />
        <PageHeader HeaderTitle="Contact Us"  />
        <Contact />
        <FooterV1 />
    </div>
}


