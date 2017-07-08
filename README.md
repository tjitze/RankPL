# What is RankPL?

RankPL is a qualitative probabilistic programming language aimed at modelling uncertainty expressible by distinguishing *normal* from *surprising* events. For example, we can express that a statement is only normally or only surprisingly executed, or that a variable normally becomes *X* but may surprisingly become *Y*. 

This kind of uncertainty often appears in “common sense” reasoning problems. For example, if we diagnose a boolean circuit, we assume that each component *normally* functions, or if we process sensor data, we assume that each piece of data we receive is *normally* correct. The precise probability of the event that a component fails or that the data is incorrect may be unknown; all we may be able to say is that these events are surprising.

Like a regular probabilistic programming language, RankPL provides the ability to represent models in a flexible manner and to  generate explanations and predictions based on observed data. Unlike a probabilistic programming language, the results are *ranked*, rather than probabilistic. In practical terms, this means that a RankPL program generates a ranking over possible outcomes: first the normal outcomes, then the surprising ones, then the very surprising ones, and so on. The computational simplicity of ranks allows for efficient and exact inference without the need for sampling techniques.

Semantically, RankPL is based on *ranking theory*, a qualitative abstraction of probability theory in which events receive integer-valued ranks that represent degrees of surprise. Ranking theory is computationally simpler than probability theory but still shares many of its powerful features, such as a ranking-theoretic equivalent of Bayesian conditioning and the ability to represent complex (in)dependence relationships. Ranking theory originates from the field of formal epistemology and has been applied in logic-based AI approaches such as belief revision and non-monotonic reasoning.

# Documentation

Detailed documentation can be found in the [wiki](https://github.com/tjitze/RankPL/wiki).

This work is also described in the paper ["RankPL: A Qualitative Probabilistic Programming Language"](https://github.com/tjitze/RankPL/tree/master/paper/rankpl.pdf) (to be presented at [ECSQARU 2017](http://www2.idsia.ch/cms/isipta-ecsqaru/)). Note that the syntax described in this paper is outdated and that the latest version of RankPL contains many new features. Consult the [wiki](https://github.com/tjitze/RankPL/wiki) for an up-to-date description.

# Usage

The RankPL interpreter is distributed as a self-contained jar file `RankPL.jar`. The jar file for the latest release can be downloaded [here](https://github.com/tjitze/RankPL/releases). Basic usage is:
```
java -jar RankPL.jar -source source_file.rpl
```
where `source_file.rpl` is the program to run. Command line options are discussed in the [wiki](https://github.com/tjitze/RankPL/wiki).

# Build Instructions

RankPL is built using Maven. The process is straightforward. For example, to build the self-contained Jar file, run the following command from the project's root directory:
```
mvn package
```
The self-contained Jar file will be written to `target/RankPL-{version}-jar-with-dependencies.jar`.

# License

RankPL is licensed under the MIT license.
