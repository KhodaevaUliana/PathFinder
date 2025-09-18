import React from "react";
import L from "leaflet";
import { MapContainer, TileLayer, Polyline, Marker, Popup, Tooltip } from "react-leaflet";
import ClickHandler from "./ClickHandler";
import MapUpdater from "./MapUpdater";


import markerIcon2x from 'leaflet/dist/images/marker-icon-2x.png';
import markerIcon from 'leaflet/dist/images/marker-icon.png';
import markerShadow from 'leaflet/dist/images/marker-shadow.png';

delete L.Icon.Default.prototype._getIconUrl;
L.Icon.Default.mergeOptions({
  iconRetinaUrl: markerIcon2x,
  iconUrl: markerIcon,
  shadowUrl: markerShadow,
});

function MapView({ points, route, onMapClick }) {
  const CENTER_DEFAULT = [48.1351, 11.582];
  const positions = route ? route.map(node => [node.latitude, node.longitude]) : [];
  //const positions = route.map(node => [node.latitude, node.longitude]);

  return (
    <MapContainer center={CENTER_DEFAULT} zoom={15} style={{ height: "70vh", width: "100%" }}>
      <TileLayer
        url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
        attribution="&copy; OpenStreetMap contributors"
      />
      <MapUpdater route={route} defaultCenter={CENTER_DEFAULT} />
      <ClickHandler onClick={onMapClick} />
      {points.map((pt, idx) => (
        <Marker key={idx} position={[pt.lat, pt.lng]}>
          <Popup>{idx === 0 ? "Start" : "Finish"}</Popup>
          <Tooltip permanent direction="top">
            {idx === 0 ? "Start" : "Finish"}
          </Tooltip>
        </Marker>
      ))}
      {positions.length > 0 && <Polyline positions={positions} color="blue" />}
    </MapContainer>
  );
}

export default MapView;
