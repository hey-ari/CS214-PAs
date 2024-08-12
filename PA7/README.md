[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/0H6J8pZX)
# CS214 Spring2024 - PA7 - Optimizing K-Means Clustering

## Due: April 29th

## Motivation

All semester we have been talking about efficiency and clean code. Now it is time to put them into practice. The task
for assignment #7 is the same as for assignment #6, but the grading scheme is different. This
time, we grade on speed (or more exactly, we grade correct programs on speed. Since you have
completed assignment #6 and had time to fix it based on our test samples, your program should
now be correct.) We will also grade on code quality (using pmd/cpd rules).


## Task

The task is the same as last week. In fact, if your Assignment #6 works, you could just resubmit
it. But this time, it will be graded on speed and code quality. In particular, we will test it on a test file with 5,000
songs and 200 users. Your program must produce a correct answer to score any points. Among
those submissions with correct answers, the fastest gets the best grade, followed by the second
fastest and so on.

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
* Use JProfiler to identify bottlenecks in your code and remove them.
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

