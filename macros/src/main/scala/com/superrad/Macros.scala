package com.superrad

import language.experimental.macros
import scala.annotation.tailrec
import reflect.macros.Context

object Macros {
  // example using reify / splice
  def cavepaint(param: Any): Unit = macro cavepaint_impl
  def cavepaint_impl(c: Context)(param: c.Expr[Any]): c.Expr[Unit] = {
    import c.universe._
    val termNameConstExpr = param.tree match {
      case Select(_, TermName(name)) =>
        c.Expr[String](Literal(Constant(name)))
    }
    reify { println("%s = %s" format(termNameConstExpr.splice, param.splice)) }
  }

  // cavepaint using quasi quotes
  def cavepaint2(param: Any): Unit = macro cavepaint2_impl
  def cavepaint2_impl(c: Context)(param: c.Expr[Any]): c.Tree = {
    import c.universe._
    val termNameConstExpr = param.tree match {
      case Select(_, TermName(name)) =>
        c.Expr[String](Literal(Constant(name)))
    }
    q"""{println("%s = %s" format($termNameConstExpr, $param))}"""
  }

  def getIn(obj: Any, syms: Symbol*) = macro getIn_impl
  def getIn_impl(c: Context)(obj: c.Expr[Any], syms: c.Expr[Symbol]*): c.Tree = {
    import c.universe._

    val path = syms.toList map { sym =>
      // println(showRaw(sym.tree))

      sym.tree match {
        case Apply(_, List(Literal(Constant(name: String)))) => name
        case _ =>
          throw new Exception("Expected list of symbols")
      }
    }

    @tailrec
    def getExpr(ref: Tree, members: List[String]): Tree = {
      members match {
        case member :: rest => getExpr(Select(ref, TermName(member)), rest)
        case Nil => ref
      }
    }
    getExpr(obj.tree, path)
  }
}
