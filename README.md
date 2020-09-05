# coffeebeans_task

open REST end points:

  POST http://localhost:8081/api/v1/client/register
   
  POST http://localhost:8081/api/v1/client/authenticate
  
  GET  http://localhost:8082/oauth2/authorization/google  (OAuth2.0 google authorization endpoint)
  
  
  
  
secured REST end points:

  GET http://localhost:8081/api/v1/client/securedpoint
  
  POST://localhost:8081/api/v1/client/logout



* If any other port is used other than 8081  OAuth wont work as it has to updated in googlr developer console
