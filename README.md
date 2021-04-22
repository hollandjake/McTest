![](https://github.com/hollandjake/McTest/actions/workflows/main.yml/badge.svg)
[![codecov](https://codecov.io/gh/hollandjake/McTest/branch/master/graph/badge.svg?token=xtiWkmR3P9)](https://codecov.io/gh/hollandjake/McTest)

<br />
<p align="center">
  <a href="https://github.com/hollandjake/COM3529">
    <img src=".github/images/logo.png" alt="Logo" width="80" height="80">
  </a>

  <h3 align="center">McTest</h3>

  <p align="center">
    An awesome automatic test generation program
  </p>
</p>

## About McTest
McTest takes in your java file and generates automatic JUnit tests. McTest does this by analysing your method's structure of conditions and applies an evolutionary 
algorithm to obtain full condition coverage, the result is a detailed coverage report, supplimented by a new Java project of JUnit tests 
that can be compiled separately and run. McTest makes use of threading to achieve optimum speeds as well as other maven dependencies which allow McTest to function better.

### McTest's Automation
All a human tester would need to do is run our jar file on the class to be tested, as McTest is fully automatic and contains the following features:
- [x] Extracts and analyses all logical conditions in each method with JavaParser, e.g. conditions inside IF, WHILE and FOR
- [x] The method's conditions are automatically parsed and intrumented
- [x] Test data generation is fully automated with use of an evolutionary algorithm
- [x] The test suite is written into JUnit Java code that can be compiled separately and run
- [x] Automatic coverage critia test requirement generation
- [x] Coverage level is automatically computed and reported


<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-mctest">About McTest</a>
      <ul>
        <li><a href="#mctests-automation">Automation</a></li>
      </ul
    </li>
    <li>
      <a href="#the-team">The Team</a>
    </li>
    <li>
      <a href="#how-to-install-and-run-mctest">How to install and run McTest</a>
      <ul>
        <li><a href="#installation">Installation</a></li>
        <li><a href="#usage">Usage</a></li>
        <li><a href="#maven-dependencies">Maven Dependencies </a></li>
      </ul>
    </li>
    <li>
      <a href="#worked-examples-of-mctest">Worked Examples of McTest</a>
      <ul>
        <li><a href="#trianglejava">Triangle.java</a></li>
        <li><a href="#bmicalculatorjava">BMICalculator.java</a></li>
      </ul>
    </li>
  </ol>
</details>

## The Team
* Jake Holland (jholland2@sheffield.ac.uk), work contributed:
    * Evolutionary algorithm
    * Handling of different input types
    * Automatically parsing and intrumenting methods
    * Command arguments to run on McTest.jar
    * Coverage level logic
    * JUnit tests for McTest
* Thomas Boyd (tlboyd1@sheffield.ac.uk), work contributed:
    * Coverage level computed and reported in a PDF
    * Automatic JUnit tests
    * Test case minimisation
    * Mutation and crossover
    * Assisted with automatically parsing and intrumenting methods with JavaParser
    * README.md

## How to install and run McTest
Here you will find information on getting started with McTest

### Installation 
On our [releases page](https://github.com/hollandjake/COM3529/releases/) you can download the TestGenerator.jar file, 
alternatively you can download the project's source code and run the following to generate the TestGenerator.jar which will then be located inside the target folder.
```sh
mvn package
  ```

### Usage 
Once you have the McTest.jar you can run it with the following program arguments
* -g (Path to Java class file you want to run McTest on)
* -o (Path to the directory you want to output the JUnit tests to)

For example
```shell
java -jar McTest.jar -g "Path to class" -o "Path to JUnit output directory"
  ```

You can also modify any of the config arguments by supplying them to the Java system properties e.g.
```shell
-DGenetics.MaxIterations=10000
```

To enable debug outputs you can pass the following system property
```shell
-Dorg.slf4j.simpleLogger.defaultLogLevel=debug
```

### Available properties
| Property | Default | Description |
| -------- | ------- | ----------- |
| Genetics.MaxIterations | 1000 | Total iterations to try before aborting |
| Genetics.TargetFitness | 0 | Target value for the fitness algorithm to target (0 meaning covering all conditions) |
| Genetics.Initial.NumTests | 2 | Number of random tests the system should start with |
| Genetics.Initial.InputDistribution | 100 | How varied the random inputs should be |
| Genetics.PopulationSize | 100 | Size of the population each iteration |
| Genetics.TopN | 10 | Number of individuals to be used as parents for the next iteration |
| Genetics.CrossoverSelectionProbability | 0.5 | Chance to crossover two parents during breeding |
| Genetics.MutationProbability | 0.1 | Change for a test to be mutated or created |
| ConditionCoverage.K | 1 | Value of the Offset |

### Maven Dependencies 
* [JavaParser](https://github.com/javaparser/javaparser "JavaParser's Github")
* [JUnit4](https://github.com/junit-team/junit4 "JUnit4's Github")
* [Project Lombok](https://projectlombok.org/ "Project Lombok's Website")
* [Reflections](https://github.com/ronmamo/reflections "Reflections's Github")
* [Apache Commons](https://github.com/apache/commons-lang "Apache Commons Github")
* [OpenHFT Compiler](https://mvnrepository.com/artifact/net.openhft/compiler "OpenHFT's Website")
* [Simple Logging Facade](http://www.slf4j.org/ "Simple Logging Facade's Website")

## Worked Examples of McTest
Supplied in the source code at `src/main/resources` are java files Triangle.java and BMICalculator.java which both can demonstrate McTest in action.

You can run `Triangle.java` and `BMICalculator.java` by executing the tests `shouldGenerateTestsForTriangle()` and `shouldGenerateTestsForBMICalculator()` respectively, 
these tests are located in `src/test/java/com/github/hollandjake/com3529/test/End2EndTest.java`

After executing the tests, the console will let you know where the project is of the JUnit tests which were generated. The new project for the tests will look like the 
following for the `Triangle.java` and `BMICalculator.java` respectively. There will also be a coverage report PDF outlining the conditional coverage of the test suite located inside the project root.

### Triangle.java
![Triangle.java tests](.github/images/Triangletests.png)
### BMICalculator.java
![BMICalculator.java tests](.github/images/BMItests.png)

There is also a string test file called `StringTest.java` which attempts to match an input string with the constant string "Yummy Cheese". This will use string generation to try and generate the two conditions for the test.
Note that you may need to increase the number of iterations by providing the java system property `-DGenetics.MaxIterations=<some_number>`;
