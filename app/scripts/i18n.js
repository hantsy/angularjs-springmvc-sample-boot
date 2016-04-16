(function () {
    var as = angular.module('exampleApp.i18n', [])
            .factory('customTranslationHandler', function () {
                return function (translationID) {
                    // return the following text as a translation 'result' - this will be
                    // displayed instead of the language key.
                    return '[' + translationID + ']';
                };
            })
            .config(function ($translateProvider) {
                $translateProvider.preferredLanguage('en');
                $translateProvider.useStaticFilesLoader({
                    prefix: '/i18n/',
                    suffix: '.json'
                });

                // $translateProvider.useMissingTranslationHandlerLog();
                $translateProvider.useMissingTranslationHandler('customTranslationHandler');

//              $translateProvider.useLocalStorage();
//              $translateProvider.determinePreferredLanguage(function () {
//              // define a function to determine the language
//              // and return a language key
//              });

//
                // Sanitize security
//                $translateProvider.useSanitizeValueStrategy('sanitize');
            });



//    as.service('i18n', function () {
//        var self = this;
//        this.setLanguage = function (language) {
//            $.i18n.properties({
//                name: 'messages',
//                path: 'i18n/',
//                mode: 'map',
//                language: language,
//                callback: function () {
//                    self.language = language;
//                }
//            });
//        };
//        this.setLanguage('zh_CN');
//    });
//
//    as.directive('msg', function () {
//        return {
//            restrict: 'EA',
//            link: function (scope, element, attrs) {
//                var key = attrs.key;
//                if (attrs.keyExpr) {
//                    scope.$watch(attrs.keyExpr, function (value) {
//                        key = value;
//                        element.text($.i18n.prop(value));
//                    });
//                }
//                scope.$watch('language()', function (value) {
//                    element.text($.i18n.prop(key));
//                });
//            }
//        };
//    });
}());