package model

import scala.swing.Publisher
import scala.swing.event.Event
import model._
import util.Theme

case class GameOver extends Event
case class FieldSolved extends Event
case class FieldReset extends Event
case class CellsGuessed(guessedCells: List[Coordinates]) extends Event
case class CellsClosed(cellsToClose: List [Coordinates])  extends Event
case class CellOpened(cellToOpen: Coordinates) extends Event
case class FeldResize(newSize: Int) extends Event
case class ThemeChanged(newTheme: Theme.Value) extends Event
// TODO: add new events

class Feld(var dimension: Int, var currentTheme: Theme.Value) extends Publisher {
  require(List(6, 4, 8).contains(dimension))
 
 
  var anzahlZellen = dimension * dimension - 1
  var gameIsOver = false
  var counterGuessed = 0
  val tempOpenCellsSet = scala.collection.mutable.Set[Coordinates]()
  
  var zellen = {generateCells(dimension)
  }
  
  def generateCells(size:Int): Array[Array[Zelle]] ={
    val temp = Array.ofDim[Zelle](size,size)
    for (
      i <- 0 to size - 1;
      j <- 0 to size - 1
    ) temp(i)(j) = new Zelle(i, j)

  
  
  	temp
  }
  setPictures(dimension)
  
  def setPictures(size:Int) :Unit={
     var isThisPictureSet = false
    for (
      pairNr <- 1 to (size * size) / 2;
      pairMember <- 1 to 2
    ) {
      while (isThisPictureSet == false) {
        var row = scala.util.Random.nextInt(size)
        var column = scala.util.Random.nextInt(size)
        val Zelle = zellen(row)(column)
        if (Zelle.pictureNr == 0) {
          isThisPictureSet = true
          Zelle.pictureNr_(pairNr)

        } else {
          isThisPictureSet = false
        }
      }
      isThisPictureSet = false
    }
  }

 def setTheme (newTheme:Theme.Value)={
   currentTheme=newTheme
   publish(new ThemeChanged(newTheme))
 }
  
  def apply(coords: Coordinates) = zellen(coords._1)(coords._2)

  def reset(fieldSize:Int) = {
    if (fieldSize ==dimension){
      for (i <- 0 to dimension - 1) {
      for (j <- 0 to dimension - 1) {
        zellen(i)(j).pictureNr_(0)

      }
    }
      setPictures(fieldSize)
      publish(new FieldReset)
    }
    else{
      zellen =generateCells(fieldSize)
      setPictures(fieldSize)
      dimension =fieldSize
      publish(new FeldResize(dimension))
    }
    
  }
    
 def solve: Unit = {
    for (i <- 0 to dimension - 1) {
      for (j <- 0 to dimension - 1) {
        zellen(i)(j).setGuessed
      }
    }
    gameIsOver=true
    publish(new FieldSolved)
 
  }

  def isMatch(coords1: (Int, Int), coords2: (Int, Int)): Boolean = 
   !this(coords1).guessed && !this(coords2).guessed &&
        (this(coords1).pictureNr == this(coords2).pictureNr) 
        
      
  
  def openCellsToString(s: scala.collection.mutable.Set [(Int, Int)])={
    s.foreach (x=> println(x))
  }

  def closeOpenCells(): Unit =
    {
      tempOpenCellsSet.foreach(coords => this(coords).open_(false))
      tempOpenCellsSet.clear
    }

  def tryOpen(row: Int, col: Int) = {
    openCellsToString(tempOpenCellsSet)
    if (!tempOpenCellsSet.contains((row, col))&& !this(row,col).guessed) {
      val openCellsCount = tempOpenCellsSet.size
      if (openCellsCount == 2){ 
         closeOpenCells()
         publish(new CellsClosed(tempOpenCellsSet.toList))
         tempOpenCellsSet.add((row, col))
         this(row, col).open_(true)
         publish(new CellOpened((row,col)))
      }
      if (openCellsCount == 1) {
        //it should check whether it is right match to decide whether to close it. 
        getCell(row, col).open_(true)
        
        val openCell = tempOpenCellsSet.head
        if(isMatch(openCell,(row,col))){
          this(row, col).setGuessed
          this(openCell).setGuessed
        counterGuessed = counterGuessed + 1
          tempOpenCellsSet.clear
          publish(new CellsGuessed(openCell::List((row,col))))
        }
         tempOpenCellsSet.add((row, col))
         publish(new CellOpened((row, col)))
      }
      if(openCellsCount==0){
         tempOpenCellsSet.add((row, col))
         getCell(row, col).open_(true)
         publish(new CellOpened((row, col)))
      }
    }
    if(gameOver){
      publish(new GameOver)
    }
  }

  def gameOver: Boolean = {
      if (counterGuessed == (dimension*dimension)/2) {
        gameIsOver = true
         gameIsOver
      }
     gameIsOver
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


}