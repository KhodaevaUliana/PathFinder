const API_URL = process.env.REACT_APP_API_URL;

export async function fetchRoute(start, finish) {
  const response = await fetch(
    `${API_URL}/route?startLatitude=${start.lat}&startLongitude=${start.lng}&finishLatitude=${finish.lat}&finishLongitude=${finish.lng}`
  );
  if (!response.ok) {
     const errorMessage = await response.text();
     throw new Error(errorMessage||"Failed to fetch the route");
  }
  return response.json();
}

export async function saveRoute(token, routeToSave) {
  const response = await fetch(
    `${API_URL}/saved_routes/save`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify(routeToSave)
    });
  if (!response.ok) {
    const errorMessage = await response.text();
    throw new Error(errorMessage || "The route was not saved. Try another name");
  }
  return response.ok;
}

export async function fetchRoutes(token) {
  const response = await fetch(`${API_URL}/saved_routes/fetch_list_of_routes`, {
    method: "GET",
    headers: {
      Authorization: `Bearer ${token}`,
    }
  });
  if (!response.ok) {
    const errorMessage = await response.text();
    throw new Error(errorMessage || "Failed to load routes");
  }
  return response.json();
}

export async function fetchRouteByName(routeName, token) {
  const response = await fetch(`${API_URL}/saved_routes/fetch_route_by_name_and_username?routeName=${routeName}`, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`,
    }
  });
  if (!response.ok) {
    const errorMessage = await response.text();
    throw new Error(errorMessage || "Failed to fetch route");
  }
  return response.json();
}

export async function deleteRoute(routeName, token) {
  const response = await fetch(`${API_URL}/saved_routes/delete_route?routeName=${routeName}`, {
    method: "DELETE",
    headers: {
      Authorization: `Bearer ${token}`,
    }
  });
  if (!response.ok) {
    const errorMessage = await response.text();
    throw new Error(errorMessage || "Failed to delete this route");
  }
  return response.ok;
}