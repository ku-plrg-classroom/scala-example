package kuplrg

/** A simple arithmetic expression data structure.
  *
  * An _expression_ is either
  *   1. a _number_ ([[Expr.Num]]) with an integer value,
  *   1. a _variable_ ([[Expr.Var]]) with a variable name,
  *   1. an _addition_ ([[Expr.Add]]) with two sub-expressions, or
  *   1. a _multiplication_ ([[Expr.Mul]]) with two sub-expressions.
  *
  * @since 1.0
  *   (2024-03-18)
  *
  * @example
  *   First Example:
  * {{{
  * // 8
  * val expr1: Expr = Num(8)
  * }}}
  * Second Example:
  * {{{
  * // x + 1
  * val expr2: Expr = Add(Var("x"), Num(1))
  * }}}
  * Third Example:
  * {{{
  * // 2 * (x + y)
  * val expr3: Expr = Mul(Num(2), Add(Var("x"), Var("y")))
  * }}}
  */
enum Expr:
  case Num(value: Int)
  case Var(name: String)
  case Add(lhs: Expr, rhs: Expr)
  case Mul(lhs: Expr, rhs: Expr)

  /** Checks if the expression _has_ a given variable.
    *
    * @since 1.0
    *   (2024-03-18)
    * @param name
    *   the variable name to check
    * @return
    *   `true` if the expression has the variable, `false` otherwise
    *
    * @example
    * {{{
    * Num(8).has("x")                               // false
    *
    * Add(Var("x"), Num(1)).has("x")                // true
    *
    * Mul(Num(2), Add(Var("x"), Var("y"))).has("y") // true
    * }}}
    */
  def has(name: String): Boolean = this match
    case Num(_)    => false
    case Var(x)    => x == name
    case Add(l, r) => l.has(name) || r.has(name)
    case Mul(l, r) => l.has(name) || r.has(name)

  /** Returns the set of variables in the expression.
    *
    * @since 1.0
    *   (2024-03-18)
    * @return
    *   the set of variables in the expression
    *
    * @example
    * {{{
    * Num(8).vars                               // Set()
    *
    * Add(Var("x"), Num(1)).vars                // Set("x")
    *
    * Mul(Num(2), Add(Var("x"), Var("y"))).vars // Set("x", "y")
    * }}}
    */
  def vars: Set[String] = this match
    case Num(_)    => Set()
    case Var(x)    => Set(x)
    case Add(l, r) => l.vars ++ r.vars
    case Mul(l, r) => l.vars ++ r.vars

  /** Evaluates the expression with the given variable assignments.
    *
    * @since 1.0
    *   (2024-03-18)
    * @param ass
    *   the variable assignments
    * @param default
    *   the default value
    * @return
    *   the value of the expression
    *
    * @example
    * {{{
    * val ass = Map("x" -> 3, "y" -> 5)
    * val default = 0
    *
    * Num(8).eval(ass, default)            // 8
    *
    * Add(Var("x"), Num(1))                // x + 1 = 3 + 1 = 4
    *
    * Mul(Num(2), Add(Var("x"), Var("y"))) // 2 * (x + y) = 2 * (3 + 5) = 16
    * }}}
    */
  def eval(ass: Map[String, Int], default: Int): Int = this match
    case Num(n) => n
    case Var(x) =>
      ass.get(x) match
        case Some(n) => n
        case None    => default
    case Add(l, r) => l.eval(ass, default) + r.eval(ass, default)
    case Mul(l, r) => l.eval(ass, default) * r.eval(ass, default)

  /** Generates a string representation of the expression.
    *
    * @since 1.0
    *   (2024-03-18)
    * @return
    *   the string representation of the expression
    *
    * @example
    * {{{
    * Num(8).show                               // "8"
    *
    * Add(Var("x"), Num(1)).show                // "x + 1"
    *
    * Mul(Num(2), Add(Var("x"), Var("y"))).show // "2 * (x + y)"
    * }}}
    */
  def show: String = this match
    case Num(n)    => n.toString
    case Var(x)    => x
    case Add(l, r) => s"${l.show} + ${r.show}"
    case Mul(l, r) =>
      val lstr = l match
        case Add(_, _) => s"(${l.show})"
        case _         => l.show
      val rstr = r match
        case Add(_, _) => s"(${r.show})"
        case _         => r.show
      s"$lstr * $rstr"
