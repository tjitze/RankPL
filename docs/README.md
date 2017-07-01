# RankPL: A Ranked Programming Language

RankPL is a qualitative probabilistic programming language aimed at modelling uncertainty expressible by distinguishing normal from surprising events. For example, we can express that a statement is only normally or only surprisingly executed, or that a variable normally becomes X but may surprisingly become Y. 

This kind of uncertainty often appears in “common sense” reasoning problems. For example, in circuit diagnosis we assume that a component normally functions, or if we process sensor data, we assume that the data we receive is normally correct. We may not know the precise probability of the event that a component fails or that the data is incorrect. Often, all we know is that these events are surprising.

Like a regular probabilistic programming language, RankPL provides the ability to represent models used to generate explanations and predictions based on observed data. Unlike a probabilistic programming language, the results are ranked, rather than probabilistic. In practical terms, this means that a RankPL program generates a ranking over possible outcomes: first the normal outcomes, then the surprising ones, then the very surprising ones, and so on. 


### Running RankPL

The RankPL interpreter comes in the form of a self-contained Jar file called `RankPL.jar`. The usage is:
```
java -jar RankPL.jar <source_file> [max_rank]
```
Where `source_file` is the RankPL source file to execute, and the optional parameter `max_rank` specifies the maximum rank (inclusive) that is generated, which defaults to zero if the `max_rank` argument is omitted. The possible outcomes of a program (returned via the `return` statement) are generated in ascending order with respect to their rank.

