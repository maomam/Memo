package view
import controller.Controller
import scala.swing._
import scala.swing.Swing.LineBorder
import scala.swing.event._
import javax.swing.ImageIcon
import controller._
import util.Theme


class CellClicked(val row: Int, val column: Int) extends Event

class GUI(controller: Controller) extends Frame {
  private val picturesPath = "src/images/ControlPictures/"
  var peopleIcon = new ImageIcon(picturesPath + "People.png")
  var fruitsIcon = new ImageIcon(picturesPath + "fruits.png")
  var fashionIcon = new ImageIcon(picturesPath + "fashion.png")
  var countriesIcon = new ImageIcon(picturesPath+ "countries.png")
  var statusline = new Label(controller.statusText)
  var bilderPanel = new BilderPanel(controller)

  listenTo(controller)
  title = "Memospiel"
  reactions += {
    case e: FeldResize => {
      statusline.text = controller.statusText
      resize(e.newSize)
    }
    case e: FieldSolved => {
      statusline.text = controller.statusText  
      redraw(true)
    }
    case e: FieldReset => {
      statusline.text = controller.statusText 
      redraw(false)
    }
    case e: GameOver => {
     statusline.text = controller.statusText 
      redraw(true)
    }
    case e: ThemeChanged =>{ 
      statusline.text = controller.statusText 
      redraw(false)
    }
  }
 
  val resetGame = new Button {
    action = Action("Spiel neu Starten") {
    controller.reset
     }
  }

  val spiel6 = new Button {
    action = Action("Spielgroesse 6") {
      controller.resize(6)
         }
  }
  val spiel4 = new Button {
    action = Action("Spielgroesse 4") {
      controller.resize(4)
       }
  }
  val spiel8 = new Button {
    action = Action("Spielgroesse 8") {
      controller.resize(8)
      }
  }
  val spiel2 = new Button {
    action = Action("Spielgroesse 2") {
      controller.resize(2)
      }
  }
  val loesen = new Button {
    action = Action("Spiel loesen") {
      controller.solve
       }
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
  
  createContents()
   
  def createContents(): Unit =
      contents = new BorderPanel {
      add(functionPanel, BorderPanel.Position.South)
      add(picturesPanel, BorderPanel.Position.East)
      add(bilderPanel, BorderPanel.Position.Center)
      revalidate()
      repaint()
      visible = true
          
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
  
  def picturesPanel = new GridPanel(4, 1) {
    contents += people
    contents += fruits
    contents += fashion
    contents += countries
  }

 
  def resize(newSize: Int) = {
    bilderPanel.deafTo(controller)
    bilderPanel = new BilderPanel(controller)
    createContents()
    repaint()
  }

  def redraw(isThisGameOver: Boolean) = {
    bilderPanel.redrawPanel(isThisGameOver)
    if(isThisGameOver){Dialog.showMessage(message = "Spiel beendet. Sie haben " + controller.counter + " Zuege benoetigt.")}
    createContents()
    repaint()
  }

 
}