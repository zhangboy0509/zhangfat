(function(){
    var app = angular.module('store', [ ]);
    app.controller('StoreController', function(){
        this.products = gems;
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