function SavedRoutesDropdown({ routes, selectedRoute, onSelect, onShow, onDelete }) {
  return (
    <div>
      <h2>Your saved routes</h2>
      <select value={selectedRoute} onChange={(e) => onSelect(e.target.value)}>
        <option value="">Select a route</option>
        {routes.map((name) => (
          <option key={name} value={name}>
            {name}
          </option>
        ))}
      </select>

      {selectedRoute && (
        <div>
          <button onClick={() => onShow(selectedRoute)}>Show</button>
          <button onClick={() => onDelete(selectedRoute)}>Delete</button>
        </div>
      )}
    </div>
  );
}

export default SavedRoutesDropdown;
