package view
import controller._
import model._
import util._
import scala.swing.Reactor


class TUI(var controller: Controller) extends Reactor {
listenTo(controller.feld)

 printTui
  reactions += {
    case e: FeldResize => printTui
    case e: CellChanged => printTui
  }

println("Sie haben folgende Auswahlmoeglichkeiten: \n" +
  		"q - Spiel verlassen,\n" +
  		"l - Spiel lösen und beenden,\n" +
  		"r - Spielfeld zurücksetzen,\n" +
  		"size <s> - Groesse dies Spielfelds veraendern (2,4 oder 8 eingeben),\n " +
  		"<x> - Zelle mit der Nummer <x> öffnen")

 var size = controller.feld.zellen.length
 def cellNumberToCoords(number:Int): (Int, Int) = {
    val fieldD= controller.feld.dimension
    ((number - 1) / fieldD, (number - 1) % fieldD)
  }
  
  def printTui = {
    println("DAS SPIELFELD:")
    println(controller.feld.toString())
    
  }
  
  
  def readInput(eingabe: String) = {
    var continue = true
    eingabe match {
      case "l" => controller.solve
      case "r" => {
        controller.reset(size)
        println("Das  Feld wurde zurueckgesetzt")
      }
      // rewrite into single case 
      case "size 8" => controller.reset(8);
      case "size 4" => controller.reset(4); 
      case "size 2" => controller.reset(2); 
      case numberString => {
        val number = numberString.toInt
        val fieldDim = controller.feld.dimension
        if (number >= 1 && number <= fieldDim*fieldDim){
          controller.selectCell(cellNumberToCoords(number)) 
        }
      }
    }
    continue
  }
}