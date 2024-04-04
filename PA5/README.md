[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/NPyjUESa)
# CS214 Spring2024 - PA5 - Prediction

## Due: April 1st

## Motivation

In assignment #4, you measured similarity among users and similarity among
songs. Now we are going to use this information to predict what songs a user
will like or dislike among the songs they haven’t ranked. As you should
remember, user’s ranked songs on a scale from 1-5, but they gave values of 0
to songs they hadn’t heard. Your job is to replace the 0’s with the scores
the users probably would have given, had they heard the song. This type of
prediction might be used, for example, to recommend new songs to a user, or
to select background music for advertisements targeted at a user.

Pedagogically, this assignment tries to add one more stage of processing
to make the process longer. If your code is modular and use unit tests,
this should be easy. If adding these steps is difficult, examine your process.

## Task

The input files for Assignment #5 are the same as in Assignments #3 & #4:

   * argv[1] contains the name of a file containing song names,
   * argv[2] contains the name of a file with rankings.
   * argv[3] is the name of a ranking file which you will write out.
     When you are done, the file in argv[3] will be the same as the file in
     argv[2], with two major difference: (1) data from uncooperative users
     will have been removed, and (2) all the zeros from cooperative users
     in the file in argv[2] will have been replaced by predicted scores in
     argv[3].

As before, you will read in the rankings, eliminate non-cooperative users, and
normalize the remaining scores by user. This time, however, you will scan
through the data and replace unranked entries with predictions. In particular,
if song **_x_** was not ranked by user **_y_**, then you will predict the
ranking as follows: among all the users who ranked song **_x_**,
find the most similar user **_z_** to user **_y_** and adopt their normalized
ranking from that. (Note: this ranking must be a true ranking, not another
prediction.) Then you need to generate a predicted “raw” ranking by taking
the predicted normalized ranking from the most similar user **_z_**,
multiplying it by the standard deviation of user **_y_**, and adding the mean
rank for user **_y_**. The result will not be an integer, so round it to
the nearest integer in the range **_1-5_**.

## Submitting your work

Your submission must be in the main branch of your `GitHub` repository.

## Grading your home work

Run your code with the following command:

```
gradle run -q --args="'input_files/songFileName' 'input_files/ratingsFileName' 'output_files/songOut'"
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
* This README is a guide to help you get started. Additional information will be provided on MS Teams upon request at the discretion of the DevOps Team.
* Debugging option: https://www.cs.colostate.edu/~fsieker/misc/debug/DEBUG.html

## Policies

All work you submit must be your own. You may not submit code written by a
peer, a former student, or anyone else. You may not copy or buy code from the
web. The department academic integrity policies apply.

You may not submit your program late. To receive credit, it must be submitted
by the due date (with a 7 day extension). The exception is an unforeseeable
emergency, for example a medical crisis or a death in the immediate family.
If an unforeseeable emergency arises, talk to the instructor.

