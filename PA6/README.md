[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/PoVzinkK)
# CS214 Spring2024 - PA6 - K-Means Clustering

## Due: April 15th

## Motivation

In assignment #4, you measured similarity among users, and in assignment #5 you predicted missing values. In this assignment, the goal is to find clusters of similar songs. Given an integer K, your task is to group the songs into K clusters, such that songs in clusters are as much alike as possible. The algorithm you will use to do this is called K-Means clustering.
This is a challenging assignment. We recommend that you don’t put it off until the last week.


## Task

You will write a program that takes five arguments. The first three are the same as in the previous assignments: 
* argv[1] contains the name of a file containing song names
* argv[2] contains the name of a file with rankings
* argv[3] is the name of the output file
* argv[4] is K (an integer), the number of clusters
* argv[5] is N (an integer), the cluster of interest

As in previous assignments, you should read the input files, and remove any data associated with uncooperative users or songs that do not have at least one non-zero ranking. You should then compute user-normalized rankings and predict values for all missing rankings. Unlike assignment #5, you should not “un-normalize” predicted values; leave all values normalized.

Now your task is to cluster songs. In order to do this, you need two new primitive operations. The first is the ability to measure the distance between two ranking arrays for songs. Let $A = (a_1, a_2, \ldots, a_n)$
 be the ranks assigned to a given song (or cluster). Note that the values are normalized by users, and some of the values may be predicted while others were assigned. Let $B = (b_1, b_2, \ldots, b_n)$ be the ranking array of another song. Then the Euclidean distance between A and B is:
```math
d(A, B) = \sqrt{\sum_{i=1}^{n} (a_i - b_i)^2}
```
The distance between two ranking arrays as defined above is a scalar. 

The second operator you will need is to compute an average ranking array from a set of ranking arrays. Let $X = [A, B, \ldots]$ be a set of ranking arrays, where each array is of length $U$ ($U$ is the number of cooperative users). Then the average of $X$ is an array of length $U$ whose first element is the average of all the first elements in $X$, whose second element is the average of all the second elements of $X$, and so on.
```math
A = (a_1,a_2,a_3)
```
```math
B = (b_1,b_2,b_3)
```
```math
...
```
```math
AvgX = ((a_1+b_1+...)/X.length,(a_2+b_2+...)/X.length, (a_3+b_3+...)/X.length)
```

Once you have operators for distances between arrays and averages of arrays, you are ready to implement K-Means. K-Means is an iterative algorithm, meaning that it begins with an approximate solution and on each iteration it makes the approximate solution better. You can run it for as many iterations as you want. In this assignment, you are expected to run K-Means for exactly 10 iterations.

A solution to K-Means is a set of *cluster centers*, where a cluster center is the average ranking array for a set of related songs. You will initialize K-Means by selecting the ranking array of the first song in the input files as the first cluster center, the ranking array of the second song as the second cluster center, and so on up to K cluster centers. The ranking array for each song is the normalized ranking array after including the predicted normalized rating.

Having initialized K cluster center arrays, you will now improve them by iterating 10 times over the following sequence of steps:

* For every song,
  * Compute the distance between the song’s ranking array and each of the K cluster
centers.
  * Assign the song to the closest cluster center (i.e. the cluster center with the
smallest Euclidean distance to the song). Ties will be exceedingly rare, but if
they happen, assign the song to the first cluster center with the smallest distance.
  * For every cluster center, update the cluster center array to be the average of the ranking
array of songs assigned to it.

When you have executed the sequence of steps above 10 times, the cluster centers will represent the average rankings for sets of closely related songs, and every song will be assigned to its closest cluster center. Now you will write to the output file the names of all the songs in the Nth cluster, where N is the number in argv[5] (N must be a positive integer between 1 and K). Write one song name per line.

## Submitting your work

Your submission must be in the main branch of your `GitHub` repository.

## Grading your home work

Run your code with the following command:

```
gradle run -q --args="'input_files/songFileName' 'input_files/ratingsFileName' 'output_files/songOut' K N"
```
We will pull your code from your repository. It must contain, at a minimum two
`Java` files. One **must** be named `CS_214_Project.java`. We will run your
program by starting it with that class name. The second file **must** be named
`CS_214_Project_Tester.java`. This will be used to run your `JUnit` tests.
All future assignments **must** contain these two files, although they may
contain different code as needed by the particulars of that assignment.

~~~~
public class CS_214_Project {
  public static void main (String[] args) {
    // your code goes here
    // You may just call the main of some other class you wrote.
  }
}
~~~~

~~~~
import org.junit.Test;

// other imports as needed

public CS_214_Project_Tester {
  @Test
  public void test1_OrWhateverNameMakesSense() {
    // code for your first test
  }

  @Test
  public void test2_OrWhateverNameMakesSense() {
    // code for your second test
  }
}
~~~~

## Hints
* There are new error cases. K must be an integer greater than zero. N must be an integer greater than zero and less than or equal to K.
* You will not be graded on speed – until PA7 (optimize as you go).
* This README is a guide to help you get started. Additional information will be provided on MS Teams upon request at the discretion of the DevOps Team.
* Debugging option: https://www.cs.colostate.edu/~fsieker/misc/debug/DEBUG.html

## Note
In real K-Means clustering, the cluster centers are initialized by picking K samples at random. However, this would defeat our automated grading mechanism, so for this assignment you will pick the first K songs as the first K cluster centers.

## Policies

All work you submit must be your own. You may not submit code written by a
peer, a former student, or anyone else. You may not copy or buy code from the
web. The department academic integrity policies apply.

You may not submit your program late. To receive credit, it must be submitted
by the due date (with a 7 day extension). The exception is an unforeseeable
emergency, for example a medical crisis or a death in the immediate family.
If an unforeseeable emergency arises, talk to the instructor.

