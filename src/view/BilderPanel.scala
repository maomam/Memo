package view

import model.Coordinates
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
  val unguessedIcon = new ImageIcon(imageDirectory + "ControlPictures/question.png")
  var allButtons = createButtons()
  
  deafTo(controller)
  listenTo(controller)
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
    val newIcon = new ImageIcon(addressString.toString);
    newIcon
  }

  private def openButton(c: Coordinates): Unit = {
    println(allButtons.length + " = " + controller.fieldSize)
    println(allButtons(1).length)
    allButtons(c._1)(c._2).icon = findPicture(c._1, c._2)
  }

  def button(Row: Int, Column: Int) = new Button {
    preferredSize = new Dimension(60, 60)
    action = Action("") {
      controller.selectCell(Row, Column)
    }
    icon = unguessedIcon
  }

  def createButtons(): Array[Array[Button]] = {
    contents.clear
    val temp = Array.ofDim[Button](controller.fieldSize, controller.fieldSize)
    for (
      i <- 0 to controller.fieldSize - 1;
      j <- 0 to controller.fieldSize - 1;
      b = button(i, j)
    ) {
      temp(i)(j) = b
      contents += b
      revalidate()
      repaint()
     
    }
    println ("field size = " + controller.fieldSize + " = " + temp.length)
    temp
  }

  def redrawPanel (isGameOver: Boolean)= {
     for (r <- 0 to (controller.fieldSize - 1)) {
      for (c <- 0 to (controller.fieldSize - 1)) {
        if(isGameOver){
        allButtons(r)(c).icon = findPicture(r, c)
        allButtons(r)(c).visible = true
        
        }else{allButtons(r)(c).icon = unguessedIcon}
      }
    }
    repaint
  }


}