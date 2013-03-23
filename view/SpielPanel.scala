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

class BilderPanel(controller: Controller, size: Int) extends GridPanel(size, size) {

  val imageDirectory = "images/"
  val unguessedIcon = new ImageIcon("C:/Users/daryna.ariamnova/Documents/eclipse/workspace/Memospiel/src/images/ControlPictures/question.png");
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
    var fileName = controller.feld(c._1, c._2).pictureNr
    var folderName = controller.currentTheme
    var imgURL = getClass().getResource(imageDirectory + folderName + fileName);
    var image: Image = null;
    var newimg :Image =null
     try {
    image = ImageIO.read(imgURL);
    var newimg = image.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH);
    } catch {
      case  ioe: IOException => println("Image konnte nicht geladen werden")
       case e: Exception => println ("Keine Ahnung was für Exeption")
       case n: NullPointerException => println("Null Pointer Exception")}
    
    val newIcon = new ImageIcon(newimg);
    newIcon
  }

  private def openButton(c: Coordinates): Unit = {
    allButtons(c._1)(c._2).icon = findPicture(c._1, c._2)
  }

  def button(Row: Int, Column: Int) = new Button {
    preferredSize = new Dimension(60, 60)
    icon = unguessedIcon
    action = Action("") {
      controller.selectCell(Row, Column)
    }
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

  def showBackground = {
    //remove from here
   // if (allButtons.length != controller.feld.zellen.length) {
   //   changePanelSize(controller.feld.zellen.length)
 //   }
    
    for (r <- 0 to (allButtons.length - 1)) {
      for (c <- 0 to (allButtons.length - 1)) {

        if (controller.feld(r, c).guessed) {
          allButtons(r)(c).visible = false
        } else {
          if (controller.feld(r, c).open)
            allButtons(r)(c).icon = findPicture(r,c)
          else { allButtons(r)(c).icon = unguessedIcon }
        }
      }
    }

  }
    def showAllPictures = {
    for (r <- 0 to (allButtons.length - 1)) {
      for (c <- 0 to (allButtons.length - 1)) {
         allButtons(r)(c).icon = findPicture(r,c)
     }
    }

  }
  
}