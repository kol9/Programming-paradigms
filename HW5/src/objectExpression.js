function Variable(varName) {
    this.varName = varName;
}

Variable.prototype.toString = function () {
    return this.varName + " ";
};

Variable.prototype.evaluate = function (x) {
    return x;
};

function Const(value) {
    this.value = value;
}

Const.prototype.toString = function () {
    return this.value.toString() + " ";
};

Const.prototype.evaluate = function (x) {
    return this.value;
};

/////////////////////////////////////////////////// perfect

function AbstractUnaryOperation(first) {
    this.first = first;
}

AbstractUnaryOperation.prototype.toString = function () {
    return this.first.toString() + this.operationName + " ";
};

AbstractUnaryOperation.prototype.evaluate = function (x) {
    return this.evalFunction(this.first.evaluate(x));
};


function Negate(first) {

    AbstractUnaryOperation.apply(this, arguments);
    this.evalFunction = ((x) => (-x));
    this.operationName = "-";
}

Negate.prototype = Object.create(AbstractUnaryOperation.prototype);


function AbstractBinaryOperation(first, second) {
    this.first = first;
    this.second = second;
}

AbstractBinaryOperation.prototype.toString = function () {
    return this.first.toString() + this.second.toString() + this.operationName + " ";
};
AbstractBinaryOperation.prototype.evaluate = function (x) {
    return this.evalFunction(this.first.evaluate(x), this.second.evaluate(x));
};

function Add(first, second) {
    AbstractBinaryOperation.apply(this, arguments);
    this.evalFunction = ((x, y) => (x + y));
    this.operationName = "+";
}

function Subtract(first, second) {
    AbstractBinaryOperation.apply(this, arguments);
    this.evalFunction = ((x, y) => (x - y));
    this.operationName = "-";
}

function Multiply(first, second) {
    AbstractBinaryOperation.apply(this, arguments);
    this.evalFunction = ((x, y) => (x * y));
    this.operationName = "*";
}

function Divide(first, second) {
    AbstractBinaryOperation.apply(this, arguments);
    this.evalFunction = ((x, y) => (x / y));
    this.operationName = "/";
}

Negate.prototype = Object.create(AbstractUnaryOperation.prototype);
Add.prototype = Object.create(AbstractBinaryOperation.prototype);
Subtract.prototype = Object.create(AbstractBinaryOperation.prototype);
Multiply.prototype = Object.create(AbstractBinaryOperation.prototype);
Divide.prototype = Object.create(AbstractBinaryOperation.prototype);


// let expr = new Negate(new Variable('x'));
// let expr = new Add(new Variable('x'), new Add(new Variable('x'), new Variable('x')));
// let expr = new Subtract(
//     new Multiply(
//         new Const(2),
//         new Variable("x")
//     ),
//     new Const(3)
// );
// print(expr.toString());
