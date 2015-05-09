var pagination = angular.module('Pagination',[]);

    pagination.factory('PaginationService',['$http', '$q', function($http, $q) {

        var service = {};

        service.paginationData = {
            pageNumber: 0,
            pageSize: 5,
            morePages: false,
            firstPage: true
        }

        service.isFirstPage = function() {
            return service.paginationData.pageNumber == 0;
        }

        service.hasMorePages = function () {
            return service.paginationData.morePages;
        }

        service.config = function(url, params, pageSize) {
            service.url = url;
            service.paginationData.pageSize = pageSize || service.paginationData.pageSize;
        }

        service.paginate = function(params, pageNumber) {
            var deferred = $q.defer();
            service.paginationData.pageNumber = pageNumber || 0;
            params.pageNumber = service.paginationData.pageNumber;
            params.pageSize = service.paginationData.pageSize;
            $http({
                url: service.url,
                method: 'GET',
                params: params
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
                })

            return deferred.promise;
        }

        function setPaginationData(morePages) {
            service.paginationData.morePages = morePages;
            service.paginationData.firstPage = service.isFirstPage();
        }

        service.getPaginationData = function()
        {
            return service.paginationData;
        }

        return service;

    }]);

