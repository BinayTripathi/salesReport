# salesReport


# Pre-requisites

* Java 17
* Gradle
* Docker desktop


# Major components

* Springboot code
* Docker compose file to build Keycloak as OIDC Connect provider for authentication
* `postgress-persist` directory mapped in docker to postgres volume with all ready to use configuration for Keycloak
* `Postman-collection` directory containing postman collection to be imported to try the endpoints

# How to run the code

We have provided scripts to execute the code.

1. Execute (by double clicking or invoking from command prompt) `runSalesReport.bat` present in root directory. Following steps are triggered in the batch script
   1. Clean .gitkeep files present in folders that are supposed to be empty at startup
   2. Runs docker compose
   3. Builds springboot application and generate Test coverage report
   4. Check for the readiness of Keycloak
   5. Runs the springboot application.

2. Testing the endpoints in postman
   1. Import postman collection from the folder `Postman-collection` present in the root directory.
      ![postman_import.png](images/postman_import.png)
   2. Navigate to `Authoriation` tab and click `Get New Access Token`
      ![TriggerAccessTokenRequest.png](images/TriggerAccessTokenRequest.png)
   3. A new Keycloak window opens for sign-in. Enter username and password as `tc-user` and sign-in. 
   `tc-user` is a user created to emulate a user in the organisation having access to the APIs. This user is created in Keycloak Realm during configuration
      ![Authenticate.png](images/Authenticate.png)
   4. Click on `Use Token` to use the bearer token for the API call.
      ![UseToken.png](images/UseToken.png)
   5. Click the 'Send' button to make the API call.
      ![authenticated_call.png](images/authenticated_call.png)
   6. Repeat the same for all the 4 APIs imported.
   7. If an expired token is used and session has expired( 10 mins), 401 Unauthorized error is received. If the session has not expired, token gets refreshed.
      ![unauthenticated_call.png](images/unauthenticated_call.png)


# Test coverage
![coverage.png](images/coverage.png)

Coverage report will be created inside `salesReport/build/reports/jacoco/test/html/index.html`
Configuration package has been excluded on purpose from the coverage validation.


# Scope for improvement
1. Adding Swagger 
2. Adding integration test
3. Running the springboot app within docker container. 


