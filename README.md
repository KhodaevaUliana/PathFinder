# PathFinder
A routing app that calculates routes between two points in Dresden, with the ability for authorized users to save and fetch routes. Full-stack project with Java/Spring backend and React frontend. Click [here](https://pathfinder.khodaeva.com) to test!

![Demo](https://github.com/KhodaevaUliana/PathFinder/blob/main/Secondattempt-ezgif.com-video-to-gif-converter.gif)

For the best experience, use a laptop or desktop computer, although adjustments have been made to ensure the mobile version is usable. Also, note that routes outside of Dresden are not supported.

## Features
- **Core functionality**: Calculate routes between any two points in Dresden: just click on the map and see the route! No registration required at this step

- Sign up, log in, and enjoy extra features for authorized users. Save your favorite routes and fetch them later. Don't hesitate to give your routes funny names: you can always delete any route you've saved or your account entirely.
  
- Full-stack architecture: Spring Boot backend with RESTful API and reponsive web interface build with React.
  
- Authentication and authorization with Spring Security
  
- Test coverage includes unit tests (with and without mock objects) and integration tests using Testcontainers.

## Tech stack
See detailed descriptions of the backend [here](https://github.com/KhodaevaUliana/PathFinder/tree/main/backend) and of the frontend [here](https://github.com/KhodaevaUliana/PathFinder/tree/main/frontend).

- **Backend**: Java, Spring Boot, Hibernate, REST API, Spring Security, and JWT
  
- **Frontend**: React, Leaflet
  
- **Database**: PostgreSQL
  
- Using geospatial data from Open Street Maps.

## How to use:
Just click [here](https://pathfinder.khodaeva.com)!

## Deployment:
The app was deployed on AWS, leveraging EC2 for hosting, CloudFront for content delivery, and Route 53 for DNS management. Many thanks to @RusFortunat for his help with the initial setup and pipeline!


## License:
