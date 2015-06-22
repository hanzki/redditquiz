var app = angular.module('redditquiz', ['ui.router']);

app.constant('api', 'http://localhost:9000/api');

app.config(function($stateProvider, $urlRouterProvider) {

  //$urlRouterProvider.otherwise("/welcome");

  $stateProvider
    .state('welcome', {
      url: '/welcome',
      templateUrl: 'assets/views/welcome.html'
    });
});
