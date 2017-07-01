# Language Reference

On this page we provide a reference of all the basic elements of the RankPL programming language, as well as an overview of all available statements and expressions. We start with instructions on how to use the interpreter.

## Usage Instructions<a name="usage-instructions"></a>

The RankPL interpreter is distributed as a self-contained jar file `RankPL.jar`. Basic usage is:
```
java -jar RankPL.jar -source source_file.rpl
```
where `source_file.rpl` is the program to run. The following command line options are available:

| Option                       | Description                    |     
|------------------------------|--------------------------------|
| `-rank <max_rank>`           | By default, only the rank zero outcomes are generated. Use this option to generate outcomes up to `max_rank`. |
| `-all`                       | Generate *all* outcomes. |
| `-t <timeout>`               | Apply an execution time-out (in milliseconds). This will cause execution to terminate (regardless of whether an outcome was generated) after the given time-out. |
| `-c <rank_cutoff>`           | Discard alternatives ranked higher than <rank_cutoff>. Defaults to ∞ (i.e., never discard alternatives). This option can be used to avoid computation for events that are too surprising, which may speed up execution. Note that the generated ranking might be incomplete or even incorrect. |
| `-d`                         | Enable *iterative deepening execution mode*. The program is executed repeatedly with increasing `rank_cutoff` values. This will significantly speed up execution of programs with large sets of alternatives, while still generating correct results. This is an experimental feature.  |
| `-f`                         | Terminate after generating the first outcome. |
| `-ns`                        | Don't print execution statistics. |
| `-nr`                        | Don't print ranks. |
| `-help`                      | Show help message. |

## Programs<a name="programs"></a>

A RankPL program is a sequence of statements and (optionally) function definitions. The values returned by a `return` statement are the values returned by the program. Alternatively, if a function `main()` is defined, then the body of the `main()` function is taken to be the program. In that case, the values returned by the `main()` function are the values returned by the program. If a `main()` function is present then statement outside the body of the `main()` function are ignored.

## Functions<a name="functions"></a>

A function definition has the form:
```
define function_name(var₁, var₂, ...) {
	s₁;
	s₂;
	...
};
```
Here, `s₁`, `s₂`, ... are the statements that make up the function body, and `var₁`, `var₂`, ... are the parameters of the function. A function call is an expression of the form:
```
function_name(arg₁, arg₂, ...)
```
Here, `arg₁ `, `arg₂ `, ... are expressions that act as arguments. The number of arguments supplied with a function call must match the number of parameters of the function. Violation leads to an error. Parameters are not explicitly typed; mistakes may lead to type errors thrown from within the function body. 

Every function must terminate with a `return` statement. A missing `return` statement is an error. Functions containing ranked choice statements return a ranking over the set of possible return values. 

Any function defined in a program can be called from any place within that program. Function definitions need not appear before their respective function calls.

## Statements<a name="statements"></a>

Statements in RankPL are separated by semicolons. If a statement contains sub-statements (such as the `if b s1 else s2` statement) then these sub-statements may be *block statements*, which are sequences of statements enclosed in curly brackets (`{ ... }`). 

The table below provides an overview of all available statements in RankPL. We use the following symbols (possibly with subscript) to refer to specific types of expressions:
- `s`: 		a statement
- `var`: 	a variable
- `e`: 		an expression (any type)
- `n`: 		an integer expression
- `b`: 		a boolean expression
- `str`: 	a string expression

|Statement      	|                  Form                   |Description	|
|-----------------------|-----------------------------------------|:--------------|
|Assignment		| `var := e`				| Assign value of `e` to `var`. **(1)** |
|Ranked assignment  	| `var := e₁ << n >> e₂`		| Normally assign to `var` the value of `e₁`, exceptionally (to degree `n`) assign the value of `e2`. **(1,5)**	|
|Range assignment	| `var := << n₁ ... n₂ >>`  		| Assign to `var` a random value between `n₁`(inclusive) and `n₂` (exclusive), all ranked 0. **(1)**|
|If-else		| `if b s₁ else s₂`		        | Regular if-else statement. **(2)** |
|while-do		| `while b do s`			| Execute `s` as long as `b` evaluates to true.		|
|for loop		| `for (s₁; b; s₂) s₃`		        | Java-style for loop. `s₁` is the init statement, `b` the termination condition and `s₂` the increment statement.|
|observe		| `observe b`				| Observe that `b` is true (uniformly shift down alternatives that satisfy `b` and eliminate alternatives not satisfying `b`).|
|observe-j		| `observe-j (n) b`			| Observe that `b` is normally, to degree `n`, true (like `observe b`, but uniformly shifts the rank of alternatives not satisfying `b` to `n`). **(3,5)**		|
|observe-l		| `observe-l (n) b`			| Improve rank of alternatives satisfying `b` by `n` units w.r.t. alternatives not satisfying `b`. **(3,5)**|
|Ranked choice  	| `normally (n) s₁ exceptionally s₂`	| Normally execute `s₁`, exceptionally (to degree `n`) execute `s₂`. **(3,4,5)**	|
|Indifferent choice  	| `either s₁ or s₂`			| Same as `normally (0) s₁ exceptionally s₂`. 		|
|Print			| `print str`				| Print `str` to console.		|
|Cut			| `cut(n)`				| Eliminate all alternatives ranked higher than `n`. |
|Return			| `return e`				| Return `e` as value of program or function.		|
|Skip			| `skip`				| Does nothing.		|
|Assert			| `assert b`	                        | Used for testing. Throws error if `b` is false. |
|Assert	Ranked		| `assert-ranked (e, [n₁, e₁], [n₂, e₂], ...)`	| Used for testing. Checks that `e` equals `e₁` with rank `n₁`, `e₂` with rank `n₂`, etc. Throws error otherwise. |

- **(1)**: Variables on the left hand side of an assignment, ranked assignment, or ranged choice statement may include array indices. That is, they may be of the form `var[e₁]...[e_n]`, where `e₁`, ..., `e_n` are indices to the (multi-dimensional) array stored in `var`. Illegal indices lead to a *value undefined* or *index out of bounds* error.
- **(2)**: The `else s₂` part may be omitted. If it is, `s₂` is taken to be the `skip` statement.
- **(3)**: The `(n)` part may be omitted. If it is, `n` defaults to 1.
- **(4)**: The `exceptionally s₂` part may be omitted. If it is, `s₂` is taken to be the `skip` statement.
- **(5)**: Rank and observation strength arguments must be non-negative integers.

## Expressions<a name="expressions"></a>

Expressions in RankPL are either integer, boolean, string, or array-valued. All expressions are dynamically typed. This means, for example, that an expression like `"ab"” <= "def"` is syntactically correct but will lead to a type error during execution. In this section we provide an overview of all expressions in RankPL.

In the list below, we use the following symbols (possibly with subscript) to refer to expressions with specific types:
- `var`: 	a variable
- `e`: 		an expression (any type)
- `n`: 		an integer expression
- `b`: 		a boolean expression
- `str`: 	a string expression

|Expression			|Evaluates to	|Description						|
|-------------------------------|---------------|-------------------------------------------------------|
|`abs(n)`			|int		|Absolute value of `n`					|
|`array(n)`			|array		|Array of length `n`. Elements not initialised.		|
|`array(n,e)`			|array		|Array of length `n`. Elements initialised to `e`.	|
|`[e₁, …, eₓ]`		        |array		|Array of length `x` initialized with values `e₁`,...,`eₓ`|
|`b? e₁: e₂`			|any		|Evaluates to `e₁` if `b` is true. Evaluates to `e₂` otherwise. |
|`b₁ & b₂`			|boolean	|Boolean AND						|
|`b₁ \| b₂`			|boolean	|Boolean OR						|
|`b₁ ^ b₂`			|boolean	|Boolean XOR						|
|`!b`				|boolean	|Boolean NOT						|
|`e₁ == e₂`			|boolean	|Equality of `e1` and `e2` **(1)**			|
|`e₁ != e₂`			|boolean	|Inequality of `e1` and `e2` **(1)**			|
|`isset(var)`			|boolean	|True iff variable `var` is set, false if not		|
|`len(v)`			|int		|Length of array or string `v`				|
|`max(n₁, ..., iₓ)`		|int		|Maximum of `n₁` ... `nₓ`				|
|`min(n₁, ..., iₓ)`		|int		|Maximum of `n₁` ... `nₓ`				|
|`parseint(str)`		|int		|`str` parsed as integer				|
|`n₁ < n₂`			|boolean	|True iff `n₁` less than `n₂`				|
|`n₁ > n₂`			|boolean	|True iff `n₁` more than `n₂`				|
|`n₁ =< n₂`			|boolean	|True iff `n₁` less than or equal to `n₂`		|
|`n₁ => n₂`			|boolean	|True iff `n₁` more than or equal to `n₂`		|
|`e₁ + e₂`			|int/string	|Sum of integers or concatenation of strings **(2)**	|
|`n₁ - n₂`			|int		|Subtraction						|
|`n₁ * n₂`			|int		|Multiplication						|
|`n₁ / n₂`			|int		|Division (integer, rounding down)			|
|`n₁ % n₂`			|int		|Remainder						|
|`-n`				|int		|Negative `n`						|
|`rank(b)`			|int		|Rank of boolean expression b **(3)**			|
|`substring(str, n₁, n₂)`	|string		|Substring of `str` between index `n₁` (inclusive) and `n₂` (exclusive)|
|`var`				|any		|Value of variable `var` 				|
|`var[n₁]...[nₓ]`		|any		|Indexed value of (multi-dimensional) array stored in `var` at index `n₁` ...`nₓ`|
|`functionname(e₁, … eₓ)`	|any		|Function call with arguments `e₁`,...,`eₓ`		|

Remarks:
- **(1)**: Equality is always based on value, never on reference. This includes arrays.
- **(2)**: Applying `+` to one string and one non-string expression results in a string where the value of the non-string expression is converted to a string.
- **(3)**: Use with caution: can be computationally expensive.