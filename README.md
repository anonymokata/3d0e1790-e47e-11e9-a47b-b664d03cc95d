# Word Search Kata

## Run tests from the command line

*TBD - get tests working in IDEA first*

## Rationale

I chose this kata because it facilitated my exploration of a new approach: 
driving the development of code through tests that reflect a DDD 
perspective. In the course of this kata, I have had to deal with conflicts 
between classic TDD approaches and the necessity of domain analysis.

The word search kata is not exactly a true-to-life task. In the absence of a 
customer to hold a conversation with, I have had to play the customer role 
myself. 

(Question about the "User Stories": is the "Puzzle Solver" supposed 
to be the user? If so, it's strange, because the user is not solving the 
puzzle. If not, this is a personification of a software object that I haven't
 come across before in user stories.)

I have tried to write Java code using clean code techniques derived from
 functional programming. For example, I have used a Result monad I developed 
 to eliminate any (re)throwing of exceptions, a feature of Java its creators 
 have agreed was a mistake.
 
### Straight vs. Kinky 
While examining the sample puzzle, I came across two  "kinky" instances of 
"SULU":

    (13,4) (12, 4) (12, 5) (13, 5)

    (13, 4) (13, 5) (12, 5) (12, 4)
    
In the Solver algorithm as implemented, those two would be found. They are not 
part of the expected result. To satisfy the straightness requirement, what 
amounts to a 
feature toggle will be implemented to filter them out. I feel this is 
justified given the general domain of puzzles: we want them to be hard. In a 
real world puzzle environment, we might offer "extra credit" for finding the 
"twisty" instances. (Of course, this kata is not "realistic" in that it 
doesn't generate a puzzle for humans to solve: it solves the puzzle.)

The alternative - changing the algorithm to enforce finding "straight" 
instances only, would make this option impossible. 

The "toggle" is actually a separate Solver method named 
**findStraightInstancesOnly**.

Having implemented a test of this filtering method on
 the full 15 x 15 puzzle, which finds only the seven expected instances and 
 runs in less than 30 milliseconds, I would expect the filtering approach to 
 be acceptable. 

### DBTDD (Domain-Based TDD)

BDD format: test class and method names form complete explanatory sentences.

Not "unit" tests: DBTDD tests are organized around behavior, not classes.

#### Ethnological Domain Modeling
The natives' domain models need not be "correct" or in the most efficient form. 
They only need to work in a reasonably sustainable way. The developers' domain 
model needs to be tight. If you prefer, you can consider this the distinction
 between the "problem domain" and the "solution domain".

#### Conflict with classical baby-step TDD

#Current commit/release

