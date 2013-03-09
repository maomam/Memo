package model1

class Feld(_dimension: Int) {
  require(List(2, 4, 8).contains(_dimension))
  val dimension = _dimension

  val zellen = {
    var temp = Array.ofDim[Zelle](dimension, dimension)

    for (i <- 0 to dimension - 1) {
      for (j <- 0 to dimension - 1) {
        temp(i)(j) = new Zelle(i, j)

      }
    }
    
    temp
  }
  setPictures

  var anzahlZellen = dimension * dimension - 1
  var gameIsOver = false
  var counterguessed = 0

  def setPictures = {
    var isThisPictureSet = false
    for (pairNr <- 1 to (dimension * dimension) / 2) {
      for (pairMember <- 1 to 2) {
        while (isThisPictureSet == false) {
          var Row = scala.util.Random.nextInt(dimension)
          var Column = scala.util.Random.nextInt(dimension)
          var Zelle = cell(Row, Column)
           if (Zelle.pictureNr == 0) {
            isThisPictureSet = true
            Zelle.setPictureNr(pairNr)

          } else {
            isThisPictureSet = false
          }
        }
        isThisPictureSet = false
      }

    }

  }

  def reset: Feld = new Feld(dimension);

  def solve: Feld = {
    for (i <- 0 to dimension - 1) {
      for (j <- 0 to dimension - 1) {
        zellen(i)(j).setGuessed

      }
    }
    return this
  }

  def checkGuess(reihe1: Int, spalte1: Int, reihe2: Int, spalte2: Int): Boolean = {
    if (zellen(reihe1)(spalte1).getGuessed == false & zellen(reihe2)(spalte2).getGuessed == false) {
      if (zellen(reihe1)(spalte1).pictureNr == zellen(reihe1)(spalte1).pictureNr)
        zellen(reihe1)(spalte1).setGuessed
        zellen(reihe2)(spalte2).setGuessed
      counterguessed = counterguessed + 1

      return true
    } else
      return false
  }

  def gameOver: Boolean = {
    if (dimension == 2) {
      if (counterguessed == 2) {
        gameIsOver = true
        return gameIsOver
      }
    }

    if (dimension == 4) {
      if (counterguessed == 8) {
        gameIsOver = true
        return gameIsOver
      }

    }
    if (dimension == 8) {
      if (counterguessed == 32) {
        gameIsOver = true
        return gameIsOver
      }

    }
    return false
  }

  def cell(r: Int, c: Int) = zellen(r)(c)

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
          
            (box = box.replaceFirst("x", zellen(reihe)(spalte).offen.toString()))
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