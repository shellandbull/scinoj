# scinoj - btc & optimal stopping theory


### Backlog

- I started a draft of the study purely out of my own memory
- I got blocked and wrote down some questions
- I set up `gptel` and the context for it
- I came back and started to fiddle around with the prompt
- I asked help from the community to continue the study
- I then did the study
- I demostrated optimal stopping theory

### Questions to the LLM

- How do I convert a unix timestamp to a `ZonedDateTime`
- How can I pick a single cell and run a function against it using tablecloth
- How can I programmatically feed a stacktrace and a code snippet and maybe some state as context to a question?
- How to check which values in a column are different?
- How do I map over a tech.v3.dataset.column


### Showing how optimal theory works

To demonstrate how optimal theory works one can plot an scenario to construct evidence. 

This is how fields like applied probability & decision theory draw out conclusions.


A famous problem for optimal stopping theory is the [secretary problem](https://en.wikipedia.org/wiki/Secretary_problem). I will now extrapolate that same problem onto the domain of buying bitcoin.

```
The basic form of the problem is the following,

Imagine a buyer who wants to buy bitcoin in hopes of making a profit.

He has to choose a moment where to buy bitcoin out of N moments in time.

The moments in time only happen once and never again. When the time is up the buyer
has to choose to buy at that price. Once the time has passed, the buyer cant recall
that point in time, as time travel is impossible.

At the time of considering a buy the buyer gains information sufficient to rank that
moment in time(and price) against the previous moments in time(and previous prices).

However, the buyer is unaware of how the price will change in the future.

The question is about optimal stopping strategy(best stopping rule) to maximise the probability of selecting the best point in time to buy.

The difficulty comes from making a decision immediately.
```


