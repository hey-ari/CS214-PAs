[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/uvbgTxUs)
[![Open in Codespaces](https://classroom.github.com/assets/launch-codespace-7f7980b617ed060a017424585567c406b6ee15c891e84e1186181d67ecf80aa0.svg)](https://classroom.github.com/open-in-codespaces?assignment_repo_id=13610375)
# PA1 - Computing Means and Standard Deviations

## Due

See the Canvas page. There is a 7 day grace period.

## Motivation

The primary goal of this assignment is to get you started writing code
using the tools and processes for CS214. This assignment gives you a
task whose logic is pretty simple, in order to give you a chance to
get over those hurdles while still writing a piece of code that will
help you in future assignments.

A secondary goal is to give you a gentle introduction to the more 
demanding expectations in CS214. For starters, you should *notice*
that the assignment specifies **what** to do, not **how** to do it.
We will not tell you what classes to define or what methods they 
should have (although we may give hints). You are expected to design 
your own programs. Program design is part of the assignment. Also, 
there may be times when part of the assignment specification
is ambiguous. In such cases, it is incumbent on you to ask the 
instructor for clarification. The instructor is playing the role of 
client in these exercises,and what the client intends is correct. If 
you are not sure of exactly what the client wants, you need to find 
out. *“But I thought it was supposed to …”* is not an excuse.

Along the same lines, testing your program is your responsibility. We 
will not hand out complete test files. You need to come up with test cases, 
make the appropriate test files, and test your code. Indeed, designing 
test cases is an **important** part of program design. Testing for 
errors is your responsibility. The assignment below is to compute the 
mean and standard deviations of numbers in a file. But here is a hint: 
**never** trust a user or a file. What if the file has letters in it? 
Or punctuation? Or nothing at all? It is part of this and all following
assignments to test for error cases and handle them cleanly.

An empty testing file is provided to you [here](src/test/java/TestP1.java).
You can run the tests in it by using `gradle test`.

## Task

Your program will take a single file name as an argument, and write two numbers
(separated by one space) to `System.out`. The first number should be the mean
of the numbers in the file (i.e. the sum of the numbers divided by the count).
The second number output by your program should be the standard deviation of
the numbers in the file. The mean and standard deviation of a set of numbers `a` are defined
as:

**Mean**

```math 
\overline{a} = \sum_{i=1}^n a_i / n
```

**Standard Deviation with Bessel's correction**

```math
  stddev(a) = \sqrt {\left( \sum_{i=1}^n \left(a_i - \overline{a} \right)^2 / n - 1\right)}
```

The format of the file is flexible. It may contain many numbers on one line, or
many lines with one (or a few) numbers each. It may contain blank lines.
However, it should only contain numbers and white space. If the file contains
anything other than numbers, your program should write out a meaningful error message
that starts with the string `Error` and not print a mean or standard deviation. Otherwise,
your program should write the mean value to `System.out`, followed by a space
and the standard deviation. If the mean and/or standard deviation is undefined,
you should write the word `UNDEFINED` in the place of the corresponding number.


## Submitting Your Work

You GitHub repo should contain all the `.java` files you have created. You
will need to push your files from wherever you are doing your work to the repo.

## Grading Your Homework

To grade your assignment, we will access your GitHub repo and compile / run using:

```
gradle run -q --args='input_files/testFileName'
```

You **must** have a `Java` file `P1.java` in your `src/main/java/` directory.
If your program does not compile, you will be awarded no points for the
assignment. We use a `-Werror` flag that treats **all** warnings as errors and
will cause a compile failure. Assuming your program compiles, the remaining 80 points will be
determined by how your program performs on test cases, including test cases
with errors in the input file. When the input file does not contain errors, you
will receive full credit for a test if both the mean and standard
deviation are correct and no other output is written. If the input file does
contain errors, you will receive full credit if your program produces a
meaningful error message beginning with `Error`, and prints nothing else to the screen.

The following is the simple starter code provided to you [here](src/main/java/P1.java):

````
public class P1 {

  public static void main (String[] args) {
    // you may add code here or refer to the main of another class
    // YourClass.main(args);
  }
}
````

## Hints

• **Never** trust a file or a user. You can be sure that we will include test cases
  with illegally formatted input, illegal arguments, etc.

• Notice the “-Werror” compiler flag above. It tells the compiler to generate all
  possible warnings. and treat them as errors, rather that just warnings.

• Test your program on the department machines in CSB120. That is where we will
  evaluate it. Your grade depends on how your program performs on those
  machines.

• Helpful Java Docs:
- https://docs.oracle.com/javase/8/docs/api/java/io/FileNotFoundException.html
- https://docs.oracle.com/javase/8/docs/api/java/util/Scanner.html
- https://docs.oracle.com/javase/8/docs/api/java/io/File.html

## Policies

All work you submit must be your own. You may not submit code written by a
peer, a former student, or anyone else. You may not copy or buy code from the
web. The department academic integrity policies apply. You may not submit your
program late. To receive credit, it must be submitted by 11:59pm on Monday.
There is a 7 day grace period. The exception is an unforeseeable emergency,
for example a medical crisis or a death in the immediate family. If an
unforeseeable emergency arises, talk to the instructor.