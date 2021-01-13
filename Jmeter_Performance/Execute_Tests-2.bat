@ECHO OFF

:: JMETER Installation Folder
:: SET JMETER=C:\Appl\apache-jmeter-5.3\bin\jmeter
SET JMETER=C:\Appl\apache-jmeter-5.3-m1-ccapp\bin\jmeter

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
SET ENV=M1
SET HOST=%COMPUTERNAME%
SET EXECUTION_RESULT_DIR=%EXECUTIONS_DIR%\%ENV%\%HOST%\%YEAR%%MONTH%%DAY%_%HOUR%%MIN%%SEC%
IF NOT EXIST %EXECUTION_RESULT_DIR% MKDIR %EXECUTION_RESULT_DIR%

:: WORK FILES
SET TEST_PLAN_FILE=%WORK_DIR%\Solactive_Pattern_Tests_RestController.jmx
SET LOG_FILE=%EXECUTION_RESULT_DIR%\log.jtl
SET RUN_LOG_FILE=%EXECUTION_RESULT_DIR%\runLog.log
SET REPORT=%EXECUTION_RESULT_DIR%\Report
SET LOAD_GEN_NUMBER=3
SET DURATION=5400

:: RUN TEST PLAN
SET CMD=%JMETER% -Jsample_variables=trxTimeStamp -JloadGenNumber=%LOAD_GEN_NUMBER% -Jduration=%DURATION% -n -t "%TEST_PLAN_FILE%" -l "%LOG_FILE%" -j "%RUN_LOG_FILE%" -Jjmeter.reportgenerator.overall_granularity=60000 -e -o "%REPORT%"
:: ECHO %CMD%
START %CMD%

:: START %JMETER%%JMETER% -Jsample_variables=trxTimeStamp -JloadGenNumber=%LOAD_GEN_NUMBER% -duration=%DURATION% -n -t "%TEST_PLAN_FILE%" -l "%LOG_FILE%" -j "%RUN_LOG_FILE%" -Jjmeter.reportgenerator.overall_granularity=60000 -e -o "%REPORT%" -n -t "C:\QA\ProjectsGIT\ccms_webservices_performance-r3\ccms_webservices_performance.jmx" -l "C:\QA\CCMS_WS_performance\ccms_ws_2020_04_22_001\log.jtl" -j "C:\QA\CCMS_WS_performance\ccms_ws_2020_04_22_001\runLog.log" -e -o "C:\QA\CCMS_WS_performance\ccms_ws_2020_04_22_001\Report"
