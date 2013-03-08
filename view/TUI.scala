package view
import controller._
import model1.Feld
import util._
import scala.swing.Reactor


class TUI(var controller: Controller) extends Reactor {
listenTo(controller)

 printTui
  reactions += {
    case e: FeldResize => printTui
    case e: CellChanged => printTui
  }
def printTui = {
    println("DAS SPIELFELD:")
    println(controller.feld.toString())
    
  }

  var size = controller.feld.zellen.length
 
  println("Sie haben folgende Auswahlmoeglichkeiten: Groesse dies Spielfelds veraendern (2,5 oder 10 eingeben), q- Spiel verlassen")
  def readInput(eingabe: String) = {
    
  }
}