(function () {
    var as = angular.module('angularspring');
    
    as.directive('fileModel', ['$parse', function ($parse) {
        return {
            restrict: 'A',
            link: function(scope, element, attrs) {
               var model = $parse(attrs.fileModel);
               var modelSetter = model.assign;
               
               element.bind('change', function(){
                  scope.$apply(function(){
                     modelSetter(scope, element[0].files[0]);
                  });
               });
            }
         };
     }]); 
    
    
    as.controller('CustomersController', function ($scope, $rootScope, $http, i18n, $location,$timeout,$q,$cookies,$modal, $log) {
    	// commun objects.
    	$scope.viewcin = true;
        $scope.viewlicense = true;
        $scope.viewcustomer = false ;
        $scope.editcustomer = false ;
        $scope.addcustomer = true ;
        $scope.delcustomer = false ;
        $scope.customer = {};
    	
    	// order column.
    	$scope.sortKey = 'date_modification' ;   //set the sortKey to the param passed
        $scope.reverse = true ;
        
    	$scope.sort = function(keyname){
    		$scope.sortKey = keyname;   //set the sortKey to the param passed
            $scope.reverse = !$scope.reverse; //if true make it false and vice versa
        }
    	
       // get all customers
       function ajaxgetcutomers() {
           $http.post('action/getCustomers/').then(function (response) {
           		$scope.customers = response.data ;
           });
       };
       ajaxgetcutomers();
       
        		
  	    //  action view
  		function ajaxgetcustomer(id) {
  			var deferred = $q.defer();
  	        $http.post('action/getCustomer/', id).then(function (response) {
            	if(response.data.id > 0){
            		$scope.customer = response.data ;
            		deferred.resolve(response);
                 }else{
            	    deferred.reject(response);
                 }
            });
            return deferred.promise;
        }
  		
  	    //  action view
  		$scope.actionview = function (id) {
  			$scope.customer = null;
  			$scope.viewcin = false;
  	        $scope.viewlicense = false;
  			$scope.viewcustomer = true ;
  	        $scope.editcustomer = false ;
  	        $scope.addcustomer = false ;
  	        $scope.delcustomer = false ;
  	        ajaxgetcustomer(id)
            .then(function(data) {
         	    if($scope.customer.scan_cin != null){
	  	        	$scope.viewcin = true;
	  	        }
	  	        if($scope.customer.scan_driver_license != null){
		        	$scope.viewlicense = true;
		        }
		        }, function(data) {
		        }
		    );
   	    };
  		
  		//  action edit
  		$scope.actionedit = function (id) {
  			$scope.customer = null;
  			$scope.viewcin = false;
  	        $scope.viewlicense = false;
  			$scope.viewcustomer = false ;
  	        $scope.editcustomer = true ;
  	        $scope.addcustomer = false ;
  	        $scope.delcustomer = false ;
  	        ajaxgetcustomer(id)
             .then(function(data) {
            	if($scope.customer.scan_cin != null){
   	  	        	$scope.viewcin = true;
   	  	        }
   	  	        if($scope.customer.scan_driver_license != null){
   		        	$scope.viewlicense = true;
   		        }
		        }, function(data) {
		        }
		    );
  	        
   	    };
  		
        //  action add
  		$scope.actionadd = function () {
  			$scope.customer = null;
  			$scope.viewcin = true;
  	        $scope.viewlicense = true;
  			$scope.viewcustomer = false ;
  	        $scope.editcustomer = false ;
  	        $scope.addcustomer = true ;
  	        $scope.delcustomer = false ;
   	    };
  		
  	   // add customer.
       $scope.deletecin = function () {
    	  $scope.viewcin = !$scope.viewcin;
 	   };

 	   $scope.deletelicense = function () {
 		  $scope.viewlicense = !$scope.viewlicense;
 	   };
       
       function ajaxsavecustomer() {
    	   var fd = new FormData();
    	   //var tmp = new File([""], "tmp");
    	   var tmp = new Blob([""], { type: "text/plain" });
    	   tmp.name="test.txt"
    	   tmp.lastModifiedDate= new Date();
    	   var cin = $scope.cin;
    	   var license = $scope.license;
    	  
    	   if(null != cin){
    	     fd.append('scan_cin', cin);
    	   }else{
    		 fd.append('scan_cin', tmp);  
    	   }
    	   if(null != license){
      	     fd.append('scan_license', license);
      	   }else{
      		 fd.append('scan_license', tmp);  
      	   }
    	   fd.append('customer', angular.toJson($scope.customer,true));
    	   var config = {
    	    	   	transformRequest: angular.identity,
    		   		headers : {
    		   			'Content-Type': undefined
    		   	    }
    	   }
    	   
           var deferred = $q.defer();
           $http.post('action/addCustomer/', fd, config).then(function (response) {
           	   if(angular.equals(response.data.type, 'success')){
           		   deferred.resolve(response);
               }else{
           	       deferred.reject(response);
               }
           });
           return deferred.promise;
       }
       
       $scope.savecustomer = function () {
       	ajaxsavecustomer()
             .then(function(data) {
            	   $scope.customer = null;
            	   $scope.viewcin = false;
         	       $scope.viewlicense = false;
            	   $scope.sortKey = 'date_modification';
            	   $scope.reverse = true;
            	   $timeout(function() {ajaxgetcutomers();}, 2000);
            	   angular.forEach(
            		angular.element("input[type='file']"),
            	    function(inputElem) {angular.element(inputElem).val(null);}
            	   );
 		        }, function(data) {
 		        }
 		    );
       };
       
       function ajaxupdatecustomer() {
    	   var fd = new FormData();
    	   var tmp = new File([""], "tmp");
    	   //var tmp = new Object();
    	   var cin = $scope.cin;
    	   var license = $scope.license;
    	  
    	   if(null != cin){
    	     fd.append('scan_cin', cin);
    	     $scope.viewcin = true;
    	   }else{
    		 fd.append('scan_cin', tmp);  
    	   }
    	   if(null != license){
      	     fd.append('scan_license', license);
      	     $scope.viewlicense = true;
      	   }else{
      		 fd.append('scan_license', tmp);  
      	   }
    	   fd.append("delcin",$scope.viewcin);
    	   fd.append("dellicense",$scope.viewlicense);
    	   fd.append('customer', angular.toJson($scope.customer,true));
    	   var config = {
    	    	   	transformRequest: angular.identity,
    		   		headers : {
    		   			'Content-Type': undefined
    		   	    }
    	   }
    	   
           var deferred = $q.defer();
           $http.post('action/updateCustomer/', fd, config).then(function (response) {
           	   if(angular.equals(response.data.type, 'success')){
           		   deferred.resolve(response);
               }else{
           	       deferred.reject(response);
               }
           });
           return deferred.promise;
       }
       
       $scope.updatecustomer = function () {
       	ajaxupdatecustomer()
             .then(function(data) {
            	   $scope.customer = null;
            	   $scope.viewcin = false;
         	       $scope.viewlicense = false;
            	   $scope.sortKey = 'date_modification';
            	   $scope.reverse = true;
            	   $timeout(function() {ajaxgetcutomers();}, 2000);
            	   angular.forEach(
            		angular.element("input[type='file']"),
            	    function(inputElem) {angular.element(inputElem).val(null);}
            	   );
 		        }, function(data) {
 		        }
 		    );
       };
       
       // display image.
       $scope.displayimage = function (src,title) {
    	     var modalInstance = $modal.open({
    	       templateUrl: 'html/displayimage.html',
    	       controller: ModalInstanceCtrl1,
    	       size: 'lg',
    	       resolve: {
    	         src : function () {
    	           return src;
    	        },
    	        title : function () {
     	           return title;
     	        }
    	       }
    	     });
       };

       var ModalInstanceCtrl1 = function ($scope, $modalInstance, src, title) {
       $scope.src = src;
       if(title == 1){
          $scope.title = 'Pièce identité';
       }
       if(title == 2){
           $scope.title = 'Permis de conduire';
        }
       $scope.cancel = function () {
          $modalInstance.dismiss('cancel');
    	};
       };
       
       // action delete
 	   function ajaxdeletecustomer(id) {
 		   var deferred = $q.defer();
 	       $http.post('action/deleteCustomer/', id).then(function (response) {
 	        	if(angular.equals(response.data.type, 'success')){
            		   deferred.resolve(response);
                }else{
            	       deferred.reject(response);
                }
           });
           return deferred.promise;
       }
 		
  	   $scope.actiondel = function (id) {
  	     var modalInstance = $modal.open({
  	       templateUrl: 'html/customermodal.html',
  	       controller: ModalInstanceCtrl2,
  	       size: 'sm',
  	       resolve: {
  	         id : function () {
  	           return id;
  	        }
  	       }
  	     });
  	    };

  	    var ModalInstanceCtrl2 = function ($scope, $modalInstance, id) {

    	  $scope.id = id;
  		  $scope.ok = function () {
  			ajaxdeletecustomer($scope.id)
            .then(function(data) {
            	  $scope.customer = null;
      			  $scope.viewcin = false;
      	          $scope.viewlicense = false;
            	  ajaxgetcutomers();
            	  $modalInstance.dismiss('cancel');
		        },function(data) {
		        }
		    );
  		  };

  		  $scope.cancel = function () {
  		    $modalInstance.dismiss('cancel');
  		  };
  		};
  		
    });
    
}());