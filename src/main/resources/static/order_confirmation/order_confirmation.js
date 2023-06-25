angular.module('market-front').controller('orderConfirmationController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:8189/market/api/v1/';

    $scope.loadCart = function(){
        $http({
            url: contextPath + 'cart/' + $localStorage.guestCartId,
            method: 'GET',
        }).then(function (response){
            $scope.cart = response.data;
        });
    };

    $scope.createOrder = function(){
        $http({
            url: contextPath + 'orders',
            method: 'POST',
            data: $scope.orderDetails
        }).then(function (response){
            alert('Ваш заказ успешно сформирован');
            $location.path('/');
        });
    };

    $scope.loadCart();
});