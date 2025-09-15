import { useState, useEffect } from "react";
import "./App.css";

import Header from "./components/Header";
import AuthControl from "./components/Auth/AuthControl";
import SaveRouteControl from "./components/Route/SaveRouteControl";
import SavedRoutesManager from "./components/Route/SavedRoutesManager";
import MapView from "./components/Map/MapView";
import { fetchRoute, saveRoute } from "./utils/api";
import { useRouteManager } from "./components/Route/RouteManager";

function App() {
  //log in
  const [token, setToken] = useState("");
  const [signUpSuccess, setSignUpSuccess] = useState(false);

  // log in handlers
  const handleLogin = (jwt) => setToken(jwt);
  const handleLogOut = () => setToken("");
  const handleSignup = () => setSignUpSuccess(true);

  const {
    points,
    route,
    distance,
    errorMessage,
    handleMapClick,
    handleRefresh
  } = useRouteManager(token);


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
        <SavedRoutesManager token = {token} />
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
