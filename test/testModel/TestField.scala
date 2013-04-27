package testModel

import org.junit.runner.RunWith
import model._
import util._
import org.specs2.mutable._
import org.specs2.runner.JUnitRunner
import org.specs2.specification.Scope
import scala.collection.immutable.ListMap

@RunWith(classOf[JUnitRunner])
class TestField extends SpecificationWithJUnit {

  "Field" should {
    "new Field" in new context {
      field.dimension must be_==(2)
      field(coordsone).pictureNr mustEqual 1
      field(coordstwo).pictureNr mustEqual 1
      field(coordsthree).pictureNr mustEqual 2
      field(coordsfour).pictureNr mustEqual 2
      field.tempOpenCellsSet.size mustEqual 0
      field.counterGuessed mustEqual 0
      field.gameOver must be_==(false)
      field.gameIsOver must be_==(false)
    }

    "newTheme" in new liteContext {
      field.setTheme(Theme.people)
      field.currentTheme must be_==(Theme.people)
    }
  }
    "Reset Field" should{
    "reset Field" in new context {
      field.reset
      field(coordsone).guessed must be_==(false)
      field(coordstwo).guessed must be_==(false)
      field(coordsthree).guessed must be_==(false)
      field(coordsfour).guessed must be_==(false)
      field(coordsone).pictureNr must not be_==(0)
      field(coordstwo).pictureNr must not be_==(0)
      field(coordsthree).pictureNr must not be_==(0)
      field(coordsfour).pictureNr must not be_==(0)
      field.counterGuessed must be_==(0)
      field.tempOpenCellsSet.size must be_==(0)
      field.gameOver must be_==(false)
      field.gameIsOver must be_==(false)
    }

  }

  "solved Field" should {

    "field solved" in new liteContext {
      field.solve
      field(0, 0).guessed must be_==(true)
      field(1, 0).guessed must be_==(true)
      field(0, 1).guessed must be_==(true)
      field(1, 1).guessed must be_==(true)
      field.tempOpenCellsSet.size must be_==(0)
      field.counterGuessed must be_==(0)
      field.gameOver must be_==(true)
      field.gameIsOver must be_==(true)
    }
  }

  "resized Field" should {
    "field resized" in new liteContext {
      field.resize(4)
      field.tempOpenCellsSet.size must be_==(0)
      field.counterGuessed must be_==(0)
      field.gameOver must be_==(false)
      field.gameIsOver must be_==(false)
      field.dimension must be_==(4)
    }
  }

  "closeOpenCells test" should {
    "closeCells" in new liteContext {
      field.tryOpen(0, 0)
      field.closeOpenCells
      field(0, 0).open must be_==(false)
    }
  }

  "OpenCell" should {

    "openCells" in new liteContext {
      field.openCell(0, 0)
      field(0, 0).open must be_==(true)
    }
  }

  "isMatch" should {

    "field match" in new context {
      field.isMatch((coordsone._1, coordsone._2), (coordstwo._1, coordstwo._2)) must be_==(true)

    }
  }

  "guessed field" should {

    "first hit" in new context {
      field.tryOpen(coordsone._1, coordsone._2)
      field.tempOpenCellsSet.size must be_==(1)
      field.gameIsOver must be_==(false)
    }

    "second hit" in new context {
      field.tryOpen(coordsone._1, coordsone._2)
      field.tryOpen(coordsthree._1, coordsthree._2)
      field(coordsthree._1, coordsthree._2).guessed must be_==(false)
      field(coordsthree._1, coordsthree._2).open must be_==(true)
      field(coordsone._1, coordsone._2).guessed must be_==(false)
      field(coordsone._1, coordsone._2).open must be_==(true)
      field.tempOpenCellsSet must contain(coordsone)
      field.tempOpenCellsSet must contain(coordsthree)
      field.tempOpenCellsSet.size mustEqual 2
    }

    "third hit" in new context {
      field.tryOpen(coordsone._1, coordsone._2)
      field.tryOpen(coordsthree._1, coordsthree._2)
      field.tryOpen(coordstwo._1, coordstwo._2)
      field.tempOpenCellsSet must contain(coordstwo)
      field(coordsthree._1, coordsthree._2).guessed must be_==(false)
      field(coordsthree._1, coordsthree._2).open must be_==(false)
      field(coordsone._1, coordsone._2).guessed must be_==(false)
      field(coordsone._1, coordsone._2).open must be_==(false)
      field(coordstwo._1, coordstwo._2).open must be_==(true)
      field.tempOpenCellsSet must contain(coordstwo)
      field.tempOpenCellsSet.size must be_==(1)
    }

    "field solved" in new context {
      field.tryOpen(coordstwo._1, coordstwo._2)
      //two times the same cell chosen
      //three not guessed. different pictureNr
      field.tryOpen(coordsfour._1, coordsfour._2)
      //four not guessed
      field.tryOpen(coordsone._1, coordsone._2)
      field.tryOpen(coordstwo._1, coordstwo._2)
      //one and two are now guessed
      field.tryOpen(coordsone._1, coordsone._2)
      field.tryOpen(coordstwo._1, coordstwo._2)
      //guessed cells clicked
      field.tryOpen(coordsthree._1, coordsthree._2)
      field.tryOpen(coordsfour._1, coordsfour._2)
      //all guessed game over
      field.counterGuessed mustEqual 2
      field(0, 0).guessed must be_==(true)
      field(1, 0).guessed must be_==(true)
      field(0, 1).guessed must be_==(true)
      field(1, 1).guessed must be_==(true)
      field.gameOver must be_==(true)
      field.gameIsOver must be_==(true)

    }
  }


}

trait liteContext extends Scope {
  val field = new Feld(2, Theme.fruits)
}

trait context extends Scope with liteContext {

  var coordinates = ListMap(
    Map((0, 0) -> field(0, 0).pictureNr,
      (0, 1) -> field(0, 1).pictureNr,
      (1, 1) -> field(1, 1).pictureNr,
      (1, 0) -> field(1, 0).pictureNr).toList.sortBy { _._2 }: _*)
  var keys = coordinates.keys.toSeq
  var coordsone: Coordinates = keys(0)
  var coordstwo: Coordinates = keys(1)
  var coordsthree: Coordinates = keys(2)
  var coordsfour: Coordinates = keys(3)
}