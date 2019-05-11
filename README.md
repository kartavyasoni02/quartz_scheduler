INTRODUCTION
------------

•This is a brief overview of the SpringMVC, Quartz scheduler and AngularJS. That is included with a complete architecture. I have created UI pages using MVC pattern of Angular JS. We have used Quartz scheduler. It has to execute at a particular time or want to repeat the same job at a particular interval. So, you can configure easily, this application is robust to meet our scheduling needs.

INSTALLATION
------------
   SOFTWARE LIST.
 
  1) JDK 1.8
  2) Maven 3.0
		
CONFIGURATION
-------------
Open application.properties file and do below configuration.

   database properties:- Add database credentials and URL in this property.
   security properties:- Change database query and rest API path in this property.
   view resolver :- Add your views path in this property.
   response message :- You can configure all reponse messages in this property.


DEPLOYMENT
---------------

Change logging file path in log4j properties.
	<Property name="log-path">G:\application_log\sping_boot</Property>


REST SERVICES
-------------

For get all configured Jobs Listing : 

response code  : 200 (process successfully)
response code  : 0 (No data found)
response code  : -2 Error occurred

Method : GET
URL: http://localhost:8080/Quartz_Scheduler/populateJobs

Method : POST

URL : http://localhost:8080/Quartz_Scheduler/scheduleJobs

The time set should be of the format (HH:MM for example 12:15)
The date set should be of the format (MM-DD-YYYY for example 25-04-2011)

mandatory field : scheduleRadio , jobName
scheduleRadio : yearly , monthly , weekly , daily , hourly , minutes

for scheduleRadio type : minutes : 
{"jobName":"SMS_JOB","scheduleRadio":"minutes","minuteInterval": "1"}

yearly: 

{"jobName":"SMS_JOB","scheduleRadio":"yearly","time": "10:52","monthDate":"15","month":"5"}

monthly : 
{"jobName":"SMS_JOB","scheduleRadio":"monthly","time": "10:52","monthDate":"15"}

weekly:
{"jobName":"SMS_JOB","scheduleRadio":"weekly","time": "12:02","dayOfWeek":"MON,TUE,WED,FRI"}

daily : 
{"jobName":"SMS_JOB","scheduleRadio":"daily","time": "10:52"}

hourly: 
{"jobName":"SMS_JOB","scheduleRadio":"hourly","hourInterval": "1"}

minutes:
{"jobName":"SMS_JOB","scheduleRadio":"minutes","minuteInterval": "1"}


## Run Command Line
-------------------
1 Go to Project folder and build project : mvn -e clean package install
2 Start server
3 Go to browser and hit URL : http://localhost:8080/Quartz_Scheduler

MAINTAINERS
-----------

Kartavya Soni(kartavya.soni02@gmail.com)

