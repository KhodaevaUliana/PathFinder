# PathFinder-Backend

Java/Spring Boot backend for a routing app. 
Provides RESTful APIs for route calculation, 
user authentication,
and storing/fetching user routes.

This repository is only for backend, 
frontend can be found [in another repository]([https://github.com/KhodaevaUliana/PathFinder-Backend]),
full project description is [here]([https://github.com/KhodaevaUliana/PathFinder])

## Features
- Use data from Open Street Map to build a graph
- Route calculation boils down to shortest path problem on a graph
- Use Dijkstra algorithm to find the shortest path
- User authentication & authorization (Spring Security + JWT)  
- Save, fetch, and delete user routes: powered by PostgreSQL
- RESTful API endpoints 
- Unit test with Mock objects and integration tests with Testcontainers

## API Endpoints

| Method | Endpoint                                       | Parameters                   | Description                                                                   |
|--------|------------------------------------------------|------------------------------|-------------------------------------------------------------------------------|
| GET    | /route/start                                   | Start and finish coordinates | Calculates a route using live OpenStreetMap data. No authentication required. |
| POST   | /auth/signup                                   | Username and password        | Registers a new user.                                                         |
| POST   | /auth/login                                    | Username and password        | Log in and receive JWT token                                                  |
| DELETE | /auth/delete                                   | --                           | Delete user's account. Requires authentication.                               |
| GET    | /route/start                                   | Start and finish coordinates | Calculates a route using live OpenStreetMap data. No authentication required. |
| POST   | /saved_routes/save                             | Route                        | Save a route. Requires authentication.                                        |
| GET    | /saved_routes/fetch_list_of_routes             | --                           | Returns a list of the user’s route names. Requires authentication.            |
| GET    | /saved_routes/fetch_route_by_name_and_username | Route's name                 | Fetch a saved route by its name. Requires authentication.                     |
| DELETE | /saved_routes/delete_route                     | Route's name                 | Delete a saved route by its name. Requires authentication.                    |

## Tech stack
- Java, Spring Boot, Hibernate
- PostgreSQL
- Spring Security, JWT
- Rest API
- JUnit and Testcontainers

## How to use

## Future improvements
- Increase efficiency by replacing Dijkstra’s algorithm with A*-algorithm.
- Move sensitive information out of application.properties and use environment variables instead.


