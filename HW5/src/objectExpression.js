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
    this.evalFunction = ((y, x) => Math.atan2(y, x));
    this.operationName = "atan2";
}

Negate.prototype = Object.create(AbstractUnaryOperation.prototype);
Add.prototype = Object.create(AbstractBinaryOperation.prototype);
Subtract.prototype = Object.create(AbstractBinaryOperation.prototype);
Multiply.prototype = Object.create(AbstractBinaryOperation.prototype);
Divide.prototype = Object.create(AbstractBinaryOperation.prototype);
ArcTan2.prototype = Object.create(AbstractBinaryOperation.prototype);


function AbstractTreeOperation(a, b, c) {
    this.a = a;
    this.b = b;
    this.c = c;
}

function min2(x, y) {
    if (x < y) return x;
    else return y;
}


function Min3() {
    AbstractTreeOperation.apply(this, arguments);
    this.evalFunction = (x, y, z) => (min2(x, min2(y, z)));
    this.operationName = "min3";
}

AbstractTreeOperation.prototype.toString = function () {
    return this.a.toString() + " " + this.b.toString() + " " + this.c.toString() + " " + this.operationName;
};
AbstractTreeOperation.prototype.evaluate = function (x, y, z) {
    return this.evalFunction(this.a.evaluate(x, y, z), this.b.evaluate(x, y, z), this.c.evaluate(x, y, z));
};

Min3.prototype = Object.create(AbstractTreeOperation.prototype);


function max2(x, y) {
    if (x > y) return x;
    else return y;
}

function AbstractFiveOperation(a, b, c, d, e) {
    this.a = a;
    this.b = b;
    this.c = c;
    this.d = d;
    this.e = e;
}


AbstractFiveOperation.prototype.toString = function () {
    return this.a.toString() + " " + this.b.toString() + " " + this.c.toString() + " " + this.d.toString() + " " + this.e.toString() + " " + this.operationName;
};
AbstractFiveOperation.prototype.evaluate = function (x, y, z) {
    return this.evalFunction(this.a.evaluate(x, y, z), this.b.evaluate(x, y, z), this.c.evaluate(x, y, z), this.d.evaluate(x, y, z), this.e.evaluate(x, y, z));
};


function Max5() {
    AbstractFiveOperation.apply(this, arguments);
    this.evalFunction = (x, y, z, n, m) => (max2(x, max2(y, max2(z, max2(n, m)))));
    this.operationName = "max5";
}

Max5.prototype = Object.create(AbstractFiveOperation.prototype);
