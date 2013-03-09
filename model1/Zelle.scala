package model1
class Zelle (row: Int, col: Int){
  
  var pictureNr= 0
  var offen = false
  var reihe = row
  var spalte = col
  var guessed = false
  
 def setPictureNr (inhalt: Int){
  pictureNr = inhalt
  
  }
  def setOffen (variable: Boolean){
  offen = variable
  
  }
def setGuessed = {guessed = true
}
 
  def getReihe: Int = reihe
  def getSpalte: Int = spalte
  def getOffen: Boolean = offen
  def getGuessed: Boolean = guessed
  

}