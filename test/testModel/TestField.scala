package testModel

import org.junit.runner.RunWith
import model._
import util._
import org.specs2.mutable._
import scala.collection.immutable.ListMap

//@RunWith(classOf[JUnitRunner])
class TestField extends SpecificationWithJUnit {
  "Field" should {
    val field = new Feld(2, Theme.fruits)
    "dimension" in {
      field.dimension must be_==(2)
      field(0, 0).pictureNr must not be_== (0)
      field(1, 1).pictureNr must not be_== (0)
      field(0, 1).pictureNr must not be_== (0)
      field(1, 0).pictureNr must not be_== (0)
      field.anzahlZellen must be_==(3)
      field.tempOpenCellsSet.size must be_==(0)
      field.gameOver must be_==(false)
      field.gameIsOver must be_==(false)
    }

    "newTheme" in {
      field.setTheme(Theme.people)
      field.currentTheme must be_==(Theme.people)
    }

    "reset Field" in {
      field.reset
      field(1, 1).guessed must be_==(false)
      field(0, 0).guessed must be_==(false)
      field.counterGuessed must be_==(0)
      field.tempOpenCellsSet.size must be_==(0)
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
      field.tempOpenCellsSet.size must be_==(0)
      field.counterGuessed must be_==(0)
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
      field.gameOver must be_==(false)
      field.gameIsOver must be_==(false)
      field.dimension must be_==(4)
    }
  }

  "closeOpenCells test" should {
    val field = new Feld(2, Theme.fruits)
    field.tryOpen(0, 0)
    field.closeOpenCells
    "closeCells" in {
      field(0, 0).open must be_==(false)

    }
  }
  
    "OpenCell" should {
    val field = new Feld(2, Theme.fruits)
   field.openCell(0,0)
    "closeCells" in {
      field(0, 0).open must be_==(true)

    }
  }

  "isMatch" should {
    val field = new Feld(2, Theme.fruits)
    var coordinates = ListMap(Map((0, 0) -> field(0, 0).pictureNr, (0, 1) -> field(0, 1).pictureNr, (1, 1) -> field(1, 1).pictureNr, (1, 0) -> field(1, 0).pictureNr).toList.sortBy { _._2 }: _*)
    var keys = coordinates.keys.toSeq
    var coordsone: Coordinates = keys(0)
    var coordstwo: Coordinates = keys(1)
    var coordsthree: Coordinates = keys(2)
    var coordsfour: Coordinates = keys(3)
    
    "field hit" in {
      field.isMatch((coordsone._1, coordsone._2),(coordstwo._1, coordstwo._2)) must be_==(true)

    }
  }

  "guessed field" should {
    val field = new Feld(2, Theme.fruits)
    var coordinates = ListMap(Map((0, 0) -> field(0, 0).pictureNr, (0, 1) -> field(0, 1).pictureNr, (1, 1) -> field(1, 1).pictureNr, (1, 0) -> field(1, 0).pictureNr).toList.sortBy { _._2 }: _*)
    var keys = coordinates.keys.toSeq
    var coordsone: Coordinates = keys(0)
    var coordstwo: Coordinates = keys(1)
    var coordsthree: Coordinates = keys(2)
    var coordsfour: Coordinates = keys(3)
    field.tryOpen(coordsone._1, coordsone._2)
    field.tryOpen(coordsthree._1, coordsthree._2)
    field.tryOpen(coordstwo._1, coordstwo._2)
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
    "field hit" in {
      field(0, 0).guessed must be_==(true)
      field(1, 0).guessed must be_==(true)
      field(0, 1).guessed must be_==(true)
      field(1, 1).guessed must be_==(true)
      field.gameOver must be_==(true)
      field.gameIsOver must be_==(true)

    }
  }
  "4 dimension Field" should {
    val field = new Feld(4, Theme.countries)
    "4dimension" in {
      field.dimension must be_==(4)
      field(0, 0).pictureNr must not be_== (0)
      field(3, 3).pictureNr must not be_== (0)
    }
  }
}