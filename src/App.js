import React, { useState, useEffect } from "react";
import "./App.css";

import Header from "./components/Header";
import AuthControl from "./components/Auth/AuthControl";
import SaveRouteControl from "./components/Route/SaveRouteControl";
import SavedRoutesDropdown from "./components/Route/SavedRoutesDropdown";
import MapView from "./components/Map/MapView";
import { fetchRoute, saveRoute } from "./utils/api";

function App() {
  //all about routes
  const [points, setPoints] = useState([]);
  const [route, setRoute] = useState([]);
  const [distance, setDistance] = useState(null);
  const [errorMessage, setErrorMessage] = useState(null);
  const [routeName, setRouteName] = useState("");

  //log in
  const [token, setToken] = useState("");
  const [signUpSuccess, setSignUpSuccess] = useState(false);

  // log in handlers
  const handleLogin = (jwt) => setToken(jwt);
  const handleLogOut = () => setToken("");
  const handleSignup = () => setSignUpSuccess(true);


  const handleMapClick = (latlng) => {
    const newPoint = { lat: latlng.lat, lng: latlng.lng };
    setPoints(points.length < 2 ? [...points, newPoint] : [newPoint]);
  };

  const handleRefresh = () => {
    setPoints([]);
    setRoute([]);
    setRouteName("");
    setDistance(null);
    setErrorMessage(null);
  };

  //fetch route
  useEffect(() => {
    if (points.length === 2) {
      const [start, finish] = points;
      fetchRoute(start, finish)
        .then((data) => {
          setRoute(data.nodes);
          setDistance(data.distance);
          setErrorMessage(null);
        })
        .catch((err) => {
          console.error(err);
          setRoute([]);
          setDistance(null);
          setErrorMessage(
            "The route between these points cannot be found. Please change start or/and finish point."
          );
        });
    }
  }, [points]);

  return (
    <div className="container">
      <Header
        points={points}
        distance={distance}
        errorMessage={errorMessage}
        onRefresh={handleRefresh}
      />

      <div className="logged-in-save">
        <SaveRouteControl token={token} route={route} distance={distance} />
      </div>

      {token && <div className="logged-in-list">
        <SavedRoutesDropdown token={token} />
      </div>}

      <div className="login">
        <AuthControl
          token={token}
          onLogin={handleLogin}
          onSignup={handleSignup}
          onLogOut={handleLogOut}
          signUpSuccess={signUpSuccess}
        />
      </div>

      <div className="map">
        <MapView points={points} route={route} onMapClick={handleMapClick} />
      </div>
    </div>
  );
}

export default App;
