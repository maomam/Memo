package testModel

import org.junit.runner.RunWith
import model._
import org.specs2.mutable._
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TestCell extends SpecificationWithJUnit{
  "Zelle" should{
    val zelle = new Zelle(0,0)
    var pictureNr = zelle.pictureNr
     var open = zelle.open
     var guessed = zelle.guessed
     
    "not be set" in{
     guessed must be_==(false)
    }
    
     "setOpen" in{
      zelle.open = true
      var newOpen =zelle.open
      newOpen must be_==(true)
      
    }
     
    "setClosed" in{
      zelle.open = false
      var newOpen1 =zelle.open
      newOpen1 must be_==(false)
    }
    
     "picNr" in{
      pictureNr must be_==(0)
    }
     
      "setGuessed" in{
      zelle.guessed = true
      var newGuessed = zelle.guessed
      newGuessed must be_==(true)
      var newOpen2 =zelle.open
       newOpen2 must be_==(true)
      
    }
      
      
      "setPictureNr" in{
      zelle.pictureNr = 1
      var newPictureNr = zelle.pictureNr
      newPictureNr must be_==(1)
      
    }
  }

}