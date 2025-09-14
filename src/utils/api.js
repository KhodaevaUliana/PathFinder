export async function fetchRoute(start, finish) {
  const response = await fetch(
    `http://localhost:8080/route?startLatitude=${start.lat}&startLongitude=${start.lng}&finishLatitude=${finish.lat}&finishLongitude=${finish.lng}`
  );
  if (!response.ok) {
    throw new Error("Failed to fetch route");
  }
  return response.json();
}

export async function saveRoute(routeToSave, token) {
  const response = await fetch(
    "http://localhost:8080/saved_routes/save", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify(routeToSave),
    }
    if (!response.ok) {
      throw new Error("The route was not saved");
    }
    return response.ok;
  );

}