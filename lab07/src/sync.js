var fs = require('fs');
var file = require('file');
var path = require('path');

var dirName = "/media/diskg/studia/semestr4/tw/labs/ms/lab1/lab07/PAM08copy";

var count = 0;
var filesArray = [];
var filesIndex = 0;
var globalSumOfLines = 0;
var lines = 0;
var dirsToProcess = 1;


var processFile = function (filePath) {
    if (filesIndex == filesArray.length) {
        var end = Date.now();
        console.log('Global sum: ', globalSumOfLines);
        console.log('Time: ', end - start);

    } else {
        lines = 0;
        fs.createReadStream(filePath).on('data', function (chunk) {
            lines += chunk.toString('utf8')
                .split(/\r\n|[\n\r\u0085\u2028\u2029]/g)
                .length - 1;
        }).on('end', function () {

            console.log(lines, path.basename(filePath));

            globalSumOfLines += lines;
            filesIndex++;
            processFile(filesArray[filesIndex]);

        }).on('error', function (err) {
            console.error(err);
        })
    }
};

var callback = function (dirPath, dirs, files) {

    dirsToProcess += dirs.length - 1;

    files.forEach(function (currentValue) {
        filesArray[filesIndex] = dirPath + '/' + currentValue;
        filesIndex++;
    });

    if (dirsToProcess == 0) {
        filesIndex = 0;
        processFile(filesArray[filesIndex]);
    }
};


var start = Date.now();
file.walkSync(dirName, callback);

