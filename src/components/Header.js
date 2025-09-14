import RoutePlottingMessage from "./Route/RoutePlottingMessage";

export default function Header({ points, distance, errorMessage, onRefresh }) {
  return (
    <div className="header">
      <h1>Let's explore Monaco together!</h1>
      <RoutePlottingMessage points={points} distance={distance} errorMessage={errorMessage} />
      <button
        onClick={onRefresh}
        style={{
          margin: "20px",
          color: "magenta",
          fontWeight: "bold",
          fontSize: "20px",
        }}
      >
        Refresh
      </button>
    </div>
  );
}
