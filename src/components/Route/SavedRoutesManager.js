import { useEffect, useState } from "react";
import { fetchRoutes, deleteRoute, saveRoute } from "../../utils/api";
import SavedRoutesDropdown from "./SavedRoutesDropdown";
import SaveRouteForm from "./SaveRouteForm";


function SavedRoutesManager({ token, route, distance, onShowRoute, newRoutePlot, saveRouteSuccess, setSaveRouteSuccess }) {
  const [routeNamesArr, setRouteNamesArr] = useState([]); //the list of saved routes
  const [errorDropdown, setErrorDropdown] = useState(""); //for dropdown: fetching saved routes etc.
  const [selectedRoute, setSelectedRoute] = useState(""); //selectedRoute for dropdown
  const [routeName, setRouteName] = useState(""); //name entered in saveForm
  //const [newRouteToSave, setNewRouteToSave] = useState(false); //flag: is it time to show saveForm?
  const [errorSave, setErrorSave] = useState(""); //for saveForm

  // fetch the list of saved routes
  const loadRoutes = async () => {
    if (!token) return;
    setErrorDropdown("");
    try {
      const routes = await fetchRoutes(token);
      setRouteNamesArr(routes);
    } catch (err) {
      setErrorDropdown(err.message);
    }
  };

  //fetch saved routes when token changes (aka a new user logs in)
  useEffect(() => {
    loadRoutes();
  }, [token]);

  //use of SaveForm
  const handleSaveRoute = async (e, route, distance) => {
    e.preventDefault();
    setSaveRouteSuccess(false);
    setErrorSave("");
    try {
      await saveRoute(token, {
        name: routeName,
        routeJson: { nodes: route, distance },
        distance,
      });
      routeNamesArr.push(routeName); //add a new route to the arr
      setRouteName(""); // clear the saving form
      setSaveRouteSuccess(true);
    } catch (err) {
      setErrorSave(err.message);
    }
  };

  const handleSelect = (name) => setSelectedRoute(name); //for dropdown list

  //delete route handle
  const onDeleteRoute =  async (name) => {
    try {
      await deleteRoute(name, token);
      setRouteNamesArr(routeNamesArr.filter(routeName => routeName !== name)); //update dropdown
    } catch (err) {
      setErrorDropdown(err.message);
    }
  };

  return (
    <div>
      <SaveRouteForm
        token={token}
        route={route}
        routeName={routeName}
        setRouteName={setRouteName}
        newRoutePlot={newRoutePlot}
        onSave={(e) => handleSaveRoute(e, route, distance)}
        saveRouteSuccess={saveRouteSuccess}
        errorMessage={errorSave}
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
