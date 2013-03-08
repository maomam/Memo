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

  var anzahlZellen = dimension * dimension - 1
  var spielfertig = false
  var zaehlerhit = 0

  def setPictures = {
    var isThisPictureSet = false
    for (pairNr <- 1 to (dimension * dimension) / 2) {
      for (pairMember <- 1 to 2) {
        while (isThisPictureSet == false) {
          var Row = scala.util.Random.nextInt(dimension)
          var Column = scala.util.Random.nextInt(dimension)
          var Zelle = cell(Row, Column)
          //println("Row"+Row)
          // println("Column"+Column)
          println(pairNr + "." + pairMember)
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
        zellen(i)(j).hit

      }
    }
    return this
  }

  def hit(reihe: Int, spalte: Int, zahl: Int): Boolean = {
    if (zellen(reihe)(spalte).getErraten == false & zellen(reihe)(spalte).getOffen == true) {
      if (zellen(reihe)(spalte).pictureNr == zahl)
        zellen(reihe)(spalte).hit
      zaehlerhit = zaehlerhit + 1

      return true
    } else
      return false
  }

  def spielFertig: Boolean = {
    if (dimension == 2) {
      if (zaehlerhit == 2) {
        spielfertig = true
        return spielfertig
      }
    }

    if (dimension == 4) {
      if (zaehlerhit == 8) {
        spielfertig = true
        return spielfertig
      }

    }
    if (dimension == 8) {
      if (zaehlerhit == 32) {
        spielfertig = true
        return spielfertig
      }

    }
    return false
  }

  def cell(r: Int, c: Int) = zellen(r)(c)

  override def toString = {
    val lineseparator = ("+-" + ("--" * (dimension / 2))) * dimension + "+\n"
    val line = ("|" + (" " * (dimension / 2)) + ("x" + (" " * (dimension / 2)))) * dimension + "|\n"
    var box = "\n" + (lineseparator + (line)) * dimension + lineseparator
    for (reihe <- 0 to dimension - 1) {
      for (spalte <- 0 to dimension - 1) {

        (box = box.replaceFirst("x", zellen(reihe)(spalte).pictureNr.toString()))

      }
    }
    box
  }

  /* override def toString = {
    val lineseparator = ("+-" + ("--" * (anzahl / 2))) * anzahl + "+\n"
    val line = ("|" + (" " * (anzahl / 2)) + ("x" + (" " * (anzahl / 2)))) * anzahl + "|\n"
    var box = "\n" + (lineseparator + (line)) * anzahl + lineseparator
    for (reihe <- 0 to anzahl - 1) {
      for (spalte <- 0 to anzahl - 1) {

        if (zellen(reihe)(spalte).getErraten == false) {
          
            (box = box.replaceFirst("x", zellen(reihe)(spalte).offen.toString()))
            (box = box.replaceFirst("false", " "))
            (box = box.replaceFirst("true", zellen(reihe)(spalte).gesetzt.toString()))
          } else {
            (box = box.replaceFirst("x", zellen(reihe)(spalte).gesetzt.toString()))
           
          }
        
    }
         }
    box
  }
  */
}