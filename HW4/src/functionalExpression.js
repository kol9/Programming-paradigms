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

function cnst(value) {
    return (x, y, z) => {
        return value;
    }
}


const unaryOperation = f => (arg) => {
    return (x, y, z) => {
        return f(arg)(x, y, z);
    }
};

const binaryOperation = f => (arg1, arg2) => {
    return (x, y, z) => {
        return f(arg1(x, y, z), arg2(x, y, z));
    }
};

const add = binaryOperation((a, b) => (a + b));
const subtract = binaryOperation((a, b) => (a - b));
const multiply = binaryOperation((a, b) => (a * b));
const divide = binaryOperation((a, b) => (a / b));
const negate = unaryOperation(a => -a);
