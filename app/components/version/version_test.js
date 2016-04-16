'use strict';

describe('exampleApp.version module', function() {
  beforeEach(module('exampleApp.version'));

  describe('version service', function() {
    it('should return current version', inject(function(version) {
      expect(version).toEqual('0.1');
    }));
  });
});
