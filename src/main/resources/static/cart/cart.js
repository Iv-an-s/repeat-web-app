angular.module('market-front').controller('cartController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:8189/market/api/v1/';

    $scope.loadCart = function(){
        $http({
            url: contextPath + 'cart/' + $localStorage.guestCartId,
            method: 'GET',
        }).then(function (response){
            $scope.cart = response.data;
        });
    };

    $scope.incrementItem = function(productId){
        $http({
            url: contextPath + 'cart/' + $localStorage.guestCartId + '/add/' + productId,
            method: 'GET',
        }).then(function (response){
            $scope.loadCart();
        });
    };

    $scope.decrementItem = function(productId){
        $http({
            url: contextPath + 'cart/' + $localStorage.guestCartId + '/decrement/' + productId,
            method: 'GET',
        }).then(function (response){
            $scope.loadCart();
        });
    };

    $scope.removeItem = function(productId){
        $http({
            url: contextPath + 'cart/' + $localStorage.guestCartId + '/remove/' + productId,
            method: 'GET',
        }).then(function (response){
            $scope.loadCart();
        });
    };

    $scope.checkOut = function(){
        $location.path("/order_confirmation")
    }

    $scope.disabledCheckOut = function(){
            alert("Для оформления заказа необходимо войти в учетную запись")
        }

    $scope.loadCart();
});