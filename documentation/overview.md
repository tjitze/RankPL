*This work is described in the paper ["RankPL: A Qualitative Probabilistic Programming Language"](https://github.com/tjitze/RankPL/tree/master/paper/rankpl.pdf) (to be presented at [ECSQARU 2017](http://www2.idsia.ch/cms/isipta-ecsqaru/))*

# RankPL: A Ranked Programming Language

RankPL is an experimental programming language aimed at representing and reasoning about processes that exhibit uncertainty expressible by distinguishing "normal" from "surprising" events. It can be thought of as a qualitative variant of a probabilistic programming language, i.e., a regular programming language extended with statements to draw values at random from a given probability distribution and condition values of variables due to observation.

Semantically, RankPL draws a parallel with probabilistic programming in terms of *ranking theory*. This is a qualitative abstraction of probability theory in which events receive discrete degrees of surprise called ranks. Events can be ranked 0 (not surprising), 1 (surprising), 2 (very surprising), and so on, or ∞ if impossible. Analoguous to probability theory, ranking theory provides notions such as conditioning and independence. However, ranking theory is computationally simpler than probability theory and allows reasoning under uncertainty without having to specify precise probabilities. 

# Ranking theory<a name="sec-rankingtheory"></a>

Here we present a brief overview ranking theory. We discuss the basic definitions and contrast ranking theory with probability theory. For a more thorough description, the reader is referred to [[1](#ref-gp)] or [[2](#ref-survey)]. 

## Ranking functions

The definition of a *ranking function* presupposes a set *Ω* of elements called *possibilities*. 
A ranking function *K* assigns to every *w ∈ Ω* a non-negative integer or ∞, such that *K(w) = 0* for at least one *w ∈ Ω*.
Intuitively, these numbers (called ranks) represent relative degrees of surprise: if *K(w) = 0* then *w* is not surprising, if *K(w) = 1* then *w* is surprising, if *K(w) = 2* then *w* is very suprising, and so on, until *K(w) = ∞*, which means that *w* is impossible.

Non-empty subsets of *Ω* are called *events*. The rank of an event *A ⊆ Ω* is defined to be the minimum of the ranks of the elements of *A*. In other words, an event *A* is as surprising as the least surprising possibility *w ∈ A* that gives rise to it. In this respect, ranks behave different from probabilities, because the probability of an event is defined to be the *sum* of its elements.

## Conditional ranks

Given two events *A* and *B*, the rank of *A* *conditional on* *B* is denoted by *K(A|B)*. The rank of *A* conditional on *B* is defined only if *K(B) < ∞* (i.e., if *B* is not impossible). If *K(B) < ∞*. then *K(A|B)* is defined by *K(A|B) = K(A∩B)-K(B)*. This can be thought of as a shifting operation: the possibilities in *B* are uniformly shifted down so that *K(B|B) = 0* (i.e., *B* is unsurprising given *B*), and the possibilities in *not-B* are shifted up so that *K(not-B|B) = ∞* (i.e., *not-B* is impossible given *B*). 

Recall that the probability of *A* *conditional on* *B* is defined by *P(A|B) = P(A∩B) / P(B)*. Just like *P(A|B) = P(A∩B) / P(B)* can be rewritten as *P(A∩B) = P(B)P(A|B)*, we can rewrite *K(A|B) = K(A∩B)-K(B)* as *K(A∩B) = K(B) + K(A|B)*. In this form, the definition says that the degree of surprise of *A and B* is the degree of surprise of *B* plus the degree of surprise of *A* *given* *B*.

# Ranked programming in RankPL

RankPL is a simple imperative programming language extended with statements to draw choices at random from a ranking function and to perform conditioning due to observations. Drawing choices at random from a ranking function is done through the *ranked choice* statement. Conditioning is done using the *observe* statement. Other special statements are the *infer* statement and the special observe statements *observe-j* and *observe-l*. We discuss these statements in this section. Because the rest of the language is fairly standard, the examples provided here should be easy to understand. A complete specification is provided in the next section.

## The ranked choice statement

The *ranked choice* statement makes it possible to introduce alternative program flows, each associated with a rank. The basic form is as follows.
```
normally (rank) A exceptionally B;
```
where `rank` is an integer expression and `A` and `B` are (block) statements. This statement states that, normally, `A` 
is executed, and that `B` is executed in the exceptional event (surprising to degree `rank`) that it is not. 
In ranking-theoretic terms, this can be understood as a draw at random from a ranking function over the statements `A` and `B`, 
where the alternative `A` is ranked 0 and the alternative `B` is ranked `rank`.
The rank expression may be omitted. If it is, the rank defaults to 1. 
If the exceptional event `B` is a no-op (does nothing) then the `exceptionally B` part can be omitted. Thus, the statement
```
normally A;
```
simply states that, normally, `A` executed, and that the event that `A` is not executed is surprising to degree 1.

If we combine ranked choice constructs we obtain larger sets of alternatives. Consider, for example, the program
```
normally (1) A exceptionally B;
normally (1) C exceptionally D;
```
This produces four alternative program flows: A-C (ranked 0), B-C (ranked 1), A-D (ranked 1), and B-D (ranked 2). 
Notice how the ranks of subsequent events sum up to form the rank of the resulting joint events. 

The ranked choice construct can also be nested. 
Consider, for example, the statement
```
normally (1) {
    A;
} exceptionally {
    normally (1) {
        B;
    } exceptionally {
        C;
    }
}
```
This statement states that, normally, `A` executed. 
If `A` is not executed (which is surprising to degree 1) then, normally, `B` is executed. 
Finally, if neither `A` nor `B` are executed (which is surprising to degree 2) then `C` is executed. 
Note that, like in the previous example, the ranks of subsequent events sum up. Here, this means that the ranks of the choices `B` and `C` are, respectively, 1 and 2. This is because they are nested inside the exceptional block of the outer ranked choice statement.

To express commonly occurring patterns, three “syntactic sugar” forms are supported. The first is the *either-or* construct, which represents regular non-deterministic choice, where each possibility is equally likely. 

```
either {                                                  normally (0) {
    A;                                                        A;
} or {                        is equivalent to            } exceptionally {
    B;                                                        B;
}                                                         }
```
The second is *ranked assignment*, which can be used to express ranked choice between values assinged to a variable:
``` 
                                                          normally (rank) {
                                                              var := e_1;
var := e_1 <<rank>> e_2       is equivalent to            } exceptionally {
                                                              var := e_2;
                                                          }
```
The third is the *range assignment*. It represents the random choice, all equally likely, among the integer values between 
`i_1` (inclusive) and `i_2` (exclusive). 
```
                                                          either {
                                                              a := i_1;
					                  } or {
a := << i_1 ... i_2 >>        is equivalent to                ...
                                                          } or {
                                                              a := i_2 - 1;
                                                          }
```
An example.
Consider the following program.
Here, `a` and `b` normally become 10 but may exceptionally (to degree 1) become 20.
At the end we return the product of `a` and `b`.
```
 1  a := 10 <<1>> 20;
 2  b := 10 <<1>> 20;
 3  c := a * b;
 4  return c;
```
The progam generates the marginalized ranking over the values of of the return variable:
```
Rank 0: result: 100
Rank 1: result: 200
Rank 2: result: 400
```
As we see later, it is often enough to determine *normal* behaviour of a model.
In the example above, the normal behaviour of the program is to return 100.
This means that we can stop generating outcomes once we have generated all the rank zero outcomes.
For this reason, the interpreter always generates results in ascending order with respect to rank.
For complex programs, this can result in significant savings in terms of computation.

## The *observe* statement

The *observe* statement revises the ranking over alternative program flows due to a given condition that we observe or learn to hold. How does this work? The observe statement does two things: 

1. it uniformly shifts down the ranks of the alternatives that satisfy `b` to zero.
1. it blocks execution of the remaining alternatives. 

Of course, this is exactly what ranking-theoretic conditioning does (discussed [above](#sec-rankingtheory)) except that we block the execution of alternatives that do not satisfy the observed condition, instead of shifting their ranks up to ∞.

To demonstate the observe statement we adapt the previous example as follows.
```
 1  a := 10 << 1 >> 20;
 2  b := 10 << 1 >> 20;
 3  c := a * b;
 5  observe c > 150;
 4  return c;
```
The result is the same ranking as above, except that
	the outcome `100` is ruled out,
	and the ranks of the outcomes `200` and `400` are shifted down by one unit of rank:
```
Rank 0: result: 200
Rank 1: result: 400
```
The observe statement makes it possible to infer the most plausible causes for a given observation. 
To see a very simple example of this, let's adapt our program.
Instead of returning `c`, we return the values of `a` and `b`.
```
 1  a := 10 << 1 >> 20;
 2  b := 10 << 1 >> 20;
 3  c := a * b;
 5  observe c > 150;
 4  return "a = " + a + ", b = " + b;
```
The result is:
```
Rank 0: result: a = 20, b = 10
Rank 0: result: a = 10, b = 20
Rank 1: result: a = 20, b = 20
```
This tells us that the most plausible cause for `c > 150` is that either `a` or `b` is 20, but not both. 
While `a` and `b` both being 20 does explain the observation, 
	it is more surprising and therefore less plauisible than just `a` or just `b` being 20.
A practical application of this principle to the problem of circuit diagnosis is included in the examples section ([link](#circuit-diagnosis)).

## The *infer* expression

In the example above, the program generates a ranking over possible outcomes,
	where the rank zero outcomes represent the normal or most plauisble behaviour of the program.
The *infer* expression can be used to collect these outcomes, 
	so that they can be used in the remainder of the program.
This is useful if we want to use the `print` statement to report back to the user,
	or handle the result of an inference in another deterministic way.
The infer expression takes as input a function call and returns an array containing all the return values 
	of this function call that are ranked zero.
We demonstrate it by adapting the prevous example as follows.
```
 1  define program() { 
 2 	a := 10 << 1 >> 20;
 3 	b := 10 << 1 >> 20;
 4 	c := a * b;
 5 	observe c > 150;
 6  	return "(a = " + a + ", b = " + b + ")";
 7 };
 8
 9 result := infer(program());
10 print "Most plausible outcomes:" + result;
```
This program prints the output:
```
Most plausible outcomes:[(a = 10, b = 20), (a = 20, b = 10)]
```

# Special observe statements

## J-observation

The statement `observe b` completely rules out the alternatives not satisfying `b`. This amounts to learning that `b` holds with absolute certainty. What if we learn that `b` *normally* holds? This is what J-conditioning does [[1](#ref-gp)]. J-conditioning is a generalized form of conditioning parameterized by a rank `n`. In RankPL, J-conditioning is implemented by the `observe-j` statement. This statement has the form:
```
observe-j (n) b
```
The effect is that the ranks of the alternatives satisfying `b` are uniformly shifted down to zero, just like the regular `observe` statement does. However, the ranks of the alternatives not satisfying `b` are uniformly shifted up or down so that the posterior rank of `!b` becomes `n`. Thus, the effect of the statement `observe-j (n) b` is that the degree of surprise that `b` does *not* hold becomes `n`.

An example. Consider the following program. It contains a range assignment statement that assigns to `a` the value 0, 1, 2 or 3, all ranked zero.
```
	a := << 0 ... 4 >>;
	return “a = “ + a;
```
Result:
```
	Rank 0: a = 0
	Rank 0: a = 1
	Rank 0: a = 2
	Rank 0: a = 3
```
If we add a regular `observe` statement with the condition `a < 2`, the alternatives `a = 2` and `a = 3` are ruled out.
```
	a := << 0 ... 4 >>;
	observe a < 2;
	return “a = “ + a;
```
Result:
```
	Rank 0: a = 0
	Rank 0: a = 1
```
Now we replace the regular `observe` statement with the statement `observe-j (10) a < 2`. That is, we learn that normally, `a < 2` holds, but exceptionally (to degree 10) we may have `a >= 2`. The result is that the alternatives `a = 2` and `a = 3` are ranked 10.
```
	a := << 0 ... 4 >>;
	observe-j (10) a < 2;
	return “a = “ + a;
```
Result:
```
	Rank 0: a = 0
	Rank 0: a = 1
	Rank 10: a = 2
	Rank 10: a = 3
```

## L-observation

The second generalized kind of conditioning is *L-conditioning* [[1](#ref-gp)]. Like J-conditioning, L-conditioning is parameterized by a rank `n`. This time, `n` indicates the degree by which want to strenghthen the prior belief in the observed condition. In RankPL, L-observation is implemented by the `observe-l` statement, which has the form:
```
observe-l (n) b
```
The effect is that the ranks of the alternatives satisfying `b` are improved by `n` units with respect to the ranks of the alternatives not satisfying `b`. Depending on the prior ranks of `b` and `!b`, this means that the rank of `b` is decreased (`b` becomes less surprising), that the rank of `!b` is increased (`!b` becomes more surprising), or both. 

L-conditioning is especially useful when we have to deal with multiple sequential observations. This is because it nicely models the accumulation of confidence due to observations that strengthen already-held beliefs, as well as the reversal of confidence that occurs when new observations contradict previous ones. 

An example. In the program below, the range assignment statement assigns to `a` the value 0, 1, 2 or 3, all ranked zero. The statement `observe-l (10) a < 2` improves the rank of `a < 2` by 10 units of rank w.r.t. `a >= 2`. In this case, this means that the rank of `a = 2` and `a = 3` becomes 10.
```
a := << 0 ... 4 >>;
observe-l (10) a < 2;
return “a = “ + a;
```
Result:
```
Rank 0: a = 0
Rank 0: a = 1
Rank 10: a = 2
Rank 10: a = 3
```
Now we consider multiple observations. In the following example, the rank of `a < 2` is improved *twice* by 10 units of rank w.r.t. `a >= 2`.
```
a := << 0 ... 4 >>;
observe-l (10) a < 2;
observe-l (10) a < 2;
return “a = “ + a;
```
Result:
```
Rank 0: a = 0
Rank 0: a = 1
Rank 20: a = 2
Rank 20: a = 3
```
The following example shows how an observation can reverse the effect of a previous observation. 
Here, the first `observe-l` statement increases the rank of `a >= 2` by 10, while the second `observe-l` statement decreases the rank of `a >= 2` by 10.
```
a := << 0 ... 4 >>;
observe-l (10) a < 2;
observe-l (10) a >= 2;
return “a = “ + a;
```
Result:
```
Rank 0: a = 0
Rank 0: a = 1
Rank 0: a = 2
Rank 0: a = 3
```
Note that the normal `observe b` statement does not adequately model multple observations, because alternatives not satisfying `b` are completely ruled out and their ranks cannot (as in the example above) be shifted down afterwards due to subsequent observations. While the `observe-j (n) b` statement does allow this, it often produces undesirable results, because it does not take the prior ranks of `b` and `!b` into account. The `observe-l (n) b` statement does take these prior ranks into account. 

For a more detailed explanation of J-conditioning and L-conditioning and their relation to generalized forms of probabilistic conditioning, we refer the reader to [[1](#ref-gp)]. A practical example using the `observe-l` statement is included in the examples section ([link](#spelling-corrector)).

# Building and running RankPL

## Build instructions

The project comes with a Maven build script. To build the project, simply run the following command from the root directory of the project. 
```
mvn package
```
This compiles the grammar and java sources, runs tests, and produces a self-contained executable .jar file
```
RankPL-{version}-jar-with-dependencies.jar
```
in the `target` directory. Rename this file to `RankPL.jar` and copy it to your working directory.

The self-contained executable jar file can also be downloaded TODO.

## Running RankPL

Basic usage of the executable jar file is: 
```
java -jar RankPL.jar -source <source_file> [-r <max_output_rank>]
```
where `source_file` is the RankPL source file to execute, and the optional argument `-r <max_output_rank>` specifies the maximum rank (inclusive) that is generated, which defaults to zero if the argument is omitted. The possible outcomes of the program (i.e., the values returned by a `return` statement) are generated and printed on the console in ascending order with respect to rank.

Programs that involve large sets of alternatives (100000 or more) may run slowly. Enabling the *iterative deepening* execution mode, by including the `-d` flag, will significantly speed up the generation of the lowest-ranked outcomes of such programs. Note that, at this stage, this is an experimental feature.

# Language reference

Apart from the special statements described above, RankPL is a simple imperative programming language. It supports integers, booleans, string, and array expressions, and is dynamically typed. Function calls in RankPL are always call-by-value. In this section we explain how to use RankPL and provide a reference of the supported statements and expressions.

## Programs and functions

A program is a sequence of function definitions. A function definition has the form
```
define function_name(var_1, ..., var_n) {
	s_1;
	s_2;
	...
};
```
Here, `s_1`, `s_2`, ... are the statements that make up the body of the function.
A function call is an expression of the form:
```
function_name(arg_1, ..., arg_n)
```
The arguments supplied with a function call must match the parameters of the function. Violation leads to an error. Every function must terminate with a `return` statement. A missing return statement will lead to an error.

The main entry point of a program is the `main()` function. The values returned by the `main()` function are the values returned by the program. If the `main()` function is omitted, the body of the program (i.e., all statements not nested inside a function definition) is taken to be the body of the `main()` function.

## Statements

Statements in RankPL are separated by semicolons. If a statement contains sub-statements (such as the `if b then s1 else s2` statement) then these sub-statements may be *block statements*, which are sequeences of statements enclosed in curly brackets ({ ... }). 

The table below provides an overview of all available statements in RankPL. We use the following symbols to refer to specific types of expressions:
- `var`: 	a variable
- `e`: 		an expression (any type)
- `n`: 		an integer expression
- `b`: 		a boolean expression
- `s`: 		a string expression

|Statement      	|Form					|Description	|
|-----------------------|-----------------------------------------|---------------|
|Assignment		| `var := e`				| Assign value of `e` to `var`. **(1)** |
|Ranked assignment  	| `var := e_1 << n >> e_2`		| Normally assign to `var` the value of `e_1`, exceptionally (to degree `n`) assign the value of `e2`. **(1)**	|
|Range assignment	| `var := << n_1 ... n_2 >>`  		| Assign to `var` a random value between `n_1`(inclusive) and `n_2` (exclusive), all ranked 0. **(1)**		|
|If-else		| `if b then s_1 else s_2`		| Regular if-else statement. **(2)** |
|while-do		| `while b do s`			| Execute `s` as long as `b` evaluates to TRUE.		|
|for loop		| `for (s_1; b; s_2) s_3`		| Java-style for loop. `s_1` is the init statement, `b` the termination condition and `s_2` the increment statement. |
|observe		| `observe b`				| Observe that `b` is true (uniformly shift down alternatives that satisfy `b` and eliminate alternatives not satisfying `b`).	|
|observe-j		| `observe-j (n) b`			| Observe that `b` is normally (to degree `n`) true. (like `observe b`, but uniformly shifts the rank of alternatives not satisfying `b` to `n`). **(3)**		|
|observe-l		| `observe-l (n) b`			| Improve rank of alternatives satisfying `b` by `n` units w.r.t. alternatives not satisfying `b`. **(3)**		|
|Ranked choice  	| `normally (n) s_1 exceptionally s_2`	| Normally execute `s_1`, exceptionally (to degree `n`) execute `s_2`. **(3,4)**	|
|Indifferent choice  	| `either s_1 or s_2`			| Same as `normally (0) s_1 exceptionally s_2`. 		|
|Print			| `print s`				| Print `s` to console.		|
|Cut			| `cut(n)`				| Eliminate all alternatives ranked higher than `n`. |
|Return			| `return e`				| Return `e` as value of program or function.		|
|Skip			| `skip`				| Does nothing.		|

- **(1)**: Variables on the left hand side of an assignment, ranked assignment, or ranged choice statement may include array indices. That is, they may be of the form `var[e_1]...[e_n]`, where `e_1`, ..., `e_n` are indices to the (multi-dimensional) array stored in `var`. Illegal indices lead to a *value undefined* or *index out of bounds* error.
- **(2)**: The `else s_2` part may be omitted. If it is, `s_2` is taken to be the `skip` statement.
- **(3)**: The `(n)` part may be omitted. If it is, `n` defaults to 1.
- **(4)**: The `exceptionally s_2` part may be omitted. If it is, `s_2` is taken to be the `skip` statement.

## Expressions

Expressions in RankPL are either integer, boolean, string, or array-valued. All expressions are dynamically typed. This means, for example, that an expression like `"ab"” <= "def"` is syntactically correct but will lead to a type error during execution. In this section we provide an overview of all expressions in RankPL.

In the list below, we use the following symbols to refer to expressions with specific types:
- `var`: 	a variable
- `e`: 		an expression (any type)
- `n`: 		an integer expression
- `b`: 		a boolean expression
- `s`: 		a string expression

Expressions

|Expression			|Evaluates to	|Description						|
|-------------------------------|---------------|-------------------------------------------------------|
|`abs(n)`			|int		|Absolute value of `i`					|
|`array(i)`			|array		|Array of length `i`. Elements not initialised.		|
|`array(i,e)`			|array		|Array of length `i`. Elements initialised to `e`.	|
|`[e_1, …, e_n]`		|array		|Array of length `n` initialised with values `e_1`,...,`e_n`|
|`b? e_1: e_2`			|any		|Evaluates to `e_1` iF `B` is TRUE. Evaluates to `e_2` otherwise. |
|`b_1 & b_2`			|boolean	|Boolean AND						|
|`b_1 \| b_2`			|boolean	|Boolean OR						|
|`b_1 ^ b_2`			|boolean	|Boolean XOR						|
|`!b`				|boolean	|Boolean NOT						|
|`e_1 == e_2`			|boolean	|Equality of e1 and e2 **(1)**				|
|`e_1 != e_2`			|boolean	|Inequality of e1 and e2 **(1)**			|
|`isset(var)`			|boolean	|True iff variable `var` is set, false if not		|
|`len(v)`			|int		|Length of array or string `v`				|
|`max(i_1, ..., i_n)`		|int		|Maximum of `i_1` ... `i_n`				|
|`min(i_1, ..., i_n)`		|int		|Maximum of `i_1` ... `i_n`				|
|`parseint(s)`			|int		|`s` parsed as integer					|
|`i_1 < i_2`			|boolean	|True iff `i_1` less than `i_2`				|
|`i_1 > i_2`			|boolean	|True iff `i_1` more than `i_2`				|
|`i_1 =< i_2`			|boolean	|True iff `i_1` less than or equal to `i_2`		|
|`i_1 => i_2`			|boolean	|True iff `i_1` more than or equal to `i_2`		|
|`e_1 + e_2`			|int/string	|Sum of integers or concatenation of strings **(2)**	|
|`i_1 - i_2`			|int		|Subtraction						|
|`i_1 * i_2`			|int		|Multiplication						|
|`i_1 / i_2`			|int		|Division (integer, rounding down)			|
|`i_1 % i_2`			|int		|Remainder						|
|`-i`				|int		|Negative `i`						|
|`rank(b)`			|int		|Rank of boolean expression b **(3)**			|
|`substring(s, i_1, i_2)`	|string		|Substring of s between index `i_1` (inclusive) and `i_2` (exclusive)|
|`var`				|any		|Value of variable `var` 				|
|`var[e_1]...[e_n]`		|any		|Indexed value of (multi-dimensional) arryay stored in `var`|
|`functionname(e_1, … e_n)`	|any		|Function call with arguments `e_1`,...,`e_n`		|

Remarks:
- **(1)**: Equality is always based on value, never on reference. This includes arrays.
- **(2)**: Applying `+` to one string and one non-string expression results in a string where the value of the non-string expression is converted to a string.
- **(3)**: Use with caution: can be computationally expensive.

# Practical Examples

### Circuit Diagnosis<a name="circuit-diagnosis"></a>

Let us look at an example. 
Conside the circuit of a 2-bit *full adder* circuit:
<p align=center>
<img src=https://github.com/tjitze/RankPL/blob/master/examples/boolcircuit.jpg width=500px />
</p>
The circuit consists of two XOR gatex x1 and x2, two AND gates a1 and a2, and an OR gate o1.
The function of this circuit is to generate a binary representation (b1,b2) of the
number of inputs among (i1,i2,i3) that are high.
The *circuit diagnosis problem* is about explaining observed incorrect behavior by finding
minimal sets of gates that, if faulty, cause this behavior.
This problem is modelled by the following program.

```
 1  # Set input variables
 2  i1 := FALSE;
 3  i2 := FALSE;
 4  i3 := TRUE;
 5
 6  # Set state of gates (TRUE is functioning, FALSE is faulty)
 7  x1_broken := FALSE <<1>> TRUE;
 8  x2_broken := FALSE <<1>> TRUE;
 9  a1_broken := FALSE <<1>> TRUE;
10  a2_broken := FALSE <<1>> TRUE;
11  o1_broken := FALSE <<1>> TRUE;
12
13  # Circuit logic
14  if (x1_broken) then l1 := FALSE <<0>> TRUE else l1 := (i1 ^ i2);
15  if (a1_broken) then l2 := FALSE <<0>> TRUE else l2 := (i1 & i2);
16  if (a2_broken) then l3 := FALSE <<0>> TRUE else l3 := (l1 & i3);
17  if (x2_broken) then b2 := FALSE <<0>> TRUE else b2 := (l1 ^ i3);
18  if (o1_broken) then b1 := FALSE <<0>> TRUE else b1 := (l3 | l2);
19  
20  # Observe output
21  observe b1 & !b2;
22 
23  # Return state of gates
24  return "x1_broken: " + x1_broken + ", x2_broken: " + x2_broken + 
25  	", a1_broken: " + a1_broken +  ", a2_broken: " + a2_broken +  ", o1_broken: " + o1_broken;
```

The program works as follows: on lines 2-4 we set the input (FALSE=0, TRUE=1).
On lines 7-11 we state that the event that an individual gate is broken is surprising to degree 1.
Lines 14-18 encode the logic of the circuit. 
Note that, if a gate is broken, its output is arbitrarily set to high or low.

Because at the start, we only set *one* of the inputs to TRUE, we should see that `b1` is FALSE and `b2` is TRUE.
However, the observe on line 21 states that we see that `b1` is TRUE and `b2` is FALSE. 
Thus, the circuit is broken.
The program produces one outcome ranked zero:
```
Rank 0: x1_broken: TRUE, x2_broken: FALSE, a1_broken: FALSE, a2_broken: FALSE, o1_broken: FALSE
```
This means that the observed behaviour is most plausibly explained by failure of gate `x2`.
Other outcomes are ranked higher than zero.
They represent explanations that are less plausible because they involve more than one broken gate.

### Spelling Corrector<a name="spelling-corrector"></a>

The following example implements a spelling correction algorithm. 
The rough idea is as follows.
On line 4 we set `input`, which is a (possibly) misspelled word.
We then pick a word (`potential_match`) at random from a dictionary (lines 3-12).
We then compare, one by one, the characters of `input` and `potential_match`.
For each character we observe that the characters of `input` and `potential_match` 
	are the same (`input[i] == potential_match[k]`). 
This is done using L-conditioning, which means that the rank of a match is improved by one unit w.r.t. the rank of a non-match.
Next, if the characters match, we increase the pointers `i` and `k` and repeat the process (lines 28-29).
If the characters don't match, we consider three possibilities and generate the corresponding alternatives using an either-or statement (lines 38-48):
- There is a misspelling (e.g. when writing c*o*t instead of cat). We increase `i` and `k`.
- There is an insertion (e.g. when writing ca*r*t instead of cat). We increase only `i`.
- There is an omission (e.g. when writing ct instead of c*a*t). We increase only `k`.
At the end we use a normal `observe` statement to condition on the fact that all characters were processed.

```
 1  # A spelling correction algorithm.
 2 
 3  # Input (possibly misspelled)
 4  input := "tweleven";
 5
 6  # Read dictionary of possible words
 7  dictionary := [
 8 	"one", "two", "three", "four", "five",
 9	"six", "seven", "eight", "nine", "ten", "eleven",
10	"twelve", "thirteen", "fourteen", "fifteen", "sixteen",
11	"seventeen", "eighteen", "nineteen", "twenty"
12  ];
13
14  # Randomly choose word from dictionary
15  choice := << 0 ... len(dictionary) >>;
16  potential_match := dictionary[choice];
17
18  # Initialize counters (i iterates over input, k over match)
19  i := 0; k := 0;
20 
21  # Append * to end of input and potential_match, to match end-of-word
22  input := input + "*";
23  potential_match := potential_match + "*";
24
25  # While there are characters left to match ...
26  while (i < len(input) & k < len(potential_match)) do {
27	# Improve rank of match with 1 unit of rank w.r.t. rank of a mis-match.
28	observe-l (1) (input[i] == potential_match[k]);
29
30	# If match, increase pointers
31	if (input[i] == potential_match[k]) then {
32		i := i + 1;
33		k := k + 1;
34	}
35
36	# If mis-match, consider three possibilities:
37	else {
38		either {
39			# misspelling (as in cot instead of cat)
40			i := i + 1; 
41			k := k + 1;
42		} or {
43			# insertion (as in cart instead of cat)
44			i := i + 1; 
45		} or {
46			# omission (as in ct instead of cat)
47			k := k + 1;
48  		};
49  	};
50  };
51
52  # Filter on event that all characters were matched
53  observe i == len(input) & k == len(potential_match);
54
55  return potential_match;


```
Result
```
Rank 0: eleven*
Rank 0: twelve*
Rank 2: seven*
Rank 3: fifteen*
Rank 3: twenty*
...
```

# References

<a name="ref-gp"></a>["Qualitative probabilities for default reasoning, belief revision, and causal modeling"](http://www.sciencedirect.com/science/article/pii/0004370295000909) (Goldszmidt, Pearl. Artificial Intelligence
Volume 84, Issues 1–2, July 1996, Pages 57-112).

<a name="ref-survey"></a>["A Survery of Ranking Theory"](https://www.researchgate.net/profile/Wolfgang_Spohn/publication/30013588_A_Survey_of_Ranking_Theory/links/0c9605231bd9314802000000.pdf) (Spohn. Degrees of belief. Springer Netherlands, 2009. 185-228).
