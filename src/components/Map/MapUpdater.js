import { useEffect } from "react";
import { useMap } from "react-leaflet";

function MapUpdater({ route, defaultCenter }) {
  const map = useMap();

  useEffect(() => {
    if (route && route.length > 0) {
      const newCenter = [route[0].latitude, route[0].longitude];
      map.setView(newCenter, map.getZoom());
    } else {
      map.setView(defaultCenter, map.getZoom());
    }
  }, [route, map, defaultCenter]);

  return null;
}

export default MapUpdater;
