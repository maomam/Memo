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
reactions += {
    case e: FeldResize => printTui
    case e: CellChanged => printTui
  }

  var size = controller.feld.zellen.length
 
  println("Sie haben folgende Auswahlmoeglichkeiten: q- Spiel verlassen, s- Spiel lösen und beenden, r- Spielfeld zurücksetzen,  Groesse dies Spielfelds veraendern (2,4 oder 8 eingeben), ")
  def readInput(eingabe: String) = {
    eingabe match {
      case "s" => {
        
        controller.solve 
       
      }
      case "r" => {
        controller.reset
        println("Das  Feld wurde zurueckgesetzt")
      }
      case "8" => controller.setSize(8);
      case "4" => controller.setSize(4); 
      case "2" => controller.setSize(2); 
      case hit =>
    }
  }
}