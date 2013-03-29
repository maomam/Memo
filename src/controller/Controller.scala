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

  def solve = { feld.solve; statusText = "Spiel beendet"; publish(new FieldSolved) }

  def changeTheme(newTheme: Theme.Value) = {
    if (feld.currentTheme != newTheme) {
      feld.setTheme(newTheme)
      statusText = "Thema der Bilder geändert"
      publish(new ThemeChanged(newTheme))  
    } else { statusText = "Das aktuelle Thema ist schon das geforderte" }

  }

  def selectCell(cellCoords: (Int, Int)) = {
   var tuples: (List[Coordinates], List[Coordinates], Coordinates, Boolean)=  feld.tryOpen(cellCoords._1, cellCoords._2) 
   var toClose=  tuples._1
   var toGuessed= tuples._2
   var toOpen = tuples._3
   
   if(toClose !=null){
    publish(new CellsClosed(toClose))
   }
   if(toGuessed !=null){
    publish(new CellsGuessed(toGuessed))
   }
   if(toOpen !=null){
    publish(new CellOpened(toOpen))
   }
    
    if( tuples._4){
     publish(new GameOver)
   }
    
  }

  def reset = {
    feld.reset
    statusText ="Zellen werden zurückgesetzt"
    publish(new FieldReset) 
   
  }
  
  def resize(newSize:Int) ={
    if(newSize!=feld.dimension){
      feld.resize(newSize)
      statusText="Spielgrösse verändert" 
      publish(new FeldResize(newSize))
    }
    else statusText ="Das Spiel ist schon in der geforderten grösse"
  }
  
  

  
 
  def currentTheme = feld.currentTheme

}