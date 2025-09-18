import AuthForm from "./AuthForm";

export default function AuthControl({ token, onLogin, onSignup, onLogOut, signUpSuccess }) {
  if (token) {
    return (
      <div>
        <h2>You are logged in</h2>
        <button onClick={onLogOut} className="auth-button">
          Log out
        </button>
      </div>
    );
  }

  return (
    <div>
      <AuthForm onLogin={onLogin} onSignup={onSignup} />
      {signUpSuccess && <p className="error">You've signed up successfully</p>}
    </div>
  );
}
