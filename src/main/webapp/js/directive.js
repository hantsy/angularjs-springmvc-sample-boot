(function() {
    var app = angular.module('statApp.directives', []);

    app.directive('datetimepicker', function() {
        return {
            restrict: 'A',
            require: 'ngModel',
            link: function(scope, element, attrs, ngModelCtrl) {
                console.log('call datetimepicker link...');
                console.log('ngModelCtrl.modelValue@'+ngModelCtrl.$modelValue);
                console.log('ngModelCtrl.viewValue@'+ngModelCtrl.$viewValue);
                var picker = element.datetimepicker({
                    dateFormat: 'dd/MM/yyyy hh:mm:ss'
                });
                
                //ngModelCtrl.$setViewValue(picker.getDate());
                
                //model->view
                ngModelCtrl.$render(function() {
                    console.log('ngModelCtrl.viewValue@'+ngModelCtrl.$modelValue);
                    picker.setDate(ngModelCtrl.$modelValue || '');
                });
                
                //view->model
                picker.on('dp.change', function(e) {
                    console.log('dp.change'+e.date);              
                    scope.$apply(function(){
                        ngModelCtrl.$setViewValue(e.date);
                    });
                });
            }
        };
    });

}());


