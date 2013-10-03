# scala macro sample

Contained is a sample sbt project demonstrating how to set up a project with macros.

This code uses Scala 2.11.0-M5; To run it, you'll need sbt 0.13.0 or later.

To run:

    sbt run

Macros sub-project is compiled automatically; example code is recompiled if macros project changes.

# Some tips

`showRaw` is your friend! Use it to experiment with various expressions and see the ASTs they create.

    scala> import scala.reflect.runtime.universe._
    scala> println(showRaw(reify { List('a, 'b, 'c) }))

Using `println(showRaw(myExpr))` in your macros can help with debugging.

# Read

- Read about compiler phases: http://docs.scala-lang.org/overviews/macros/paradise.html
- Quasiquotes: http://docs.scala-lang.org/overviews/macros/quasiquotes.html
- All of Google: http://google.com

# Major Award Challenge!

Without searching Google for an existing implementation, implement a swap macro that works for the following example:

    object Main extends App {
      var jimState = "UT"
      var billState = "CA"

      def runTest(): Unit = {
        var jimDollars = 10;
        var billDollars = 20;
        println("jim = %d; bill = %d" format (jimDollars, billDollars))
        swap(jimDollars, billDollars)
        println("jim = %d; bill = %d" format (jimDollars, billDollars))

        println("jim = %s; bill = %s" format (jimState, billState))
        swap( this.jimState, this.billState)
        println("jim = %s; bill = %s" format (jimState, billState))

        val a = Array(1,2,3)
        val b = Array(4,5,6)
        println("a = %s; b = %s" format (a.mkString(", "), b.mkString(", ")))
        swap(a(0), b(0))
        println("a = %s; b = %s" format (a.mkString(", "), b.mkString(", ")))
      }
      runTest()
    }

Write it first using straight-up AST generation. The try writing it using Quasiquotes. For a bonus, see how much reify / splice can help (hint: not much).
