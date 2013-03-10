package model

import scala.swing.Publisher
import scala.swing.event.Event


case object GameOver extends Event

case class CellChanged extends Event

case class CurrentGuessOver(erfolg: Boolean) extends Event

case class FeldResize(newSize: Int) extends Event

class Feld(_dimension: Int) extends Publisher {
  require(List(2, 4, 8).contains(_dimension))
  val dimension = _dimension

  val zellen = {val temp = Array.ofDim[Zelle](dimension, dimension)
    for (
      i <- 0 to dimension - 1;
      j <- 0 to dimension - 1
    ) temp(i)(j) = new Zelle(i, j)

    var isThisPictureSet = false
    for (
      pairNr <- 1 to (dimension * dimension) / 2;
      pairMember <- 1 to 2
    ) {
      while (isThisPictureSet == false) {
        var row = scala.util.Random.nextInt(dimension)
        var column = scala.util.Random.nextInt(dimension)
        val Zelle = temp(row)(column)
        if (Zelle.pictureNr == 0) {
          isThisPictureSet = true
          Zelle.setPictureNr(pairNr)

        } else {
          isThisPictureSet = false
        }
      }
      isThisPictureSet = false
    }
  	publish(new FeldResize(dimension))
  	println ("FeldResize published")
    temp
  }

  var anzahlZellen = dimension * dimension - 1
  var gameIsOver = false
  var counterGuessed = 0
  val openCellsSet = scala.collection.mutable.Set[(Int,Int)]()
  
  def apply(coords: (Int, Int)) = zellen(coords._1)(coords._2)

  def reset: Feld = new Feld(dimension);

  def solve: Feld = {
    for (i <- 0 to dimension - 1) {
      for (j <- 0 to dimension - 1) {
        zellen(i)(j).setGuessed

      }
    }
    publish(new CellChanged)
    return this
  }

  def isMatch(coords1: (Int, Int), coords2: (Int, Int)): Boolean = {
    var result: Boolean = false
    if (!this(coords1).getGuessed && !this(coords2).getGuessed)
      if (this(coords1).pictureNr == this(coords2).pictureNr) {
        this(coords1).setGuessed
        this(coords2).setGuessed
        result = true
        counterGuessed = counterGuessed + 1
      }
    result
  }

  def closeOpenCells: Unit =
    {
      openCellsSet.foreach(coords => this(coords).open = false)
      openCellsSet.clear
    }

  def tryOpen(row: Int, col: Int) = {
    if (!openCellsSet.contains((row, col))) {
      val openCellsCount = openCellsSet.size
      if (openCellsCount == 2) closeOpenCells
      if (openCellsCount == 1) {
        openCellsSet.add((row, col))
        if (isMatch(openCellsSet.head,(row,col)))
          publish(new CellChanged()) //Correct event?
      }
    }
  }

  def gameOver: Boolean = {
    if (dimension == 2) {
      if (counterGuessed == 2) {
        gameIsOver = true
        return gameIsOver
      }
    }

    if (dimension == 4) {
      if (counterGuessed == 8) {
        gameIsOver = true
        return gameIsOver
      }

    }
    if (dimension == 8) {
      if (counterGuessed == 32) {
        gameIsOver = true
        return gameIsOver
      }

    }
    return false
  }

  def getCell(r: Int, c: Int) = zellen(r)(c)

  /*  override def toString = {
    val lineseparator = ("+-" + ("--" * (dimension / 2))) * dimension + "+\n"
    val line = ("|" + (" " * (dimension / 2)) + ("x" + (" " * (dimension / 2)))) * dimension + "|\n"
    var box = "\n" + (lineseparator + (line)) * dimension + lineseparator
    for (reihe <- 0 to dimension - 1) {
      for (spalte <- 0 to dimension - 1) {

        (box = box.replaceFirst("x", zellen(reihe)(spalte).pictureNr.toString()))

      }
    }
    box
  }*/

  override def toString = {
    val lineseparator = ("+-" + ("--" * (dimension / 2))) * dimension + "+\n"
    val line = ("|" + (" " * (dimension / 2)) + ("x" + (" " * (dimension / 2)))) * dimension + "|\n"
    var box = "\n" + (lineseparator + (line)) * dimension + lineseparator
    for (reihe <- 0 to dimension - 1) {
      for (spalte <- 0 to dimension - 1) {

        if (zellen(reihe)(spalte).getGuessed == false) {

          (box = box.replaceFirst("x", zellen(reihe)(spalte).open.toString()))
          (box = box.replaceFirst("false", " "))
          (box = box.replaceFirst("true", zellen(reihe)(spalte).pictureNr.toString()))
        } else {
          (box = box.replaceFirst("x", zellen(reihe)(spalte).pictureNr.toString()))

        }

      }
    }
    box
  }

}