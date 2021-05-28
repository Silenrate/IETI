import React, { useState } from 'react';
import { BrowserRouter as Router, Link, Route } from 'react-router-dom';
import IconButton from '@material-ui/core/IconButton';
import SearchIcon from '@material-ui/icons/Search';
import logo from './logo.svg';
import './App.css';
import { SearchPlace } from './components/SearchPlace';
import { PlaceInfo } from './components/PlaceInfo';

function App() {

  const [searchValue, setSearchValue] = useState("");

  const nameChangeHandler = (e) => {
    setSearchValue(e.target.value);
  };

  const searchPlaceHandler = (e) => {
    e.preventDefault();
    if (searchValue === "") {
      window.location.href = "/search?value=Cundinamarca";
    } else {
      window.location.href = "/search?value=" + searchValue;
    }
  };

  //LA BARRA DE BUSQUEDA DEBE TENER onChange={nameChangeHandler}

  const HomeView = () => (
    <div>
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.js</code> and save to reload.
          </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
          </a>
      </header>
      <IconButton type="submit" aria-label="search" onClick={searchPlaceHandler}>
        <SearchIcon />
      </IconButton>
    </div>
  );

  const SearchView = () => (<SearchPlace />);

  const PlaceView = () => (<PlaceInfo />)

  return (
    <Router>
      <div className="App">
        <Route exact path="/" component={HomeView} />
        <Route path="/search" component={SearchView} />
        <Route path="/place" component={PlaceView} />
      </div>
    </Router>
  );
}

export default App;
