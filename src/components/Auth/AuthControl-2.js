import AuthForm from "./AuthForm-2";
import {useState} from "react";
import { logIn, signUp } from "../../utils/api-auth";

function AuthControl({ token, onLogin, onLogOut }) {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [signUpSuccess, setSignUpSuccess] = useState(false);

  const handleSubmit = async (mode) => {
    setError("");
    setSignUpSuccess(false);
    try {
      if (mode === "signup") {
        const response = await signUp(username, password);
        setSignUpSuccess(true);
      } else { //sign-up mode
        const response = await logIn(username, password);
        onLogin(response.text());
      }
    } catch (error) {
      setError(error.message);
    }
  };

  const onChangeUsername = (e) => setUsername(e.target.value);
  const onChangePassword = (e) => setPassword(e.target.value);

  return (
    <AuthForm-2
      token={token}
      handleSubmit={handleSubmit}
      username={username}
      password={password}
      onChangeUsername={onChangeUsername}
      onChangePassword={onChangePassword}
      onLogOut={onLogOut}
      error={error}
    />
  );


}

export default AuthControl;