*This work is described in the paper ["RankPL: A Qualitative Probabilistic Programming Language"](https://github.com/tjitze/RankPL/tree/master/paper/rankpl.pdf) (to be presented at [ECSQARU 2017](http://www2.idsia.ch/cms/isipta-ecsqaru/))*

# RankPL: A Ranked Programming Language

RankPL is an experimental programming language aimed at representing and reasoning about processes that exhibit uncertainty expressible by distinguishing "normal" from "surprising" events. It can be thought of as a qualitative variant of a probabilistic programming language, i.e., a regular programming language extended with statements to draw values at random from a given probability distribution and condition values of variables due to observation.

Semantically, RankPL draws a parallel with probabilistic programming in terms of *ranking theory*. This is a qualitative abstraction of probability theory in which events receive discrete degrees of surprise called ranks. Events can be ranked 0 (not surprising), 1 (surprising), 2 (very surprising), and so on, or ∞ if impossible. Analoguous to probability theory, ranking theory provides notions such as conditioning and independence. However, ranking theory is computationally simpler than probability theory and allows reasoning under uncertainty without having to specify precise probabilities. 

# Ranking theory<a name="sec-rankingtheory"></a>

Here we present a brief overview ranking theory. We discuss the basic definitions and contrast ranking theory with probability theory. For a more thorough description, the reader is referred to [1](#ref-gp) or [2](#ref-survey). 

### Ranking functions

The definition of a *ranking function* presupposes a set *Ω* of elements called *possibilities*. 
A ranking function *K* assigns to every *w ∈ Ω* a non-negative integer or ∞, such that *K(w) = 0* for at least one *w ∈ Ω*.
Intuitively, these numbers (called ranks) represent relative degrees of surprise: if *K(w) = 0* then *w* is not surprising, if *K(w) = 1* then *w* is surprising, if *K(w) = 2* then *w* is very suprising, and so on, until *K(w) = ∞*, which means that *w* is impossible.

Non-empty subsets of *Ω* are called *events*.
The rank of an event *A ⊆ Ω* is defined to be the minimum of the ranks of the elements of *A*. In other words, an event *A* is as surprising as the least surprising possibility *w ∈ A* that gives rise to it. In this respect, ranks behave different from probabilities, because the probability of an event is defined to be the *sum* of its elements.

### Conditional ranks

Given two events *A* and *B*, the rank of *A* *conditional on* *B* is denoted by *K(A|B)*. The rank of *A* conditional on *B* is defined only if *K(B) < ∞* (i.e., if *B* is not impossible). If *K(B) < ∞*. then *K(A|B)* is defined by *K(A|B) = K(A∩B)-K(B)*.

How do conditional ranks compare to conditional probabilities? Recall that the probability of *A* *conditional on* *B* is defined by *P(A|B) = P(A∩B) / P(B)*. Here, the division of *P(A∩B)* by *P(B)* can be thought of as a normalization step, which ensures that *P(B|B) = 1*. In the definition of *K(A|B)*, the rank of *K(B)* is subtracted from the rank of *K(A∩B)*. This, too, can be thought of as a normalization step. It ensures that *K(B|B) = 0* (i.e., *B* is not surprising given *B*).

Just like *P(A|B) = P(A∩B) / P(B)* can be rewritten as *P(A∩B) = P(B)P(A|B)*, we can rewrite *K(A|B) = K(A∩B)-K(B)* as *K(A∩B) = K(B) + K(A|B)*. In this form, the definition says that the degree of surprise of *A and B* is the degree of surprise of *B* plus the degree of surprise of *A* *given* *B*.

# Ranked programming in RankPL

RankPL is a simple imperative programming language extended with statements to draw choices at random from a ranking function and to perform ranking-theoretic conditioning. This makes it possible to model uncertainty expressible by distinguishing normal (rank 0) from surprising (rank > 0) events. 

Drawing choices at random from a ranking function is done through the *ranked choice* statement. Ranking-theoretic conditioning is done using the *observe* statement. Other special statements are the *infer* statement and the special observe statements *observe-j* and *observe-l*. 

We discuss these statements in this section. Because the rest of the language is fairly standard, the examples provided here should be easy to understand. A complete specification of the language is provided in the next section.

### The ranked choice statement

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
If not (which is surprising to degree 1) then, normally, `B` is executed. 
Finally, if neither `A` nor `B` is executed (surprising to degree 2) then `C` is executed. 
Note that, like in the previous example, the ranks of subsequent events sum up. Here, this means that the ranks of the choices `B` and `C` are, respectively, 1 and 2, since these choices are nested inside the exceptional block of the outer ranked choice statement.

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
The third is the *range expression*. It represents the random choice, all equally likely, among the integer values between 
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

### The *observe* statement

The *observe* statement revises the ranking of alternatives due to a given condition that we observe or learn to hold. How does this work? Intuitively, if we would halt the execution of a RankPL program at a given point, we end up with a ranking function over alternative program states. The observe statement does two things: 

1. it rules out (or blocks) all alternatives that do not satisfy the observed condition, and 
2. it uniformly shifts down the ranks of the remaining alternatives so that the result is, again, a proper ranking function over alternatives. 

Of course, this is exactly what the ranking-theoretic conditioning operation does, which we described in the [section on ranking theory](#sec-rankingtheory). 

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
	and the ranks of the outcomes `200` and `400` are shifted down:
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
Rank 0: result: "a = 20, b = 10"
Rank 0: result: "a = 10, b = 20"
Rank 1: result: "a = 20, b = 20"
```
This tells us that the most plausible cause for `c > 150` is that either `a` or `b` is 20, but not both. 
While `a` and `b` both being 20 does explain the observation, 
	it is more surprising and therefore less plauisible than just `a` or just `b` being 20.
A practical application of this principle to the problem of circuit diagnosis is included in the examples section ([link](#circuit-diagnosis)).

### The *infer* expression

In the example above, the program generates a ranking over possible outcomes,
	where the rank zero outcomes represent the normal or most plauisble behaviour of the program.
The *infer* expression can be used to collect these outcomes, 
	so that they can be used in the remainder of the program.
This is useful if we want to use the `print` statement to report back to the user,
	or handle the result of an inference in another deterministic way.
	
The infer expression takes as input a function call and returns an array containing all the return values 
	of this function call that are ranked zero.
We demonstrate it by adapting the circuit diagnosis program as follows.
The program does the same as before, but instead of returning a ranking over return values,
	the program prints the rank zero explanations on the console.
```
 1  result := infer(getValue());
 2  print "Most plausible outcomes:" + result;
 3
 4  define getValue() { 
 5 	a := 10 << 1 >> 20;
 6 	b := 10 << 1 >> 20;
 7 	c := a * b;
 8 	observe c > 1;
 9  	return "a = " + a + ", b = " + b;
10  };
```
This program prints the output:
```
Most plausible values: [ "a = 20, b = 10", "a = 10, b = 20"]
```

# Special observe statements

### J-observation

Instead of learning that a condition `b` holds with absolute certainty, we may also learn that b *normally* holds. In other words, instead of completely ruling out alternatives not satisfying `b`, which is what the observe statement does, we merely increase their rank. We penalise them, so to say, because they do not satisfy what we expect to normally hold. This is what J-observation does. It implements a special form of conditioning called J-conditioning [1](#ref-gp). The statement has the form:
```
observe-j (n) b
```
Here, `n` is an integer-valued expression that indicates the degree of surprise that `b` does *not* hold. Like with the normal `observe` statement, the effect is that the ranks of the alternatives satisfying `b` are uniformly shifted down to zero. However, the ranks of the alternatives not satisfying `b` are shifted up only by `n` units, rather than ∞ units, as with the normal observe statement. An example:
```
a := << 0 ... 4 >>;
observe-j (2) a < 2;
return “a = “ + a;
```
Result:
```
Rank 0: a = 0
Rank 0: a = 1
Rank 2: a = 2
Rank 2: a = 3
```

### L-observation

The second generalised kind of observation is *L-observation*. It implements a special form of conditioning called L-conditioning [1](#ref-gp). L-conditioning is especially useful when we have to deal with multiple sequential observations. The statement has the form:
```
observe-l (n) b
```
Here, `n` indicates, intuitively, the degree by which want to strenghthen the prior belief in `b`. The effect is that the ranks of the alternatives satisfying `b` are improved by `n` units with respect to the alternatives not satisfying `b`. Depending on the prior ranks of `b` and `!b`, this means that the rank of `b` is decreased, that the rank of `!b` is increased, or both. Consider the following example. 
```
a := << 0 ... 4 >>;
observe-l (2) a < 2;
return “a = “ + a;
```
Result:
```
Rank 0: a = 0
Rank 0: a = 1
Rank 2: a = 2
Rank 2: a = 3
```
Note that the the rank of `a < 2` has improved by 2 units of rank w.r.t. `a >= 2`. In this instance, the result is the same as with the `observe-j` statement in the earlier example. This changes if we consider multiple observations. In the following example, the rank of `a < 2` is improved *twice* by 2 units of rank w.r.t. `a >= 2`.
```
a := << 0 ... 4 >>;
observe-l (2) a < 2;
observe-l (2) a < 2;
return “a = “ + a;
```
Result:
```
Rank 0: a = 0
Rank 0: a = 1
Rank 4: a = 2
Rank 4: a = 3
```
The following example shows that observations done with the `observe-l` statement can be undone. 
Here, the first observation increases the rank of `a >= 2` by two, while the second statement decreases the rank of `a >= 2` by two.
```
a := << 0 ... 4 >>;
observe-l (2) a < 2;
observe-l (2) a >= 2;
return “a = “ + a;
```
Result:
```
Rank 0: a = 0
Rank 0: a = 1
Rank 0: a = 2
Rank 0: a = 3
```
Note that the normal `observe` statement is less suitable to deal with multple observations, because alteranatives not satisfying the observation are completely ruled out and their ranks cannot (as in the example above) be shifted down afterwards due to subsequent observations. While J-observation does allow this, it often produces undersirable results. The main problem is that a statement `observe-J (n) b` does not take the prior ranks of `b` and `!b` into account. L-conditioning does take these prior ranks into account. For more information about J-conditioning and L-conditioning we refer the reader to [1](#ref-gp).
A practical example using L observation is included in the examples section ([link](#spelling-corrector)).

# Language reference

Apart from the special statements described above, RankPL is a simple imperative programming language. It supports integers, booleans, string, and array expressions, and is dynamically typed. Function calls in RankPL are always call-by-value. In this section we explain how to use RankPL and provide a reference of the supported statements and expressions.

### Running RankPL

The RankPL interpreter comes in the form of a self-contained Jar file called `RankPL.jar`. The usage is:
```
java -jar RankPL.jar <source_file> [max_rank]
```
Where `source_file` is the RankPL source file to execute, and the optional parameter `max_rank` specifies the maximum rank (inclusive) that is generated, which defaults to zero if the `max_rank` argument is omitted. The possible outcomes of a program (returned via the `return` statement) are generated in ascending order with respect to their rank.

### Programs and functions

A program is a sequence of function definitions. A function definition has the form
```
define function_name(var_1, ..., var_n) {
	s_1;
	s_2;
	...
};
```
A function call is an expression of the form:
```
function_name(arg_1, ..., arg_n)
```
The arguments supplied with a function call must match the parameters of the function. Violation leads to an error. Every function must terminate with a `return` statement. A missing return statement will lead to an error.

The main entry point of a program is the `main()` function. The values returned by the `main()` function are the values returned by the program. If the `main()` function is omitted, the body of the program (i.e., all statements not nested inside a function definition) is taken to be the body of the `main()` function.

### Statements

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
|Range choice		| `var := << n_1 ... n_2 >>`  		| Assign to `var` a random value between `n_1`(inclusive) and `n_2` (exclusive), all ranked 0. **(1)**		|
|If-else		| `if b then s_1 else s_2`		| Regular if-else statement. **(2)** |
|while-do		| `while b do s`			| Execute `s` as long as `b` evaluates to TRUE.		|
|observe		| `observe b`				| Observe condition `b` to hold (eliminate alternatives not satisfying `b` and uniformly shift down alternatives that remain`).	|
|observe-j		| `observe-j (n) b`			| Like `observe b`, but increase rank of alternatives not satisfying `b` by value of `n`. **(3)**		|
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

### Expressions

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
On line 1 we set `input`, which is a (possibly) misspelled word.
We then pick a word (`potential_match`) at random from a dictionary (lines 3-12).
We then compare, one by one, the characters of `input` and `potential_match`.
For each character we observe that the characters of `input` and `potential_match` 
	are the same (`input[i] == potential_match[k]`). 
This is done using L-conditioning, which means that the rank of a match is improved by one unit w.r.t. the rank of a non-match.
Next, if the characters match, we increase the pointers `i` and `k` and repeat the process (lines 28-29).
If the characters don't match, we consider three possibilities and generate the corresponding alternatives using an either-or statement (lines 26-36):
- There is a misspelling (e.g. when writing c*o*t instead of cat). We increase `i` and `k`.
- There is an insertion (e.g. when writing ca*r*t instead of cat). We increase only `i`.
- There is an omission (e.g. when writing ct instead of c*a*t). We increase only `k`.

At the end we use a normal `observe` statement to condition on the fact that all characters were processed.

```
 1  input := "foorteen";
 2
 3  dictionary := [
 4  	"one", "two", "three", "four", "five", 
 5  	"six", "seven", "eight", "nine", "ten", "eleven", 
 6  	"twelve", "thirteen", "fourteen", "fifteen", "sixteen",
 7  	"seventeen", "eighteen", "nineteen", "twenty"
 8  ]; 
 9
10  # Randomly choose word from dictionary
11  choice := << 0 ... len(dictionary) >>;
12  potential_match := dictionary[choice];
13
14  # Initialize counters (i iterates over input, k over match)
15  i := 0; k := 0;
16
17  # While there are characters left to match ...
18  while (i < len(input) & k < len(potential_match)) do {
19	# Normally we expect characters i and k to match
19  	observe-l (1) input[i] == potential_match[k];
20
21	# If characters match, increase both indices
22	if (input[i] == potential_match[k]) then {
23		i := i + 1;
24		k := k + 1;
25	} else {
26		either {
27			# Misspelling
28			i := i + 1;
29			k := k + 1;
30		} or {
31			# Insertion 
32			i := i + 1;
33		} or {
34			# Omission
35			k := k + 1;
36		}
37  	}
38  };
39  # Condition on all characters being matched
40  observe i == len(input) & k == len(potential_match);
41  return potential_match;
```
Result
```
Rank 0: fourteen
Rank 2: thirteen
Rank 2: fifteen
...
```

# References

<a name="ref-gp"></a>["Qualitative probabilities for default reasoning, belief revision, and causal modeling"](http://www.sciencedirect.com/science/article/pii/0004370295000909) (Goldszmidt, Pearl. Artificial Intelligence
Volume 84, Issues 1–2, July 1996, Pages 57-112).

<a name="ref-survey"></a>["A Survery of Ranking Theory"](https://www.researchgate.net/profile/Wolfgang_Spohn/publication/30013588_A_Survey_of_Ranking_Theory/links/0c9605231bd9314802000000.pdf) (Spohn. Degrees of belief. Springer Netherlands, 2009. 185-228).
