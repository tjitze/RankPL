# Examples

## Causal Reasoning<a name="spelling-corrector"></a>

The ranking-based counterpart of a Bayesian network is called a *ranking network*. The following example shows how a ranking network can be represented in RankPL. The figure below depicts a ranking network for the 
typical rain-sprinkler-wet example. There are three boolean variables: **Rain**, **Sprinkler** and **GrassWet**, each associated with a conditional ranking table. Like in Bayesian network, these tables encode the ranking over the possible values of a node given its parent nodes. In our example, they encode that:

- It normally rains. Absence of rain is surprising to degree 1.
- If it rains, the sprinkler is normally OFF and surprisingly (to degree 3) ON. If it does not rain, the sprinkler is normally ON and surprisingly (to degree 1) OFF.
- If it does not rain and the sprinkler is OFF, the grass is dry. If it rains or if the sprinkler is ON but not both, the grass is normally wet and surprisingly (to degree 2) dry. If it rains *and* the sprinkler is on, the grass is normally wet and surprisingly (to degree 5) dry.

<p align=center>
<img src=https://github.com/tjitze/RankPL/blob/master/examples/rankingnetwork.jpg width=400px />
</p>

This ranking network is represented by the program shown below (it can be found in the examples directory under the name `rankingnetwork.rpl`). We use the boolean variables `rain `, `sprinkler` and `grass_wet` to represent the three events. The encoding of the ranking network is similar to how Bayesian networks are encoded in probabilistic programming languages. Roughly speaking, we traverse the network in topological order and encode the conditional ranking tables using ranked choice statements nested inside if-else statements. 
```
 1  rain := true << 1 >> false;
 2  if (!rain) {
 3  	sprinkler := true << 1 >> false;
 4  } else {
 5  	sprinkler := false << 3 >> true;
 6  };
 7  if (!rain & !sprinkler) {
 8  	grass_wet := false; 
 9  } else if (rain & sprinkler) then {
10	grass_wet := true << 5 >> false;
11  } else {
12  	grass_wet := true << 2 >> false;
13  };
14  return "rain: " + rain + " sprinkler: " + sprinkler + 
         " grass_wet: " + grass_wet;
```
Running this program yields the ranking function represented by this ranking network: 
```
java -jar RankPL.jar -source examples/rankingnetwork.rpl -all (ENTER)
```
```
Rank    Outcome
   0    rain: true	sprinkler: false	grass_wet: true
   1    rain: false	sprinkler: true 	grass_wet: true
   2    rain: false	sprinkler: false	grass_wet: false
   2    rain: true	sprinkler: false	grass_wet: false
   3    rain: true	sprinkler: true 	grass_wet: true
   3    rain: false	sprinkler: true		grass_wet: false
   8    rain: true	sprinkler: true		grass_wet: false
```
Inference can be modeled by adding an observe statement. 
Suppose we observe that the sprinkler is on. We insert, right before the `return` statement, the statement
```
observe sprinkler;
```
and run it as above. The program now yields the ranking shown below. It tells us that, if we observe that the sprinkler is on, it normally does not rain but the grass is wet.
```
Rank    Outcome
   0    rain: false	sprinkler: true		grass_wet: true
   2    rain: true	sprinkler: true		grass_wet: true
   2    rain: false	sprinkler: true		grass_wet: false
   7    rain: true	sprinkler: true		grass_wet: false
```
## Circuit Diagnosis<a name="circuit-diagnosis"></a>

Consider the circuit of a 2-bit *full adder* depicted below.

<p align=center>
<img src=https://github.com/tjitze/RankPL/blob/master/examples/boolcircuit.jpg width=500px />
</p>

This circuit consists of two XOR gates *X₁* and *X₂*, two AND gates *A₁* and *A₂*, and an OR gate *O₁*.
The function of this circuit is to generate a binary representation (*out₁*,*out₂*) of the
number of inputs among (*in₁*,*in₂*,*in₃*) that are high.
The *circuit diagnosis problem* is about explaining observed incorrect behavior by finding
minimal sets of gates that, if failing, cause this behavior.

Suppose, for example, that we set the inputs *in₁* and *in₂* to low and *in₃* to high. 
The correct output would be *out₁* low and *out₂* high.
However, we observe that *out₁* is high and *out₂* is low.
Thus, the circuit is defective. But which gate is (or which gates are) most likely failing?
This problem is modelled by the following program.
It can be found in the examples directory under the name `boolcircuit.rpl`.

```
 1  # Set input variables
 2  in1 := FALSE;
 3  in2 := FALSE;
 4  in3 := TRUE;
 5
 6  # Set state of gates (TRUE is functioning, FALSE is broken)
 7  x1_broken := FALSE <<1>> TRUE;
 8  x2_broken := FALSE <<1>> TRUE;
 9  a1_broken := FALSE <<1>> TRUE;
10  a2_broken := FALSE <<1>> TRUE;
11  o1_broken := FALSE <<1>> TRUE;
12
13  # Circuit logic
14  if (x1_broken) then l1 := FALSE <<0>> TRUE else l1 := (in1 ^ in2);
15  if (a1_broken) then l2 := FALSE <<0>> TRUE else l2 := (in1 & in2);
16  if (a2_broken) then l3 := FALSE <<0>> TRUE else l3 := (l1 & in3);
17  if (x2_broken) then out2 := FALSE <<0>> TRUE else out2 := (l1 ^ in3);
18  if (o1_broken) then out1 := FALSE <<0>> TRUE else out1 := (l3 | l2);
19  
20  # Observe output
21  observe out1 & !out2;
22 
23  # Return state of gates
24  return "X1 broken: " + x1_broken + ", X2 broken: " + x2_broken + 
   	", A1 broken: " + a1_broken +  ", a2 broken: " + a2_broken +  
        ", O1 broken: " + o1_broken;
```

The program works as follows: on lines 2-4 we set the input variables `in1` and `in2` to low (false) and `in3` to high (true). On lines 7-11 we state that the event that an individual gate is broken is surprising to degree 1.
Lines 14-18 encode the logic of the circuit. Note that, if a gate is broken, its output is arbitrarily set to high or low. On line 21 we observe the faulty output and on line 24 we return a string describing the state of each gate. Running this program yields exactly one outcome ranked zero: 
```
java -jar RankPL.jar -source examples/rankingnetwork.rpl (ENTER)
```
```
Rank    Outcome
   0    X1 broken: true, X2 broken: false, A1 broken: false, a2 broken: false, O1 broken: false
```
This means that the observed behaviour is most plausibly explained by failure of gate *X₁*.
Other outcomes are ranked higher than zero. They can be generated by adding the command line parameter `-all`, to generate all outcomes, or `-rank n` to generate outcomes up to rank `n`.
The higher ranked outcomes represent explanations that are less plausible because they involve more than one broken gate.

## Spelling Corrector<a name="spelling-corrector"></a>

The following example implements a spelling correction algorithm. 
The idea is to pick a word (`potential_match`) at random from a dictionary (lines 6-16) and to consider each character `input[i]` (for `i` ranging from 0 to the size of `input`) as a piece of information that strengthens our belief that the actual character at index `i` equals `index[i]`. For this we use L-observation (line 28) with parameter 1, which will shift the ranks of the considered potential matches accordingly. 

Furthermore, if a character doesn't match, we consider three possibilities and generate the corresponding alternatives using an either-or statement (lines 38-48):
- There is a misspelling (e.g. when writing c*o*t instead of cat).
- There is an insertion (e.g. when writing ca*r*t instead of cat).
- There is an omission (e.g. when writing ct instead of c*a*t).
At the end we use a normal `observe` statement to condition on the fact that all characters were processed.
For demonstration purposes, the program uses the word "tweleven" as input, while the dictionary consists of all numbers from "one" to "twenty".
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
Running this program yields a ranking over the most plausible corrections of the word "tweleven":
```
java -jar RankPL.jar -source examples/spellingcorrector.rpl -all (ENTER)
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
To run this algorithm with a more realistic dictionary, we have included the file `google-10000-english-no-swears.txt` (taken from [this](https://github.com/first20hours/google-10000-english) repository) in the examples directory. This file contains the 10000 most used words in the english language, ordered by frequency of use. To use it, replace lines 7-12 with the following statement (change path if necessary):
```
dictionary := readfile("examples/google-10000-english-no-swears.txt");
```
To handle the large set of alternatives generated by this dictionary, use the `-d` setting to enable iterative deepening mode:
```
java -jar RankPL.jar -source examples/spellingcorrector.rpl -d (ENTER)
```
This will result in a one to two second look-up time for most words.