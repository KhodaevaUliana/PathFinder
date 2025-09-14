function RoutePlottingMessage({ points, distance, errorMessage }) {
  if (errorMessage) return <h2>{errorMessage}</h2>;
  if (points.length === 2 && distance !== null) {
    return <h2>The distance is {Math.round(distance / 10) / 100} km. Press Refresh to start over.</h2>;
  }
  if (points.length === 0) return <h2>Click on the start point</h2>;
  return <h2>Click on the finish point</h2>;
}

export default RoutePlottingMessage;
