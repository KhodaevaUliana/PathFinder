import logo from './logo.svg';
import './App.css';
import React, { useEffect, useState } from 'react';
import { MapContainer, TileLayer, Polyline, Marker, Popup, useMapEvents, Tooltip } from 'react-leaflet';
import 'leaflet/dist/leaflet.css';
import L from 'leaflet';
import LogIn from './AuthForm';

//here, we are manually setting the marker icon since the default appeared as a broken image
import markerIcon2x from 'leaflet/dist/images/marker-icon-2x.png';
import markerIcon from 'leaflet/dist/images/marker-icon.png';
import markerShadow from 'leaflet/dist/images/marker-shadow.png';

delete L.Icon.Default.prototype._getIconUrl;
L.Icon.Default.mergeOptions({
  iconRetinaUrl: markerIcon2x,
  iconUrl: markerIcon,
  shadowUrl: markerShadow,
});

function ClickHandler({onClick}) {
  useMapEvents({
    click(e) {  //e is a click event object (LeafletMouseEvent)
      onClick(e.latlng); //latitude (lat) and longitude (lng) of a click event object
      //console.log("Event object:", e);
    }
  });
  return null;
}


function App() {
  const [route, setRoute] = useState([]);
  const [points, setPoints] = useState([]);
  const [distance, setDistance] = useState(null);
  const [errorMessage, setErrorMessage] = useState(null);
  //log-in info
  const [token, setToken] = useState("");
  const [signUpSuccess, setSignUpSuccess] = useState(false);

  const handleLogin = (jwt) => {
    setToken(jwt);
    setSignUpSuccess(false);
  };

  const handleLogOut = () => {
    setToken("");
  };

  const handleSignup = () => {
    setSignUpSuccess(true);
  };




  const handleMapClick = (latlng) => {
    const newPoint = {
      lat: latlng.lat,
      lng: latlng.lng
    };

    if (points.length < 2) {
      setPoints([...points, newPoint]); //append a new point
    } else {
      setPoints([newPoint]); //if we already have 2 points, we start fresh
    }
  };

  //delete route and start/finish marks when we click on 'Refresh button'
  const handleRefresh = () => {
    setPoints([]);
    setRoute([]);
    setDistance(null);
    setErrorMessage(null);
  };



  useEffect(() => {
    if (points.length == 2) {
      const [start, finish] = points;

      fetch(`http://localhost:8080/route?startLatitude=${start.lat}&startLongitude=${start.lng}&finishLatitude=${finish.lat}&finishLongitude=${finish.lng}`)
        .then(res => res.json())
        .then(data => {
          //console.log("Fetched data:", data);
          setRoute(data.nodes);
          setDistance(data.distance);
          setErrorMessage(null);
        })
        .catch(err => {
            console.error(err);
            setRoute([]);
            setDistance(null);
            setErrorMessage("The route between these points cannot be found. Please, change start or/and finish point.");
        }
       );
      }
    }, [points]);


  const positions = route.map(node => [node.latitude, node.longitude]);

  let message;
  if (points.length === 2 && distance !== null) {
    message = `The distance is ${Math.round(distance/10)/100} km. Press Refresh to start over.`;
  } else if (points.length === 0) {
    message = "Click on the start point";
  } else {
    message = "Click on the finish point";
  }


  return (
      <div className = "container">
        <div className = "header">
          <h1>Let's explore Monaco together! </h1>
          <h2> {errorMessage ? errorMessage : message } </h2>
          <button onClick={handleRefresh} style={{ margin: '20px', color: 'magenta', fontWeight: 'bold', fontSize: '20px' }}>
            Refresh
          </button>
        </div>


        <div className = "login">
          {token ? (
          <div>
          <h2> You are logged in </h2>
          <button onClick={handleLogOut} className = "auth-button"> Log out </button>
          </div>
          ) : (
          <div>
          <LogIn onLogin = {handleLogin} onSignup = {handleSignup}/>
          {signUpSuccess && <p className = "error"> You've signed up successfully </p>}
          </div>
          )}
        </div>

        <div className = "map">
        <MapContainer center={[43.735, 7.42]} zoom={15} style={{ height: '70vh', width: '100%' }}>
          <TileLayer
            url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
            attribution="&copy; OpenStreetMap contributors"
          />
          <ClickHandler onClick={handleMapClick} />
          {points.map((pt, idx) => (
            <Marker key={idx} position={[pt.lat, pt.lng]}>
              <Popup>{idx === 0 ? 'Start' : 'Finish'}</Popup>
               <Tooltip permanent direction="top">
                  {idx === 0 ? 'Start' : 'Finish'}
               </Tooltip>
            </Marker>
          ))}
          {positions.length > 0 && (
            <Polyline positions={positions} color="blue" />
          )}
        </MapContainer>
        </div>
      </div>
    );

}

export default App;
