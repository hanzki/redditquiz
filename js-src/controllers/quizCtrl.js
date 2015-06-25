angular.module("redditquiz")
.controller('quizCtrl', ['$scope', '$state', '$http', function($scope, $state, $http){

    $scope.quiz = {
        id: -1,
        imageUrl: '/assets/img/Reddit-alien.png',
        choices: []
    };

    $scope.hasAnswered = true;
    $scope.answerId = 0;
    $scope.userAnswer = 0;


    $scope.loadNewQuiz = function() {
        $http.get('/api/quiz/random').success(function(data) {
            angular.copy(data, $scope.quiz);
            $scope.hasAnswered = false;
            $scope.answerId = 0;
            $scope.userAnswer = 0;
        });
    }

    $scope.answer = function(answerId) {
        if($scope.hasAnswered == false){
            $scope.hasAnswered = true;
            $scope.userAnswer = answerId;
            $http.post('/api/quiz/answer',
             {
                 quizId: $scope.quiz.id,
                 answerId: answerId
             }).success(function(data) {
                $scope.answerId = data.answerId;
                if(data.answerId === answerId){
                    Materialize.toast('Correct!', 4000, 'green')
                } else {
                    Materialize.toast('Wrong!', 4000, 'red')
                }
            });
        }
    }

    $scope.loadNewQuiz();

}]);
