## Solactive  - JMeter Performance Reports
* Before run this line
	* Delete the file previous File **"Solactive_Performance_Test.csv"**
	* Delete the previous folder **"JMeter-Reports-Solactive"**
```
	jmeter -n -t D:/Projects/Solactive_RealTime_RestController/Jmeter_Performance/Solactive_Tests_RestController.jmx -l D:/Projects/Solactive_RealTime_RestController/Jmeter_Performance/Solactive_Performance_Test.csv -e -o D:/Projects/Solactive_RealTime_RestController/Jmeter_Performance/JMeter-Reports-Solactive/
```

### Report Summary
> Open the **index.html** whitin the Folder: 
```
		{Your Main Folder}/Solactive_RealTime_RestController/Jmeter_Performance/JMeter-Reports-Solactive/index.html
```

### Sequential Report Summary
```
	jmeter -n -t D:/Projects/Solactive_RealTime_RestController/Jmeter_Performance/Solactive_Tests_RestController.jmx -l D:/Projects/Solactive_RealTime_RestController/Jmeter_Performance/Solactive_Performance_Test-1.csv -e -o D:/Projects/Solactive_RealTime_RestController/Jmeter_Performance/HTMLReports-1/
	jmeter -n -t D:/Projects/Solactive_RealTime_RestController/Jmeter_Performance/Solactive_Tests_RestController.jmx -l D:/Projects/Solactive_RealTime_RestController/Jmeter_Performance/Solactive_Performance_Test-2.csv -e -o D:/Projects/Solactive_RealTime_RestController/Jmeter_Performance/HTMLReports-2/
	jmeter -n -t D:/Projects/Solactive_RealTime_RestController/Jmeter_Performance/Solactive_Tests_RestController.jmx -l D:/Projects/Solactive_RealTime_RestController/Jmeter_Performance/Solactive_Performance_Test-3.csv -e -o D:/Projects/Solactive_RealTime_RestController/Jmeter_Performance/HTMLReports-3/
```

### To run these agregations you will need to install JMeter Plugins
* **CMDRunner.jar** and  **JMeterPluginsCMD.jar**
	* Aggregate Report
```
	java -jar CMDRunner.jar  --tool Reporter --generate-csv Aggregate-Solactive-50-Threads.csv --input-jtl out/test-results.csv --plugin-type AggregateReport
	java -jar JMeterPluginsCMD.jar --generate-csv Aggregate-Solactive-50-Threads.csv --input-jtl testResults.jtl --plugin-type AggregateReport
```