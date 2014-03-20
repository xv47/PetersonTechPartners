/**
 * Created by Osiris on 3/12/14.
 */
describe("loginCtrl", function(){
    beforeEach(module('ptech'));

    var  scope, controller;

    var mockFactory = {
        getEmail : function(){
            //Some random emails collected
            return [{email: 'admin@email.com'},
                {email: 'email@email.com'},
                {email: 'sunny@email.com'}]
        }
    }

//    beforeEach(inject(function($rootScope, $controller){
//        scope = $rootScope.$new();
//        controller = $controller;
//    }));

    it("should pull emails from factory",  inject(function($rootScope, $controller) {
        scope = $rootScope.$new();
        $controller("loginCtrl",
            {$scope: scope,
            loginFactory: mockFactory});
        expect(scope.emails.length).toEqual(3);
    })
    );


});