import AuthForm from "./AuthForm";
import {useState, useEffect} from "react";
import { logIn, signUp, deleteUser } from "../../utils/api-auth";

function AuthControl({ token, onLogin, handleLogOut }) {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [signUpSuccess, setSignUpSuccess] = useState(false);
  const [deleteAttempt, setDeleteAttempt] = useState(false);

  const handleSubmit = async (mode) => {
    setError("");
    setSignUpSuccess(false);
    try {
      if (mode === "signup") {
        const response = await signUp(username, password);
        setSignUpSuccess(true);
      } else { //sign-up mode
        const token = await logIn(username, password);
        onLogin(token);
      }
    } catch (error) {
      setError(error.message);
    }
  };

  const onChangeUsername = (e) => setUsername(e.target.value);
  const onChangePassword = (e) => setPassword(e.target.value);

  const onLogOut = () => {
    setUsername("");
    setPassword("");
    handleLogOut();
  }

  const onDeleteAttempt = () => {
    setDeleteAttempt(true);
  }

  const onDeleteCancel = () => {
      setDeleteAttempt(false);
    }

  const handleDelete = async () => {
    setDeleteAttempt(false);
    try {
      await deleteUser(token);
    } catch (error) {
      setError(error.message);
    } finally {
      onLogOut();
    }
  }



  return (
    <AuthForm
      token={token}
      handleSubmit={handleSubmit}
      username={username}
      password={password}
      onChangeUsername={onChangeUsername}
      onChangePassword={onChangePassword}
      onLogOut={onLogOut}
      error={error}
      signUpSuccess={signUpSuccess}
      deleteAttempt={deleteAttempt}
      onDeleteAttempt={onDeleteAttempt}
      onDeleteCancel={onDeleteCancel}
      handleDelete={handleDelete}
    />
  );


}

export default AuthControl;