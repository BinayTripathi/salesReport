@echo off
:: Script to delete .gitkeep files recursively
:: Uses "postgress-persist" as the default directory if no argument is provided

:: Check if the root directory is provided as an argument
if "%~1"=="" (
    echo No directory specified. Using "postgress-persist" as the default.
    set "ROOT_DIR=postgress-persist"
) else (
    set "ROOT_DIR=%~1"
)

:: Recursively find and delete all .gitkeep files
for /r "%ROOT_DIR%" %%f in (.gitkeep) do (
    echo Deleting %%f
    del "%%f"
)

echo Process complete! All .gitkeep files have been deleted.
