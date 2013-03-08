package view
import scala.swing._
import scala.swing.event._
import controller.Controller
import model1.Zelle
import controller.CellChanged
import controller.VersuchBeendet
import scala.swing.Button
class BilderPanel (controller: Controller, size: Int) extends GridPanel(size, size) {
  val InitialfarbeSpieler = new Color(200, 200, 255)
  val Schiffgesetzt = new Color(192, 255, 192)
  val Schiffgetroffen = new Color(190, 245, 170)
  val SchiffNichtgetroffen = new Color(150, 160, 162)
 
  var spielSize = size
  var bildErraten= false
  var Reihe =0
  var Spalte =0
  
  
  var alleButtons = Array.ofDim[Button](size, size)
  reactions += {
    case e: VersuchBeendet => {
    

    }
  }
 def getZelle(reihe: Int, spalte: Int): Zelle = {

    return controller.cell(reihe, spalte)
  }
 
 def button(a: Int, b: Int) = new Button {
    Reihe = a
    Spalte = b
    preferredSize = new Dimension(60, 60)

    background = java.awt.Color.GRAY

    reactions += {
      case e: CellChanged =>
        setBackground
      case ButtonClicked(buttons) =>

         if (bildErraten == true) {
            background = java.awt.Color.GREEN
                
            setBackground
          } else {
            background = java.awt.Color.RED
            preferredSize_=(new Dimension(60, 60))

          }
           }
    listenTo(controller)

  }
def createButtons {

    contents.clear()
    background = java.awt.Color.BLACK

    for (m <- 0 to (alleButtons.length - 1)) {
      for (n <- 0 to (alleButtons.length - 1)) {

        var buttons = button(m, n)

        alleButtons(m)(n) = buttons
        contents += buttons

      }

    }

  }
  createButtons
    def setSize(newSize: Int) = {
    alleButtons = Array.ofDim[Button](newSize, newSize)
    spielSize = newSize

    spielSize = newSize

  }
  def redraw = {

    contents.clear()
    alleButtons = Array.ofDim[Button](spielSize, spielSize)
    createButtons
  
    repaint


  }
  def setAlleButtonSize(anzahl: Int) = {

    alleButtons = Array.ofDim[Button](anzahl, anzahl)
    createButtons

  }
  def bildersetzen{
    
   // controller.setPictures
  }
 def setBackground = {
    while (alleButtons.length != controller.feld.zellen.length) {
      setAlleButtonSize(controller.feld.zellen.length)
    }

    for (k <- 0 to (alleButtons.length - 1)) {
      for (l <- 0 to (alleButtons.length - 1)) {

        if (getZelle(k, l).getErraten == true) {
          if (getZelle(k, l).getErraten == true) {
            alleButtons(k)(l).background = java.awt.Color.RED
          } else {
            alleButtons(k)(l).background = java.awt.Color.GREEN
          }
        } else {
          if (getZelle(k, l).guessed == true) {
            alleButtons(k)(l).background = java.awt.Color.BLACK
          } else {
            alleButtons(k)(l).background = java.awt.Color.GRAY
          }

        }
      }
    }

  }
}