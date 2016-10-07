(function(){
    var app = angular.module('store', [ ]);
    app.controller('StoreController', function(){
        this.products = gems;
    });
    
    app.controller('TabController',function(){
    	this.tab = 1;
	    this.setTab = function(tabNum){
	      this.tab = tabNum;
	    };
	    this.isSet = function(tabNum){
	      return this.tab === tabNum;
	    };
	});
    
    app.controller('GalleryController', function(){
        this.current = 0;
        this.setCurrent = function(newGallery){
          this.current = newGallery || 0;
        };
	});
    
    var gems = [
	    {
	        name:  'Dodecahedron',
	        price: 2.95,
	        description: '...',
	        canPurchase: true
	    },
	    {
	        name:  'Dodecahedron',
	        price: 3.95,
	        description: '...',
	        canPurchase: false
	    },
	    {
	        name:  'Dodecahedron',
	        price: 4.95,
	        description: '...',
	        canPurchase: true
	    },
	    {
	        name:  'Dodecahedron22',
	        price: 5.95,
	        description: 'asdfgdadreefger...'
	    }
    ]
})();