The `P1.java` file contains a Java program designed to perform calculations on numerical data read from a file. Here's an overview of its structure and functionality:

## Overview

**Imports:** The program uses classes from the `java.io` package for file handling and the `java.util` package for reading from the file.
**Main Class and Method:** The main class is named `P1`, and it contains the main method and an additional method `calculations`.
**Command-Line Argument Handling:** The program expects the name of an input file as a command-line argument. If no arguments are provided, it outputs an error message and terminates.
**File Reading and Processing:** The `calculations` method reads the input file and processes its contents. The expected file content is numerical data, potentially spread across multiple lines.

## Key Functionalities

**Error Handling for Input:** Checks for the absence of command-line arguments and prints an error message if none are found.

**File Processing:**
* Opens the specified file for reading.
* Reads the file line by line, splitting each line into numbers based on whitespace.
* Converts each number from a string to a double and performs calculations.
* 
**Calculations:**
* **Count:** Tracks the number of numeric values processed.
* **Sum:** Calculates the total sum of the numeric values.
* **Sum of Squares:** Calculates the sum of the squares of the numeric values.
These calculations are used to compute the **mean** and **standard deviation** of the data. Bessel's correction is applied when calculating the variance for more than one data point, which is crucial for an unbiased estimate of the population variance from a sample.

**Error Handling for Data:**
* If non-numeric data is encountered, an error message is printed, and the program terminates.
* If the file is empty or contains no valid numeric data, an error message is printed.

**Output:**
The program outputs the mean and the standard deviation of the input data. If there is only one data point, the standard deviation is undefined and accordingly marked as `"UNDEFINED"`.

**File Not Found Handling:**
Catches and handles the `FileNotFoundException`, printing an appropriate error message.

## Study Points
* Understand the use of `Scanner` for reading file content and parsing input.
* Learn about error handling in Java, including checking for command-line arguments, handling `FileNotFoundException`, and dealing with invalid input data.
* Get familiar with basic statistical calculations in a programming context, especially the computation of mean, variance, and standard deviation.
* Recognize the importance of Bessel's correction in variance and standard deviation calculation for sample data.


********************
The `TestP1.java` file contains JUnit tests for the `P1` Java program. These tests are designed to verify the program's behavior under different conditions, particularly focusing on error handling and input validation. Here's a breakdown of what's covered in the tests:
********************

## Overview

**Imports:** The test file utilizes JUnit's `assertThrows` method for exception handling tests, and the `Test` annotation to denote test methods. It also imports necessary Java I/O classes.
**Test Class:** The test class is named `TestP1`, matching the convention of prefixing the class under test with `Test`.

## Key Tests

**File Not Found:**
This test checks the program's response when a non-existent file is specified. It verifies that a `FileNotFoundException` is thrown, ensuring the program correctly handles and reports missing files.
**Empty File Handling:**
This test simulates an empty file as input and checks whether the program throws a RuntimeException. This is aimed at validating the program's ability to detect and report empty input sources. However, the implementation seems to misalign with the main program's behavior, which does not explicitly throw a `RuntimeException` for empty files but prints an error message and terminates. The test may not accurately reflect the program's designed behavior without adjustments or a specific implementation in `P1.java` to throw such an exception.
**Non-Numeric Data Handling:**
The test for non-numeric data aims to ensure that the program properly detects and handles input files containing invalid data (i.e., non-numeric strings). It expects a `NumberFormatException` to be thrown when the main method encounters non-numeric data, aligning with the program's error handling for such cases.


## Study Points

*Testing Strategy:**  Understand how JUnit tests validate various aspects of a program, particularly error handling and input validation. These tests focus on edge cases and exceptional conditions, which are critical for robust software development.
**Exception Handling Tests:** Learn how to use `assertThrows` to verify that a program throws the expected exceptions under specific conditions. This is crucial for testing the program's robustness and its ability to handle error scenarios gracefully.
**Automated Testing Practices:** Recognize the importance of covering different input scenarios, including valid, invalid, and edge cases, to ensure comprehensive testing. Automated tests like these are essential for verifying that changes to the codebase do not introduce regressions.


## Recommendations

* For a more in-depth study, consider exploring how to set up and run JUnit tests in your development environment. This includes understanding annotations like `@Test` and the setup of test cases.
* Investigate further into exception handling in Java, particularly how exceptions are used to manage errors and exceptional conditions in programs.
* Review the handling of command-line arguments and file I/O operations in Java, as these are fundamental concepts demonstrated in the `P1` program and its tests.
