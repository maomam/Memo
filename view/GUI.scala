package view
import controller.Controller
import model._
import scala.swing._
import scala.swing.Swing.LineBorder
import scala.swing.event._
import javax.swing.ImageIcon

class CellClicked(val row: Int, val column: Int) extends Event

class GUI(controller: Controller) extends Frame {
  var groesse = controller.feld.zellen.length
  listenTo(controller.feld)
  var peopleIcon = new ImageIcon("C:/Users/daryna.ariamnova/Documents/eclipse/workspace/Memospiel/src/images/ControlPictures/People.png")
  var fruitsIcon = new ImageIcon("C:/Users/daryna.ariamnova/Documents/eclipse/workspace/Memospiel/src/images/ControlPictures/fruits.png")
  var fashionIcon = new ImageIcon("C:/Users/daryna.ariamnova/Documents/eclipse/workspace/Memospiel/src/images/ControlPictures/fashion.png")
  var countriesIcon = new ImageIcon("C:/Users/daryna.ariamnova/Documents/eclipse/workspace/Memospiel/src/images/ControlPictures/countries.png")

  var statusline = new Label(controller.statusText)
  var cells = new BilderPanel(controller, groesse)
  def UserButtons(groesse: Int): BilderPanel = {
    cells = new BilderPanel(controller, groesse)
    cells
  }
  title = "Memospiel"
    reactions += {
    case e: FeldResize => resize(e.newSize)
    case e: CellChanged => redraw
    case e: GameOver => endGame
    case e: ThemeChanged =>
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
        controller.setStatusText("Spielfeld ist schon 2 Zellen gross")
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

        controller.setStatusText("Spielfeld ist schon 4 Zellen gross")
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
        //make this right here!
        controller.setStatusText("Spielfeld ist schon 8 Zellen gross")
      } else {

        groesse = 8
        controller.reset(8)
        

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
    contents += resetGame
    contents += spiel6
    contents += spiel4
    contents += spiel8
    contents += loesen
    contents += statusline
  }
  val people = new Button {
    action = Action("People") {
    controller.changeTheme(2)
    }
    preferredSize_=(new Dimension(120, 60))
    icon = peopleIcon
  }
  val fruits = new Button {
    action = Action("Fruits") {
    controller.changeTheme(1)
    }
    preferredSize_=(new Dimension(120, 60))
    icon = fruitsIcon
  }
  val fashion = new Button {
    action = Action("Fashion") {
    controller.changeTheme(3)
    }
    preferredSize_=(new Dimension(120, 60))
    icon = fashionIcon
  }

  val countries = new Button {
    action = Action("Countries") {
    controller.changeTheme(4)
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

    add(UserButtons(groesse), BorderPanel.Position.Center)
    visible = true

  }

  

  def endGame = {
    if (controller.feld.gameOver == true) {
      contents = new BorderPanel {
        add(functionPanel, BorderPanel.Position.South)
        add(picturesPanel, BorderPanel.Position.East)
        add(new FlowPanel {
        }, BorderPanel.Position.South)
        add(cells, BorderPanel.Position.West)

      }
    }
  }

  def resize(newSize: Int) = {
    groesse = newSize
    cells = new BilderPanel(controller, newSize)
    cells.setAlleButtonSize(newSize)

    contents = new BorderPanel {
      add(functionPanel, BorderPanel.Position.South)
      add(picturesPanel, BorderPanel.Position.East)
      add(new FlowPanel {
      }, BorderPanel.Position.North)
      add(cells, BorderPanel.Position.West)

    }

  }

  def redraw = {

   /* if (controller.feld.gameOver | controller.feld.gameIsOver == true) {
      contents = new BorderPanel {
        add(functionPanel, BorderPanel.Position.South)
        add(picturesPanel, BorderPanel.Position.East)
        add(new FlowPanel {
        }, BorderPanel.Position.North)
        add(cells, BorderPanel.Position.West)

      }
    } else {*/
      contents = new BorderPanel {
        add(functionPanel, BorderPanel.Position.South)
        add(picturesPanel, BorderPanel.Position.East)
        add(new FlowPanel {

        }, BorderPanel.Position.North)
        add(cells, BorderPanel.Position.West)

        cells = new BilderPanel(controller, groesse)

      
    }

    repaint()

  }

}