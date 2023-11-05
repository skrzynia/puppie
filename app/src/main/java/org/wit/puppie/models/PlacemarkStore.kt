package org.wit.puppie.models

interface PlacemarkStore {
    fun findAll(): List<PlacemarkModel>
    fun create(placemark: PlacemarkModel)
    fun update(placemark: PlacemarkModel)
    fun delete(placemark: PlacemarkModel)
    fun getRecomended(): List<PlacemarkModel>
    fun getPopular(): List<PlacemarkModel>
}