

import view._
import controller._
import model._
import view.TUI
import util.Theme


object Memospiel {
  val controller = new Controller(new Feld(4,Theme.fruits))
  val userTui = new TUI(controller)
  val userGui = new GUI(controller)
def main(args: Array[String]) {

  
    userGui.visible = true
    userGui.resizable = false
    userGui.pack

    while ((userTui.readInput(readLine())) == true) {
      
    }

  }
}