import React, {useEffect} from 'react';
import { Navbar } from './global-components/navbar';
import { BannerV2 } from './section-components/banner-v2';
import { IntroV2 } from './section-components/intro-v2';
import { Video } from './section-components/video';
import { Counter } from './section-components/counter';
import { FooterV1 } from './global-components/footer';


export const Home_V3 = () => {

    
	
	  
    return <div>
        <Navbar />
        <BannerV2 />
        <IntroV2 />
        <Video />
        <Counter />
        <FooterV1 />
    </div>
}


