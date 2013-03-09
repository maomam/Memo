package controller
import scala.math.sqrt
import scala.util.Random
import model1.Feld
import util._
import model1.Zelle
import scala.swing.Publisher
import scala.swing.event.Event
import javax.swing.JOptionPane
case object GameOver extends Event
case class CellChanged extends Event
case class VersuchBeendet(erfolg: Boolean) extends Event
case class FeldResize(newSize: Int) extends Event
class Controller(var feld: Feld) extends Publisher {
  var statusText = ""
  var feldGesetzt = false
  var zaehler = 0

  def setFeldGesetzt(gesetzt: Boolean) = { this.feldGesetzt = gesetzt }
  def setStatusText(text: String) { this.statusText = text }
  def spielfertig: Boolean = { if (feld.gameOver == true) { true } else { false } }
  def solve = { feld = feld.solve; statusText = "Spiel beendet"; publish(new CellChanged) }
  def updateFeld(feld: Feld) { this.feld = feld }
  def cell(row: Int, col: Int) = feld.cell(row, col)
 
    
  

  def hit(cell1Coordinates1: (Int,Int), cell2Coordinates2: (Int,Int)): Unit = {
   if (feld.checkGuess(cell1Coordinates1._1, cell1Coordinates1._2, cell2Coordinates2._1, cell2Coordinates2._1)==true) {
      publish(new CellChanged)
     statusText = "Richtig"
   }
     
     
    
   
  }



 
  def reset = {
    feld = feld.reset
    feldGesetzt = false
    zaehler = 0
    statusText = "Spiel zurueckgesetzt"
    publish(new FeldResize(feld.dimension))
  }

  
  def setSize(newSize: Int) = {
    if (feld.dimension == newSize) {
      setStatusText("Spielfeld ist schon in der benoetigten Groesse")
    } else {
      
        feld = new Feld(newSize)
        statusText = "Spielgroesse veraendert"
        publish(new FeldResize(newSize))
      
    }
  }

  publish(new CellChanged)

}