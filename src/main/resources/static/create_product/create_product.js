angular.module('market-front').controller('createProductController', function ($scope, $http, $location) {
    const contextPath = 'http://localhost:8189/market/api/v1/';

    $scope.createProduct = function(){
        if($scope.new_product == null){
            alert('Форма не заполнена');
            return;
        }
        $http.post(contextPath + 'products', $scope.new_product)
            .then(function successCallback(response){
                $scope.new_product = null;
                alert('Продукт успешно создан');
                $location.path('/store');
            }, function failureCallback(response){
                alert(response.data.messages);
            });
    }
});
