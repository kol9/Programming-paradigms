const ONE = new Const(1);
const ZERO = new Const(0);

const isNumber = function (x) {
    return (x[0] <= '9' && x[0] >= '0') || (x[0] === '-' && x.length !== 1);
};

let variables = ["x", "y", "z"];
let operations = {
    "negate": [Negate, 1],
    '+': [Add, 2],
    '-': [Subtract, 2],
    '*': [Multiply, 2],
    '/': [Divide, 2],
    "sum": [Sum, -1],
    "avg": [Avg, -1]
};

function toArray(args) {
    let arr = [];
    arr.push.apply(arr, args);
    return arr;
}

function getPrefix(expression) {
    return expression.prefix();
}

function getPostfix(expression) {
    return expression.postfix();
}

const foldLeft = (f, g = (a) => (a)) => {
    return (...args) => {
        let numberOfArguments = 0;
        let result = 0;
        for (const arg of args) {
            ++numberOfArguments;
            result = f(result, arg);
        }
        return g(result, numberOfArguments);
    }
};


/////-----Variable-----/////
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
Variable.prototype.prefix = function () {
    return this.varName;
};
Variable.prototype.postfix = function () {
    return this.varName;
};


/////-----Const-----/////

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
Const.prototype.prefix = function () {
    return this.value.toString();
};
Const.prototype.postfix = function () {
    return this.value.toString();
};

/////-----AbstractOperation-----/////

function AbstractOperation() {
    this.args = toArray(arguments);
}

AbstractOperation.prototype.toString = function () {
    return this.args.map((expression) => {
        return (expression.toString());
}).join(" ") + " " + this.operationName;
};

AbstractOperation.prototype.diff = function (diffVar) {
    return this.diffFunction(diffVar, this);
};

AbstractOperation.prototype.evaluate = function (x, y, z) {
    return this.evalFunction.apply(null, this.args.map((expression) => {
        return expression.evaluate.apply(expression, arguments);
}));
};


AbstractOperation.prototype.prefix = function () {
    return "(" + this.operationName + " " + this.args.map(getPrefix).join(" ") + ")";
};
AbstractOperation.prototype.postfix = function () {
    return "(" + this.args.map(getPostfix).join(" ") + " " + this.operationName + ")";
};

/////-----Description of operations-----/////

function Negate() {
    AbstractOperation.apply(this, arguments);
    this.evalFunction = ((x) => (-x));
    this.diffFunction = (diffVar, operand) =>
    new Negate(
        operand.args[0].diff(diffVar)
    );
    this.operationName = "negate";
}

function Add() {
    AbstractOperation.apply(this, arguments);
    this.evalFunction = ((x, y) => (x + y));
    this.diffFunction = (diffVar, operands) =>
    new Add(
        operands.args[0].diff(diffVar),
        operands.args[1].diff(diffVar)
    );
    this.operationName = "+";
}

function Subtract() {
    AbstractOperation.apply(this, arguments);
    this.evalFunction = ((x, y) => (x - y));
    this.diffFunction = (diffVar, operands) =>
    new Subtract(
        operands.args[0].diff(diffVar),
        operands.args[1].diff(diffVar)
    );
    this.operationName = "-";
}

function Multiply() {
    AbstractOperation.apply(this, arguments);
    this.evalFunction = ((x, y) => (x * y));
    this.diffFunction = (diffVar, operands) =>
    new Add(
        new Multiply(operands.args[0].diff(diffVar), operands.args[1]),
        new Multiply(operands.args[0], operands.args[1].diff(diffVar))
    );
    this.operationName = "*";
}

function Divide() {
    AbstractOperation.apply(this, arguments);
    this.evalFunction = ((x, y) => (x / y));
    this.diffFunction = (diffVar, operands) =>
    new Divide(
        new Subtract(
            new Multiply(operands.args[0].diff(diffVar), operands.args[1]),
            new Multiply(operands.args[0], operands.args[1].diff(diffVar))),
        new Multiply(operands.args[1], operands.args[1])
    );
    this.operationName = "/";
}


function Sum() {
    AbstractOperation.apply(this, arguments);
    this.evalFunction = foldLeft((a, b) => (a + b));
    this.operationName = "sum";
}

function Avg() {
    AbstractOperation.apply(this, arguments);
    this.evalFunction = foldLeft((a, b) => (a + b), (a, numberOfArguments) => (a / numberOfArguments));
    this.operationName = "avg";
}

Negate.prototype = Object.create(AbstractOperation.prototype);
Add.prototype = Object.create(AbstractOperation.prototype);
Subtract.prototype = Object.create(AbstractOperation.prototype);
Multiply.prototype = Object.create(AbstractOperation.prototype);
Divide.prototype = Object.create(AbstractOperation.prototype);
Sum.prototype = Object.create(AbstractOperation.prototype);
Avg.prototype = Object.create(AbstractOperation.prototype);


/////-----parse function-----/////

let parse = function (expression) {

    let stack = [];

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
            } else if (variables.indexOf(token) !== -1) {
                stack.push(new Variable(token));
            }
        }
    );

    return stack.pop();
};

/////-----ParseError-----/////

function ParseError(message) {
    this.name = "ParseError";
    this.message = message;
}

ParseError.prototype = Error.prototype;

/////-----parseException-----/////

function parseExpression(expression, bracketParser, order) {
    let len = expression.length;
    let tokens = parseTokens();
    let tokensLength = tokens.length;
    let ind = 0;

    let parsedExpression = parseOperand();

    if (parsedExpression === undefined) {
        throw new ParseError("Empty input");
    }
    if (parsedExpression === bracketParser(')')) {
        throw new ParseError("Expression starts with " + bracketParser(')'));
    }
    if (ind < tokensLength) {
        throw new ParseError("Wrong prefix format");
    }
    return parsedExpression;


    function parseTokens() {

        let tokens = [];
        let ind = 0;
        let token;
        let separator = "() ";
        while ((token = getToken()) !== undefined) {
            tokens.push(token);
        }

        return order(tokens);

        function getToken() {
            while (expression[ind] === " ") {
                ind++;
            }
            if (ind === len) {
                return undefined;
            }
            if (expression[ind] === '(' || expression[ind] === ')') {
                return bracketParser(expression[ind++]);
            }
            let startIndex = ind;
            while (ind < len && separator.indexOf(expression[ind]) === -1) {
                ind++;
            }
            return expression.substring(startIndex, ind);
        }
    }

    function toNumber(x) {
        let len = x.length;
        let res = 0;
        let startIndex = (x[0] === "-") ? 1 : 0;
        for (let i = startIndex; i < len; i++) {
            if ((isNumber(x[i])) === -1) {
                throw new ParseError("Invalid number");
            }
            res = res * 10 + parseInt(x[i]);
        }
        if (startIndex) res *= -1;
        if (isNaN(res)) {
            throw new ParseError("Invalid number");
        }
        return res;
    }


    function parseOperand() {
        if (ind === tokensLength) {
            return undefined;
        }
        token = tokens[ind++];
        if (token === '(') {
            return parseOperation();
        }
        if (token === ')') {
            return ')';
        }
        if (/^[A-Za-z]*$/.test(token)) {
            if (variables.indexOf(token) === -1) {
                throw new ParseError("Unknown variable");
            }
            return new Variable(token);
        }
        if (operations[token] !== undefined) {
            throw new ParseError("Missing " + bracketParser('('));
        }
        return new Const(toNumber(token));

    }

    function parseOperation() {
        let stack = [], operand;
        let curOperation = tokens[ind++];

        if (operations[curOperation] === undefined) {
            throw new ParseError("Unknown operation");
        }
        while ((operand = parseOperand()) !== ')') {
            if (operand === undefined) {
                throw new ParseError("Missing " + bracketParser(')'));
            }
            stack.push(operand);
        }
        let f = operations[curOperation][0],
            numberOfArguments = operations[curOperation][1];

        if (numberOfArguments === -1) {
            numberOfArguments = stack.length;
        }

        if (stack.length !== numberOfArguments) {
            throw new ParseError("Wrong number of arguments");
        }

        let curExpr = Object.create(f.prototype);
        f.apply(curExpr, order(stack));
        return curExpr;
    }
}

/////-----parsePrefix & parsePostfix-----/////

let parsePrefix = (expression) =>
parseExpression(expression,
    (bracket) => (bracket),
    (tokens) => (tokens)
);
let parsePostfix = (expression) =>
parseExpression(expression,
    (bracket) => (bracket === '(' ? ')' : '('),
    (tokens) => (tokens.reverse())
);
