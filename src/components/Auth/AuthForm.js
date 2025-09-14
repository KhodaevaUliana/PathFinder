import { useState } from "react";

export default function AuthForm({ onLogin, onSignup }) {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  const handleSubmit = async (mode) => {
    setError("");

    const url =
      mode === "login" ? "http://localhost:8080/auth/login" : "http://localhost:8080/auth/signup";

    try {
      const response = await fetch(url, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, password }),
      });

      if (!response.ok) {
        const message = await response.text();
        throw new Error(message || `${mode} failed`);
      }

      const data = await response.text(); // JWT token for login
      if (mode === "login") {
        onLogin(data);
      } else {
        onSignup();
      }
    } catch (err) {
      setError(err.message);
    }
  };

  return (
    <div>
      <div>
        <label>Username</label>
        <input type="text" value={username} onChange={(e) => setUsername(e.target.value)}/>
      </div>

      <div>
        <label>Password</label>
        <input type="password" value={password} onChange={(e) => setPassword(e.target.value)}/>
        {error && <p className="error">Error: {error}</p>}
      </div>

      <div className="auth-buttons">
        <button type="button" onClick={() => handleSubmit("login")} className="auth-button">
          Log in
        </button>
        <button type="button" onClick={() => handleSubmit("signup")}className="auth-button">
          Sign up
        </button>
      </div>
    </div>
  );
}
