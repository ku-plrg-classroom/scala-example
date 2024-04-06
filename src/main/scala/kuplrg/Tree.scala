package kuplrg

/** A simple tree data structure.
  *
  * A _tree_ is either
  *   1. a _leaf_ ([[Tree.Leaf]]) with a value or
  *   1. a _node_ ([[Tree.Node]]) with a value and a list of children.
  *
  * @version 1.0
  * (2024-03-18)
  *
  * @example
  *   First Example:
  * {{{
  * // 8
  * val tree1: Tree = Leaf(8)
  * }}}
  * Second Example:
  * {{{
  * //   1
  * //  / \
  * // 3   2
  * val tree2: Tree = Node(1, List(Leaf(3), Leaf(2)))
  * }}}
  * Third Example:
  * {{{
  * //    7
  * //   / \
  * //  2   3
  * //     / \
  * //    5   1
  * //   / \
  * //  1   8
  * val tree3: Tree = Node(7, List(
  *   Leaf(2),
  *   Node(3, List(
  *     Node(5, List(Leaf(1), Leaf(8))),
  *     Leaf(1)
  *   ))
  * ))
  * }}}
  */
enum Tree:
  /** A _leaf_ node with a value.
    *
    * @since 1.0
    *   (2024-03-18)
    * @constructor
    *   create a new leaf with a value
    * @param value
    *   the value of the leaf
    */
  case Leaf(value: Int)

  /** A _node_ with a value and a list of children.
    *
    * @since 1.0
    *   (2024-03-18)
    * @constructor
    *   create a new node with a value and a list of children
    * @param value
    *   the value of the node
    * @param children
    *   the list of children trees
    */
  case Node(value: Int, children: List[Tree])

  /** Checks if the tree _has_ a given value.
    *
    * @since 1.0
    *   (2024-03-18)
    * @param value
    *   the value to check
    * @return
    *   `true` if the tree has the value, `false` otherwise
    *
    * @example
    * {{{
    * Leaf(8).has(8)                           // true
    *
    * Node(1, List(Leaf(3), Leaf(2))).has(8)   // false
    *
    * Node(7, List(
    *   Leaf(2),
    *   Node(3, List(
    *     Node(5, List(Leaf(1), Leaf(8))),
    *     Leaf(1)
    *   ))
    * )).has(8)   // true
    * }}}
    */
  def has(value: Int): Boolean = this match
    case Leaf(v)           => v == value
    case Node(v, children) => v == value || children.exists(_.has(value))

  /** Maps a function over the tree.
    *
    * @since 1.0
    *   (2024-03-18)
    * @param f
    *   the function to map
    * @return
    *   a new tree with the function applied to each value
    *
    * @example
    * {{{
    * // 8  =>  9
    * Leaf(8).map(_ + 1)
    *
    * //   1         2
    * //  / \   =>  / \
    * // 3   2     6   4
    * Node(1, List(Leaf(3), Leaf(2))).map(_ * 2)
    *
    * //    7          15
    * //   / \        / \
    * //  2   3      5   7
    * //     / \  =>    / \
    * //    5   1      11  3
    * //   / \        / \
    * //  1   8      3   17
    * Node(7, List(
    *   Leaf(2),
    *   Node(3, List(
    *     Node(5, List(Leaf(1), Leaf(8))),
    *     Leaf(1)
    *   ))
    * )).map(_ * 2 + 1)
    * }}}
    */
  def map(f: Int => Int): Tree = this match
    case Leaf(v)           => Leaf(f(v))
    case Node(v, children) => Node(f(v), children.map(_.map(f)))

  /** Counts the _number of leaves_ in the tree.
    *
    * @since 1.0
    *   (2024-03-18)
    * @return
    *   the number of leaves in the tree
    *
    * @example
    * {{{
    * Leaf(8).countLeaves                         // 1
    *
    * Node(1, List(Leaf(3), Leaf(2))).countLeaves // 2
    *
    * Node(7, List(
    *   Leaf(2),
    *   Node(3, List(
    *     Node(5, List(Leaf(1), Leaf(8))),
    *     Leaf(1)
    *   ))
    * )).countLeaves                              // 4
    * }}}
    */
  def countLeaves: Int = this match
    case Leaf(_)           => 1
    case Node(_, children) => children.map(_.countLeaves).sum

  /** Sorts the tree.
    *
    * @since 1.0
    *   (2024-03-18)
    * @return
    *   a new tree with the values sorted in ascending order
    * @note
    *   The tree is sorted in a pre-order traversal.
    *
    * @example
    * {{{
    * // 8
    * Leaf(8).sort
    *
    * //   1        1
    * //  / \  =>  / \
    * // 3   2    2   3
    * Node(1, List(Leaf(3), Leaf(2))).sort
    *
    * //    7          1
    * //   / \        / \
    * //  2   3      1   2
    * //     / \  =>    / \
    * //    5   1      3   8
    * //   / \        / \
    * //  1   8      5   7
    * Node(7, List(
    *   Leaf(2),
    *   Node(3, List(
    *   Node(5, List(Leaf(1), Leaf(8))),
    *   Leaf(1)
    * )))).sort
    * }}}
    */
  def sort: Tree =
    // Convert the tree to a list in a pre-order traversal
    def toList(tree: Tree): List[Int] = tree match
      case Leaf(v)           => List(v)
      case Node(v, children) => v :: children.flatMap(toList)
    // Merge the list back into a tree
    def merge(tree: Tree, list: List[Int]): (Tree, List[Int]) =
      (tree, list) match
        case (Leaf(_), head :: tail) => (Leaf(head), tail)
        case (Node(_, children), head :: tail) =>
          val (newChildren, remaining) =
            children.foldLeft((List.empty[Tree], tail)) {
              case ((acc, remaining), child) =>
                val (newChild, newRemaining) = merge(child, remaining)
                (newChild :: acc, newRemaining)
            }
          (Node(head, newChildren.reverse), remaining)
        case _ => (tree, list)
    // Sort the tree
    val (newTree, _) = merge(this, toList(this).sorted)
    newTree
