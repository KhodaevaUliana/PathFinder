function SaveRouteForm({ token, route, routeName, setRouteName, newRouteToSave, onSave, routeSaveAttempted, errorMessage }) {
  //don't show anything for those who are not logged in
  if (!token) return <h2>Log in for some extra functionality!</h2>;
  //don't show the saving form if there is no route to be saved
  if (!newRouteToSave) return <div></div>;
  if (routeSaveAttempted && !errorMessage) {
    return <h2> This route was successfully saved! </h2>;
  }

  return (
    <div>
        <form onSubmit={onSave}>
          <label>Name this route</label>
          <input type="text" value={routeName} onChange={e => setRouteName(e.target.value)} />
          <button type="submit">Save route</button>
        </form>
      {errorMessage && <h2 className="error"> {errorMessage} </h2>}
    </div>
  );
}

export default SaveRouteForm;
