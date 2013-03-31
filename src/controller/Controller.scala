package controller

import scala.swing.Publisher
import scala.swing.event.Event
import scala.math.sqrt
import scala.util.Random
import model._
import util.Theme
import util._
import javax.swing.JOptionPane
import util.Theme



case class GameOver extends Event
case class FieldSolved extends Event
case class FieldReset extends Event
case class CellsGuessed(guessedCells: List[Coordinates]) extends Event
case class CellsClosed(cellsToClose: List [Coordinates])  extends Event
case class CellOpened(cellToOpen: Coordinates) extends Event
case class FeldResize(newSize: Int) extends Event
case class ThemeChanged(newTheme: Theme.Value) extends Event

class Controller(var feld: Feld) extends Publisher {
  var statusText = "Spiel angefangen"

  def solve = { feld.solve; statusText = "Spiel gelöst"; publish(new FieldSolved) }

  def changeTheme(newTheme: Theme.Value) = {
    if (feld.currentTheme != newTheme) {
      feld.setTheme(newTheme)
      statusText = "Thema der Bilder geändert"
      publish(new ThemeChanged(newTheme))  
    } else { statusText = "Das aktuelle Thema ist schon das geforderte" }

  }

  def selectCell(cellCoords: (Int, Int)) = {
   val chgCells = feld.tryOpen(cellCoords._1, cellCoords._2) 
   
   if(!chgCells.closedCells.isEmpty) publish(new CellsClosed(chgCells.closedCells))
   if(!chgCells.guessedCells.isEmpty) publish(new CellsGuessed(chgCells.guessedCells))
   chgCells.openedCell match {
     case Some(coords) => publish(new CellOpened(coords))
   }
   if(feld.gameOver) { statusText ="Spiel ist beendet"
     publish(new GameOver)}
  }

  def reset = {
    feld.reset
    statusText ="Zellen werden zurückgesetzt"
    publish(new FieldReset) 
   
  }
  
  def resize(newSize:Int) ={
    if(newSize != feld.dimension){
      feld.resize(newSize)
      statusText="Spielgrösse verändert" 
      publish(new FeldResize(newSize))
    }
    else statusText ="Das Spiel ist schon in der geforderten grösse"
  }
  
  

  def pictureNr (c :Coordinates) : Int ={
    feld(c._1, c._2).pictureNr
  }
  
  def fieldSize = feld.dimension
 
  def currentTheme = feld.currentTheme

}