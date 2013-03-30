package model



class Zelle (val row: Int, val col: Int){

  
  var _pictureNr= 0
  var _open = false
  var _guessed = false
    
 def pictureNr_ (inhalt: Int){
  _pictureNr = inhalt
  
  }
  def open_ (variable: Boolean){
  _open = variable
  }
  def guessed_ (variable: Boolean){
  _guessed = variable
  }
  
def setGuessed = {
  _guessed = true
  _open = true
}
 
  def open: Boolean = _open
  def guessed: Boolean = _guessed
  def pictureNr: Int =_pictureNr
  
  

}