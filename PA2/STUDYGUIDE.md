// DISCLAIMER: CREATED BY ARIADNA SHAMRAEVA FOR SELF-STUDY PURPOSES ONLY. NOBODY HAS PERMISSION TO COPY AND REUSE ANY MATERIALS IN THE CURRENT REPOSITORY. COPYING ANY PARTS OF THIS ASSIGNMENT IS CONSIDERED PLAGIARISM AT CSU AND OTHER INSTITUTIONS. VIOLATORS WILL BE RESPONSIBLE IN FULL AND FACE CONSEQUENCES SPECIFIED BY THE INSTITUTION.  



## Understanding the Java Program
**Objective**

The program calculates the mean and standard deviation of ratings for a list of songs. It takes two input files (song titles and ratings) and writes the results to an output file.

**Key Concepts**

**File I/O:** Uses `Scanner` to read and `PrintWriter` to write files, demonstrating handling of text files in Java.
**Exception Handling:** Incorporates try-catch blocks to manage exceptions, such as `FileNotFoundException`, and validate input data.
**Data Structures:** Utilizes `List` and `ArrayList` to store and manipulate data dynamically.
**Statistical Calculations:** Implements formulas to calculate mean and standard deviation, essential for data analysis.

**Core Functions**

**`readSongTitles(String fileName)`:** Reads song titles from a file, ensuring non-empty entries.
**`readSongRatings(String fileName, int expectedSize)`:** Reads ratings, validates them (1-5), and matches the number of titles.
**`writeSongStatistics(List<String>, List<List<Integer>>, String)`:** Calculates statistics and writes them to an output file, illustrating data processing and output generation.


## Writing Effective JUnit Tests

**Setup**

JUnit tests require a structured approach to ensure comprehensive coverage and reliability.

@BeforeEach: Prepares the environment for each test, such as setting up necessary files.
@AfterEach: Cleans up after tests, though `@TempDir` in JUnit 5 automatically handles temporary files.
Test Cases

**Input Validation:** Tests verify that the program correctly handles empty files, invalid ratings, and mismatches between song titles and ratings.
**Calculation Accuracy:** Ensures that mean and standard deviation are calculated correctly for given inputs.
**Error Handling:** Confirms that the program reports errors as expected for various faulty inputs.

**Annotations**

`@Test`: Marks a method as a test case.
`@TempDir`: Creates a temporary directory that's removed after tests, ideal for file operations.


## Managing Projects with `Gradle`
**Gradle Basics**

`Gradle` automates building, testing, and running Java projects, simplifying dependency management and project configuration.

** Key Gradle Commands**

`gradle build`: Compiles the project and runs tests.
`gradle test`: Executes all JUnit tests, generating a report.
`gradle run`: Runs the Java application with provided arguments `(--args)`.

**`build.gradle` Configuration**

**Plugins:** Specifies `java` and `application` for Java applications.
**Dependencies:** Includes JUnit Jupiter for testing.
**Main Class Name:** Identifies the entry point for the application.

## Study Tips

* Practice File I/O: Experiment with reading and writing different file formats.
* Understand Statistics: Review the formulas for mean and standard deviation to grasp their application.
* Experiment with JUnit: Write additional tests to cover edge cases and improve your testing skills.
* Explore Gradle: Try adding more dependencies and customizing the build script.

##Conclusion

This guide covers the development of a Java program for calculating song rating statistics, including setting up JUnit tests and managing the project with Gradle. Each section aims to build a solid foundation in Java programming, testing practices, and project management. Engage with each component actively, write your own variations of the program, and explore additional functionalities to deepen your understanding and proficiency.
