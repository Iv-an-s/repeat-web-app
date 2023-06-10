(function (){
    angular
        .module('market-front', ['ngRoute', 'ngStorage'])
        .config(config)
        .run(run);

    function config($routeProvider){
    $routeProvider
        .when('/', {
            templateUrl: 'welcome/welcome.html',
            controller: 'welcomeController'
        })
        .when('/store', {
            templateUrl: 'store/store.html',
            controller: 'storeController'
        })
        .when('/create_product', {
            templateUrl: 'create_product/create_product.html',
            controller: 'createProductController'
        })
        .when('/edit_product/:productId', {
            templateUrl: 'edit_product/edit_product.html',
            controller: 'editProductController'
        })
        .when('/registration', {
                templateUrl: 'registration/registration.html',
                controller: 'registerController'
        })
        .when('/cart', {
                templateUrl: 'cart/cart.html',
                controller: 'cartController'
        })
        .when('/order_confirmation', {
                templateUrl: 'order_confirmation/order_confirmation.html',
                controller: 'orderConfirmationController'
        })
        .when('/orders', {
                templateUrl: 'orders/orders.html',
                controller: 'ordersController'
        })
        .otherwise({
            redirectTo: '/'
        });
    }

    function run($rootScope, $http, $localStorage){
    const contextPath = 'http://localhost:8189/market';
        if ($localStorage.webMarketUser){
            $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.webMarketUser.token;
        }
        if (!$localStorage.guestCartId){
            $http.get(contextPath + '/api/v1/cart/generate')
            .then(function successCallback(response){
                $localStorage.guestCartId = response.data.value;
            });
        }
    }
})();

angular.module('market-front').controller('indexController', function($rootScope, $scope, $http, $localStorage){
    const contextPath = 'http://localhost:8189/market/api/v1';

    $scope.tryToAuth = function () {
        $http.post(contextPath + '/auth', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.webMarketUser = {username: $scope.user.username, token: response.data.token};

                    $scope.user.username = null;
                    $scope.user.password = null;

                    $http.get(contextPath + '/cart/' + $localStorage.guestCartId + '/merge')
                        .then(function successCallback(response){
//                            $localStorage.guestCartId = response.data.value;
                        });
                }
            }, function errorCallback(response){
        });
    };

    $scope.tryToLogout = function(){
        $scope.clearUser();
        if($scope.user.username){
            $scope.user.username = null;
        }
        if ($scope.user.password){
            $scope.user.password = null;
        }
    };

    $scope.clearUser = function(){
        delete $localStorage.webMarketUser;
        $http.defaults.headers.common.Authorization = '';
    };

    $rootScope.isUserLoggedIn = function() {
        if ($localStorage.webMarketUser){
            return true;
        }else {
            return false;
        }
    };
});





