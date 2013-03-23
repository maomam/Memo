package controller

import scala.math.sqrt
import scala.util.Random
import model.Feld
import util.Theme
import util._
import javax.swing.JOptionPane
import util.Theme

class Controller(var feld: Feld) {
  var statusText = "Spiel angefangen"

  def solve = { feld.solve; statusText = "Spiel beendet"; }

  def changeTheme(newTheme: Theme.Value) = {
    if (feld.currentTheme != newTheme) {
      feld.setTheme(newTheme)
      statusText = "Thema der Bilder geändert"
    } else { statusText = "Das aktuelle Thema ist schon das geforderte" }

  }

  def selectCell(cellCoords: (Int, Int)) =
    feld.tryOpen(cellCoords._1, cellCoords._2)

  def reset(size: Int) = {
    feld.reset(size)
    if(size==feld.dimension){
      statusText ="Zellen werden zurückgesetzt"
    }else{statusText="Spielgrösse verändert"}

  }
  def resizeTheSameSize ={
    statusText ="Das Spiel ist schon in der geforderten grösse"
  }
  def currentTheme = feld.currentTheme

}