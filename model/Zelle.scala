package model



class Zelle (val row: Int, val col: Int){

  //Think: should we move open and guessed logic to Feld?
  var pictureNr= 0
  var open = false
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
 
  def getOpen: Boolean = open
  def getGuessed: Boolean = guessed
  

}