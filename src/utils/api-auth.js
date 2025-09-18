export async function logIn(username, password) {
  const response = await fetch("http://localhost:8080/auth/login", {
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
  const response = await fetch("http://localhost:8080/auth/signup", {
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