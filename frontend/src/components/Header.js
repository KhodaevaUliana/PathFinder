import RoutePlottingMessage from "./Route/RoutePlottingMessage";
import "./Header.css";

export default function Header({ points, distance, errorMessage, onRefresh }) {
  return (
    <div className="header">
      <h1 className="title">Let's explore Dresden together!</h1>
      <RoutePlottingMessage points={points} distance={distance} errorMessage={errorMessage} />
      <button onClick={onRefresh} className="refresh-button">
        Refresh
      </button>
    </div>
  );
}
