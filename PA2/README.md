[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/Ie-WcIRw)
[![Open in Codespaces](https://classroom.github.com/assets/launch-codespace-7f7980b617ed060a017424585567c406b6ee15c891e84e1186181d67ecf80aa0.svg)](https://classroom.github.com/open-in-codespaces?assignment_repo_id=13722901)
# PA2: How popular is a Song?

## Due: See Canvas

## Motivation

In this assignment, you will extend Assignment #1 to do something useful,
namely determine how popular a song is (or isn’t). You may reuse as much of
your own code from Assignment #1 as you want. In fact, reusing your own code
is encouraged. As this course progresses and the assignments get larger,
almost the only way you will be able to complete them within the allotted
time is by reusing parts or all of your previous assignments.

Writing code that can be reused is one of the skills we want you to develop
in this course. If you write object oriented code in which data and its
related methods are encapsulated in objects and every method implements a
clearly defined function, your code will be easy to reuse. If you write
(and keep) code for testing each object, it will be easy to update it and
find bugs when code it reused. If you document the purpose and assumptions
of each method, you will know whether it can be reused as is, or what needs
to be modified. You may have noticed that we do not grade for style or
documentation. We don’t have to. Good style and documentation are
self-rewarding. If your code is clean and documented, it will be easy
to reuse and maintain. If, on the other hand, you write poorly documented
“spaghetti” code, you will struggle in later assignments. So pay attention
to style and documentation for your own sake.[^1]

Note that you may only reuse your own code. Using anyone else’s code (from
this class or elsewhere), even if it is subsequently modified, is a violation
of ethics and the department’s academic integrity policy.

## Task

In Assignment #1, you wrote code to compute the mean and standard deviation
of a set of numbers. In Assignment #2, we put that to use. Your goal is to
evaluate the relative popularity of songs, as measured by user ratings.
Your task is to compute the mean and standard deviation of the ratings for
every song.

Your program will take three arguments: the names of two input files and
one output file.

In particular, the first input file is a text file of song titles,
with one song title per line.
Song titles can contain spaces, and they can contain numbers and punctuation
as well. If `I had $1,000,000` is the name of a song. All songs must have a
name, so a blank line in the file of song titles should be treated as an error.

The second file contains the ratings for each song. It should have the
same number of lines as the first file. Each line contains a set of integer
values between 1 (the user hated the song) and 5 (they loved it). Note that
some listeners rank more songs than others, so some lines will have more
values than others.

Your task is to write an output file (the third parameter) with the same number
of lines as the input files. Each line should contain the name of a song,
followed by the mean rank of that song and the standard deviation of the
ranks of the song. As in Assignment #1, if a mean or standard deviation is
undefined, you should print the word `UNDEFINED` for its value. 

As always, **never** trust a user or a file. There are many possible errors
that could arise. For example, one file might have more lines than another,
or the ranking file might contain values other than integers from 1 to 5.
In the event of an error in the input files (or with the output file),
your program should write an error message to `System.err`. The error
message should describe the error and, if appropriate, report the filename
and line number where the error occurred. After printing the error,
your program should return. If the data files are correct and no error occurs,
your program should not print anything to `System.out`.

## Grading Your Homework

Your program will be graded by running the `PA2.main()` method. As always,
if your program fails to compile you will be awarded no points for the
assignment. Assuming your program compiles, your grade will be determined by
your programs performs on test cases.

Therefore, you **must** have a `Java` file `PA2.java` in your `src/main/java/` directory. We will continue to use gradle in this assignment.
The following is the simple starter code provided to you [here](PA2.java):

````
public class PA2 {

  public static void main (String[] args) {
    // you may add code here or refer to the main of another class
    // YourClass.main(args);
  }
}
````
Your input and output files can technically be called and stored anywhere on your system but we have provided `input_files` and `output_files` directories for the sake of convenience and organization.

## Hints

 - There are lots of error cases. Be sure and test for them.

## Policies

All work you submit must be your own. You may not submit code written by a
peer, a former student, or anyone else. You may not copy or buy code from the
web. The department academic integrity policies apply.

You may not submit your program late. To receive credit, it must be submitted
by the due date (with a 7 day extension). The exception is an unforeseeable
emergency, for example a medical crisis or a death in the immediate family.
If an unforeseeable emergency arises, talk to the instructor


[^1] “Experience is the hardest teacher, because it gives the test first and
     the lesson afterwards” – attributed to Vernon Law.
