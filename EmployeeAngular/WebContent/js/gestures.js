'use strict';

angular.module('angular-gestures', []);

/**
 * Inspired by AngularJS' implementation of "click dblclick mousedown..."
 *
 * This ties in the Hammer 1.0.0 events to attributes like:
 *
 * hm-tap="add_something()" hm-swipe="remove_something()"
 *
 * and also has support for Hammer options with:
 *
 * hm-tap-opts="{hold: false}"
 *
 * or any other of the "hm-event" listed underneath.
 */
var HGESTURES = {
    hmDoubleTap : 'doubletap',
    hmDragstart : 'dragstart',
    hmDrag : 'drag',
    hmDragUp : 'dragup',
    hmDragDown : 'dragdown',
    hmDragLeft : 'dragleft',
    hmDragRight : 'dragright',
    hmDragend : 'dragend',
    hmHold : 'hold',
    hmPinch : 'pinch',
    hmPinchIn : 'pinchin',
    hmPinchOut : 'pinchout',
    hmRelease : 'release',
    hmRotate : 'rotate',
    hmSwipe : 'swipe',
    hmSwipeUp : 'swipeup',
    hmSwipeDown : 'swipedown',
    hmSwipeLeft : 'swipeleft',
    hmSwipeRight : 'swiperight',
    hmTap : 'tap',
    hmTouch : 'touch',
    hmTransformstart : 'transformstart',
    hmTransform : 'transform',
    hmTransformend : 'transformend'
};

var VERBOSE = true;

angular.forEach(HGESTURES, function(eventName, directiveName) {
    angular.module('angular-gestures').directive(
            directiveName,
            ['$parse', '$log', function($parse, $log) {
                return function(scope, element, attr) {
                    var hammertime, handler;
                    attr.$observe(directiveName, function(value) {
                        var fn = $parse(value);
                        var opts = $parse(attr[directiveName + 'Opts'])
                        (scope, {});
                        hammertime = new Hammer(element[0], opts);
                        handler = function(event) {
                            if (VERBOSE) {
                                $log.debug('angular-gestures: %s',
                                        eventName);
                            }
                            scope.$apply(function() {
                                fn(scope, { $event : event });
                            });
                        };
                        hammertime.on(eventName, handler);
                    });
                    scope.$on('$destroy', function() {
                        hammertime.off(eventName, handler);
                    });
                };
            }]);
});
