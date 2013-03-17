package controller

import scala.math.sqrt
import scala.util.Random
import model.Feld
import util._
//import model1.Zelle
import javax.swing.JOptionPane

class Controller(var feld: Feld) {
  var statusText = ""
  
  def setStatusText(text: String) { this.statusText = text }
  def spielfertig: Boolean = { feld.gameOver}
  def solve = { feld.solve; statusText = "Spiel beendet";}
  

 
  
 def changeTheme(newTheme :Int)={
   if(feld.currentTheme != newTheme){
     feld.setTheme(newTheme)
   }
   
 }

  def selectCell(cellCoords: (Int,Int)) = 
    feld.tryOpen(cellCoords._1, cellCoords._2)
 
  def reset(size :Int) = {
    feld.reset(size)
     
   
  }

  
 

}