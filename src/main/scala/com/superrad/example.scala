package com.superrad

import com.superrad.Macros._

case class Address(state: String)
case class Person(name: String, address: Address)

object Main extends App {
  val person = Person("Tim", Address(state = "UT"))
  val my_awesome_variable_name = "hello"
  cavepaint(my_awesome_variable_name)

  def outputValue(v: String) = println("The String Value = %s" format v)
  def outputValue(v: Any)    = println("The Any Value = %s" format v)
  val a = getIn(person, 'address, 'state)
  outputValue(a) // Notice the correct inferred type!
  outputValue("CA".asInstanceOf[Any])
}
