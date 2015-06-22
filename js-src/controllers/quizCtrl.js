angular.module("redditquiz")
.controller('quizCtrl', ['$scope', '$state', '$http', function($scope, $state, $http){

    $scope.quiz = {
        id: -1,
        imageUrl: '/assets/img/Reddit-alien.png',
        choices: []
    };

    $scope.hasAnswered = true;

    $scope.loadNewQuiz = function() {
        $http.get('http://localhost:9000/api/quiz/random').success(function(data) {
            angular.copy(data, $scope.quiz);
            $scope.hasAnswered = false;
        });
    }

    $scope.answer = function(answerId) {
        if($scope.hasAnswered == false){
            $scope.hasAnswered = true;
            $http.post('http://localhost:9000/api/quiz/answer',
             {
                 quizId: $scope.quiz.id,
                 answerId: answerId
             }).success(function(data) {
                 alert(data.message);
            });
        }
    }

    $scope.loadNewQuiz();

}]);
