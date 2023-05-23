angular.module('market-front').controller('ordersController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/market/api/v1/';

    $scope.loadOrders = function(){
        $http({
            url: contextPath + 'orders',
            method: 'GET',
        }).then(function (response){
            alert("Запрос на показ заказов прошел успешно");
            $scope.orders = response.data;
            console.log($scope.orders);
        });
    };

    $scope.loadOrders();
});