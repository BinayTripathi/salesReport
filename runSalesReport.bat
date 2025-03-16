@echo off
:: Script to automate tasks: delete .gitkeep files, run Docker Compose, and build with Gradle

:: Step 1: Call delete_gitkeep.bat
echo Deleting .gitkeep files...
call delete_gitkeep.bat
if %ERRORLEVEL% NEQ 0 (
    echo Failed to delete .gitkeep files.
    exit /b 1
)

:: Step 2: Run Docker Compose
echo Starting Docker containers...
docker-compose up -d
if %ERRORLEVEL% NEQ 0 (
    echo Failed to start Docker containers.
    exit /b 1
)

:: Step 3: Run Gradle clean and build
echo Running Gradle build...
call ./gradlew clean build jacocoTestReport
if %ERRORLEVEL% NEQ 0 (
    echo Gradle build failed.
    exit /b 1
)


:: Step 4: Wait for containers to be ready
echo Waiting for all containers to be ready...
:wait_loop
timeout /t 10 > nul
docker-compose ps | find "Up" > nul
if %ERRORLEVEL% NEQ 0 (
    echo Containers not ready yet. Checking again...
    goto wait_loop
)


:: Step 5: Check if the service is accessible at http://localhost:8080/
echo Checking if the service is up...
set "MAX_RETRIES=24"
set "WAIT_SECONDS=5"
set /a "ATTEMPT=1"

:check_url
curl --silent --head http://localhost:8080/ > nul
if %ERRORLEVEL% EQU 0 (
    echo Service is up and running!
    goto springboot_run
)

if %ATTEMPT% GEQ %MAX_RETRIES% (
    echo Service is not responding after %MAX_RETRIES% attempts.
    exit /b 1
)

echo Attempt %ATTEMPT%: Service not ready. Retrying in %WAIT_SECONDS% seconds...
set /a "ATTEMPT+=1"
timeout /t %WAIT_SECONDS% > nul
goto check_url
echo All containers are ready!


:springboot_run
./gradlew bootRun   


echo All tasks completed successfully!

pause
