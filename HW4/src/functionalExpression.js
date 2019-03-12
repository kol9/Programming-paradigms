"use strict";

function variable(varName) {
    return (x, y, z) => {
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

const unaryOperation = f => (a) => {
    return (x, y, z) => {
        let apply = toFunction(a, x, y, z);
        return f(apply);
    }
};


const binaryOperation = f => (a, b) => {
    return (x, y, z) => {
        let first = toFunction(a, x, y, z);
        let second = toFunction(b, x, y, z);
        return f(first, second);
    }
};

const add = binaryOperation((a, b) => (a + b));
const subtract = binaryOperation((a, b) => (a - b));
const multiply = binaryOperation((a, b) => (a * b));
const divide = binaryOperation((a, b) => (a / b));
const negate = unaryOperation(a => -a);
const cnst = unaryOperation(a => a);


