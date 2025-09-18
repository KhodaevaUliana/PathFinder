import { useState, useEffect } from "react";
import "./App.css";

import Header from "./components/Header";
import AuthControl from "./components/Auth/AuthControl-2";
//import SaveRouteControl from "./components/Route/SaveRouteControl";
import SavedRoutesManager from "./components/Route/SavedRoutesManager";
import MapView from "./components/Map/MapView";
import { useRouteManager } from "./components/Route/RouteManager";

function App() {
  //log in
  const [token, setToken] = useState("");
  //const [signUpSuccess, setSignUpSuccess] = useState(false);

  // log in handlers
  const handleLogin = (jwt) => setToken(jwt);
  const handleLogOut = () => setToken("");
  //const handleSignup = () => setSignUpSuccess(true);

  const {
    points, //start and finish
    route, //sequence of points making up the path
    distance, //length of the path
    errorMessage, //in case of issues with plotting
    newRoutePlot, //are we plotting a new route? we might need to save it
    handleMapClick, //click on start and finish
    handleRefresh, //refresh button
    onShowRoute, //for saved routes -- plot them on the map
    saveRouteSuccess, //we just saved a route and want you let it know
    setSaveRouteSuccess
  } = useRouteManager(token);


  return (
    <div className="container">
      <Header
        points={points}
        distance={distance}
        errorMessage={errorMessage}
        onRefresh={handleRefresh}
      />



      <div className="saved-routes-manager">
        <SavedRoutesManager
          token={token}
          route={route}
          distance={distance}
          onShowRoute={onShowRoute}
          newRoutePlot={newRoutePlot}
          saveRouteSuccess={saveRouteSuccess}
          setSaveRouteSuccess={setSaveRouteSuccess}
        />
      </div>

      <div className="login">
        <AuthControl
          token={token}
          onLogin={handleLogin}
          //onSignup={handleSignup}
          onLogOut={handleLogOut}
          //signUpSuccess={signUpSuccess}
        />
      </div>

      <div className="map">
        <MapView
          points={points}
          route={route}
          onMapClick={handleMapClick}
        />
      </div>
    </div>
  );
}

export default App;

