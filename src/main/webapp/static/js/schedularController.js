app.controller("schedularController", function($http, $scope, $location,
		$rootScope,myConfig) {

	$scope.empty = function() {

		$scope.hmstimeslice = "";
		$scope.hmsdate = "";
		$scope.hmsmonth = "";
		$scope.hmsminuteInterval = "";
		$scope.hmsday = "";
		$scope.hmshourInterval = "";
		$scope.hmsdayOfWeek = "";
		$scope.hmsdays = [];

		$scope.smstimeslice = "";
		$scope.smsdate = "";
		$scope.smsmonth = "";
		$scope.smsminuteInterval = "";
		$scope.smsday = "";
		$scope.smshourInterval = "";
		$scope.smsdayOfWeek = "";
		$scope.smsdays = [];
		
		$rootScope.success_msg = '';
		$rootScope.response_msg = '';
	};

	$scope.schduleMinuteJob = function(jobName, scheduleRadio) {

		console.log("schduleMinuteJob");

		var minuteInterval = "";

		if (jobName == 'HMS_JOB')
			minuteInterval = $scope.hmsminuteInterval;
		else
			minuteInterval = $scope.smsminuteInterval;

		$http({
			url : myConfig.url + "" + myConfig.port + ""+ myConfig.applicationName+'scheduleJobs',
			method : 'POST',

			data : {
				"jobName" : jobName,
				"minuteInterval" : minuteInterval,
				"scheduleRadio" : scheduleRadio
			},
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(response) {
			$rootScope.response_msg = 'Schedule Job Successful';
			$location.path("/configurationschedule");
		}).error(function(data) {

			alert("Job is scheduled error " + data);

		});

		$scope.empty();
	}

	$scope.reScheduleJob = function(Jobname) {
		console.log("inside reScheduleJob  " + Jobname);

		$http.get(myConfig.url + "" + myConfig.port + ""+ myConfig.applicationName+'startJob/'+ Jobname).success(
				function(data) {
					console.log("inside reScheduleJob success" + data);

					$rootScope.success_msg = 'Start Job Successful';
					$scope.getAllschduleRunningJob();
					//$location.path("/listschedulejob");

				}).error(function(data) {
			console.log("inside reScheduleJob error" + data);
		});
	}

	$scope.schduleWeeklyJob = function(jobName, scheduleRadio) {

		var timeslice = "";
		var dayOfWeek = "";
		var days = [];

		if (jobName == 'HMS_JOB') {

			timeslice = $scope.hmstimeslice;
			dayOfWeek = $scope.hmsdayOfWeek;
			days = $scope.hmsdays;

		} else {

			timeslice = $scope.smstimeslice;
			dayOfWeek = $scope.smsdayOfWeek;
			days = $scope.smsdays;
		}

		for (var int = 0; int < days.length; int++) {
			dayOfWeek += days[int];
			if (int != (days.length) - 1) {
				dayOfWeek += ',';
			}
		}

		$http({
			url :myConfig.url+ ""+myConfig.port + ""+ myConfig.applicationName+'scheduleJobs',
			method : 'POST',

			data : {
				"jobName" : jobName,
				"time" : timeslice,
				"scheduleRadio" : scheduleRadio,
				"dayOfWeek" : dayOfWeek
			},
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(response) {
			$rootScope.response_msg = 'Reschedule Job Successful';
			$location.path("/configurationschedule");
		}).error(function(data) {

			alert("Job is scheduled error " + data);

		});

		$scope.empty();
	}

	$scope.stopScheduleJob = function(jobName) {
		console.log("inside stopScheduleJob go function " + jobName);
		$http.get(myConfig.url + "" + myConfig.port + ""+ myConfig.applicationName+'stopJob/' + jobName).success(
				function(data) {

					if (data.responseCode == 200) {
						console.log("inside if responseCode ");
						$rootScope.success_msg = 'Stop Job Successful';
						$scope.getAllschduleRunningJob();
						//$location.path("/listschedulejob");

					}

					$rootScope.authenticated_show_link = true;
				}).error(function(data, status, headers) {

			$location.path("/exception");

		});

	}

	$scope.openConfirmBox = function(jobName) {

		$.confirm({
			text : "Are you sure you want to Stop this Job?",
			title : "Confirmation required",
			confirm : function(button) {
				$scope.stopScheduleJob(jobName);
			},
			cancel : function(button) {
			},
			confirmButton : "Yes",
			cancelButton : "No",
			post : true,
			confirmButtonClass : "btn-danger",
			cancelButtonClass : "btn-default",
			dialogClass : "modal-dialog" // Bootstrap classes for large modal
		});
	}
 
	$scope.getAllschduleRunningJob = function() {
		//$rootScope.success_msg = "";
		$http.get(myConfig.url + "" + myConfig.port + ""+ myConfig.applicationName+'populateJobs').success(function(data) {
			console.log("inside  success" + data);
			$scope.listSchedulesJob = data.quartzList;

		}).error(function(data) {

			console.log("Job is scheduled error " + data);

		});
		;

	}

	$scope.schduleYearlyJob = function(jobName, scheduleRadio) {

		var timeslice = '';
		var date = '';
		var month = '';

		if (jobName == 'HMS_JOB') {

			timeslice = $scope.hmstimeslice;
			date = $scope.hmsdate;
			month = $scope.hmsmonth;

		} else {

			timeslice = $scope.smstimeslice;
			date = $scope.smsdate;
			month = $scope.smsmonth;
		}

		$http({
			url : myConfig.url + "" + myConfig.port + ""+ myConfig.applicationName+'scheduleJobs',
			method : 'POST',
			data : {
				"jobName" : jobName,
				"time" : timeslice,
				"monthDate" : date,
				"month" : month,
				"scheduleRadio" : scheduleRadio
			},
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(response) {
			$rootScope.response_msg = 'Schedule Job Successful';
			$location.path("/configurationschedule");
		}).error(function(data) {

			alert("Job is scheduled error " + data);

		});

		$scope.empty();
	}

	$scope.schduleDailyJob = function(jobName, scheduleRadio) {

		var timeslice = '';

		if (jobName == 'HMS_JOB')
			timeslice = $scope.hmstimeslice;
		else
			timeslice = $scope.smstimeslice;

		$http({
			url : myConfig.url + "" + myConfig.port + ""+ myConfig.applicationName+'scheduleJobs',
			method : 'POST',
			data : {
				"jobName" : jobName,
				"time" : timeslice,
				"scheduleRadio" : scheduleRadio
			},
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(response) {
			$rootScope.response_msg = 'Schedule Job Successful';
			$location.path("/configurationschedule");
		}).error(function(data) {

			alert("Job is scheduled error " + data);

		});
		
		$scope.empty();
	}

	$scope.schduleMonthlyJob = function(jobName, scheduleRadio) {

		var timeslice = '';
		var date = '';
		
		if (jobName == 'HMS_JOB') {

			timeslice = $scope.hmstimeslice;
			date = $scope.hmsdate;

		} else {

			timeslice = $scope.smstimeslice;
			date = $scope.smsdate;
		}

		$http({
			url : myConfig.url + "" + myConfig.port + ""+ myConfig.applicationName+'scheduleJobs',
			method : 'POST',
			data : {
				"jobName" : jobName,
				"time" : timeslice,
				"monthDate" : date,
				"scheduleRadio" : scheduleRadio
			},
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(response) {
			$rootScope.response_msg = 'Reschedule Job Successful';
			$location.path("/configurationschedule");
		}).error(function(data) {

			alert("Job is scheduled error " + data);

		});

		$scope.empty();

	}
	$scope.schduleHourlyJob = function(jobName, scheduleRadio) {

		var hourInterval = "";
		
		if (jobName == 'HMS_JOB')
			hourInterval = $scope.hmshourInterval;
		else
			hourInterval = $scope.smshourInterval;
		
		$http({
			url : myConfig.url + "" + myConfig.port + ""+ myConfig.applicationName+'scheduleJobs',
			method : 'POST',
			data : {
				"jobName" : jobName,
				"hourInterval" : hourInterval,
				"scheduleRadio" : scheduleRadio
			},
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(response) {
			$rootScope.response_msg = 'Reschedule Job Successful';
			$location.path("/configurationschedule");
		}).error(function(data) {

			alert("Job is scheduled error " + data);

		});
		
		$scope.empty();

	}

});

app.filter('monthName', [ function() {
	return function(monthNumber) {
		var monthNames = [ 'January', 'February', 'March', 'April', 'May',
				'June', 'July', 'August', 'September', 'October', 'November',
				'December' ];
		return monthNames[monthNumber - 1];
	}
} ]);