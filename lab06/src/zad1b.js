var async = require('async');

function printAsync(s, cb) {
    var delay = Math.floor((Math.random()*1000)+500);
    setTimeout(function() {
        console.log(s);
        if (cb) cb();
    }, delay);
}

function task1(cb) {
    printAsync("1", function() {
        task2(cb);
    });
}

function task2(cb) {
    printAsync("2", function() {
        task3(cb);
    });
}

function task3(cb) {
    printAsync("3", cb);
}

async.waterfall([
        function(callback) {
            task1(function() {
                console.log(' first iteration ');
                callback();
            });
        },
        function(callback) {
            task1(function() {
                console.log(' second iteration ');
            callback();
            });
        },
        function(callback) {
            task1(function() {
                console.log(' third iteration ');
            callback();
            });
        },
        function(callback) {
            task1(function() {
                console.log(' fourth iteration ');
            callback();
            });
        },
        function(callback) {
            task1(function() {
                console.log('done!');
            callback();
            });
        }
    ],
    function (err, result) {
        // result now equals
        // 'done'
    });
