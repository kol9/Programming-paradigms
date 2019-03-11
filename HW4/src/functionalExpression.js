"use strict";

function variable(varName) {
    return (x, y = 0, z = 0) => {
        // let arr = Object.values(varValues);
        // // println(arr);
        // let x = arr[0];
        // let y = arr[1];
        // let z = arr[2];

        switch (varName) {
            case 'x':
                return x;
            case 'y':
                return y;
            case 'z':
                return z;
        }
    };
}


function toFunction(a, x, y, z) {
    if (typeof a === "function") {
        return a(x, y, z);
    } else {
        return a;
    }
}

let unaryOperation = f => (a) => {
    return (x, y, z) => {
        let apply = toFunction(a,x,y,z);
        return f(apply);
    }
};


const binaryOperation = f => (a, b) => {
    return (x, y, z) => {
        let c = toFunction(a,x,y,z);
        let d = toFunction(b,x,y,z);
        // a = toFunction(a, x, y, z);
        // b = toFunction(b, x, y, z);
        return f(c, d);
    }
};

//
let add = binaryOperation((a, b) => (a + b));
let subtract = binaryOperation((a, b) => (a - b));
let multiply = binaryOperation((a, b) => (a * b));
let divide = binaryOperation((a, b) => (a / b));
let negate = unaryOperation(a => -a);
let cnst = unaryOperation(a => a);

// let expr1 = add(variable('x'), cnst(2));
// let expr2 = add(cnst(3), variable("x"));
// println(expr1(2, 0, 0));
// println(expr1(3, 0, 0));
// println(expr2(5, 0, 0));
// println(expr2(228, 0, 0));


