import React from 'react';
import { Navbar } from './global-components/navbar';
import { PageHeader } from './global-components/page-header';
import { IntroV2 } from './section-components/intro-v2';
import { FooterV1 } from './global-components/footer';
import { Video } from './section-components/video';

export const AboutPage = () => {
    return <div>
        <Navbar />
        <PageHeader HeaderTitle="About Us"  />
        <IntroV2 />
        <Video></Video>
        <FooterV1 />
    </div>
}


