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
Baby step - code is still too complicated.
