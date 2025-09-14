import {useEffect, useState} from "react";
import { fetchRoutes } from "../../utils/api"

function SavedRoutesDropdown ({token}) {
  const [routeNamesArr, setRouteNamesArr] = useState([]);
  const [error, setError] = useState("");
  const [selectedRoute, setSelectedRoute] = useState("");

  useEffect(() => {
    setRouteNamesArr([]);
    setError("");
    const loadRoutes = async () => {
      try {
        const routeNames = await fetchRoutes(token);
        setRouteNamesArr(routeNames);
      } catch (err) {
        setError(err.message);
      }
    };

    loadRoutes();
  }, [token]);

  const handleSelect = (e) => {
    setSelectedRoute(e.target.value);
  };

  if (error) {
    return <h2 className="error"> {error.message} </h2>
  }

  return (
    <div>
      <h2> Your saved routes </h2>
      <select value={selectedRoute} onChange={handleSelect} className = "dropdown">
        <option value=""> Select a route </option>
        {routeNamesArr.map((name) => (<option key={name} value={name}>{name}</option>))}
      </select>

      {selectedRoute && (
        <div>
          <h3> Further action </h3>
        </div>
        )}

    </div>

  );

}

export default SavedRoutesDropdown;