package view

import model._
import scala.swing._
import scala.swing.event._
import controller.Controller
import scala.swing.Button

class BilderPanel(controller: Controller, size: Int) extends GridPanel(size, size) {
  val InitialColour = new Color(200, 200, 255)

  var gameSize = size
 
  var Row = 0
  var Column = 0
  var allButtons = Array.ofDim[Button](size, size)
  listenTo(controller.feld)
  createButtons

  
  reactions += {
    case e: CurrentGuessOver => {
    }
     case e: CellChanged =>
        setBackground
     case e: GameOver =>{
       
     }
       
  }
 

  def button(Row: Int, Column: Int) = new Button {
    preferredSize = new Dimension(60, 60)
    background = java.awt.Color.WHITE
    action = Action(""){
      controller.selectCell(Row, Column)
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

  def redraw = {
    contents.clear()
    allButtons = Array.ofDim[Button](gameSize, gameSize)
    createButtons
//scala.swing.button
    repaint
  }
  
  def setAlleButtonSize(anzahl: Int) = {
    allButtons = Array.ofDim[Button](anzahl, anzahl)
    createButtons
  }

  def setBackground = {
    //remove from here
    if (allButtons.length != controller.feld.zellen.length) {
      setAlleButtonSize(controller.feld.zellen.length)
    }

    for (r <- 0 to (allButtons.length - 1)) {
      for (c <- 0 to (allButtons.length - 1)) {

        if (controller.feld(r, c).getGuessed ) {
          //if (controller.feld(r, c).getGuessed) {
            allButtons(r)(c).background = java.awt.Color.GRAY
          /*} else {
            allButtons(r)(c).background = java.awt.Color.GREEN
          }*/
        } else {
          /*if (controller.feld(r, c).guessed == true) {
            allButtons(r)(c).background = java.awt.Color.BLACK
          } else {*/
          if(controller.feld(r, c).getOpen)
            allButtons(r)(c).background = java.awt.Color.RED
            else {allButtons(r)(c).background = java.awt.Color.WHITE}
        }
      }
    }

  }
}