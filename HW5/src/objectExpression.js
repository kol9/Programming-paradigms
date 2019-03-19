function Variable(varName) {
    this.varName = varName;
}

Variable.prototype.toString = function () {
    return this.varName;
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

Const.prototype.evaluate = function (x, y, z) {
    return this.value;
};


function AbstractUnaryOperation(first) {
    this.first = first;
}

AbstractUnaryOperation.prototype.toString = function () {
    return this.first.toString() + " " + this.operationName;
};

AbstractUnaryOperation.prototype.evaluate = function (x, y, z) {
    return this.evalFunction(this.first.evaluate(x, y, z));
};




function AbstractBinaryOperation(first, second) {
    this.first = first;
    this.second = second;
}

AbstractBinaryOperation.prototype.toString = function () {
    return this.first.toString() + " " + this.second.toString() + " " + this.operationName;
};
AbstractBinaryOperation.prototype.evaluate = function (x, y, z) {
    return this.evalFunction(this.first.evaluate(x, y, z), this.second.evaluate(x, y, z));
};

function Negate(first) {
    AbstractUnaryOperation.apply(this, arguments);
    this.evalFunction = ((x) => (-x));
    this.operationName = "negate";
}


function ArcTan(first) {
    AbstractUnaryOperation.apply(this, arguments);
    this.evalFunction = ((x) => Math.atan(x));
    this.operationName = "atan";
}

Negate.prototype = Object.create(AbstractUnaryOperation.prototype);
ArcTan.prototype = Object.create(AbstractUnaryOperation.prototype);

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
function ArcTan2(first, second) {
    AbstractBinaryOperation.apply(this, arguments);
    this.evalFunction = ((y, x) => Math.atan2(y,x));
    this.operationName = "atan2";
}

Negate.prototype = Object.create(AbstractUnaryOperation.prototype);
Add.prototype = Object.create(AbstractBinaryOperation.prototype);
Subtract.prototype = Object.create(AbstractBinaryOperation.prototype);
Multiply.prototype = Object.create(AbstractBinaryOperation.prototype);
Divide.prototype = Object.create(AbstractBinaryOperation.prototype);
ArcTan2.prototype = Object.create(AbstractBinaryOperation.prototype);

