package view
import controller._
import model._
import util._
import scala.swing.Reactor
import java.io.IOException
import controller.Controller
import model.Theme


class TUI(var controller: Controller) extends Reactor {
listenTo(controller)


 printTui

  reactions += {
    case e: FeldResize => {printTui
      println(controller.statusText)}
    case e: FieldSolved => {printTui
      println(controller.statusText)}
    case e: FieldReset => {printTui
      println(controller.statusText)}
    case e: CellsGuessed => printTui
    case e: CellsClosed => printTui
    case e: CellOpened => printTui
    case e: GameOver => println(controller.statusText)
    case e: ThemeChanged => {printTui 
      println(controller.statusText)}
  }

println("Sie haben folgende Auswahlmoeglichkeiten: \n" +
  		"q - Spiel verlassen,\n" +
  		"l - Spiel lösen und beenden,\n" +
  		"r - Spielfeld zurücksetzen,\n" +
  		"size <s> - Groesse dies Spielfelds veraendern (2,4 oder 8 eingeben),\n " +
  		"theme <y> -Thema mit der Nummer y öffnen"+
  		"<x> - Zelle mit der Nummer <x> öffnen")

 
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
        controller.reset
        println("Das  Feld wurde zurueckgesetzt")
      }
      // rewrite into single case 
      case "size 8" => controller.resize(8);
      case "size 4" => controller.resize(4); 
      case "size 6" => controller.resize(6); 
      case "size 2" => controller.resize(2);
      case "theme 1" => controller.changeTheme(Theme.people)
      case "theme 2" => controller.changeTheme(Theme.countries)
      case "theme 3" => controller.changeTheme(Theme.fashion)
      case "theme 4" => controller.changeTheme(Theme.fruits)
      case numberString => {
        try {
        val number = numberString.toInt
        val fieldDim = controller.fieldSize
        if (number >= 1 && number <= fieldDim*fieldDim){
          controller.selectCell(cellNumberToCoords(number)) 
        }
        else{ println("Falsche Eingabe. Bitet Spielfeldgrösse beachten")}
      }
       catch {
         case  ioe: IOException => println("IOException. Falsche Eingabe. Bitte Zahl eingeben")
         case  e: Exception => println("Unbekannte Exception. Falsche Eingabe. Bitte Zahl eingeben")
      }
      }
    
      
    }
    continue
  }
   def fieldToString= {
    val feld =controller.feld
    val dim =controller.fieldSize
    val lineseparator = ("+--" + ("--" * (dim / 2))) * dim + "+\n"
    val line = ("|" + (" " * (dim / 2)) + ("xx" + (" " * (dim / 2)))) * dim + "|\n"
    var box = "\n" + (lineseparator + (line)) * dim + lineseparator
    var n = 1
    for (reihe <- 0 to dim - 1) {
      for (spalte <- 0 to dim - 1) {

        if (feld(reihe, spalte).guessed == false) {
          if(feld(reihe, spalte).open){
            controller.currentTheme match {
            case Theme.people =>  (box = box.replaceFirst("xx", ":)"))
            case Theme.fruits => (box = box.replaceFirst("xx", "00"))
            case Theme.countries => (box = box.replaceFirst("xx", "??"))
            case Theme.fashion => (box = box.replaceFirst("xx", "<>"))
              }
          }
            else {
              box = box.replaceFirst("xx", n.toString.padTo(2," ").mkString)
            }
          }
         else {
           box = box.replaceFirst("xx", "  ")
              
        }
       n+=1
      }
    }
    box
  }
}