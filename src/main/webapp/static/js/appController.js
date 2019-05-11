app.controller("appController", function($http, $scope, $location) {

	$scope.showhmsminute = false;
	$scope.showhmshourly = false;
	$scope.showhmsdaily = false;
	$scope.showhmsweekly = false;
	$scope.showhmsmonthly = false;
	$scope.showhmsyearly = false;

	$scope.showsmsminute = false;
	$scope.showsmshourly = false;
	$scope.showsmsdaily = false;
	$scope.showsmsweekly = false;
	$scope.showsmsmonthly = false;
	$scope.showsmsyearly = false;

	$scope.hmsminutesload = function() {

		$scope.showhmsminute = true;

		$scope.showhmshourly = false;
		$scope.showhmsdaily = false;
		$scope.showhmsmonthly = false;
		$scope.showhmsyearly = false;
		$scope.showhmsweekly = false;

	}

	$scope.hmsyearlyload = function() {

		$scope.showhmsyearly = true;

		$scope.showhmsminute = false;
		$scope.showhmshourly = false;
		$scope.showhmsdaily = false;
		$scope.showhmsmonthly = false;
		$scope.showhmsweekly = false;

	}

	$scope.hmsdailyload = function() {

		$scope.showhmsdaily = true;

		$scope.showhmsminute = false;
		$scope.showhmshourly = false;
		$scope.showhmsweekly = false;
		$scope.showhmsmonthly = false;
		$scope.showhmsyearly = false;

	}

	$scope.hmshourlyload = function() {

		$scope.showhmshourly = true;

		$scope.showhmsminute = false;
		$scope.showhmsweekly = false;
		$scope.showhmsdaily = false;
		$scope.showhmsmonthly = false;
		$scope.showhmsyearly = false;

	}

	$scope.hmsmonthlyload = function() {

		$scope.showhmsmonthly = true;

		$scope.showhmsminute = false;
		$scope.showhmshourly = false;
		$scope.showhmsdaily = false;
		$scope.showhmsweekly = false;
		$scope.showhmsyearly = false;

	}

	$scope.hmsweeklyload = function() {

		$scope.showhmsweekly = true;

		$scope.showhmsminute = false;
		$scope.showhmshourly = false;
		$scope.showhmsdaily = false;
		$scope.showhmsmonthly = false;
		$scope.showhmsyearly = false;

	}

	$scope.smsminutesload = function() {

		$scope.showsmsminute = true;

		$scope.showsmshourly = false;
		$scope.showsmsdaily = false;
		$scope.showsmsmonthly = false;
		$scope.showsmsyearly = false;
		$scope.showsmsweekly = false;

	}

	$scope.smsyearlyload = function() {

		$scope.showsmsyearly = true;
		//$location.path("/yearly");

		$scope.showsmsminute = false;
		$scope.showsmshourly = false;
		$scope.showsmsdaily = false;
		$scope.showsmsmonthly = false;
		$scope.showsmsweekly = false;

	}

	$scope.smsdailyload = function() {

		$scope.showsmsdaily = true;
		//$location.path("/daily");

		$scope.showsmsminute = false;
		$scope.showsmshourly = false;
		$scope.showsmsweekly = false;
		$scope.showsmsmonthly = false;
		$scope.showsmsyearly = false;

	}

	$scope.smshourlyload = function() {

		$scope.showsmshourly = true;
		//$location.path("/hourly");

		$scope.showsmsminute = false;
		$scope.showsmsweekly = false;
		$scope.showsmsdaily = false;
		$scope.showsmsmonthly = false;
		$scope.showsmsyearly = false;
	}

	$scope.smsmonthlyload = function() {

		$scope.showsmsmonthly = true;
		//$location.path("/monthly");

		$scope.showsmsminute = false;
		$scope.showsmshourly = false;
		$scope.showsmsdaily = false;
		$scope.showsmsweekly = false;
		$scope.showsmsyearly = false;
	}

	$scope.smsweeklyload = function() {

		$scope.showsmsweekly = true;

		$scope.showsmsminute = false;
		$scope.showsmshourly = false;
		$scope.showsmsdaily = false;
		$scope.showsmsmonthly = false;
		$scope.showsmsyearly = false;

	}
});
