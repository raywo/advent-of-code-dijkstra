# Advent of Code – Find the most risk-free path

In the 2021’s edition of [Advent of Code on day 15](https://adventofcode.com/2021/day/15) 
there was the task given to implement an algorithm which was able to compute 
the most risk-free path through a given cavern. The risk matrix for the cavern 
given to me looks like so:

```
1163751742
1381373672
2136511328
3694931569
7463417111
1319128137
1359912421
3125421639
1293138521
2311944581
```

So it is a 10x10 matrix where the digit in each cell correspondents to a risk 
entering the cell. Higher digits means higher risk. The task was to navigate 
through the cell on a path with the lowest overall risk. No diagonal moves where 
allowed.

## Approach on solving the puzzle

My first idea was to use some sort of already known and established algorithm 
for that. I myself would never be able to do that on my own. At least not in 
a somewhat acceptable time. After considering the [Floyd-Warshall algorithm](https://en.wikipedia.org/wiki/Floyd–Warshall_algorithm) 
I decided to actually use the [Dijkstra’s algorithm](https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm) 
for finding the shortest path in a graph.

In the given matrix the risk can be understood as distances between nodes. So 
we can immediately use Dijkstra’s algorithm to find the path with the lowest 
risk.

The algorithm itself actually was written by AI as well as the corresponding 
data structure (`Edge` and `Node` class). Since my goal was not only to solve 
the puzzle but also write nice and maintainable (read over-engineered) code I 
created the `Graph` class which accepts a matrix in the above form on creation 
and constructs the graph suitable for Dijkstra’s algorithm. 

Later on in the puzzle you are presented with bigger matrices. So it was clear 
to me that I also need something which can read those matrices from a file and 
provide them to a user. That’s why the `DataProvider` class came into existence. 
This class is also responsible for creating the expanded matrix which is later 
needed in the puzzle.

## Have fun with…

…the (probably) most over-engineered advent of code solution for [this puzzle](https://adventofcode.com/2021/day/15).
