package controller

import scala.math.sqrt
import scala.util.Random
import model.Feld
import util._
//import model1.Zelle
import javax.swing.JOptionPane

class Controller(var feld: Feld) {
  var statusText = ""
  var feldGesetzt = false
  var zaehler = 0

  def setFeldGesetzt(gesetzt: Boolean) = { this.feldGesetzt = gesetzt }
  def setStatusText(text: String) { this.statusText = text }
  def spielfertig: Boolean = { feld.gameOver}
  def solve = { feld = feld.solve; statusText = "Spiel beendet";}
  //def updateFeld(feld: Feld) { this.feld = feld }

 
  
  /*def hit(cell1Coordinates1: (Int,Int), cell2Coordinates2: (Int,Int)): Unit = {
   if (feld.isMatch(cell1Coordinates1._1, cell1Coordinates1._2, cell2Coordinates2._1, cell2Coordinates2._1)==true) {
      publish(new CellChanged)
     statusText = "Richtig"
   }
  }*/

  def selectCell(cellCoords: (Int,Int)) = 
    feld.tryOpen(cellCoords._1, cellCoords._2)
 
  def reset = {
    feld = feld.reset
    feldGesetzt = false
    zaehler = 0
    statusText = "Spiel zurueckgesetzt"
  }

  
  def setSize(newSize: Int) = {
    if (feld.dimension == newSize) {
      //think if controller should do this
      setStatusText("Spielfeld ist schon in der benoetigten Groesse")
    } else {
    	//should just resize the existing field
        feld = new Feld(newSize)
        statusText = "Spielgroesse veraendert"
    }
  }

  //move to model publish(new CellChanged)

}