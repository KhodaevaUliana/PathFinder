import "./SaveRouteForm.css"
import "./SavedRoutesManager.css"

function SaveRouteForm({ token, route, routeName, setRouteName, newRoutePlot, onSave, saveRouteSuccess, errorMessage }) {
  //don't show anything for those who are not logged in
  if (!token) return <h2>Log in for some extra functionality!</h2>;
  //don't show the saving form if there is no route to be saved
  if (!newRoutePlot) return <div className="save-form"></div>;
  if (saveRouteSuccess) {
    return <h2 className="saved-route-message"> The current route was successfully saved! </h2>;
  }

  return (
    <div>
        <form onSubmit={onSave}>
          <label>Name this route</label>
          <input type="text" value={routeName} onChange={e => setRouteName(e.target.value)} />
          <button type="submit" className="save-button">Save route</button>
        </form>
       {errorMessage && <h2 className="error">{errorMessage.length > 200 ? "Error: " + errorMessage.slice(0, 50) + "..." : errorMessage}</h2>}
    </div>
  );
}

export default SaveRouteForm;
