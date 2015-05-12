var fs = require('fs');
var file = require('file');
var path = require('path');

var dirName = "/media/diskg/studia/semestr4/tw/labs/ms/lab1/lab07/PAM08copy";

var count = 0;
var filesArray = [];
var filesIndex = 0;
var globalSumOfLines = 0;
var dirsToProcess = 1;


function fork(asyncCalls, sharedCallback) {
    var counter = asyncCalls.length;
    var callback = function () {
        counter--;
        if (counter == 0) {
            sharedCallback();
        }
    };

    var countersMap = {};

    for (var i = 0; i < asyncCalls.length; i++) {

        function createEndHandler(name) {
            return function () {
                var counter = countersMap[name];
                //console.log(counter, path.basename(name));
                callback();
            }
        }

        function createDataHandler(name) {
            return function (chunk) {
                var count = countersMap[name];

                var currentlyReadLines = chunk.toString('utf8')
                        .split(/\r\n|[\n\r\u0085\u2028\u2029]/g)
                        .length - 1;

                globalSumOfLines += currentlyReadLines;
                countersMap[name] = count ? count + currentlyReadLines : currentlyReadLines;
            }
        }

        var filePath = asyncCalls[i];

        fs.createReadStream(filePath)
            .on('data', createDataHandler(filePath))
            .on('end', createEndHandler(filePath))
            .on('error', function (err) {
                console.error(err);
            });
    }
}


var shared = function () {
    var end = Date.now();
    console.log('Global sum: ', globalSumOfLines);
    console.log('Time: ', end - start);
};


var walkCallback = function (error, dirPath, dirs, files) {

    dirsToProcess += dirs.length - 1;

    files.forEach(function (currentValue) {
        filesArray[filesIndex] = currentValue;
        filesIndex++;
    });

    if (dirsToProcess == 0) {
        fork(filesArray, shared);
    }
};

var start = Date.now();
file.walk(dirName, walkCallback);



