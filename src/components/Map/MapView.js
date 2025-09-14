import { MapContainer, TileLayer, Polyline, Marker, Popup, Tooltip } from "react-leaflet";
import ClickHandler from "./ClickHandler";

function MapView({ points, route, onMapClick }) {
  const positions = route.map(node => [node.latitude, node.longitude]);

  return (
    <MapContainer center={[43.735, 7.42]} zoom={15} style={{ height: "70vh", width: "100%" }}>
      <TileLayer
        url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
        attribution="&copy; OpenStreetMap contributors"
      />
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
