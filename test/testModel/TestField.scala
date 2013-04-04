package testModel

import org.junit.runner.RunWith
import model._
import util._
import org.specs2.mutable._


//@RunWith(classOf[JUnitRunner])
class TestField extends SpecificationWithJUnit {
  "Field" should {
    val field = new Feld(2, Theme.fruits)
    "dimension" in {
      field.dimension must be_==(2)
    }

    "newTheme" in {
      field.setTheme(Theme.people)
      field.currentTheme must be_==(Theme.people)
    }

    "gameOver" in {
      field.gameOver must be_==(false)
      field.gameIsOver must be_==(false)
    }

  }
  "solved Field" should {
    val field = new Feld(2, Theme.fruits)
    "field solved" in {
      field.solve
      field(0, 0).guessed must be_==(true)
      field(1, 0).guessed must be_==(true)
      field(0, 1).guessed must be_==(true)
      field(1, 1).guessed must be_==(true)
      field.gameOver must be_==(true)
      field.gameIsOver must be_==(true)
    }
  }

  "resized Field" should {
    val field = new Feld(2, Theme.fruits)
    field.resize(4)
    "field resized" in {
      field.tempOpenCellsSet.size must be_==(0)
      field.counterGuessed must be_==(0)
      field.gameIsOver must be_==(false)
      field.dimension must be_==(4)
    }
  }

  "hit the field" should {
    val field = new Feld(2, Theme.fruits)
    field.tryOpen(0, 0)
    field.tryOpen(1, 0)
    "field hit" in {
      field(0, 0).open must be_==(true)
      field(1, 0).open must be_==(true)
     
    }
  }
  
   "guessed field" should {
    val field = new Feld(2, Theme.fruits)
    field.tryOpen(0, 0)
    field.tryOpen(1, 1)
    field.tryOpen(1, 1)
    
    field.tryOpen(1, 0)
    field.tryOpen(0, 1)
    
     field.tryOpen(0, 0)
     field.tryOpen(1, 0)
     
      field.tryOpen(0, 1)
      field.tryOpen(1, 1)
      
       field.tryOpen(1, 0)
       field.tryOpen(1, 1)
       
       field.tryOpen(0, 0)
       field.tryOpen(0, 1)
       
          
    "field hit" in {
      field(0, 0).guessed must be_==(true)
      field(1, 0).guessed must be_==(true)
      field(0, 1).guessed must be_==(true)
      field(1, 1).guessed must be_==(true)
      field.gameOver must be_==(true)
     
    }
  }
}