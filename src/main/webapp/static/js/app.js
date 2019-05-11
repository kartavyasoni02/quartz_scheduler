var app = angular.module("myApp", [ 'ngRoute' ])

app.constant("myConfig", {
    "url": "http://localhost:",
    "port": "8080/",
   "applicationName": "Quartz_Scheduler/"
//"applicationName": ""
})
/*Angular routing*/
app.config([ '$routeProvider',

function($routeProvider) {

	$routeProvider.
	when('/', {
		templateUrl : 'static/html/home.html'
	}).
	when('/configurationschedule', {
		templateUrl : 'static/html/configuration-schedule-job.html',
		controller : 'schedularController'
	}).
	when('/listschedulejob', {
		templateUrl : 'static/html/lists-schdule-Job.html',
		controller : 'schedularController'
	}).
	otherwise({
		redirectTo : '/'
	});

} ]);
