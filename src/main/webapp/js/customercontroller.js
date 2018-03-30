(function () {
    var as = angular.module('angularspring');
    
    
    as.controller('CustomersController', function ($scope, $rootScope, $http, i18n, $location,$timeout,$q,$cookies,$modal, $log) {
    	
    	$scope.items = ['item1', 'item2', 'item3'];

  	   $scope.open = function () {

  	     var modalInstance = $modal.open({
  	       templateUrl: 'html/customermodal.html',
  	       controller: ModalInstanceCtrl,
  	       resolve: {
  	         items: function () {
  	           return $scope.items;
  	         }
  	       }
  	     });

  	     modalInstance.result.then(function (selectedItem) {
  	       $scope.selected = selectedItem;
  	     }, function () {
  	       $log.info('Modal dismissed at: ' + new Date());
  	     });
  	   };


  	   var ModalInstanceCtrl = function ($scope, $modalInstance, items) {

  		  $scope.items = items;
  		  $scope.selected = {
  		    item: $scope.items[0]
  		  };

  		  $scope.ok = function () {
  		    $modalInstance.close($scope.selected.item);
  		  };

  		  $scope.cancel = function () {
  		    $modalInstance.dismiss('cancel');
  		  };
  		};
    	 
        $scope.customers = {};
        $scope.customer = {};
        
        $scope.users = [{"id":1,"first_name":"Heather","last_name":"Bell","hobby":"Eating"},
    	                {"id":2,"first_name":"Andrea","last_name":"Dean","hobby":"Gaming"},
    	                {"id":3,"first_name":"Peter","last_name":"Barnes","hobby":"Reading Books"},
    	                {"id":4,"first_name":"Harry","last_name":"Bell","hobby":"Youtubing"},
    	                {"id":5,"first_name":"Deborah","last_name":"Burns","hobby":"Fishing"},
    	                {"id":6,"first_name":"Larry","last_name":"Kim","hobby":"Skipping"},
    	                {"id":7,"first_name":"Jason","last_name":"Wallace","hobby":"Football"},
    	                {"id":25,"first_name":"Russell","last_name":"Patterson","hobby":"Singing"}];
    	
    	$scope.sort = function(keyname){
    		$scope.sortKey = keyname;   //set the sortKey to the param passed
            $scope.reverse = !$scope.reverse; //if true make it false and vice versa
        }
       
    	function ajaxgetcutomers() {
            $http.post('action/getCustomers/').then(function (response) {
            		$scope.customers = response.data ;
            });
        };
        ajaxgetcutomers();
                
        $scope.editagency = function () {
        	 $scope.iseditagency = true;
        };
        
        $scope.editprofile = function () {
       	     $scope.iseditprofile = true;
        };
        
        function ajaxsaveprofile() {
            var deferred = $q.defer();
            $http.post('action/updateProfile/', $scope.profile).then(function (response) {
            	if(angular.equals(response.data.type, 'success')){
            		deferred.resolve(response);
                 }else{
            	    deferred.reject(response);
                 }
            });
            return deferred.promise;
        }
        
        $scope.saveprofile = function () {
        	ajaxsaveprofile()
              .then(function(data) {
            	   ajaxgetprofile();
             	   $scope.iseditprofile = false;
  		        }, function(data) {
  		        }
  		    );
        };
        
        function ajaxsaveagency() {
           var deferred = $q.defer();
           $http.post('action/updateAgency/', $scope.agency).then(function (response) {
           	if(angular.equals(response.data.type, 'success')){
           		deferred.resolve(response);
                }else{
           	    deferred.reject(response);
                }
           });
           return deferred.promise;
       }
       
       $scope.saveagency = function () {
    	   ajaxsaveagency()
             .then(function(data) {
            	   ajaxgetagency();
            	   $scope.iseditagency = false;
 		        }, function(data) {
 		        }
 		    );
       };
       
    });
    
}());