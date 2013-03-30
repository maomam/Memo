package test


import org.junit.runner.RunWith
import model._
import util._
import org.specs2.mutable._

//@RunWith(classOf[JUnitRunner])
class TestField extends SpecificationWithJUnit {
"Field" should{
  val field = new Feld(2, Theme.fruits)
   "dimension" in{
    field.dimension must be_==(2)
    }
  
   "newTheme" in{
    field.setTheme(Theme.people)
    field.currentTheme must be_==(Theme.people)
    }
   
   "gameOver" in{
    field.gameOver must be_==(false)
     field.gameIsOver must be_==(false)
    }
}
}