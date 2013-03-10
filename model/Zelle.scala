package model
class Zelle (row: Int, col: Int){
  //Think: should we move open and guessed logic to Feld?
  var pictureNr= 0
  var open = false
  val reihe = row
  val spalte = col
  var guessed = false
  
 def setPictureNr (inhalt: Int){
  pictureNr = inhalt
  
  }
  def setOpen (variable: Boolean){
  open = variable
  
  }
def setGuessed = {
  guessed = true
  open = true
}
 
  def getReihe: Int = reihe
  def getSpalte: Int = spalte
  def getOffen: Boolean = open
  def getGuessed: Boolean = guessed
  

}