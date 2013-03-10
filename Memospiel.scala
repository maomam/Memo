

import view._
import controller._
import model._

object Memospiel {
  val controller = new Controller(new Feld(4))
  val userTui = new TUI(controller)
  val userGui = new GUI(controller)
def main(args: Array[String]) {

  
    userGui.visible = true
    userGui.resizable = false

    while ((userTui.readInput(readLine())) == true) {
      
    }

  }
}