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

function toFunction(arg, x, y, z) {
    if (typeof arg === "function") {
        return arg(x, y, z);
    } else {
        return arg;
    }
}


const unaryOperation = f => (arg) => {
    return (x, y, z) => {
        let apply = toFunction(arg, x, y, z);
        return f(apply);
    }
};

const binaryOperation = f => (arg1, arg2) => {
    return (x, y, z) => {
        let first = toFunction(arg1, x, y, z);
        let second = toFunction(arg2, x, y, z);
        return f(first, second);
    }
};

const add = binaryOperation((a, b) => (a + b));
const subtract = binaryOperation((a, b) => (a - b));
const multiply = binaryOperation((a, b) => (a * b));
const divide = binaryOperation((a, b) => (a / b));
const negate = unaryOperation(a => -a);
const cnst = unaryOperation(a => a);


