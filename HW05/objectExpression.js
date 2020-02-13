const ONE = new Const(1);
const ZERO = new Const(0);
const isNumber = function (x) {
    return (x[0] <= '9' && x[0] >= '0') || (x[0] === '-' && x.length !== 1);
};

function Variable(varName) {
    this.varName = varName;
}

Variable.prototype.toString = function () {
    return this.varName;
};

Variable.prototype.diff = function (diffVar) {
    return this.varName === diffVar ? ONE : ZERO;
};

Variable.prototype.evaluate = function (x, y, z) {
    if (this.varName === "x") {
        return x;
    } else if (this.varName === "y") {
        return y;
    } else if (this.varName === "z") {
        return z;
    }
};

function Const(value) {
    this.value = value;
}

Const.prototype.toString = function () {
    return this.value.toString();
};

Const.prototype.diff = function (diffVar) {
    return ZERO;
};
Const.prototype.evaluate = function (x, y, z) {
    return this.value;
};


function toArray(args) {
    let arr = [];
    arr.push.apply(arr, args);
    return arr;
}

function AbstractOperation() {
    this.args = toArray(arguments);
}

AbstractOperation.prototype.toString = function () {
    return this.args.map((expression) => {
        return (expression.toString());
}).join(" ") + " " + this.operationName;
};

AbstractOperation.prototype.diff = function (diffVar) {
    return this.diffFunction(this, diffVar);
};

AbstractOperation.prototype.evaluate = function (x, y, z) {
    return this.evalFunction.apply(null, this.args.map((expression) => {
        return expression.evaluate.apply(expression, arguments);
}));
};


function Negate() {
    AbstractOperation.apply(this, arguments);
    this.evalFunction = ((x) => (-x));
    this.diffFunction = (operand, diffVar) =>
    new Negate(
        operand.args[0].diff(diffVar)
    );
    this.operationName = "negate";
}

function Add() {
    AbstractOperation.apply(this, arguments);
    this.evalFunction = ((x, y) => (x + y));
    this.diffFunction = (operands, diffVar) =>
    new Add(
        operands.args[0].diff(diffVar),
        operands.args[1].diff(diffVar)
    );
    this.operationName = "+";
}

function Subtract() {
    AbstractOperation.apply(this, arguments);
    this.evalFunction = ((x, y) => (x - y));
    this.diffFunction = (operands, diffVar) =>
    new Subtract(
        operands.args[0].diff(diffVar),
        operands.args[1].diff(diffVar)
    );
    this.operationName = "-";
}

function Multiply() {
    AbstractOperation.apply(this, arguments);
    this.evalFunction = ((x, y) => (x * y));
    this.diffFunction = (operands, diffVar) =>
    new Add(
        new Multiply(operands.args[0].diff(diffVar), operands.args[1]),
        new Multiply(operands.args[0], operands.args[1].diff(diffVar))
    );
    this.operationName = "*";
}

function Divide() {
    AbstractOperation.apply(this, arguments);
    this.evalFunction = ((x, y) => (x / y));
    this.diffFunction = (operands, diffVar) =>
    new Divide(
        new Subtract(
            new Multiply(operands.args[0].diff(diffVar), operands.args[1]),
            new Multiply(operands.args[0], operands.args[1].diff(diffVar))),
        new Multiply(operands.args[1], operands.args[1])
    );
    this.operationName = "/";
}

function Min3() {
    AbstractOperation.apply(this, arguments);
    this.evalFunction = Math.min;
    this.diffFunction = (operands, diffVar) => new Min3(
        operands.args[0].diff(diffVar),
        operands.args[1].diff(diffVar),
        operands.args[2].diff(diffVar),
    );
    this.operationName = "min3";
}

function Max5() {
    AbstractOperation.apply(this, arguments);
    this.evalFunction = Math.max;
    this.diffFunction = (operands, diffVar) => new Max5(
        operands.args[0].diff(diffVar),
        operands.args[1].diff(diffVar),
        operands.args[2].diff(diffVar),
        operands.args[3].diff(diffVar),
        operands.args[4].diff(diffVar));
    this.operationName = "max5";
}

function Sinh() {
    AbstractOperation.apply(this, arguments);
    this.evalFunction = Math.sinh;
    this.diffFunction = (operand, diffVar) => {
        return new Multiply(
            new Cosh(operand.args[0]),
            operand.args[0].diff(diffVar)
        );
    };
    this.operationName = "sinh"
}

function Cosh() {
    AbstractOperation.apply(this, arguments);
    this.evalFunction = Math.cosh;
    this.diffFunction = (operand, diffVar) =>
    new Multiply(
        new Sinh(operand.args[0]),
        operand.args[0].diff(diffVar)
    );
    this.operationName = "cosh"
}

Negate.prototype = Object.create(AbstractOperation.prototype);
Add.prototype = Object.create(AbstractOperation.prototype);
Subtract.prototype = Object.create(AbstractOperation.prototype);
Multiply.prototype = Object.create(AbstractOperation.prototype);
Divide.prototype = Object.create(AbstractOperation.prototype);
Min3.prototype = Object.create(AbstractOperation.prototype);
Max5.prototype = Object.create(AbstractOperation.prototype);
Sinh.prototype = Object.create(AbstractOperation.prototype);
Cosh.prototype = Object.create(AbstractOperation.prototype);


let parse = function (expression) {
    let stack = [];
    let variables = {
        "x": Variable,
        "y": Variable,
        "z": Variable
    };
    let operations = {
        "negate": [Negate, 1],
        '+': [Add, 2],
        '-': [Subtract, 2],
        '*': [Multiply, 2],
        '/': [Divide, 2],
        "min3": [Min3, 3],
        "max5": [Max5, 5],
        "sinh": [Sinh, 1],
        "cosh": [Cosh, 1],
    };

    let tokens = expression.split(' ').filter(token => token.length > 0);
    tokens.map(function (token) {
            if (token in operations) {
                let operation = operations[token][0];
                let args = [];
                stack.slice(stack.length - operations[token][1], stack.length).forEach(() => {
                    args.push(stack.pop());
            });
                let expr = new operation;
                args.reverse();
                operation.apply(expr, args);
                stack.push(expr);
            } else if (isNumber(token)) {
                stack.push(new Const(parseFloat(token)));
            } else if (token in variables) {
                stack.push(new Variable(token));
            }
        }
    );

    return stack.pop();
};

