angular.module('market-front', []).controller('indexController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/market/'

//    var loadProducts = function(){ - можно присвоить функцию обычной переменной, и вызывать ее здесь по имени,
// если в html она не нужна. Используем для вспомагательных функций, чтобы не захламлять $scope

    $scope.loadProducts = function(pageIndex = 1){  // запись означает, что если мы не подали аргумент в pageIndex, по дефолту его значение будет равно 1
        $http({
            url: contextPath + 'products',
            method: 'GET',
            params: {
                p: pageIndex
            }
        }).then(function (response){
            console.log(response)
            $scope.productsPage = response.data;
        });
    };

//    $scope.loadProducts = function(){
//        $http.get(contextPath + 'products')
//            .then(function (response){
//                console.log(response)
//                $scope.productsPage = response.data;
//            });
//    };

    $scope.showInfo = function (product){
        alert(product.title);
    }


    // $scope.wrongRequest = function () {
    // WRONG:
    // $http.get(contextPath + 'products/update/1');
    // reload(); - вызов обновления страницы произойдет раньше чем ответит бекэнд. Нужно применять колбеки [...then(function(response){...]

    // CORRECT
    // $http.get(contextPath + 'products/update/1')
    //     .then(function (response) {
    //         reload();
    //     });
    // }

    $scope.loadProducts(2);
});
