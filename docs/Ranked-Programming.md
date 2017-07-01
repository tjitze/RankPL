RankPL is a programming language extended with statements to distinguish “normal” from “surprising” events and to perform conditioning due to observations. RankPL embodies the *ranked programming* principle. It can be thought of as a qualitative variant of a probabilistic programming language, based on [Ranking Theory](https://github.com/tjitze/RankPL/wiki/Ranking-Theory). 

Broadly speaking, RankPL can be used to represent and reason about models with uncertain behaviour, and where this uncertainty can be expressed by distinguish “normal” from “surprising” events, rather than using precise probabilities. Applications include abduction (e.g. fault diagnosis), natural language processing, causal reasoning and much more.

Here we present an overview of the special statements of the language, namely the *ranked choice* statement, used to draw choices at random from a ranking function; the *observe* statement, used to perform conditioning, as well as several related statements (`observe-j`, `observe-l` and `infer`). Apart from these special statements, RankPL is a fairly standard imperative programming language. A complete overview of all statements and expression types supported by RankPL can be found on the [Language Reference](https://github.com/tjitze/RankPL/wiki/Language-Reference) page.

## The ranked choice statement<a name="ranked-choice"></a>

The *ranked choice* statement makes it possible to introduce alternative program flows, each associated with a rank. The basic form is as follows.
```
normally (rank) A exceptionally B;
```
where `rank` is an integer expression and `A` and `B` are (block) statements. This statement states that:

- Normally, `A` is executed.
- If `A` is not executed (which is surprising to degree `rank`) then `B` is executed.

In ranking-theoretic terms, this can be understood as a draw at random from a ranking function over the statements `A` and `B`, where the alternative `A` is ranked 0 and the alternative `B` is ranked `rank`.

The `(rank)` expression may be omitted. If it is, the rank defaults to 1. 
The `exceptionally B` part may also be omitted. If it is, `B` is taken to be the `skip` statement (a "no-op" statement that does nothing).
Thus, the statement
```
normally A;
```
states that, normally, `A` executed, and that the event that `A` is not executed is surprising to degree 1.

Combining ranked choice statements leads to larger sets of alternatives. An example:
```
normally (1) A exceptionally B;
normally (1) C exceptionally D;
```
This produces four alternative program flows: A-C (ranked 0), B-C (ranked 1), A-D (ranked 1), and B-D (ranked 2). 
Notice how the ranks of subsequent events sum up to form the rank of the resulting joint events. 

By nesting the ranked choice construct we can express draws over larger sets of alternatives. 
An example:
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
This statement states that:

- Normally, `A` executed. 
- If `A` is not executed (surprising to degree 1) then, normally, `B` is executed.
- If neither `A` nor `B` are executed (surprising to degree 2) then `C` is executed. 

### Either-Or

Three variations of the ranked choice statement are supported. They are "syntactic sugar" to support commonly occurring patterns. The first is the *either-or* statement, which represents regular non-deterministic choice between two or more choices that are all equally likely:
```
either A or B
```
is equivalent to:
```
normally (0) A exceptionally B
```

### Ranked Assignment

The second is the *ranked assignment* statement. It expresses ranked choice between two values assinged to a variable:
```
var := e₁ <<rank>> e₂
```
is equivalent to
```
normally (rank) var := e₁ exceptionally var := e₂
```

### Range Assignment

The third is the *range assignment* statement. It represents choice among the integer values between `n₁` (inclusive) and `n₂` (exclusive), all equally likely:
```
a := << n₁ ... n₂ >>
```
is equivalent to:
```
either a := n₁ or a := (n₁ + 1) or ... or a := (n₂-1)
```

### An Example<a name="example-1"></a>

Alice is tossing an extremely biased coin. It normally lands heads, and only surprisingly (to degree 1) lands tails. She tosses the coin three times. How many times will she throw heads? In the program below, we use 1 to represent heads and 0 to represent tails.

```
 1  flip1 := 1 <<1>> 0;
 2  flip2 := 1 <<1>> 0;
 3  flip3 := 1 <<1>> 0;
 4  return flip1 + flip2 + flip3;
```
Running a RankPL program leads to the generation of a marginalized ranking function over the possible return values. The program above generates the ranking:
```
Rank    Outcome
   0    3
   1    2
   2    1
   3    0
```
This ranking says that Alice normally throws heads three times; surprisingly (to degree 1) two times; very surprisingly (to degree 2) one time; and very very surprisingly (to degree 3) zero times. 

## The *observe* statement<a name="observe"></a>

The statement `observe b` revises the ranking over alternatives due to observing or learning that the condition `b` is true. It does two things: 

- Uniformly shift down the ranks of the alternatives that satisfy `b` to zero.
- Block execution of the remaining alternatives. 

This is exactly what ranking-theoretic conditioning does (discussed [here](https://github.com/tjitze/RankPL/wiki/Ranking-Theory#conditional-ranks)) except for how alternatives that don't satisfy `b` are handled: instead of shifting their ranks up to ∞, we simply block their execution.

### An Example<a name="example-2"></a>
Suppose we observe that Alice throws tails at least once (i.e., `flip1 + flip2 + flip3 < 3`). 
```
 1  flip1 := 1 <<1>> 0;
 2  flip2 := 1 <<1>> 0;
 3  flip3 := 1 <<1>> 0;
 4  observe flip1 + flip2 + flip3 < 3;
 5  return flip1 + flip2 + flip3;
```
The result is the same ranking as before, except that
	the outcome `3` is ruled out,
	and the ranks of remaining outcomes are shifted down.
```
Rank    Outcome
   0    2
   1    1
   2    0
```
This ranking says that Alice normally throws heads two times; surprisingly (to degree 1) one time; and very surprisingly (rank 2) zero times. 

### Abductive Reasoning

Similar to conditionalization in a probabilistic setting, the `observe` statement in RankPL can also be used to reason *backwards*. 
To see a very simple example of this, let's adapt the previous example.
Instead of returning `flip1 + flip2 + flip3`, we return an array containing the values of `flip1`, `flip2` and `flip3`.
```
 1  flip1 := 1 <<1>> 0;
 2  flip2 := 1 <<1>> 0;
 3  flip3 := 1 <<1>> 0;
 4  observe flip1 + flip2 + flip3 < 3;
 5  return [flip1, flip2, flip3];
```
The result is:
```
Rank    Outcome
   0    [0, 1, 1]
   0    [1, 0, 1]
   0    [1, 1, 0]
   1    [0, 0, 1]
   1    [0, 1, 0]
   1    [1, 0, 0]
   2    [0, 0, 0]
```
This result can be though of as a ranking over the most likely explanations for the observation that Alice threw tails at least once.
The normal (or most plausible) explanation is that either the first, second or third flip was tails.
Other explanations receive a higher rank because they assume that more surprising events took place than necessary to explain the observation.
This is a simple example of what is called *abduction*, or *inference to the best explanation*.
The circuit diagnosis example included in section TODO is a more practical application of this principle.

### J-observation

The J-observation statement generalizes the regular observation statement and implements the J-conditionalization operator (discussed [here](https://github.com/tjitze/RankPL/wiki/Ranking-Theory#conditional-ranks)). It has the form `observe-j (x) b`. It revises the ranking over alternatives due to observing or learning that the condition `b` is *normally* true, where `x` is the degree of surprise that `b` is *not* true. It does two things: 

- Uniformly shift down the ranks of the alternatives that satisfy `b` to zero.
- Shift the ranks of the alternatives not satisfying `b` to `x`.

The regular observation statement is equivalent to the j-observation with parameter ∞. 

An example. Suppose Bob tells us that Alice threw tails at least once. However, we believe that Bob may be mistaken. The degree of surprise we assign to Bob being mistaken is 5. We adapt the program as follows.
```
 1  flip1 := 1 <<1>> 0;
 2  flip2 := 1 <<1>> 0;
 3  flip3 := 1 <<1>> 0;
 4  observe-j (5) flip1 + flip2 + flip3 < 3;
 5  return flip1 + flip2 + flip3;
```
The result is:
```
Rank    Outcome
   0    2
   1    1
   2    0
   5    3
```
Whereas the statement `observe flip1 + flip2 + flip3 < 3` completely ruled out the outcome `3` (see [previous example](https://github.com/tjitze/RankPL/wiki/Ranked-Programming#example-2)), the statement `observe-j (5) flip1 + flip2 + flip3 < 3` shifted its rank up to 5.

### L-observation

The L-observation statement is another generalization of the regular observation statement. It implements the L-conditionalization operator (discussed [here](https://github.com/tjitze/RankPL/wiki/Ranking-Theory#conditional-ranks)). The statement has the form `observe-l (x) b`. It revises the ranking over alternatives due to receiving evidence that strengthens our belief in `b`, where `x` represents the impact of the evidence. More precisely, the effect is that the rank of the alternatives satisfying `b` improve by `x` units with respect to the rank of the alternatives not satisfying `!b`. Depending on the prior ranks of `b` and `!b`, this means that the rank of the alternatives satisfying `b` decreases, that the ranks of the alternatives satisfying `!b` increases, or both (the sum of this decrease and increase equals x). Like J-observation, L-observation generalizes the regular observation statement, which is equivalent to L-observation with parameter ∞. 

An example. We receive information that strengthens our belief that Alice threw tails at least once by 5 units of rank. We adapt the program as follows.
```
 1  flip1 := 1 <<1>> 0;
 2  flip2 := 1 <<1>> 0;
 3  flip3 := 1 <<1>> 0;
 4  observe-l (5) flip1 + flip2 + flip3 < 3;
 5  return flip1 + flip2 + flip3;
```
The result is:
```
Rank    Outcome
   0    2
   1    1
   2    0
   4    3
```
If we compare this ranking to the result of the example without observation (see [here](https://github.com/tjitze/RankPL/wiki/Ranked-Programming#example-1)) we see that the rank of the alternatives satisfying `b` are shifted down by one unit of rank, and the alternatives satisfying `!b` are shifted up by four units of rank. The sum of this increase and decrease is 5.

### Iterated Revision with L-observation

L-observation is especially useful when we have to deal with iterated revision scenarios that involve multiple sequential observations. This is because the operation on which it is based (L-conditionalization) nicely models the accumulation of confidence due to observations that strengthen already-held beliefs, as well as the reversal of confidence that occurs when new observations contradict previous ones. J-conditioning does not account for accumulation and reversal because it does not take the prior ranks of the evidence into account.

The following example demonstrates the accumulation of evidence: we receive the same evidence twice.
```
 1  flip1 := 1 <<1>> 0;
 2  flip2 := 1 <<1>> 0;
 3  flip3 := 1 <<1>> 0;
 4  observe-l (5) flip1 + flip2 + flip3 < 3;
 4  observe-l (5) flip1 + flip2 + flip3 < 3;
 5  return flip1 + flip2 + flip3;
```
The result is:
```
Rank    Outcome
   0    2
   1    1
   2    0
   9    3
```
The outcome `3` is now ranked 9.

The following example demonstrates the reversal of the effect of an observation. Here, the second observation negates the first one.
```
 1  flip1 := 1 <<1>> 0;
 2  flip2 := 1 <<1>> 0;
 3  flip3 := 1 <<1>> 0;
 4  observe-l (5) flip1 + flip2 + flip3 < 3;
 4  observe-l (5) flip1 + flip2 + flip3 >= 3;
 5  return flip1 + flip2 + flip3;
```
The result is:
```
Rank    Outcome
   0    3
   1    2
   2    1
   3    0
```
The second observation has reversed the effect of the first. The result is the same as in the example without observation (see [here](https://github.com/tjitze/RankPL/wiki/Ranked-Programming#example-1)).

A practical example involving iterated revision using the `observe-l` statement is included in the examples section ([link](#spelling-corrector)).

## The *infer* function<a name="infer"></a>

In the example above, the program generates a ranking over possible return values, where the rank zero results represent the normal or most plausible results of the program. The *infer* function can be used to collect these outcomes so that they can be used in some deterministic way. The infer function takes as input a function call and returns an array containing all the rank zero return values. We demonstrate it by adapting the prevous example as follows.
```
 1  define program() { 
 2      flip1 := 1 <<1>> 0;
 3      flip2 := 1 <<1>> 0;
 4      flip3 := 1 <<1>> 0;
 5      observe flip1 + flip2 + flip3 < 3;
 6      return [flip1, flip2, flip3];
 7 };
 8
 9 result := infer(program());
10 print "Most plausible outcomes:" + result;
```
This program prints the output:
```
Most plausible outcomes:[[0,1,1],[1,0,1],[1,1,0]]
```

## Least-Surprising-First Execution<a name="least-surprising-first"></a>

When running a RankPL program, the possible outcomes are always generated in ascending order with respect to rank. In other words, first the normal outcomes, then the surprising ones, then the very surprising ones, and so on. This is not just a matter of sorting the outcomes; it is a direct result of the ordering in which the alternatives at each choice point are executed: a *least-surprising-first* execution ordering. Roughly speaking, this means that an alternative with rank **x** is explored *only* after the alternatives with rank **x - 1** have been explored. 

The RankPL interpreter returns, by default, only the rank zero outcomes of a program (to generate outcomes with higher ranks, use the `-r` command line parameter). This makes sense because we are normally only interested in the “normal” behaviour of a program. It also significantly reduces computation time, because the least-surprising-first execution ordering ensures that higher-ranked alternatives are explored only if lower-ranked alternatives are ruled out by observe statements.