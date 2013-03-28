package view
import controller.Controller
import model._
import scala.swing._
import scala.swing.Swing.LineBorder
import scala.swing.event._
import javax.swing.ImageIcon
import util.Theme


class CellClicked(val row: Int, val column: Int) extends Event

class GUI(controller: Controller) extends Frame {
  var groesse = controller.feld.zellen.length
  listenTo(controller.feld)
  var peopleIcon = new ImageIcon("src/images/ControlPictures/People.png")
  var fruitsIcon = new ImageIcon("src/images/ControlPictures/fruits.png")
  var fashionIcon = new ImageIcon("src/images/ControlPictures/fashion.png")
  var countriesIcon = new ImageIcon("src/images/ControlPictures/countries.png")
  var statusline = new Label(controller.statusText)
  var cells = new BilderPanel(controller, groesse)
  
  title = "Memospiel"
    reactions += {
    case e: FeldResize => resize(e.newSize)
    case e: FieldSolved => showAllpictures
    case e: FieldReset => redraw
    case e: GameOver => endGame
    case e: ThemeChanged => redraw
  }

  val resetGame = new Button {
    action = Action("Spiel neu Starten") {
      controller.reset(groesse)
      statusline.text = controller.statusText

    }
  }
  

  val spiel6 = new Button {
    action = Action("Spielgroesse 6") {
      if (groesse == 6) {
        controller.resizeTheSameSize
        statusline.text = controller.statusText
      } else {
        groesse = 6
        controller.reset(6)
       }
      statusline.text = controller.statusText
    }
  }
  val spiel4 = new Button {
    action = Action("Spielgroesse 4") {
      if (groesse == 4) {
        controller.resizeTheSameSize
       statusline.text = controller.statusText
      } else {
        groesse = 4
        controller.reset(4)
       }
      statusline.text = controller.statusText
    }
  }
  val spiel8 = new Button {
    action = Action("Spielgroesse 8") {
      if (groesse == 8) {
        controller.resizeTheSameSize
        statusline.text = controller.statusText
      } else {
        groesse = 8
        controller.reset(8)
        }
      statusline.text = controller.statusText
    }
  }
  val spiel2 = new Button {
    action = Action("Spielgroesse 2") {
      if (groesse == 2) {
        controller.resizeTheSameSize
        statusline.text = controller.statusText
      } else {
        groesse = 2
        controller.reset(2)
        }
      statusline.text = controller.statusText
    }
  }
  val loesen = new Button {
    action = Action("Spiel loesen") {
      controller.solve
      statusline.text = controller.statusText
    }
  }

  def functionPanel = new FlowPanel {
    contents += spiel2
    contents += spiel4
    contents += spiel6
    contents += spiel8
    contents += loesen
    contents += resetGame
    contents += statusline
  }
  val people = new Button {
    action = Action("") {
    controller.changeTheme(Theme.people)
    }
    preferredSize_=(new Dimension(120, 60))
    icon = peopleIcon
  }
 
  val fruits = new Button {
    action = Action("") {
    controller.changeTheme(Theme.fruits)
    }
    preferredSize_=(new Dimension(120, 60))
    icon = fruitsIcon
  }
  val fashion = new Button {
    action = Action("") {
    controller.changeTheme(Theme.fashion)
    }
    preferredSize_=(new Dimension(120, 60))
    icon = fashionIcon
  }

  val countries = new Button {
    action = Action("") {
    controller.changeTheme(Theme.countries)
    }
    preferredSize_=(new Dimension(120, 60))
    icon = countriesIcon
  }
  def picturesPanel = new GridPanel(4, 1) {
    contents += people
    contents += fruits
    contents += fashion
    contents += countries
  }

  contents = new BorderPanel {
    add(functionPanel, BorderPanel.Position.South)
    add(picturesPanel, BorderPanel.Position.East)
    add(new FlowPanel {
    }, BorderPanel.Position.North)
    add(cells, BorderPanel.Position.Center)
    visible = true
  }

  

  def endGame = {
      cells.showAllPictures
      contents = new BorderPanel {
        add(functionPanel, BorderPanel.Position.South)
        add(picturesPanel, BorderPanel.Position.East)
        add(new FlowPanel {
        }, BorderPanel.Position.North)
        add(cells, BorderPanel.Position.Center)

      }
        repaint()
      Dialog.showMessage(message = "Spiel gelöst")
    
  }

  def resize(newSize: Int) = {
    groesse = newSize
    cells = new BilderPanel(controller, newSize)
    contents = new BorderPanel {
      add(functionPanel, BorderPanel.Position.South)
      add(picturesPanel, BorderPanel.Position.East)
      add(new FlowPanel {
      }, BorderPanel.Position.North)
      add(cells, BorderPanel.Position.Center)

    }

  }

  def redraw = {
    cells.redrawPanel
      contents = new BorderPanel {
      add(functionPanel, BorderPanel.Position.South)
      add(picturesPanel, BorderPanel.Position.East)
      add(new FlowPanel {
      }, BorderPanel.Position.North)
      add(cells, BorderPanel.Position.Center)
    }
    repaint()
  }
  
 def showAllpictures ={
   cells.showAllPictures
     contents = new BorderPanel {
      add(functionPanel, BorderPanel.Position.South)
      add(picturesPanel, BorderPanel.Position.East)
      add(new FlowPanel {
      }, BorderPanel.Position.North)
      add(cells, BorderPanel.Position.Center)
    }
   repaint()
    Dialog.showMessage(message = "Spiel beendet")
 }
}