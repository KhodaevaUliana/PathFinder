export async function logIn(username, password) {
  const response = await fetch(`${API_URL}/auth/login`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ username, password }),
  });
  if (!response.ok) {
    const errorMessage = await response.text();
    throw new Error(errorMessage || "Log in failed");
  }
  return response.text();
}

export async function signUp(username, password) {
  const response = await fetch(`${API_URL}/auth/signup`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({username, password}),
  });
  if (!response.ok) {
    const errorMessage = await response.text();
    throw new Error(errorMessage || "Sign up failed");
  }
  return response.ok;
}

export async function deleteUser(token) {
  const response = await fetch(`${API_URL}/auth/delete`, {
    method: "DELETE",
    headers: {
      Authorization: `Bearer ${token}`,
    }
  });
  if (!response.ok) {
    const errorMessage = await response.text();
    throw new Error(errorMessage || "Sign up failed");
  }
  return response.ok;
}