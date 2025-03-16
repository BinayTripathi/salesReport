@echo off
:: Script to create .gitkeep files in empty directories recursively

:: Check if root directory is provided as an argument
if "%~1"=="" (
    echo Please provide the root directory as an argument.
    echo Usage: create_gitkeep.bat [root_directory]
    exit /b 1
)

set "ROOT_DIR=%~1"

:: Function to check and create .gitkeep in empty directories
for /f "delims=" %%d in ('dir "%ROOT_DIR%" /ad /s /b') do (
    :: Check if the directory is empty
    for /f %%e in ('dir "%%d" /a-d /b 2^>nul ^| find /v /c ""') do (
        if %%e EQU 0 (
            echo Creating .gitkeep in %%d
            echo. > "%%d\.gitkeep"
        )
    )
)

echo Process complete!
pause
