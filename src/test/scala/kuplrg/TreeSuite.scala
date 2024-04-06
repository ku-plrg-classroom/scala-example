package kuplrg

import org.scalatest.funsuite.AnyFunSuite

class TreeSuite extends AnyFunSuite {
  import Tree.*

  // 8
  val tree1: Tree = Leaf(8)

  //   1
  //  / \
  // 2   3
  val tree2: Tree = Node(1, List(Leaf(3), Leaf(2)))

  //    7
  //   / \
  //  2   3
  //     / \
  //    5   1
  //   / \
  //  1   8
  val tree3: Tree = Node(
    7,
    List(
      Leaf(2),
      Node(
        3,
        List(
          Node(5, List(Leaf(1), Leaf(8))),
          Leaf(1),
        ),
      ),
    ),
  )

  test("The `has` should return if the tree has the value") {
    assert(tree1.has(8) == true)
    assert(tree2.has(8) == false)
    assert(tree3.has(8) == true)
  }

  test("The `map` should map the tree with the given function") {
    assert(tree1.map(_ + 1) == Leaf(9))
    assert(tree2.map(_ * 2) == Node(2, List(Leaf(6), Leaf(4))))
    assert(
      tree3.map(_ * 2 + 1) == Node(
        15,
        List(
          Leaf(5),
          Node(
            7,
            List(
              Node(11, List(Leaf(3), Leaf(17))),
              Leaf(3),
            ),
          ),
        ),
      ),
    )
  }

  test("The `countLeaves` should count the number of nodes in the tree") {
    assert(tree1.countLeaves == 1)
    assert(tree2.countLeaves == 2)
    assert(tree3.countLeaves == 4)
  }

  test("The `sort` should return a new tree with the values sorted") {
    assert(tree1.sort == Leaf(8))
    assert(tree2.sort == Node(1, List(Leaf(2), Leaf(3))))
    assert(
      tree3.sort == Node(
        1,
        List(
          Leaf(1),
          Node(
            2,
            List(
              Node(3, List(Leaf(5), Leaf(7))),
              Leaf(8),
            ),
          ),
        ),
      ),
    )
  }
}
