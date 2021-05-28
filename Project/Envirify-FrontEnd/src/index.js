import React from 'react';
import ReactDOM from 'react-dom';
import { Home } from './Home';
import * as serviceWorkerRegistration from './serviceWorkerRegistration';
import reportWebVitals from './reportWebVitals';

ReactDOM.render(<Home />, document.getElementById('viaje'));
serviceWorkerRegistration.register();
reportWebVitals();
