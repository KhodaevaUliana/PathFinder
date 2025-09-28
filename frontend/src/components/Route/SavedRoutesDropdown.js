import "./SavedRoutesDropdown.css";

function SavedRoutesDropdown({ routes, selectedRoute, onSelect, onShow, onDelete, deleteCandidate, handlePerformDelete, handleCancelDelete, errorMessage }) {
  return (
    <div>
      <h2 className="savedTitle">Your saved routes</h2>
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
          <button className="show-button" onClick={() => onShow(selectedRoute)}>Show</button>
          <button className="delete-button" onClick={() => onDelete(selectedRoute)}>Delete</button>
          {deleteCandidate &&
            <div>
              <h2 className="confirm-message"> Are you sure to delete
              <span className="highlight"> {selectedRoute} </span>?</h2>
              <button className="delete-button" onClick={() => {handlePerformDelete(selectedRoute)}}> Yes </button>
              <button className="show-button"  onClick={handleCancelDelete}> No </button>
            </div>}
        </div>
      )}
      {errorMessage && <h2 className="error">{errorMessage.length > 200 ? "Error: " + errorMessage.slice(0, 50) + "..." : errorMessage}</h2>}
    </div>
  );
}

export default SavedRoutesDropdown;
