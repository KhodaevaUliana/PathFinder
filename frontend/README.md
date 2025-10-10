# PathFinder-Frontend

React frontend for a routing app.
Provides a responsive web interface for calculating routes, 
managing user accounts, and visualizing routes on a map (powered by Leaflet). 
Communicates with the Java/Spring backend via REST API.


## Features
- Interactive map powered by Leaflet: Users can click on the map
to select the starting and finishing points for route calculation, the calculated route
will be shown on the map
- User Authentication: Signup and login functionality with JWT-based authentication.
- Authorized users can save routes using the SaveRouteForm, 
which appears each time a user plots a new route.
- For authorized users: Displays a list of saved routes in a dropdown menu (see SavedRoutesDropdown component). 
Click on a route’s name, then choose whether to view or delete the route.
- Flexible map centering: By default, the map centers around München Hauptbahnhof. 
If a route is displayed or a user has clicked on a starting point, 
the map centers around the starting point.
- Error handling: Errors are displayed near the corresponding interface element
and are shown in red to make them noticeable.

## Tech stack
- React.js
- Leaflet for the map
- HTML, CSS
- JavaScript Fetch API for REST calls


## Future improvements
- Increase test coverage
- Improve UX design (feedback from users would be appreciated)
