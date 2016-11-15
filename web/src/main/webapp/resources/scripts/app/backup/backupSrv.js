var module = angular.module('backupModule');


module
    .factory('BackupSrv', ['$http', function ($http) {
        return {
            generate: function() {
                return $http({
                    url: 'api/backup/generate',
                    method: 'PUT'
                })
            }
        }
    }]);


