package controller

import scala.swing.Publisher
import scala.swing.event.Event
import scala.math.sqrt
import scala.util.Random
import model._
import javax.swing.JOptionPane
import util.Theme

case class GameOver extends Event
case class FieldSolved extends Event
case class FieldReset extends Event
case class CellsGuessed(guessedCells: List[Coordinates]) extends Event
case class CellsClosed(cellsToClose: List[Coordinates]) extends Event
case class CellOpened(cellToOpen: Coordinates) extends Event
case class FeldResize(newSize: Int) extends Event
case class ThemeChanged(newTheme: Theme.Value) extends Event

class Controller(var field: Feld) extends Publisher {
  var statusText = "Spiel angefangen"

  def solve = { field.solve; statusText = "Spiel gelöst"; publish(new FieldSolved) }

  def changeTheme(newTheme: Theme.Value) = {
    if (field.currentTheme != newTheme) {
      field.setTheme(newTheme)
      statusText = "Thema der Bilder geändert"
      publish(new ThemeChanged(newTheme))
    } else {
      statusText = "Das aktuelle Thema ist schon das geforderte"
      publish(new ThemeChanged(newTheme))
    }

  }

  def selectCell(cellCoords: (Int, Int)) = {
    val chgCells = field.tryOpen(cellCoords._1, cellCoords._2)

    if (!chgCells.closedCells.isEmpty) publish(new CellsClosed(chgCells.closedCells))
    if (!chgCells.guessedCells.isEmpty) publish(new CellsGuessed(chgCells.guessedCells))
    chgCells.openedCell match {
      case Some(coords) => publish(new CellOpened(coords))
      case None =>
    }
    if (field.gameOver) {
      statusText = "Spiel ist beendet"
      publish(new GameOver)
    }
  }

  def reset = {
    field.reset
    statusText = "Zellen werden zurückgesetzt"
    publish(new FieldReset)

  }

  def resize(newSize: Int) = {
    if (newSize != field.dimension) {
      field.resize(newSize)
      statusText = "Spielgrösse verändert"
      publish(new FeldResize(newSize))
    } else {
      statusText = "Das Spiel ist schon in der geforderten grösse"
      publish(new FeldResize(newSize))
    }
  }

  def pictureNr(c: Coordinates): Int = {
    field(c._1, c._2).pictureNr
  }

  def fieldSize = field.dimension

  def currentTheme = field.currentTheme
  
  def counter = field.counterTried

}