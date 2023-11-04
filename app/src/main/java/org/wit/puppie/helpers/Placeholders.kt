package org.wit.puppie.helpers

import org.wit.puppie.models.PlacemarkModel

open class Placeholders {

      fun getListOfPlaces():List<PlacemarkModel>{
        val places:List<PlacemarkModel> = listOf(PlacemarkModel("Jeden", "Jeden"), PlacemarkModel("Dwa","Dwa"))
        return places
    }
}