call runcrud.bat
if "%ERRORLEVEL%" == "0" goto runbrowser
echo.
echo RUNCRUD has errors - breaking work.
goto fail

:runbrowser
"C:\Program Files (x86)\Google\Chrome\Application\chrome.exe" http://localhost:8080/crud/v1/task/getTasks
goto end

:fail
echo.
echo There were errors.

:end
echo.
echo Work is finished.