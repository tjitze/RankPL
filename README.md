# News

29 october 2017: A new version ([0.7.1](https://github.com/tjitze/RankPL/releases/tag/0.7.1)) has been released.

# What is RankPL?

RankPL is a semi-qualitative probabilistic programming language aimed at modelling uncertainty expressible by distinguishing *normal* from *surprising* events. For example, we can express that a statement is only normally or only surprisingly executed, or that a variable normally becomes *X* but may surprisingly become *Y*. 

This kind of uncertainty often appears in common sense reasoning problems. For example, if we diagnose a boolean circuit, we assume that each component *normally* functions correctly, or if we process sensor data, we assume that each piece of data we receive is *normally* correct. If precise probabilites of such events are unknown, a probabilstic approach is inappropriate.

Like a regular probabilistic programming language, RankPL can be used to easily represent models that generate explanations and predictions based on observed data. RankPL programs generate rankings over possible outcomes: first the normal outcomes, then the surprising ones, then the very surprising ones, and so on. The computational simplicity of ranks allows for efficient and exact inference without the need for sampling techniques.

Semantically, RankPL is based on *ranking theory*. This is a formalism for reasoning about uncertainty in which events receive discrete, integer-valued ranks that represent degrees of surprise. Ranking theory can be understood as a qualitative abstraction of probability theory that retains many desirable features of the probabilistic approach. For example, it supports update through conditioning and representation of complex (in)dependence relationships. Ranking theory is well known as a basis for logic-based AI approaches such as belief revision and non-monotonic reasoning.

# Documentation

A detailed introduction and language reference can be found in the [wiki](https://github.com/tjitze/RankPL/wiki).

RankPL is described in the paper ["RankPL: A Qualitative Probabilistic Programming Language"](https://github.com/tjitze/RankPL/tree/master/paper/rankpl.pdf) (presented at [ECSQARU 2017](http://www2.idsia.ch/cms/isipta-ecsqaru/)). Note that the syntax described in this paper is outdated and that the latest version of RankPL contains many new features. Consult the [wiki](https://github.com/tjitze/RankPL/wiki) for an up-to-date description. An updated and extended version of the paper is in preparation.

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

# Contact

Please contact me if you have questions or suggestions (tjitze@gmail.com). Contributions are welcome.

# License

RankPL is licensed under the MIT license.
