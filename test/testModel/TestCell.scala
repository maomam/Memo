package testModel

import org.junit.runner.RunWith
import model._
import org.specs2.mutable._
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TestCell extends SpecificationWithJUnit {
  "Zelle" should {
    val zelle = new Zelle(0, 0)
    var pictureNr = zelle.pictureNr
    var open = zelle.open
    var guessed = zelle.guessed

    "not be set" in {
      guessed must be_==(false)
    }

  
    "picNr" in {
      pictureNr must be_==(0)
    }

    "setGuessed" in {
      val zelle = new Zelle(0, 0)
      zelle.guessed = true
      zelle.open must be_==(true)

    }

    "setPictureNr" in {
      zelle.pictureNr = 1
      var newPictureNr = zelle.pictureNr
      newPictureNr must be_==(1)

    }
  }

}