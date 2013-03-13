package view

import model._
import scala.swing._
import scala.swing.event._
import controller.Controller
import scala.swing.Button


class BilderPanel (controller: Controller, size: Int) extends GridPanel(size, size) {
  val InitialColour = new Color(200, 200, 255)
 
 
  var gameSize = size
  var cellGuessed= false
  var Row =0
  var Column =0
  listenTo(controller.feld)
  
  var allButtons = Array.ofDim[Button](size, size)
  reactions += {
    case e: CurrentGuessOver => {
    

    }
  }
 def getZelle(reihe: Int, spalte: Int): Zelle = {

    return controller.feld((reihe, spalte))
  }
 
 def button(a: Int, b: Int) = new Button {
    Row = a
    Column = b
    preferredSize = new Dimension(60, 60)

    background = java.awt.Color.WHITE

    reactions += {
      case e: CellChanged =>
        setBackground
      case ButtonClicked(buttons) =>

         if (cellGuessed == true) {
            background = java.awt.Color.GREEN
                
            setBackground
          } else {
            background = java.awt.Color.RED
            preferredSize_=(new Dimension(60, 60))

          }
           }
  }

 def createButtons {

    contents.clear()
    background = java.awt.Color.BLACK

    for (m <- 0 to (allButtons.length - 1)) {
      for (n <- 0 to (allButtons.length - 1)) {

        var buttons = button(m, n)

        allButtons(m)(n) = buttons
        contents += buttons

      }

    }

  }
  createButtons
    def setSize(newSize: Int) = {
    allButtons = Array.ofDim[Button](newSize, newSize)
    gameSize = newSize

    gameSize = newSize

  }
  def redraw = {

    contents.clear()
    allButtons = Array.ofDim[Button](gameSize, gameSize)
    createButtons
  
    repaint


  }
  def setAlleButtonSize(anzahl: Int) = {

    allButtons = Array.ofDim[Button](anzahl, anzahl)
    createButtons

  }
 
 def setBackground = {
    while (allButtons.length != controller.feld.zellen.length) {
      setAlleButtonSize(controller.feld.zellen.length)
    }

    for (k <- 0 to (allButtons.length - 1)) {
      for (l <- 0 to (allButtons.length - 1)) {

        if (getZelle(k, l).getGuessed == true) {
          if (getZelle(k, l).getGuessed == true) {
            allButtons(k)(l).background = java.awt.Color.RED
          } else {
            allButtons(k)(l).background = java.awt.Color.GREEN
          }
        } else {
          if (getZelle(k, l).guessed == true) {
            allButtons(k)(l).background = java.awt.Color.BLACK
          } else {
            allButtons(k)(l).background = java.awt.Color.GRAY
          }

        }
      }
    }

  }
}