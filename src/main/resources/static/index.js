angular.module('frontapp', []).controller('indexController', function ($scope, $http) {
    $http.get('http://localhost:8189/app/students/json/1')
        .then(function (response){
            $scope.student = response.data;
        })

        $scope.counter = 0;

        $scope.btnPlusClick = function(){
            $scope.counter +=1;
        }

        $scope.btnMinusClick = function(){
            $scope.counter -=1;
        }
});