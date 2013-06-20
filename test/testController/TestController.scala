package testController

import org.junit.runner.RunWith
import model._
import controller._
import util._
import org.specs2.mutable._
import org.specs2.runner.JUnitRunner
import org.specs2.specification.Scope
import scala.collection.immutable.ListMap

@RunWith(classOf[JUnitRunner])
class TestController extends SpecificationWithJUnit {
  "Controller" should {
    "new controller" in new liteContext {
      c.pictureNr(coordsone) mustEqual 1
      c.pictureNr(coordstwo) mustEqual 1
      c.fieldSize must be_==(2)
      c.currentTheme must be_==(Theme.fashion)
      c.field.counterGuessed must be_==(0)
      c.field.tempOpenCellsSet.size must be_==(0)
      c.field.gameOver must be_==(false)
      
    }

    "changeTheme" in new liteContext {
      c.changeTheme(Theme.fashion)
      c.changeTheme(Theme.countries)
      c.currentTheme must be_==(Theme.countries)
    }
  }
  "solved Controller" should {

    "gameOver" in new liteContext {
      c.solve
   
      c.field.gameOver must be_==(true)
    }
  }
  " resized Controller" should {
    "resize" in new liteContext {
      c.resize(2)
      c.resize(4)
      c.fieldSize must be_==(4)
      c.field.counterGuessed must be_==(0)
      c.field.tempOpenCellsSet.size must be_==(0)
      c.field.gameOver must be_==(false)
     
    }
  }
  " reset Controller" should {
    "reset" in new liteContext {
      c.reset
      c.field.counterGuessed must be_==(0)
      c.field.tempOpenCellsSet.size must be_==(0)
      c.field.gameOver must be_==(false)
     
      
    }
  }

  " play Controller" should {
    "first hit" in new liteContext {
      c.selectCell(coordsone)
      c.field(coordsone).open must be_==(true)
      c.field.tempOpenCellsSet must contain(coordsone)
      c.field.tempOpenCellsSet.size mustEqual 1
    }

  }
  " solved Controller" should {
    "solve" in new liteContext {
      c.selectCell(coordstwo)
       c.field(coordstwo).open must be_==(true)
      c.selectCell(coordstwo)
      c.selectCell(coordsfour)
      c.selectCell(coordsone)
      c.selectCell(coordstwo)
      c.selectCell(coordsone)
      c.selectCell(coordstwo)
      c.selectCell(coordsthree)
      c.selectCell(coordsfour)
      c.field(coordsone).guessed must be_==(true)
      c.field(coordstwo).guessed must be_==(true)
      c.field(coordsthree).guessed must be_==(true)
      c.field(coordsfour).guessed must be_==(true)
      c.field.counterGuessed mustEqual 2
      c.field.gameOver must be_==(true)
     
    }

  }
  trait liteContext extends Scope {
    val f = new Feld(2, Theme.fashion)
    val c = new Controller(f)
    var coordinates = ListMap(
      Map((0, 0) -> c.field(0, 0).pictureNr,
        (0, 1) -> c.field(0, 1).pictureNr,
        (1, 1) -> c.field(1, 1).pictureNr,
        (1, 0) -> c.field(1, 0).pictureNr).toList.sortBy { _._2 }: _*)
    var keys = coordinates.keys.toSeq
    var coordsone: Coordinates = keys(0)
    var coordstwo: Coordinates = keys(1)
    var coordsthree: Coordinates = keys(2)
    var coordsfour: Coordinates = keys(3)
  }

}