# Scala Example Project

This is a **simple example project** that demonstrates how to use the Scala
programming language to build a simple application.

It includes a simple arithmetic expression
([`Expr`](src/main/scala/kuplrg/Expr.scala)) and a simple tree
([`Tree`](src/main/scala/kuplrg/Tree.scala)) data structure.

## Building the Project

To **build the project**, you will need to have the Scala build tool (SBT)
installed on your system. Once you have SBT installed, you can build the project
by running the following command:

```bash
sbt compile
```

## Running the Project

In general, you can **run the project** by using the `sbt run` command. For example:

```bash
sbt run
```
Then, it prints the following output:

```sbt
[info] running kuplrg.main
Hello, world!
```

In addition, you can **interactively explore the project** with the console
(Scala REPL) by running the following command:

```bash
sbt console
```

Then, it will show the following Scala prompt:

```scala
scala> import kuplrg.{ Expr, Tree }

scala> import Expr.*

scala> val expr: Expr = Mul(Num(2), Add(Var("x"), Var("y")))
val expr: kuplrg.Expr = Mul(Num(2),Add(Var(x),Var(y)))

scala> expr.show
val res0: String = 2 * (x + y)

scala> expr.eval(Map("x" -> 3, "y" -> 5), 0)
val res1: Int = 16

scala>
```


## Generating the Documentation

To **generate the documentation**, you can run the following command:

```bash
sbt doc
```

Then, the documentation will be generated in the `target/scala-3.3.3/api`
directory. Please enter the directory and run the server to see the
documentation using `python3`:

```bash
cd target/scala-3.3.3/api
python3 -m http.server 8080
```

Then, you can open the following URL in your web browser:

```
http://localhost:8080
```


## Testing the Project

To **test the project**, you can run the following command:

```bash
sbt test
```

Then, it will show the following output:

```sbt
[info] TreeSuite:
[info] - The `has` should return if the tree has the value
[info] - The `map` should map the tree with the given function
[info] - The `countLeaves` should count the number of nodes in the tree
[info] - The `sort` should return a new tree with the values sorted
[info] ExprSpec:
[info] `has`
[info] - should returns the set of variables in the expression
[info] `vars`
[info] - should returns the set of variables in the expression
[info] `eval`
[info] - should evaluate to values with assignments and a default value
[info] `show`
[info] - should generate a string representation of the expression
[info] Run completed in 107 milliseconds.
[info] Total number of tests run: 8
[info] Suites: completed 2, aborted 0
[info] Tests: succeeded 8, failed 0, canceled 0, ignored 0, pending 0
[info] All tests passed.
[success] Total time: 0 s, completed Mar 18, 2024 4:05:11 AM
```

If you want to see the defined test cases, please check the
[`ExprSpec`](src/test/scala/kuplrg/ExprSpec.scala) and
[`TreeSuite`](src/test/scala/kuplrg/TreeSuite.scala) files.


## Coverage Report

To **generate the coverage report**, you can run the following command:

```bash
sbt clean coverage test coverageReport
```

Then, the coverage report will be generated in the
`target/scala-3.3.3/scoverage-report/index.html` file. You can open the file in
your web browser to see the coverage report.
