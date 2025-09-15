import { useEffect, useState } from "react";
import { fetchRoutes, deleteRoute } from "../../utils/api";
import SavedRoutesDropdown from "./SavedRoutesDropdown";

function SavedRoutesManager({ token, onShowRoute }) {
  const [routeNamesArr, setRouteNamesArr] = useState([]);
  const [error, setError] = useState("");
  const [selectedRoute, setSelectedRoute] = useState("");

  // fetch the list when token changes
  useEffect(() => {
    if (!token) return;

    const loadRoutes = async () => {
      setError("");
      setRouteNamesArr([]);
      try {
        const routeNames = await fetchRoutes(token);
        setRouteNamesArr(routeNames);
      } catch (err) {
        setError(err.message);
      }
    };

    loadRoutes();
  }, [token]);

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
      <SavedRoutesDropdown
        routes={routeNamesArr}
        selectedRoute={selectedRoute}
        onSelect={handleSelect}
        onShow={onShowRoute}
        onDelete={onDeleteRoute}
      />
    </div>
  );
}

export default SavedRoutesManager;
