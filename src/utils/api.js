export async function fetchRoute(start, finish) {
  const response = await fetch(
    `http://localhost:8080/route?startLatitude=${start.lat}&startLongitude=${start.lng}&finishLatitude=${finish.lat}&finishLongitude=${finish.lng}`
  );
  if (!response.ok) {
    throw new Error(response.text());
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
      body: JSON.stringify(routeToSave)
    });
    if (!response.ok) {
      const errorMessage = await response.text();
      throw new Error(errorMessage || "The route was not saved. Try another name");
    }
    return response.ok;
}