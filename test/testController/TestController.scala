package testController

import org.junit.runner.RunWith
import model._
import controller._
import util._
import org.specs2.mutable._

//@RunWith(classOf[JUnitRunner])
class TestController extends SpecificationWithJUnit{
"Controller" should{
  val f= new Feld(2, Theme.fashion)
  val c =new Controller(f)
  val pic = c.pictureNr(0,0)
  c.changeTheme(Theme.fashion)
  c.changeTheme(Theme.countries)
  c.fieldSize must be_==(2)
  c.currentTheme must be_==(Theme.countries)
}
}