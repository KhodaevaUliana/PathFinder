import { useState, useEffect } from "react";
import { fetchRoute, fetchRouteByName } from "../../utils/api.js";

export function useRouteManager(token) {
  const [points, setPoints] = useState([]);
  const [route, setRoute] = useState([]);
  const [distance, setDistance] = useState(null);
  const [errorMessage, setErrorMessage] = useState(null);
  const [routeName, setRouteName] = useState("");
  const [newRoutePlot, setNewRoutePlot] = useState(false);

  const handleMapClick = (latlng) => {
    const newPoint = { lat: latlng.lat, lng: latlng.lng };
    setPoints(points.length < 2 ? [...points, newPoint] : [newPoint]);
    setNewRoutePlot(true);
  };

  const handleRefresh = () => {
    setPoints([]);
    setRoute([]);
    setRouteName("");
    setDistance(null);
    setErrorMessage(null);
    setNewRoutePlot(false);
  };

  //find a route between two points
  useEffect(() => {
    if (!newRoutePlot) {
      return;
    }
    if (points.length === 2) {
      const [start, finish] = points;
      fetchRoute(start, finish)
        .then((data) => {
          setRoute(data.nodes);
          setDistance(data.distance);
          setErrorMessage(null);
          setNewRoutePlot(true);
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

  //fetch a saved route by name
  const onShowRoute = async (routeName) => {
    setNewRoutePlot(false);
    try {
      const data = await fetchRouteByName(routeName, token);
      setDistance(data.distance);
      const fetchedRoute = data.routeJson.nodes;
      setRoute(fetchedRoute);
      if (fetchedRoute.length > 1) {
        setPoints([
          { lat: fetchedRoute[0].latitude, lng: fetchedRoute[0].longitude },
          { lat: fetchedRoute[fetchedRoute.length - 1].latitude, lng: fetchedRoute[fetchedRoute.length - 1].longitude }
        ]);
      }
      setErrorMessage("");
    } catch (error) {
      setRoute([]);
      setDistance(null);
      setErrorMessage(error.message);
    }
  };




  return  {
    points,
    route,
    distance,
    errorMessage,
    newRoutePlot,
    handleMapClick,
    handleRefresh,
    onShowRoute
  };

}
