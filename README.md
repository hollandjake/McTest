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

More information can be found in [the Wiki](https://github.com/hollandjake/McTest/wiki).
