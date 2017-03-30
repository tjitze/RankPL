# RankPL

*This work is described in the paper ["RankPL: A Qualitative Probabilistic Programming Language"](https://github.com/tjitze/RankPL/tree/master/paper/rankpl.pdf) (under submission)*

RankPL is a modelling language aimed at representing and reasoning about processes that exhibit uncertainty expressible by distinguishing "normal" from "surprising" events. It can be thought of as a qualitative variant of a *probabilistic programming language*, which is a regular programming language extended with statements to draw values at random from a given probability distribution and condition values of variables due to observation. Rather than a deterministic outcome,  probabilistic programs represent probability distributions over different possible outcomes. 

Semantically, RankPL draws a parallel with probabilistic programming in terms of *ranking theory*. This is a qualitative abstraction of probability theory in which events receive discrete degrees of surprise called *ranks*. That is, events can be ranked 0 (not surprising), 1 (surprising), 2 (very surprising), and so on, or ∞ if impossible. Analoguous to probability theory, ranking theory provides powerful notions such as conditioning and independence.

Thus, RankPL is a programming language (a simple imperative one) extended with statements to draw choices at random from a ranking function and to perform ranking-theoretic conditioning. This makes it possible to model uncertainty expressible by distinguishing normal (rank 0) from surprising (rank > 0) events. Drawing choices at random from a ranking function is done through the *ranked choice* construct. Example:

(1) `{ x := 10 } << 1 >> { x := 20 };` (x normally becomes 10 but can surprisingly—to degree 1—become 20)

Ranking-theoretic conditioning is done using the *observe* statement. Example:

(2) `OBSERVE x > 15;` (x is observed to be more than 15)

Just like a probabilistic program represents a probability distribution over possible outcomes, a RankPL program represents a ranking function over possible outcomes. Thus, a program has outcomes ranked 0 (normal), ranked 1 (surprising), and so on. For example, the program consisting of just the statment (1) above has two outcomes: x = 10 (ranked 0) and x = 20 (ranked 1), whereas the program consisting of (1) followed by (2) has one outcome: x = 20 (ranked 0). 

RankPL supports various reasoning tasks, including causal inference, abduction and (iterated) belief revision. 
Let’s look at an example. Consider the *two-bit full adder* circuit shown in the figure below. It contains two *XOR* gates *X<sub>1</sub>, X<sub>2</sub>*, two *AND* gates *A<sub>1</sub>, A<sub>2</sub>* and an *OR* gate *O<sub>1</sub>*.
The 2-bit output b<sub>1</sub>b<sub>2</sub> is a binary representation of the number of inputs a<sub>1</sub>a<sub>2</sub>a<sub>3</sub> that are high. The *circuit diagnosis problem* is about explaining observed incorrect behavior by finding minimal sets of gates that, if faulty, cause this behavior.

<p align=center>
<img src=https://github.com/tjitze/RankPL/blob/master/examples/boolcircuit.jpg width=500px />
</p>

The listing below shows a RankPL solution. Lines 1-3 encode the space of possible inputs (0 or 1, equally likely). The failure variables fx<sub>1</sub>fx<sub>2</sub>fa<sub>1</sub>fa<sub>2</sub>fo<sub>1</sub> represent the events of individual gates failing and are set on lines 4-8 (0 = OK, 1 = failing). Here, we assume that failure is surprising to degree 1. The circuit's logic is encoded on lines 9-13, where the output of a failing gate is arbitrarily set to 0 or 1. At the end we observe that a<sub>1</sub>a<sub>2</sub>a<sub>3</sub>$ is valued 001 while the output $b<sub>1</sub>b<sub>2</sub>$ is incorrectly valued 10 instead of 01.

```
1  { a1 := 0 } << 0 >> { a1 := 1 };
2  { a2 := 0 } << 0 >> { a2 := 1 };
3  { a3 := 0 } << 0 >> { a3 := 1 };
4  { fx1 := 0 } << 1 >> { fx1 := 1 };
5  { fx2 := 0 } << 1 >> { fx2 := 1 };
6  { fa1 := 0 } << 1 >> { fa1 := 1 };
7  { fa2 := 0 } << 1 >> { fa2 := 1 };
8  { fo1 := 0 } << 1 >> { fo1 := 1 };
9  IF (fx1 == 0) THEN l1 := (a1 ^ a2) ELSE l1 := 0 << 0 >> 1;
10 IF (fa1 == 0) THEN l2 := (a1 & a2) ELSE l2 := 0 << 0 >> 1;
11 IF (fa2 == 0) THEN l3 := (l1 & a3) ELSE l3 := 0 << 0 >> 1;
12 IF (fx2 == 0) THEN b2 := (l1 ^ a3) ELSE b2 := 0 << 0 >> 1;
13 IF (fo2 == 0) THEN b1 := (l3 | l2) ELSE b1 := 0 << 0 >> 1;
14 OBSERVE ((a1 == 0) & (a2 == 0) & (a3 == 1) & (b1 == 1) & (b2 == 0));
```
The different valuations of the failure variables produced by this program represent explanations for the observation, ranked according to plausibility. We can run this program (called boolcircuit.rpl) as follows:

`java -jar RankPL.jar boolcircuit.rpl 2 fx1 fx2 fa1 fa2 fo1`

This means that we will only get outcomes ranked at most 2, and we only want to get the values of the variables fx<sub>1</sub>fx<sub>2</sub>fa<sub>1</sub>fa<sub>2</sub>fo<sub>1</sub>. The output is:

```
Rank 0: [fa1=0, fa2=0, fo1=0, fx1=1, fx2=0]
Rank 1: [fa1=0, fa2=0, fo1=0, fx1=1, fx2=1]
Rank 1: [fa1=0, fa2=0, fo1=1, fx1=1, fx2=0]
Rank 1: [fa1=1, fa2=0, fo1=0, fx1=0, fx2=1]
Rank 1: [fa1=1, fa2=0, fo1=0, fx1=1, fx2=0]
Rank 1: [fa1=0, fa2=1, fo1=0, fx1=0, fx2=1]
Rank 1: [fa1=0, fa2=1, fo1=0, fx1=1, fx2=0]
...
```

That is, the observation is most plausibly explained by failure of X<sub>1</sub> (the single outcome ranked 0). Other explanations are ranked higher than 0 and involve more than one faulty gate, such as the second one, which states that X<sub>1</sub> and X<sub>2</sub> both fail.

As opposed to probabilistic programming languages, which usually require the computation (whether exact or approximate) of the the complete probability distribution, RankPL uses a *most-plausible-first* strategy. That is, the possible outcomes of a RankPL program can be iterated over in ascending order with respect to rank, 	and higher-ranked alternatives do not need to be explored if knowing only the lowest-ranked outcomes is sufficient, which is often the case. RankPL programs can be written as above, but can also be expressed using the Java classes that map to its syntax. This makes it possible to embed RankPL code inside Java code and to make it interact with and use classes and methods written Java. 

A more detailed description can be found in the [paper](https://github.com/tjitze/RankPL/tree/master/paper/rankpl.pdf), which includes a formal syntax and semantics and an example involving iterated belief revision. The latest release (stand-alone jar file) can be downloaded [here](https://github.com/tjitze/RankPL/releases). RankPL is still work in progress but the basic functionality is there.
