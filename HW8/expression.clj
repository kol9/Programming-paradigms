(defn constant [value] (fn [applyMap] value))
(defn variable [varName] (fn [applyMap] (applyMap varName)))

(defn operation [f]
  (fn [& args]
    (fn [applyMap]
      (apply f (map (fn [arg] (arg applyMap)) args)))))

(def add (operation +))
(def subtract (operation -))
(def multiply (operation *))
(def divide (operation (fn [a b] (/ (double a) (double b)))))
(def negate subtract)


(def sqrt (operation (fn [x] (Math/sqrt (Math/abs ^double x)))))
(def square (fn [x] (multiply x x)))


(def operations {'+      add,
                 '-      subtract,
                 '*      multiply,
                 '/      divide,
                 'negate negate,
                 'sqrt   sqrt,
                 'square square
                 })

(defn parse [expression]
  (cond
    (number? expression) (constant expression)
    (symbol? expression) (variable (str expression))
    (list? expression) (apply (operations (first expression)) (map parse (rest expression)))
    ))

(defn parseFunction [s] (parse (read-string s)))
