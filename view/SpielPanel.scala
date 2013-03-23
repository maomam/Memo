package view

import model.Coordinates
import model._
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

class BilderPanel(controller: Controller, size: Int) extends GridPanel(size, size) {

  val imageDirectory = "src/images/"
  val unguessedIcon = new ImageIcon("src/images/ControlPictures/question.png")
  var gameSize = size
  var allButtons = Array.ofDim[Button](size, size)
  listenTo(controller.feld)
  createButtons

  reactions += {
    case e: CellsGuessed => {
      Thread.sleep(1000)
      hideButtons(e.guessedCells)
    }
    case e: CellsClosed => {
      Thread.sleep(1000)
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
    var fileName = controller.feld(c._1, c._2).pictureNr.toString
    var folderName = controller.currentTheme.toString()
    var addressString = new StringBuilder()
    addressString.append(imageDirectory)
    addressString.append(folderName)
    addressString.append("/")
    addressString.append(fileName)
    addressString.append(".jpg")
    var imgURL = new File(addressString.toString)
    var image: Image = ImageIO.read(imgURL)
    val newIcon = new ImageIcon(image);
    newIcon
  }

  private def openButton(c: Coordinates): Unit = {
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
    contents.clear()
    for (m <- 0 to (allButtons.length - 1)) {
      for (n <- 0 to (allButtons.length - 1)) {
        var buttons = button(m, n)
        allButtons(m)(n) = buttons
        contents += buttons
      }
    }
  }

  def redrawPanel = {
    allButtons = Array.ofDim[Button](gameSize, gameSize)
    createButtons
    repaint
  }

  def showAllPictures = {
    redrawPanel
    for (r <- 0 to (allButtons.length - 1)) {
      for (c <- 0 to (allButtons.length - 1)) {
        allButtons(r)(c).icon = findPicture(r, c)
      }
    }

  }

}