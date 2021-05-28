import React from 'react';
import { Navbar } from './global-components/navbar';
import { PageHeader } from './global-components/page-header';
import { UserProfile } from './section-components/user-profile';
import { FooterV1 } from './global-components/footer';

export const UserProfilePage = () => {
    return <div>
        <Navbar />
        <PageHeader HeaderTitle="User Profile"  />
        <UserProfile />
        <FooterV1/>
    </div>
}
