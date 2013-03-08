package view
import controller.Controller
import scala.swing._
import scala.swing.Swing.LineBorder
import scala.swing.event._
import controller.FeldResize
import controller.CellChanged
import javax.swing.ImageIcon
import controller.GameOver
import controller.VersuchBeendet
class CellClicked(val row: Int, val column: Int) extends Event

class GUI(controller: Controller) extends Frame {
  var groesse = controller.feld.zellen.length
  listenTo(controller)
  var statusline = new Label(controller.statusText)
  var cells = new BilderPanel(controller, groesse)
  def UserButtons(groesse: Int): BilderPanel = {
    cells = new BilderPanel(controller, groesse)
    cells
  }
  title = "Memospiel"

  val neustarten = new Button { //Button zum Neustarten des Spiels
    action = Action("Spiel neu Starten") {
      controller.reset
      statusline.text = controller.statusText
      //      computercells.pcSchiffeSetzen(pccontroller.feld.zellen.length)
    }
  }

  val spiel2 = new Button { //Button zu aendern der Spielfeldgroe�e auf 2
    action = Action("Spielgroesse 2") {
      if (groesse == 2) {
        controller.setStatusText("Spielfeld ist schon 2 Zellen gross")
      } else {
        groesse = 2
        controller.reset
        newSize(2)

      }
      statusline.text = controller.statusText
    }
  }
  val spiel4 = new Button { //Button zu aendern der Spielfeldgroe�e auf 5
    action = Action("Spielgroesse 4") {
      if (groesse == 4) {

        controller.setStatusText("Spielfeld ist schon 4 Zellen gross")
      } else {

        groesse = 4
        controller.reset
        newSize(4)

      }
      //      redraw
      statusline.text = controller.statusText
    }
  }
  val spiel8 = new Button { //Button zu aendern der Spielfeldgroe�e auf 10
    action = Action("Spielgroesse 8") {
      if (groesse == 8) {
        controller.setStatusText("Spielfeld ist schon 8 Zellen gross")
      } else {
        
          //                       controller.setSize(10); pccontroller.setSize(10)
          groesse = 8
          controller.reset
          newSize(8)
          //          redraw
       
      }
      statusline.text = controller.statusText
    }
  }
  val loesen = new Button { //Button zum Loesen des Spiels
    action = Action("Spiel loesen") {
      controller.solve
      controller.setFeldGesetzt(false)
      statusline.text = controller.statusText
    }
  }

  def funktionsleiste = new FlowPanel {
    contents += neustarten
    contents += spiel2
    contents += spiel4
    contents += spiel8
    contents += loesen
    contents += statusline
  }

  contents = new BorderPanel {

    add(funktionsleiste, BorderPanel.Position.North)

    add(new FlowPanel {

    }, BorderPanel.Position.South)

    add(UserButtons(groesse), BorderPanel.Position.Center)
    visible = true

  }

  reactions += {

    case e: FeldResize => resize(e.newSize)

    case e: CellChanged => redraw

    case GameOver => endGame

  }

  def endGame = {
    if (controller.feld.spielFertig == true) {
      contents = new BorderPanel {
        add(funktionsleiste, BorderPanel.Position.North)
        add(new FlowPanel { //ueberschrift fuer die beiden Spielfelder
        }, BorderPanel.Position.South)
        add(cells, BorderPanel.Position.West)
        add(endGamePanel, BorderPanel.Position.Center)

      }
    }
  }

  def endGamePanel: Button = {
    var ende = new Button //var ende = new Label("Spiel ist vorbei",new ImageIcon("c:\\test\\test.png"),Alignment.Right)
    if (controller.feld.spielFertig == true) {
      ende = new Button("Spiel ist vorbei\n Der PC hat gewonnen")
    } else {
      ende = new Button("Spiel ist vorbei\n Sie haben gewonnen")
    }
    ende
  }
  def resize(newSize: Int) = {
    //    println("HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH")
    groesse = newSize

    // cells.setSize(newSize)
    cells = new BilderPanel(controller, newSize)
    cells.setAlleButtonSize(newSize)

    contents = new BorderPanel {
      add(funktionsleiste, BorderPanel.Position.North)
      add(new FlowPanel { //ueberschrift fuer die beiden Spielfelder
      }, BorderPanel.Position.South)
      add(cells, BorderPanel.Position.Center)

    }
    //    repaint()
  }

  def redraw = {

    if (controller.feld.spielFertig == true) {
      controller.setFeldGesetzt(false)

      //      cells = new SpielerPanel(controller, groesse, schiffsleiste)
      //        computercells = new PCPanel(pccontroller, groesse, controller)
      contents = new BorderPanel {
        add(funktionsleiste, BorderPanel.Position.North)
        add(new FlowPanel { //ueberschrift fuer die beiden Spielfelder
        }, BorderPanel.Position.South)
        add(cells, BorderPanel.Position.West)
        add(endGamePanel, BorderPanel.Position.Center)

      }
    } else {
      contents = new BorderPanel {
        add(funktionsleiste, BorderPanel.Position.North)
        add(new FlowPanel { //ueberschrift fuer die beiden Spielfelder

        }, BorderPanel.Position.South)
        add(cells, BorderPanel.Position.West)
        add(endGamePanel, BorderPanel.Position.Center)
        cells = new BilderPanel(controller, groesse)

      }
    }

    repaint()

  }

  def newSize(newSize: Int) {
    groesse = newSize
    cells.contents.clear()
    controller.reset
    controller.setSize(newSize)

  }

}