var async = require('async');

var Fork = function () {
    this.state = 0;
    return this;
};

Fork.prototype.acquire = function (id, cb) {

    // zaimplementuj funkcję acquire, tak by korzystala z algorytmu BEB
    // (http://pl.wikipedia.org/wiki/Binary_Exponential_Backoff), tzn:
    // 1. przed pierwszą próbą podniesienia widelca Filozof odczekuje 1ms
    // 2. gdy próba jest nieudana, zwiększa czas oczekiwania dwukrotnie
    //    i ponawia próbę, itd.

    var fork = this;

    var tryAcquire = function (time, callback) {
        setTimeout(function () {
            if (fork.state === 0) {
                fork.state = 1;
                callback();
            } else {
                times[id] += 2 * time;
                tryAcquire(2 * time, callback);
            }
        }, time);
    };

    tryAcquire(1, cb);
};

Fork.prototype.release = function () {
    this.state = 0;
};

var Philosopher = function (id, forks) {
    this.id = id;
    this.forks = forks;
    this.leftfork = forks[id % forks.length];
    this.rightFork = forks[(id + 1) % forks.length];
    return this;
};


// ------------- NAIVE SOLUTION --------------

Philosopher.prototype.startNaive = function (count) {
    // zaimplementuj rozwiązanie naiwne
    // każdy filozof powinien 'count' razy wykonywać cykl
    // podnoszenia widelców -- jedzenia -- zwalniania widelców

    var forks = this.forks;
    var left = this.leftfork;
    var right = this.rightFork;
    var id = this.id;

    var eatFuns = [];

    for (var i = 0; i < count; i++) {
        eatFuns.push(function (callback) {
            left.acquire(id, function () {
                    console.log("Philosopher " + id + " took LEFT fork.");
                    right.acquire(id, function () {
                            console.log("Philosopher " + id + " took RIGHT fork.");
                            setTimeout(function () {
                                left.release();
                                right.release();
                                console.log("Philosopher " + id + " RELEASED forks.");
                                callback();
                            }, 200);
                        }
                    );
                }
            );
        });
    }

    async.waterfall(eatFuns, function (error, result) {
        console.log("Philosopher " + id + " finished!");
    });
};


// ------------- ASYMMETRIC SOLUTION --------------

Philosopher.prototype.startAsym = function (count, cb) {
    var forks = this.forks;
    var left = this.leftfork;
    var right = this.rightFork;
    var id = this.id;

    // zaimplementuj rozwiązanie asymetryczne
    // każdy filozof powinien 'count' razy wykonywać cykl
    // podnoszenia widelców -- jedzenia -- zwalniania widelców


    var tasks = [];

    if (id % 2 === 0) {

        for (var i = 0; i < count; i++) {
            tasks.push(function (callback) {
                left.acquire(id, function () {
                    console.log("Philosopher " + id + " took LEFT fork.");
                    right.acquire(id, function () {
                        console.log("Philosopher " + id + " took RIGHT fork.");

                        setTimeout(function () {
                            left.release();
                            right.release();
                            console.log("Philosopher " + id + " RELEASED forks.");

                            callback();
                        }, 200);
                    });
                });
            });
        }
    } else {

        for (var i = 0; i < count; i++) {
            tasks.push(function (callback) {
                right.acquire(id, function () {
                    console.log("Philosopher " + id + " took RIGHT fork.");
                    left.acquire(id, function () {
                        console.log("Philosopher " + id + " took LEFT fork.");

                        setTimeout(function () {
                            left.release();
                            right.release();
                            console.log("Philosopher " + id + " RELEASED forks");

                            callback();
                        }, 200);
                    });
                });
            });
        }
    }

    async.waterfall(tasks, function (error, result) {
        console.log("Philosopher " + id + " FINISHED! (waited " + result + " ms)");
        if (cb !== undefined) {
            cb();
        }
    });
};


// ---------- CONDUCTOR SOLUTION -------------

var Conductor = function () {
};

Conductor.prototype.acquireForks = function (id, left, right, cb) {

    var tryAcquire = function (time, callback) {
        setTimeout(function () {
            if (left.state === 0 && right.state === 0) {
                left.state = 1;
                right.state = 1;
                callback();
            } else {
                times[id] += 2 * time;
                tryAcquire(2 * time, callback);
            }
        }, time);
    };

    tryAcquire(1, cb);
};

Philosopher.prototype.startConductor = function (conductor, count, cb) {
    var forks = this.forks;
    var left = this.leftfork;
    var right = this.rightFork;
    var id = this.id;

    // zaimplementuj rozwiązanie z kelnerem
    // każdy filozof powinien 'count' razy wykonywać cykl
    // podnoszenia widelców -- jedzenia -- zwalniania widelców


    var eatFuns = [];

    for (var i = 0; i < count; i++) {
        eatFuns.push(function (callback) {

            conductor.acquireForks(id, left, right, function () {
                console.log("Philosopher " + id + " GOT forks.");

                setTimeout(function () {
                    left.release();
                    right.release();
                    console.log("Philosopher " + id + " RELEASED forks.");
                    callback();
                }, 200);
            });
        });
    }

    async.waterfall(eatFuns, function (error, result) {
        console.log("Philosopher " + id + " FINISHED!");
        if (cb !== undefined) {
            cb();
        }
    });
};


// -------- NAIVE TEST --------

function testNaive(N) {

    var forks = [];
    var philosophers = [];
    for (var i = 0; i < N; i++) {
        forks.push(new Fork());
    }

    for (var i = 0; i < N; i++) {
        philosophers.push(new Philosopher(i, forks));
    }

    for (var i = 0; i < N; i++) {
        philosophers[i].startNaive(10);
    }
}

// -------- ASYMMETRIC TEST --------

function testAsym(N, philosophers, times, cb) {

    var tasks = [];
    for (var i = 0; i < N; i++) {
        tasks.push(i);
    }

    tasks = tasks.map(function (i) {
        return function (callback) {
            philosophers[i].startAsym(10, callback);
        };
    });

    async.parallel(tasks, function (error, results) {
        if (cb !== undefined) {
            cb();
        }
    });
}


// -------- CONDUCTOR TEST --------

function testConductor(N, philosophers, times, conductor, cb) {

    var tasks = [];
    for (var i = 0; i < N; i++) {
        tasks.push(i);
    }

    tasks = tasks.map(function (i) {
        return function (callback) {
            philosophers[i].startConductor(conductor, 10, callback);
        };
    });

    async.parallel(tasks, function (error, results) {
        times.push(results);
        if (cb !== undefined) {
            cb();
        }
    });
}


// ----------- RUN ------------

function run(N) {

    var forks = [];
    var philosophers = [];
    var times = [];
    var conductor = new Conductor();
    for (var i = 0; i < N; i++) {
        forks.push(new Fork());
        times[i] = 1;
    }

    for (var i = 0; i < N; i++) {
        philosophers.push(new Philosopher(i, forks));
    }

/*
    testAsym(N, philosophers, times, function () {
        console.log("\n\nASYMMETRIC SOLUTION\n");
        printTimes();
    });

    */

    testConductor(N, philosophers, times, conductor, function () {
        console.log("\n\nCONDUCTOR SOLUTION\n");
        printTimes();
    });


    function printTimes() {
        for (var i = 0; i < N; i++) {
            console.log("Philosopher " + i + " waited: " + times[i] + " ms.")
        }
    }
}


run(10);