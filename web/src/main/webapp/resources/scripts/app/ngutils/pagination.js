var pagination = angular.module('Pagination',[]);

    pagination.factory('PaginationService',['$http', '$q', function($http, $q) {

        var service = {};

        service.paginationData = {
            pageNumber: 0,
            pageSize: 5,
            morePages: false,
            firstPage: true
        };

        service.isFirstPage = function() {
            return service.paginationData.pageNumber == 0;
        };

        service.hasMorePages = function () {
            return service.paginationData.morePages;
        };

        service.config = function(url, pageSize) {
            service.url = url;
            service.paginationData.pageSize = pageSize || service.paginationData.pageSize;
        };

        service.paginate = function(params, pageNumber) {
            var deferred = $q.defer();
            var parameters = angular.copy(params);
            service.paginationData.pageNumber = pageNumber || 0;
            parameters.pageNumber = service.paginationData.pageNumber;
            parameters.pageSize = service.paginationData.pageSize;
            $http({
                url: service.url,
                method: 'GET',
                params: parameters
            })
                .success(function(data) {
                    if (data.length > service.paginationData.pageSize) {
                        data.splice(-1, 1);
                        setPaginationData(true)
                    } else {
                        setPaginationData(false);
                    }
                    deferred.resolve(data)
                })
                .error(function() {
                    deferred.reject();
                });

            return deferred.promise;
        };

        function setPaginationData(morePages) {
            var imagesIdx = [];
            for(var i = 0; i < service.paginationData.pageSize; i++) {
                imagesIdx.push(Math.floor(Math.random() * (47 - 1 + 1)) + 1);
            }
            service.paginationData.imagesIdx = imagesIdx;
            service.paginationData.morePages = morePages;
            service.paginationData.firstPage = service.isFirstPage();
        };

        service.getPaginationData = function()
        {
            return service.paginationData;
        };

        return service;

    }]);

