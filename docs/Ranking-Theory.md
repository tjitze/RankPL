Ranking theory [\[1\]](#references) is a qualitative abstraction of probability theory in which events receive integer-valued *ranks* that indicate degrees of surprise. Ranks allow reasoning about uncertainty expressible by distinguishing “normal” from “surprising” events. While ranks are computationally simpler than probabilities, ranking theory still shares many powerful features with probability theory. This includes a ranking-theoretic equivalents of conditioning and Bayesian networks.

Ranking theory originates from the field of formal epistemology and found application in logic-based AI approaches such as belief revision and non-monotonic reasoning [\[2\]](#references). Ranking theory is also the semantic basis for the RankPL programming language, which can be thought of as ranking-based variant of a probabilistic programming language (a "ranked programming language").

In this section we present an overview of ranking theory. The aim is to provide what is necessary to understand the formal background of RankPL and its relationship with probabilistic programming. We present the main definitions of ranking theory and contrast it with probability theory. At the end we provide a list of relevant references.

## Ranking functions<a name="ranking-functions"></a>

Let **Ω** be a set of **possibilities**. A **ranking function** **K** assigns to every **w ∈ Ω** a non-negative integer or ∞, such that **K(w) = 0** for at least one **w ∈ Ω**. Intuitively, these numbers (called **ranks**) represent degrees of surprise: if **K(w) = 0** then **w** is not surprising, if **K(w) = 1** then **w** is surprising, if **K(w) = 2** then **w** is very suprising, and so on, until **K(w) = ∞**, which means that **w** is impossible. 

Non-empty subsets of **Ω** are called **propositions** or **events**. The rank of a proposition **A ⊆ Ω** is defined to be the minimum of the ranks of the elements of **A.** In other words, a proposition **A** is as surprising as the least surprising possibility **w ∈ A** that gives rise to it:

<p align="center"><b>K(A) = min({w ∈ A | K(w)})</b>.</p>

Ranks can also be seen as degrees of disbelief. That is, if **K(A) = 0** then **A** is not disbelieved, and if **K(A) > 0** then **A** is disbelieved to degree **K(A)**. Disbelief in **A** is the same as belief in **A̅**. Thus, **A** is believed whenever **K(**A̅**) > 0** (which implies **K(A) = 0**). More generally, we say that **A** is  believed with *firmness* **x** iff **K(**A̅**) = x**.

Ranks can also be interpreted as **order of magnitude** probabilities: if **K(A) < K(B)** then the probability of **A** is of a higher order of magnitude than the probability of **B**. We discuss the details of this interpretation [below](#sec-probability).

## Conditionalization<a name="conditionalization"></a>

### Plain Conditionalization 

Like in probability theory, conditionalization in ranking theory makes it possible to update a ranking function due to new information. The rank of a possibility **w** *conditional on* a proposition **B** is denoted by **K(w\|B)**. If **K(B) < ∞** (**B** is not impossible) then **K(w\|B)** is defined as 

<p align="center"><b>K(w|B) = K(w) - K(B)</b> if <b>w ∈ B</b>, and</p>
<p align="center"><b>K(w|B) = ∞</b> if <b>w ∉ B</b>.</p>

In terms of propositions, this definition can be written more compactly as 

<p align="center"><b>K(A|B) = K(A∩B)-K(B)</b>.</p>

Conditionalization on **B** can be thought of as a shifting operation: the possibilities in **B** are uniformly shifted down by **K(B)** units of rank and the possibilities in **B̅** are shifted up to **∞**. The effect is that **K(B\|B) = 0** and **K(**B̅**\|B) = ∞** (i.e., **B** is believed with firmness **∞**).

### J-Conditionalization 

Conditionalization on **B** means that **B** becomes irreversibly believed with absolute certainty. We discuss two generalized forms of conditionalization. They account for revision that is reversible and does not necessarily lead to belief with absolute certainty. These are essential aspects of dealing with *iterated* revision problems. As we show later, these generalized forms of conditionalization also have practical applications in RankPL. 

The first is *J-conditionalization* [\[2\]](#references) (also called *result-oriented* conditionalization [\[1\]](#references)). It is parameterised by a rank **x** that indicates the firmness with which the new information must be believed. In the following definition we denote the ranking function resulting from J-conditionalization of **K** on **B** with parameter **x** by **J**:

<p align="center"><b>J(w) = K(w) - K(B)</b> if <b>w ∈ B</b>, and</p>
<p align="center"><b>J(w) = x</b> if <b>w ∉ B</b>.</p>

This is a generalization of plain conditionalization, which is equivalent to J-conditionalization with parameter **∞**. The effect of J-conditionalization on **B** with parameter **x** is that **J(B) = 0** and **J(B̅) = x** (i.e., **B** is believed with firmness **x**).

### L-Conditionalization 

The second is *L-conditionalization* [\[2\]](#references) (also called *evidence-oriented* conditionalization [\[1\]](#references)). L-conditionalization is, like J-conditionalization, parameterised by a rank **x**. This time, **x** indicates the "evidential impact" of the new information. In the following definition we denote the ranking function resulting from L-conditionalization of **K** on **B** with parameter **x** by **L**:

<p align="center"><b>L(w) = K(w) - y</b> if <b>w ∈ B</b>, and</p>
<p align="center"><b>L(w) = K(w) + x - y</b> if <b>w ∉ B</b>,</p>
<p align="center">where <b>y = min(K(B), x)</b>.</p>

The effect of L-conditionalization on **B** with parameter **x** is that the ranks of the possibilities satisfying **B** improve by **x** units of rank w.r.t. the possibilities not satisfying **B**. Depending on the prior ranks of **B** and **B̅**, this means that the firmness with which **B** is believed increases, that the firmness with which **B̅** is believed decreases, or both (the sum of this increase and decrease equals **x**). 

Like J-conditionalization, L-conditionalization is a generalization of plain conditionalization, which is equivalent to L-conditionalization with parameter **∞**.

## Ranking Theory versus Probability Theory<a name="sec-probability"></a>

How do ranks compare to probabilities? An important difference is that ranks of propositions do not add up as probabilities do. That is, if **A** and **B** are disjoint then **P(A∪B) = P(A) + P(B)** but **K(A∪B) = min(K(A), K(B))**. This is, however, consistent with the interpretations of ranks as degrees of surprise: **A∪B** is as surprising as the least surprising among **A** and **B**.

Furthermore, ranks provide deductively closed beliefs, whereas probabilities do not.
More precisely, if **A** and **B** are believed with firmness **x** then so is **A∩B**.
In probabilistic terms, we can translate this to: if **P(A) ≥ p** and **P(B) ≥ p** then **P(A∩B) ≥ p**, where **0 < p < 1**. This is not a theorem of probability theory. See, e.g., the so called [Lottery Paradox](https://en.wikipedia.org/wiki/Lottery_paradox), which is a result of this fact.

What about the similarities? In general, **1** and **0** in probability theory play the same role as, respectively, **0** and **∞** in ranking theory. Similarly, the operations **×**, **÷** and **+** in probability theory play same role as **+**, **-** and **min** in ranking theory. Using this correspondence, many theorems of probability theory can be translated into theorems of ranking theory. The following table lists some examples.

| Description              | Probability Theory                 |  Ranking Theory                     |
| -------------------------|:----------------------------------:|:-----------------------------------:|
| Falsehood                | **P(∅) = 0**                       | **K(∅) = ∞**                        |
| Truth                    | **P(Ω) = 1**                       | **K(Ω) = 0**                        |
| Complement Rule          | **P(A) + P(A̅) = 1**                | **min(K(A), K(A̅)) = 0**             |
| Multiplication Rule      | **P(A∩B) = P(A)P(B\|A)**           | **K(A∩B) = K(A) + K(B\|A)**         |
| Conditional Independence | **P(A∩B\|C) = P(A\|C)P(B\|C)**     | **K(A∩B\|C) = K(A\|C) + K(B\|C)**   |
| Bayes' Theorem           | **P(A\|B) = P(B\|A)P(A) / P(B)**   | **K(A\|B) = K(B\|A) + K(A) - K(B)** |

The probabilistic **order of magnitude** interpretation of ranks establishes a more direct correspondence between ranks and probabilities [\[2\]](#references). Let **P** be a probability function over **Ω** such that, for each **w ∈ Ω**, **P(w)** is a polynomial function of one unknown **ε** (e.g., **a**, **bε**, **cε²**). Note that this implies that any **P(A)** or **P(A\|B)** is also a polynomial function of **ε**. We can think of the exponent of the most significant term of such a polynomial as the order of magnitude of the probability, with 0 being the highest, 1 the second-highest, and so on. These orders of magnitude are encoded by the ranking function **K** defined as follows.

<p align="center"><b>K(A)</b> is the lowest <b>n</b> s.t. <b>P(A)/εⁿ</b> is nonzero as <b>ε</b> approaches 0.</p>

Conversely, if we start with a ranking function **K**, we can always construct a probability function **P** that satisfies the condition above. 

## References

[1] <a name="ref-survey"></a>["A Survery of Ranking Theory"](http://www.uni-konstanz.de/philosophie/files/57_spohn_a_survey_of_1e6d43.pdf) (Spohn. Degrees of belief. Springer Netherlands, 2009. 185-228).

[2] <a name="ref-gp"></a>["Qualitative probabilities for default reasoning, belief revision, and causal modeling"](http://www.sciencedirect.com/science/article/pii/0004370295000909) (Goldszmidt, Pearl. Artificial Intelligence
Volume 84, Issues 1–2, July 1996, Pages 57-112).
