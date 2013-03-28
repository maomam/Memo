package test

import org.junit.runner.RunWith
import model._
import org.specs2.mutable._

//@RunWith(classOf[JUnitRunner])
class TestCell extends SpecificationWithJUnit{
  "A new Zelle" should{
    val zelle = new Zelle(0,0)
    
    "not be set" in{
      var guessed = zelle.guessed
      guessed must be_==(false)
    }
    "not be open" in{
      var open = zelle.open
      open must be_==(false)
    }
  }

}