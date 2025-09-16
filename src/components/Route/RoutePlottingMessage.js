function RoutePlottingMessage({ points, distance, errorMessage }) {
  if (errorMessage) return <h2>{errorMessage}</h2>;
  if (points.length === 2 && distance !== null) {
    return <h2>The distance is {Math.round(distance / 10) / 100} km. Press
    <span style={{color: "magenta"}}> Refresh </span>
     to start over.</h2>;
  }
  if (points.length === 0) return <h2>Click on the
  <span style={{color: "green"}}>  start </span> point</h2>;
  return <h2>Click on the <span style={{color: "red"}}>  finish </span> point</h2>;
}

export default RoutePlottingMessage;
