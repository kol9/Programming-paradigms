# Programming-paradigms
Homeworks from programming paradigms course in ITMO

##Домашнее задание 1. Обработка ошибок
- Добавьте в программу вычисляющую выражения обработку ошибок, в том числе:
 - ошибки разбора выражений;
 - ошибки вычисления выражений.
- Для выражения 1000000*x*x*x*x*x/(x-1) вывод программы должен иметь следующий вид:
x --------      f
0  -------- 0
1  --------division by zero
2  --------32000000
3  --------  121500000
4   --------    341333333
5   --------    overflow
6   --------    overflow
7  --------     overflow
8  --------    overflow
9    --------   overflow
10  --------    overflow
- Результат division by zero (overflow) означает, что в процессе вычисления произошло деление на ноль (переполнение).
- При выполнении задания следует обратить внимание на дизайн и обработку исключений.
Человеко-читаемые сообщения об ошибках должны выводится на консоль.
- Программа не должна «вылетать» с исключениями (как стандартными, так и добавленными).
Тесты к домашним заданиям

##Домашнее задание 2. Markdown to HTML
- Разработайте конвертер из Markdown-разметки в HTML.
- Конвертер должен поддерживать следующие возможности:
- Абзацы текста разделяются пустыми строками.
- Элементы строчной разметки: выделение (* или _), сильное выделение (** или __), зачеркивание (--), код (`)
- Заголовки (# * уровень заголовка)
- Конвертер должен называться Md2Html и принимать два аргумента: название входного файла с Markdown-разметкой и название выходного файла c HTML-разметкой. Оба файла должны иметь кодировку UTF-8.
####Пример
Входной файл
# Заголовок первого уровня

## Второго

### Третьего ## уровня

#### Четвертого
# Все еще четвертого

Этот абзац текста,
содержит две строки.

    # Может показаться, что это заголовок.
Но нет, это абзац начинающийся с `#`.

#И это не заголовок.

###### Заголовки могут быть многострочными
(и с пропуском заголовков предыдущих уровней)

Мы все любим *выделять* текст _разными_ способами.
**Сильное выделение**, используется гораздо реже,
но __почему бы и нет__?
Немного --зачеркивания-- еще ни кому не вредило.
Код представляется элементом `code`.

Обратите внимание, как экранируются специальные
HTML-символы, такие как `<`, `>` и `&`.

Знаете ли вы, что в Markdown, одиночные * и _
не означают выделение?
Они так же могут быть заэкранированы
при помощи обратного слэша: \*.



Лишние пустые строки должны игнорироваться.

Любите ли вы *вложеные __выделения__* так,
как __--люблю--__ их я?
            
Выходной файл
<h1>Заголовок первого уровня</h1>
<h2>Второго</h2>
<h3>Третьего ## уровня</h3>
<h4>Четвертого
# Все еще четвертого</h4>
<p>Этот абзац текста,
содержит две строки.</p>
<p>    # Может показаться, что это заголовок.
Но нет, это абзац начинающийся с <code>#</code>.</p>
<p>#И это не заголовок.</p>
<h6>Заголовки могут быть многострочными
(и с пропуском заголовков предыдущих уровней)</h6>
<p>Мы все любим <em>выделять</em> текст <em>разными</em> способами.
<strong>Сильное выделение</strong>, используется гораздо реже,
но <strong>почему бы и нет</strong>?
Немного <s>зачеркивания</s> еще ни кому не вредило.
Код представляется элементом <code>code</code>.</p>
<p>Обратите внимание, как экранируются специальные
HTML-символы, такие как <code>&lt;</code>, <code>&gt;</code> и <code>&amp;</code>.</p>
<p>Знаете ли вы, что в Markdown, одиночные * и _
не означают выделение?
Они так же могут быть заэкранированы
при помощи обратного слэша: *.</p>
<p>Лишние пустые строки должны игнорироваться.</p>
<p>Любите ли вы <em>вложеные <strong>выделения</strong></em> так,
как <strong><s>люблю</s></strong> их я?</p>
            
Реальная разметка
Заголовок первого уровня
Второго
Третьего ## уровня
Четвертого # Все еще четвертого
Этот абзац текста, содержит две строки.

# Может показаться, что это заголовок. Но нет, это абзац начинающийся с #.

#И это не заголовок.

Заголовки могут быть многострочными (и с пропуском заголовков предыдущих уровней)
Мы все любим выделять текст разными способами. Сильное выделение, используется гораздо реже, но почему бы и нет? Немного зачеркивания еще ни кому не вредило. Код представляется элементом code.

Обратите внимание, как экранируются специальные HTML-символы, такие как <, > и &.

Знаете ли вы, что в Markdown, одиночные * и _ не означают выделение? Они так же могут быть заэкранированы при помощи обратного слэша: *.

Лишние пустые строки должны игнорироваться.

Любите ли вы вложеные выделения так, как люблю их я?

## Домашнее задание 3. Вычисление в различных типах
 - Добавьте в программу вычисляющую выражения поддержку различных типов.
- Первым аргументом командной строки программа должна принимать указание на тип, в котором будут производится вычисления:
- Опция	Тип
-i	int
-d	double
-bi	BigInteger
- Реализация не должна содержать непроверяемых преобразований типов.
Реализация не должна использовать аннотацию @SuppressWarnings.
- При выполнении задания следует обратить внимание на легкость добавления новых типов и операциий.
## Домашнее задание 4. Функциональные выражения на JavaScript
- Разработайте функции cnst, variable, add, subtract, multiply, divide, negate для вычисления выражений с одной переменной.
- Функции должны позволять производить вычисления вида:


    let expr = subtract(
        multiply(
            cnst(2),
            variable("x")
        ),
        cnst(3)
    );
println(expr(5));
            
- При вычислении такого выражения вместо каждой переменной подставляется значение, переданное в качестве параметра функции expr (на данном этапе имена переменных игнорируются). Таким образом, результатом вычисления приведенного примера должно стать число 7.
- Тестовая программа должна вычислять выражение x2−2x+1, для x от 0 до 10.
Усложненный вариант. Требуется написать функцию parse, осуществляющую разбор выражений, записанных в обратной польской записи. Например, результатом
parse("x x 2 - * x * 1 +")(5)
должно быть число 76.
- При выполнение задания следует обратить внимание на:
- Применение функций высшего порядка.
- Выделение общего кода для бинарных операций.
## Домашнее задание 5. Объектные выражения на JavaScript
- Разработайте классы Const, Variable, Add, Subtract, Multiply, Divide, Negate для представления выражений с одной переменной.
- Пример описания выражения 2x-3:


    let expr = new Subtract(
        new Multiply(
            new Const(2),
            new Variable("x")
        ),
        new Const(3)
    );
                    
- Метод evaluate(x) должен производить вычисления вида: При вычислении такого выражения вместо каждой переменной подставляется значение x, переданное в качестве параметра функции evaluate (на данном этапе имена переменных игнорируются). Таким образом, результатом вычисления приведенного примера должно стать число 7.
- Метод toString() должен выдавать запись выражения в обратной польской записи. Например, expr.toString() должен выдавать 2 x * 3 -.
**УСЛОЖНЕННЫЙ ВАРИАНТ.**
- Метод diff("x") должен возвращать выражение, представляющее производную исходного выражения по переменной x. Например, expr.diff("x") должен возвращать выражение, эквивалентное new Const(2) (выражения new Subtract(new Const(2), new Const(0)) и


    new Subtract(
        new Add(
            new Multiply(new Const(0), new Variable("x")),
            new Multiply(new Const(2), new Const(1))
        )
        new Const(0)
    )
                 
- так же будут считаться правильным ответом).
Функция parse должна выдавать разобранное объектное выражение.

**Бонусный вариант.**  
- Требуется написать метод simplify(), производящий вычисления константных выражений. Например,


    parse("x x 2 - * 1 *").diff("x").simplify().toString()
- должно возвращать «x x 2 - +».
- При выполнение задания следует обратить внимание на:
- Применение инкапсуляции.
- Выделение общего кода для операций.
## Домашнее задание 6. Обработка ошибок на JavaScript
- Добавьте в предыдущее домашнее задание функцию parsePrefix(string), разбирающую выражения, задаваемые записью вида (- (* 2 x) 3). Если разбираемое выражение некорректно, метод parsePrefix должен бросать человеко-читаемое сообщение об ошибке.
- Добавьте в предыдущее домашнее задание метод prefix(), выдающий выражение в формате, ожидаемом функцией parsePrefix.
- При выполнение задания следует обратить внимание на:
- Применение инкапсуляции.
- Выделение общего кода для бинарных операций.
- Обработку ошибок.
- Минимизацию необходимой памяти.
##Домашнее задание 7. Линейная алгебра на Clojure
- Разработайте функции для работы с объектами линейной алгебры, которые представляются следующим образом:
- скаляры – числа
- векторы – векторы чисел;
- матрицы – векторы векторов чисел.
- Функции над векторами:
 -  v+/v-/v* – покоординатное сложение/вычитание/умножение;
 - scalar/vect – скалярное/векторное произведение;
 - v*s – умножение на скаляр.
- Функции над матрицами:
 - m+/m-/m* – поэлементное сложение/вычитание/умножение;
 - m*s – умножение на скаляр;
 - m*v – умножение на вектор;
 - m*m – матричное умножение;
 - transpose – траспонирование;
**Усложненный вариант.**
- Ко всем функциям должны быть указаны контракты. Например, нельзя складывать вектора разной длины.
- Все функции должны поддерживать произвольное число аргументов. Например (v+ [1 2] [3 4] [5 6]) должно быть равно [9 12].
- При выполнение задания следует обратить внимание на:
- Применение функций высшего порядка.
- Выделение общего кода для операций.
##Домашнее задание 8. Функциональные выражения на Clojure
- Разработайте функции constant, variable, add, subtract, multiply и divide для представления арифметических выражений.
- Пример описания выражения 2x-3:


    (def expr
      (subtract
        (multiply
          (constant 2)
          (variable "x"))
        (constant 3)))
                    
- Выражение должно быть функцией, возвращающей значение выражение при подстановке элементов, заданных отображением. Например, (expr {"x" 2}) должно быть равно 1.
- Разработайте разборщик выражений, читающий выражения в стандартной для Clojure форме. Например,


     (parseFunction "(- (* 2 x) 3)")
- должно быть эквивалентно expr.
**Усложненный вариант.** 
- Функции add, subtract, multiply и divide должны принимать произвольное число аргументов. Разборщик так же должен допускать произвольное число аргументов для +, -, *.
- При выполнение задания следует обратить внимание на:
- Выделение общего кода для операций.
## Домашнее задание 9. Объектные выражения на Clojure
- Разработайте конструкторы Constant, Variable, Add, Subtract, Multiply и Divide для представления выражений с одной переменной.
- Пример описания выражения 2x-3:


    (def expr
      (Subtract
        (Multiply
          (Constant 2)
          (Variable "x"))
        (Const 3)))
                    
- Функция (evaluate expression vars) должна производить вычисление выражения expression для значений переменных, заданных отображением vars. Например, (evaluate expr {"x" 2}) должно быть равно 1.
- Функция (toString expression) должна выдавать запись выражения в стандартной для Clojure форме.
- Функция (parseObject "expression") должна разбирать выражения, записанные в стандартной для Clojure форме. Например,


    (parseObject "(- (* 2 x) 3)")
- должно быть эквивалентно expr.
- Функция (diff expression "variable") должена возвращать выражение, представляющее производную исходного выражения по заданой пермененной. Например, (diff expression "x") должен возвращать выражение, эквивалентное (Constant 2), при этом выражения (Subtract (Const 2) (Const 0)) и


    (Subtract
      (Add
        (Multiply (Const 0) (Variable "x"))
        (Multiply (Const 2) (Const 1)))
      (Const 0))
                    
- так же будут считаться правильным ответом.
**Усложненный вариант.**
- Констуркторы Add, Subtract, Multiply и Divide должны принимать произвольное число аргументов. Разборщик так же должен допускать произвольное число аргументов для +, -, *, /.
- При выполнение задания можно использовать любой способ преставления объектов.
##Домашнее задание 10. Комбинаторные парсеры
**Простой вариант. **
-- Реализуйте функцию (parseObjectSuffix "expression"), разбирающую выражения, записанные в суффиксной форме, и функцию toStringSuffix, возвращающую строковое представление выражения в этой форме. Например,


    (toStringSuffix (parseObjectSuffix "( ( 2 x * ) 3 - )"))
- должно возвращать ((2 x *) 3 -).
**Сложный вариант.** 
- Реализуйте функцию (parseObjectInfix "expression"), разбирающую выражения, записанные в инфиксной форме, и функцию toStringInfix, возвращающую строковое представление выражения в этой форме. Например,


    (toStringInfix (parseObjectInfix "2 * x - 3"))
- должно возвращать ((2 * x) - 3).
**Бонусный вариант.** 
- Добавьте в библиотеку комбинаторов возможность обработки ошибок и продемонстрируйте ее использование в вашем парсере.
- Функции разбора должны базироваться на библиотеке комбинаторов, разработанной на лекции.
## Домашнее задание 11. Ассоциативные массивы на Prolog
- Реализуйте ассоциативный массив (map).
- Простой вариант. Ассоциативный массив на упорядоченном списке пар ключ-значение.

#####Разработайте правила:

- map_get(ListMap, Key, Value), проверяющее, что дерево содержит заданную пару ключ-значение.
- map_put(ListMap, Key, Value, Result), добавляющее пару ключ-значение в дерево, или заменяющее текущее значение для ключа;
- map_remove(ListMap, Key, Result), удаляющее отображение для ключа.
- Правила не должны анализировать хвост списка, если в нем точно нет необходимого ключа.

**Сложный вариант**.
 - Ассоциативный массив на двоичном дереве.
 

 - Разработайте правила:

-  tree_build(ListMap, TreeMap) 
 - строящее дерево из упорядоченного набора пар ключ-значение;
- map_get(TreeMap, Key, Value).
 - Для решения можно реализовать любое дерево поиска логарифмической высоты.

- Бонусный вариант. 
 - Дополнительно разработайте правила:

- map_put(TreeMap, Key, Value, Result);
map_remove(TreeMap, Key, Result).
