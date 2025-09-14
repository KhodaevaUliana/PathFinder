import { useState } from "react";
import SaveRouteForm from "./SaveRouteForm";
import { saveRoute } from "../../utils/api";

export default function SaveRouteControl({ token, route, distance }) {
  const [routeName, setRouteName] = useState("");
  const [routeSaveSuccess, setRouteSaveSuccess] = useState(true);

  const handleSaveRoute = async (e) => {
    e.preventDefault();

    try {
      await saveRoute(token, {
        name: routeName,
        routeJson: { nodes: route, distance },
        distance,
      });
      setRouteSaveSuccess(true);
    } catch (err) {
      console.error("Error saving route:", err);
      setRouteSaveSuccess(false);
    }
  };

  return (
    <SaveRouteForm
      token={token}
      route={route}
      routeName={routeName}
      setRouteName={setRouteName}
      routeSaveSuccess={routeSaveSuccess}
      onSave={handleSaveRoute}
    />
  );
}
