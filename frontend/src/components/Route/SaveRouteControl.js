import { useState, useEffect } from "react";
import SaveRouteForm from "./SaveRouteForm";
import { saveRoute } from "../../utils/api-routes";

export default function SaveRouteControl({ token, route, distance, newRoutePlot  }) {
  const [routeName, setRouteName] = useState("");
  const [routeSaveAttempted, setRouteSaveAttempted] = useState(false);
  const [newRouteToSave, setNewRouteToSave] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");

  useEffect(() => {
    setRouteName("");
    setErrorMessage("");
    setRouteSaveAttempted(false);
    if (route.length > 0 && newRoutePlot) {
      setNewRouteToSave(true);
    } else {
      setNewRouteToSave(false);
    }
  }, [route]);

  const handleSaveRoute = async (e) => {
    e.preventDefault();
    setRouteSaveAttempted(true);
    try {
      await saveRoute(token, {
        name: routeName,
        routeJson: { nodes: route, distance },
        distance,
      });
    } catch (err) {
      setErrorMessage(err.message);
    }
  };

  return (
    <SaveRouteForm
      token={token}
      route={route}
      routeName={routeName}
      setRouteName={setRouteName}
      newRouteToSave = {newRouteToSave}
      onSave={handleSaveRoute}
      routeSaveAttempted = {routeSaveAttempted}
      errorMessage = {errorMessage}
    />
  );
}
