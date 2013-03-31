package view

import model.Coordinates
import model._
import controller._
import scala.swing._
import scala.swing.event._
import controller.Controller
import scala.swing.Button
import javax.swing.ImageIcon
import java.awt.Image
import java.net.URL
import javax.imageio.ImageIO
import java.io.IOException
import java.io.File

class BilderPanel(controller: Controller) extends GridPanel(controller.fieldSize, controller.fieldSize) {

  val imageDirectory = "src/images/"
  val unguessedIcon = new ImageIcon("src/images/ControlPictures/question.png")
  var allButtons = Array.ofDim[Button](controller.fieldSize, controller.fieldSize)
  
  listenTo(controller)
  //TODO: make this method return an array of buttons
  createButtons
  reactions += {
    case e: CellsGuessed => {
      hideButtons(e.guessedCells)
    }
    case e: CellsClosed => {
      closeButtons(e.cellsToClose)
    }
    case e: CellOpened =>
     openButton(e.cellToOpen)
  }

  private def hideButtons(c: List[Coordinates]): Unit =
    c.foreach(x => allButtons(x._1)(x._2).visible = false)

  private def closeButtons(c: List[Coordinates]): Unit =
    c.foreach(x => allButtons(x._1)(x._2).icon = unguessedIcon)

  private def findPicture(c: Coordinates): ImageIcon = {
    var fileName = controller.pictureNr(c._1, c._2).toString
    var folderName = controller.currentTheme.toString()
    var addressString = new StringBuilder()
    addressString.append(imageDirectory)
    addressString.append(folderName)
    addressString.append("/")
    addressString.append(fileName)
    addressString.append(".jpg")
    var imgURL = new File(addressString.toString)
    var image: Image = ImageIO.read(imgURL)
    //see if we can simplify this method
    val newIcon = new ImageIcon(image);
    newIcon
  }

  private def openButton(c: Coordinates): Unit = {
    println ("(" + c._1 + ", " + c._2 + ")")
    allButtons(c._1)(c._2).icon = findPicture(c._1, c._2)
  }

  def button(Row: Int, Column: Int) = new Button {
    preferredSize = new Dimension(60, 60)
    action = Action("") {
      controller.selectCell(Row, Column)
    }
    //Why the hell does this matter?
    icon = unguessedIcon
  }

  def createButtons {
    for (m <- 0 to (controller.fieldSize - 1)) {
      for (n <- 0 to (controller.fieldSize - 1)) {
        var buttons = button(m, n)
        allButtons(m)(n) = buttons
        contents += buttons
      }
    }
  }

  def redrawPanel = {
    allButtons = Array.ofDim[Button](controller.fieldSize, controller.fieldSize)
    createButtons
    repaint
  }

  def showAllPictures = {
    redrawPanel
    for (r <- 0 to (controller.fieldSize - 1)) {
      for (c <- 0 to (controller.fieldSize - 1)) {
        allButtons(r)(c).icon = findPicture(r, c)
      }
    }

  }

}