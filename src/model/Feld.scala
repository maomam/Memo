package model

import model._
import util.Theme

class Feld(var dimension: Int, var currentTheme: Theme.Value){
  require(List(2, 6, 4, 8).contains(dimension))
 
 
  var anzahlZellen = dimension * dimension - 1
  var gameIsOver = false
  var counterGuessed = 0
  val tempOpenCellsSet = scala.collection.mutable.Set[Coordinates]()
  
  var zellen = {generateCells(dimension)}
  
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
   reset
   currentTheme=newTheme
   }
  
  def apply(coords: Coordinates) = zellen(coords._1)(coords._2)

  def reset : Unit = {
    counterGuessed =0
    gameIsOver = false
      for (i <- 0 to dimension - 1) {
      for (j <- 0 to dimension - 1) {
        zellen(i)(j).pictureNr_(0)
        zellen(i)(j).guessed_(false)
        zellen(i)(j).open_(false)
        
        
      }
    }
      setPictures(dimension)
      
     
  }
  
  def resize(newFieldSize:Int)={
    counterGuessed =0
    gameIsOver = false
     zellen =generateCells(newFieldSize)
      setPictures(newFieldSize)
      dimension =newFieldSize
  }
    
 def solve: Unit = {
    for (i <- 0 to dimension - 1) {
      for (j <- 0 to dimension - 1) {
        zellen(i)(j).setGuessed
      }
    }
    gameIsOver=true
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

  def tryOpen(row: Int, col: Int): ( List[Coordinates], List[Coordinates], Coordinates, Boolean ) = {
     var toClose : List[Coordinates] = null
     var toGuessed : List[Coordinates] = null
     var toOpen:  Coordinates = null
     var thisGameOver =false
    if (!tempOpenCellsSet.contains((row, col))&& !this(row,col).guessed) {
      val openCellsCount = tempOpenCellsSet.size
      if (openCellsCount == 2){ 
         this(row, col).open_(true)
         toClose = tempOpenCellsSet.toList
         toOpen = (row,col)
        closeOpenCells()
         tempOpenCellsSet.add((row, col))
        
      }
      if (openCellsCount == 1) {
        this(row, col).open_(true)
        toOpen =(row,col)
        val openCell = tempOpenCellsSet.head
        if(isMatch(openCell,(row,col))){
          this(row, col).setGuessed
          this(openCell).setGuessed
        counterGuessed = counterGuessed + 1
          tempOpenCellsSet.clear
         toGuessed=  openCell::List((row,col)) 
        }else{
          tempOpenCellsSet.add((row, col))
        
        }
         
         
      }
      if(openCellsCount==0){
         tempOpenCellsSet.add((row, col))
         this(row, col).open_(true)
         toOpen = (row,col)
         
      }
    }
    if(gameOver){
     thisGameOver = true
    }
   ( toClose, toGuessed , toOpen, thisGameOver)
  }

  def gameOver: Boolean = {
      if (counterGuessed == (dimension*dimension)/2) {
        gameIsOver = true
       }
     gameIsOver
  }


}