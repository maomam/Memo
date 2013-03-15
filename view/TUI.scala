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
    println(fieldToString)
    
  }
  
  
  def readInput(eingabe: String) = {
    var continue = true
    eingabe match {
      case "l" => {controller.solve; continue=false;}
      case "q" => continue =false
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
        else{ println("Falsche Eingabe")}
      }
      
    }
    continue
  }
   def fieldToString= {
     val feld =controller.feld
     val dim =feld.dimension
    val lineseparator = ("+--" + ("--" * (dim / 2))) * dim + "+\n"
    val line = ("|" + (" " * (dim / 2)) + ("xx" + (" " * (dim / 2)))) * dim + "|\n"
    var box = "\n" + (lineseparator + (line)) * dim + lineseparator
    var n = 1
    for (reihe <- 0 to dim - 1) {
      for (spalte <- 0 to dim - 1) {

        if (feld(reihe, spalte).getGuessed == false) {
          (box = box.replaceFirst("xx", feld(reihe, spalte).open.toString()))
          (box = box.replaceFirst("false", n.toString.padTo(2," ").mkString))
          (box = box.replaceFirst("true", "##"))
        } else {
          (box = box.replaceFirst("xx", "**"))

        }
       n+=1
      }
    }
    box
  }
}