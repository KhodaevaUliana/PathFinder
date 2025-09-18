import "./Auth.css";

function AuthForm ({token, handleSubmit, username, password,
                   onChangeUsername, onChangePassword, onLogOut, error, signUpSuccess,
                   deleteAttempt, onDeleteAttempt, onDeleteCancel, handleDelete}) {
  if (token) {
    return (
      <div>
        <h2>You are logged in</h2>
        <button onClick={onLogOut} className="auth-button">
          Log out
        </button>
        <button onClick={onDeleteAttempt} className="delete-button">
          Delete account
        </button>
      {deleteAttempt &&
        <div>
          <h2 className="confirm-message"> Are you sure to delete your account? </h2>
          <button className="delete-button" onClick={handleDelete}> Yes </button>
          <button className="cancel-delete-button"  onClick={onDeleteCancel}> No </button>
        </div>}
      </div>
    );
  } else {
    return (
    <div>
      <div>
        <label className="form-row">Username</label>
        <input type="text" value={username} onChange={onChangeUsername} autoComplete="off"/>
      </div>

      <div>
        <label className="form-row">Password</label>
        <input type="password" value={password} onChange={onChangePassword} autoComplete="off"/>
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
      {signUpSuccess && <p className="sign-up-success"> You've successfully signed up </p>}
    </div>
    );
  }
}

export default AuthForm;