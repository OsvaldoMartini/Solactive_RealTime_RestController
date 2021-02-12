@ECHO OFF

:: JMETER Installation Folder
:: SET JMETER=C:\Appl\apache-jmeter-5.3\bin\jmeter
SET JMETER=D:\Installed\apache-jmeter-5.3\bin\jmeter

:: CURRENT DATETIME
SET DAY=%date:~0,2%
SET MONTH=%date:~3,2%
SET YEAR=%date:~-4%
SET HOUR=%time:~0,2%
IF "%HOUR:~0,1%" == " " SET HOUR=0%HOUR:~1,1%
SET MIN=%time:~3,2%
IF "%MIN:~0,1%" == " " SET MIN=0%MIN:~1,1%
SET SEC=%time:~6,2%
IF "%SEC:~0,1%" == " " SET SEC=0%SEC:~1,1%

:: WORK DIRS
SET WORK_DIR=D:\Projects\Solactive_RealTime_RestController\Jmeter_Performance
SET EXECUTIONS_DIR=%WORK_DIR%\executions
SET LOG_DIR=%WORK_DIR%\log
SET ENV=D1
SET HOST=%COMPUTERNAME%
SET EXECUTION_RESULT_DIR=%EXECUTIONS_DIR%\%ENV%\%HOST%\%YEAR%%MONTH%%DAY%_%HOUR%%MIN%%SEC%
IF NOT EXIST %EXECUTION_RESULT_DIR% MKDIR %EXECUTION_RESULT_DIR%

:: WORK FILES
SET TEST_PLAN_FILE=%WORK_DIR%\Solactive_Pattern_RestController.jmx
SET LOG_FILE=%EXECUTION_RESULT_DIR%\log.jtl
SET RUN_LOG_FILE=%EXECUTION_RESULT_DIR%\runLog.log
SET REPORT=%EXECUTION_RESULT_DIR%\Report
SET LOAD_GEN_NUMBER=1
SET DURATION=300

:: RUN TEST PLAN
SET CMD=%JMETER% -Jsample_variables=trxTimeStamp -JloadGenNumber=%LOAD_GEN_NUMBER% -Jduration=%DURATION% -n -t "%TEST_PLAN_FILE%" -l "%LOG_FILE%" -j "%RUN_LOG_FILE%" -Jjmeter.reportgenerator.overall_granularity=60000 -e -o "%REPORT%"
:: ECHO %CMD%
START %CMD%
