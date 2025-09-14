function SaveRouteForm({ token, route, routeName, setRouteName, onSave, routeSaveSuccess }) {
  if (!token) return <h2>Log in for some extra functionality!</h2>;

  return (
    <div>
      {route.length > 0 && (
        <form onSubmit={onSave}>
          <label>Name this route</label>
          <input type="text" value={routeName} onChange={e => setRouteName(e.target.value)} />
          <button type="submit">Save route</button>
        </form>
      )}
      {!routeSaveSuccess && <h2>Route was not saved. Choose another name</h2>}
    </div>
  );
}

export default SaveRouteForm;
