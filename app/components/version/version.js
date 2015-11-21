'use strict';

angular.module('exampleApp.version', [
  'exampleApp.version.interpolate-filter',
  'exampleApp.version.version-directive'
])

.value('version', '0.1');
