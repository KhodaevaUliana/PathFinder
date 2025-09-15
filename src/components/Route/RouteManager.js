import { useState, useEffect } from "react";
import { fetchRoute } from "../../utils/api.js";

export function useRouteManager(token) {
  const [points, setPoints] = useState([]);
  const [route, setRoute] = useState([]);
  const [distance, setDistance] = useState(null);
  const [errorMessage, setErrorMessage] = useState(null);
  const [routeName, setRouteName] = useState("");

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

  return  {
    points,
    route,
    distance,
    errorMessage,
    handleMapClick,
    handleRefresh
  };

}
