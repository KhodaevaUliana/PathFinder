import { useState } from 'react';

export default function LogIn ({onLogin, onSignup}) {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");

    const handleSubmit = (e, mode) => {
      e.preventDefault();
      setError("");

      const url = mode === 'login' ?
        "http://localhost:8080/auth/login" :
        "http://localhost:8080/auth/signup";
      fetch(url, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, password }),
        })
      .then((response) => {
             if (!response.ok) {
               return response.text().then((message) => {
                 throw new Error(message || `${mode} failed`);
               });
             }
             return response.text(); // the JWT token as plain text
           })
      .then((data) => {
        if (mode === 'login') {
          onLogin(data);
        } else {
          onSignup();
        }
      })
      .catch((error) => {
        setError(error.message);
      });
    }

    return (
      <form onSubmit = {handleSubmit}>
        <div>
          <label> Username </label>
          <input type="text" value={username} onChange={(e) => setUsername(e.target.value)}/>
        </div>
        <div>
          <label> Password </label>
          <input type="password" value={password} onChange={(e) => setPassword(e.target.value)}/>
          {error && <p className = "error"> Error: {error} </p>}
        </div>
        <div className = "auth-buttons">
          <button type="submit" onClick={(e) => handleSubmit(e, "login")} className = "auth-button"> Log in </button>
          <button type="submit" onClick={(e) => handleSubmit(e, "signup")} className = "auth-button"> Sign up </button>
        </div>
      </form>
    );



}