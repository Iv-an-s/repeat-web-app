angular.module('market-front').controller('storeController', function ($scope, $http, $location) {
    const contextPath = 'http://localhost:8189/market/api/v1/';
    let currentPageIndex = 1;

    //запись angular.module('market-front')... без квадратных скобок через запятую после 'market-front' говорит о том,
    // что мы не создаем новый модуль, а подключаемся к существующему.
    //$scope относится только к данному контроллеру. Обмениваться данными через $scope между контроллерами нельзя!
    $scope.loadProducts = function(pageIndex = 1){
        currentPageIndex = pageIndex;
        $http({
            url: contextPath + 'products',
            method: 'GET',
            params: {
                p: pageIndex
            }
        }).then(function (response){
            console.log(response)
            $scope.productsPage = response.data;
            $scope.paginationArray = $scope.generatePagesIndexes(1, $scope.productsPage.totalPages);
        });
    };

    $scope.addToCart = function(productId){
        $http({
            url: contextPath + 'cart/add/' + productId,
            method: 'GET',
        }).then(function (response){
            console.log(response)
        });
    };

    $scope.generatePagesIndexes = function (startPage, endPage){
        let arr = [];
        for(let i = startPage; i < endPage +1; i++){
            arr.push(i);
        }
        return arr;
    };

    $scope.navToEditProductPage = function (productId){
       $location.path('/edit_product/' + productId)
    }

    $scope.loadProducts();
});