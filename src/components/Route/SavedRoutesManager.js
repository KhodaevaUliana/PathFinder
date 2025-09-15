import { useEffect, useState } from "react";
import { fetchRoutes, deleteRoute, saveRoute } from "../../utils/api";
import SavedRoutesDropdown from "./SavedRoutesDropdown";
import SaveRouteForm from "./SaveRouteForm";


function SavedRoutesManager({ token, route, distance, onShowRoute }) {
  const [routeNamesArr, setRouteNamesArr] = useState([]);
  const [error, setError] = useState(""); //for drop down
  const [selectedRoute, setSelectedRoute] = useState("");
  const [routeName, setRouteName] = useState("");
  const [routeSaveAttempted, setRouteSaveAttempted] = useState(false);
  const [newRouteToSave, setNewRouteToSave] = useState(false);
  const [errorMessage, setErrorMessage] = useState(""); //for saving

  // fetch the list of saved routes
  const loadRoutes = async () => {
    if (!token) return;
    setError("");
    try {
      const routes = await fetchRoutes(token);
      setRouteNamesArr(routes);
    } catch (err) {
      setError(err.message);
    }
  };

  //fetch saved routes when token changes
  useEffect(() => {
    loadRoutes();
  }, [token]);

  const handleSaveRoute = async (e, route, distance) => {
    e.preventDefault();
    setRouteSaveAttempted(true);
    setErrorMessage("");
    try {
      await saveRoute(token, {
        name: routeName,
        routeJson: { nodes: route, distance },
        distance,
      });
      setRouteName(""); // clear the saving form
      setRouteSaveAttempted(false);
      await loadRoutes();  //update saved routes
    } catch (err) {
      setErrorMessage(err.message);
    }
  };

  const handleSelect = (name) => setSelectedRoute(name);

  const onDeleteRoute =  async (name) => {
    try {
      await deleteRoute(name, token);
      setRouteNamesArr(routeNamesArr.filter(routeName => routeName !== name));
    } catch (err) {
      setError(err.message);
    }
  };

  return (
    <div>
      {error && <h2 className="error">{error.length > 200 ? "Error: " + error.slice(0, 50) + "..." : error}</h2>}
      <SaveRouteForm
        token={token}
        route={route}
        routeName={routeName}
        setRouteName={setRouteName}
        newRouteToSave={newRouteToSave}
        onSave={(e) => handleSaveRoute(e, route, distance)}
        routeSaveAttempted={routeSaveAttempted}
        errorMessage={errorMessage}
      />

      {token && <SavedRoutesDropdown
        routes={routeNamesArr}
        selectedRoute={selectedRoute}
        onSelect={handleSelect}
        onShow={onShowRoute}
        onDelete={onDeleteRoute}
      />}
    </div>
  );
}

export default SavedRoutesManager;
