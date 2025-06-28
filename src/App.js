import logo from './logo.svg';
import './App.css';
import React, { useEffect, useState } from 'react';
import { MapContainer, TileLayer, Polyline, Marker, Popup } from 'react-leaflet';
import 'leaflet/dist/leaflet.css';


function App() {
  const [route, setRoute] = useState([]);

  useEffect(() => {
      fetch('http://localhost:8080/route?startLatitude=43.7314&startLongitude=7.4246&finishLatitude=43.7333&finishLongitude=7.4167')
        .then(res => res.json())
        .then(data => {
        console.log("Fetched data:", data);
        setRoute(data.nodes);
        })
        .catcgit rm -r --cached node_modulesh(err => console.error(err));
    }, []);
  const positions = route.map(node => [node.latitude, node.longitude]);

  return (
      <div>
        <h1>Let's explore Monaco together! </h1>
        <MapContainer center={[43.735, 7.42]} zoom={15} style={{ height: '90vh', width: '100%' }}>
          <TileLayer
            url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
            attribution="&copy; OpenStreetMap contributors"
          />
          {positions.length > 0 && (
            <>
              <Polyline positions={positions} color="blue" />
              <Marker position={positions[0]}>
                <Popup>Start</Popup>
              </Marker>
              <Marker position={positions[positions.length - 1]}>
                <Popup>End</Popup>
              </Marker>
            </>
          )}
        </MapContainer>
      </div>
    );


  /*return (
    <div className="App">
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
    </div>
  );*/
}

export default App;
