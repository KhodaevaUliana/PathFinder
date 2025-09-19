import { useEffect } from "react";
import { useMap } from "react-leaflet";

function MapUpdater({ points, defaultCenter }) {
  const map = useMap();

  useEffect(() => {
    if (points && points.length > 0) {
      const newCenter = [points[0].lat, points[0].lng];
      map.setView(newCenter, map.getZoom());
    } else {
      map.setView(defaultCenter, map.getZoom());
    }
  }, [points, map, defaultCenter]);

  return null;
}

export default MapUpdater;
