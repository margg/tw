function printAsync(s, cb) {
    var delay = Math.floor((Math.random()*10)+500);
    setTimeout(function() {
        console.log(s);
        if (cb) cb();
    }, delay);
}

function task1(n, cb) {
    printAsync("1", function() {
        task2(n, cb);
    });
}

function task2(n, cb) {
    printAsync("2", function() {
        task3(n, cb);
    });
}

function task3(n, cb) {
    if(n>1) {
        printAsync("3", function() {
            task1(n-1, cb);
        })
    } else {
        printAsync("3", cb);
    }
}

/*
 ** Zadanie:
 ** Napisz funkcje loop(n), ktora powoduje wykonanie powyzszej
 ** sekwencji zadan n razy. Czyli: 1 2 3 1 2 3 1 2 3 ... done
 **
 */

function loop(n) {

    task1(n, function() {
        console.log('done!');
    })

}

loop(10);