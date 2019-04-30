(defn evaluate [expression, applyMap] ((.evaluate expression) applyMap))
(defn toString [expression] (.toStr expression))
(defn diff [expression, diffVarName] ((.diff expression) diffVarName))

(definterface Any
              (evaluate [])
              (toStr [])
              (diff []))

(declare ZERO)
(declare ONE)
(declare TWO)

(deftype Const [value]
         Any
         (evaluate [this] (fn [applyMap] value))
         (toStr [this] (format "%.1f" (double value)))
         (diff [this] (fn [diffVarName] ZERO)))

(defn Constant [value] (Const. value))

(def ZERO (Constant 0))
(def ONE (Constant 1))
(def TWO (Constant 2))

(deftype Var [varName]
         Any
         (evaluate [this] (fn [applyMap] (applyMap varName)))
         (toStr [this] (str varName))
         (diff [this] #(if (= % varName) ONE ZERO)))

(defn Variable [varName] (Var. varName))

(deftype newOp [evalFun, opName, args, diffFun]
         Any
         (evaluate [this] #(apply evalFun (map (fn [x] (evaluate x %)) args)))
         (toStr [this] (str "(" opName " " (clojure.string/join " " (map toString args)) ")"))
         (diff [this] #(apply diffFun (concat args (map (fn [x] (diff x %)) args)))))

(defn Add [& args] (newOp. +, "+", args,
                           (fn [a b da db] (Add da db))))

(defn Subtract [& args] (newOp. -, "-", args,
                                (fn [a b da db] (Subtract da db))))

(defn Multiply [& args] (newOp. *, "*", args,
                                (fn [a b da db] (Add
                                                  (Multiply da b)
                                                  (Multiply a db)))))

(defn Divide [& args] (newOp. #(/ %1 (double %2)), "/", args,
                              (fn [a b da db] (Divide
                                                (Subtract
                                                  (Multiply da b)
                                                  (Multiply a db))
                                                (Multiply b b)))))


(defn Negate [& arg] (newOp. -, "negate", arg,
                             (fn [a da] (Negate da))))

(defn Square [& arg] (newOp. #(* % %), "square", arg,
                             (fn [a da] (Multiply TWO a da))))

(defn Sqrt [& arg] (newOp. #(Math/sqrt (Math/abs ^double %)), "sqrt", arg,
                           (fn [a da] (Divide (Multiply a da)
                                              (Multiply TWO
                                                        (Sqrt (Multiply (Square a) a)))))))

(declare Sinh)
(declare Cosh)

(defn Sinh [& arg] (newOp. #(Math/sinh %), "sinh", arg,
                           (fn [a da] (Multiply da (Cosh a)))))

(defn Cosh [& arg] (newOp. #(Math/cosh %), "cosh", arg,
                           (fn [a da] (Multiply da (Sinh a)))))

(def operations {'+      Add,
                 '-      Subtract,
                 '*      Multiply,
                 '/      Divide,
                 'negate Negate,
                 'square Square,
                 'sqrt   Sqrt,
                 'sinh   Sinh,
                 'cosh   Cosh
                 })

(defn parse [expression]
      (cond
        (number? expression) (Constant expression)
        (symbol? expression) (Variable (str expression))
        (list? expression) (apply (operations (first expression)) (map parse (rest expression)))
        ))

(defn parseObject [s] (parse (read-string s)))
