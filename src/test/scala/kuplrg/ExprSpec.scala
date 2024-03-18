package kuplrg

import org.scalatest.flatspec.AnyFlatSpec

class ExprSpec extends AnyFlatSpec {
  import Expr.*

  // 8
  val expr1: Expr = Num(8)

  // x + 1
  val expr2: Expr = Add(Var("x"), Num(1))

  // 2 * (x + y)
  val expr3: Expr = Mul(Num(2), Add(Var("x"), Var("y")))

  // assignments: x -> 3 and y -> 5
  val ass = Map("x" -> 3, "y" -> 5)
  val default = 0

  "`has`" should "returns the set of variables in the expression" in {
    assert(expr1.has("x") == false)
    assert(expr2.has("x") == true)
    assert(expr3.has("y") == true)
  }

  "`vars`" should "returns the set of variables in the expression" in {
    assert(expr1.vars == Set())
    assert(expr2.vars == Set("x"))
    assert(expr3.vars == Set("x", "y"))
  }

  "`eval`" should "evaluate to values with assignments and a default value" in {
    assert(expr1.eval(ass, default) == 8)
    assert(expr2.eval(ass, default) == 4)
    assert(expr3.eval(ass, default) == 16)
  }

  "`show`" should "generate a string representation of the expression" in {
    assert(expr1.show == "8")
    assert(expr2.show == "x + 1")
    assert(expr3.show == "2 * (x + y)")
  }
}
