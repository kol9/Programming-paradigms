(defn evaluate [expression, applyMap] ((.evaluate expression) applyMap))
(defn toString [expression] (.toStr expression))
(defn toStringSuffix [expression] (.toStrSuff expression))
(defn diff [expression, diffVarName] ((.diff expression) diffVarName))

(definterface Any
              (evaluate [])
              (toStr [])
              (toStrSuff [])
              (diff []))

(declare ZERO)
(declare ONE)
(declare TWO)

(deftype Const [value]
         Any
         (evaluate [this] (fn [applyMap] value))
         (toStr [this] (format "%.1f" (double value)))
         (toStrSuff [this] (format "%.1f" (double value)))
         (diff [this] (fn [diffVarName] ZERO)))

(defn Constant [value] (Const. value))

(def ZERO (Constant 0))
(def ONE (Constant 1))
(def TWO (Constant 2))

(deftype Var [varName]
         Any
         (evaluate [this] (fn [applyMap] (applyMap varName)))
         (toStr [this] (str varName))
         (toStrSuff [this] (str varName))
         (diff [this] #(if (= % varName) ONE ZERO)))

(defn Variable [varName] (Var. varName))

(deftype newOp [evalFun, opName, args, diffFun]
         Any
         (evaluate [this] #(apply evalFun (map (fn [x] (evaluate x %)) args)))
         (toStr [this] (str "(" opName " " (clojure.string/join " " (map toString args)) ")"))
         (toStrSuff [this] (str "(" (clojure.string/join " " (map toStringSuffix args)) " " opName ")"))
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

(defn Pow [& args] (newOp. #(Math/pow %1 %2), "**", args, ()))

(defn Log [& args] (newOp. #(/ (Math/log (Math/abs ^double %2)) (Math/log (Math/abs ^double %1))), "//", args, ()))

(def OPERATIONS {"+"      Add,
                 "-"      Subtract,
                 "*"      Multiply,
                 "/"      Divide,
                 "negate" Negate,
                 "square" Square,
                 "sqrt"   Sqrt,
                 "sinh"   Sinh,
                 "cosh"   Cosh,
                 "**"     Pow,
                 "//"     Log
                 })

(defn parse [expression]
      (cond
        (number? expression) (Constant expression)
        (symbol? expression) (Variable (str expression))
        (list? expression) (apply (OPERATIONS (str first expression)) (map parse (rest expression)))
        ))

(defn parseObject [s] (parse (read-string s)))

;------------------------------------------------------------------------------------------
(defn -return [value tail] {:value value :tail tail})
(def -valid? boolean)
(def -value :value)
(def -tail :tail)

(defn _empty [value] (partial -return value))
(defn _char [p] (fn [[c & cs]] (if (and c (p c)) (-return c cs))))
(defn _map [f] (fn [result] (if (-valid? result) (-return (f (-value result)) (-tail result)))))
(defn _combine [f a b] (fn [str] (let [ar ((force a) str)]
                                      (if (-valid? ar) ((_map (partial f (-value ar))) ((force b) (-tail ar)))))))
(defn _either [a b] (fn [str] (let [ar ((force a) str)] (if (-valid? ar) ar ((force b) str)))))
(defn _parser [p] (fn [input] (-value ((_combine (fn [v _] v) p (_char #{\u0000})) (str input \u0000)))))

(defn +char [chars] (_char (set chars)))
(defn +char-not [chars] (_char (comp not (set chars))))
(defn +map [f parser] (comp (_map f) parser))
(defn iconj [coll value] (if (= value 'ignore) coll (conj coll value)))
(defn +seq [& ps] (reduce (partial _combine iconj) (_empty []) ps))
(defn +seqf [f & ps] (+map (partial apply f) (apply +seq ps)))
(defn +seqn [n & ps] (apply +seqf (fn [& vs] (nth vs n)) ps))
(defn +or [p & ps] (reduce (partial _either) p ps))
(defn +opt [p] (+or p (_empty nil)))
(defn +star [p] (letfn [(rec [] (+or (+seqf cons p (delay (rec))) (_empty ())))] (rec)))
(defn +plus [p] (+seqf cons p (+star p)))
(defn +str [p] (+map (partial apply str) p))

(def +parser _parser)
(def +ignore (partial +map (constantly 'ignore)))


(def *possible-chars (mapv char (range 32 128)))
(def *whitespace (+char (apply str (filter #(Character/isWhitespace ^char %) *possible-chars))))
(def *letter (+char (apply str (filter #(Character/isLetter ^char %) *possible-chars))))
(def *digit (+char (apply str (filter #(Character/isDigit ^char %) *possible-chars))))

(def *ws (+ignore (+star *whitespace)))

(def *const (+map (comp Constant read-string) (+str (+seq (+opt (+char "-")) (+str (+plus *digit)) (+char ".") *digit))))

(def *token-char (+or *letter (+char "+-*/")))

(def *identifier (+str (+seqf cons *ws *token-char (+star (+or *token-char *digit)))))

(def *token (+map (comp #(get OPERATIONS (str %) (Variable (str %))) symbol) *identifier))


(declare *expression)
(defn *seq [p] (+seqn 1 *ws (+char "(") (+opt (+seqf cons *ws p (+star (+seqn 0 *ws p)))) *ws (+char ")")))
(def *list (+map (fn [list] (apply (last list) (butlast list))) (*seq (delay *expression))))
(def *expression (+or *const *token *list))

(def parseObjectSuffix (+parser (+seqn 0 *ws *expression *ws)))

;
;(def expr (parseObjectSuffix "yet"))
;(println (evaluate expr {'x' 2, 'y' 3}))
;(println (toStringSuffix expr))
;(def expr (Log (Add (Constant 2.0) (Multiply (Constant 4.0) (Subtract (Variable "x") (Variable "z")))) (Add (Constant 1.0) (Multiply (Constant 2.0) (Subtract (Variable "y") (Variable "z"))))))
;(println (evaluate expr {"z" 1.0, "x" 0.0, "y" 0.0}))