package org.setu.skillswapper

import org.setu.skillswapper.PlaceMarkerData
import java.util.concurrent.atomic.AtomicLong
class PlacemarkMemStore {

    private val placemarks = ArrayList<PlaceMarkerData>()
    private val lastId = AtomicLong(0L)

    fun findAll(): List<PlaceMarkerData> {
        return placemarks
    }

    fun create(placemark: PlaceMarkerData) {
        placemark.id = lastId.incrementAndGet()
        placemarks.add(placemark)
    }

    fun update(placemark: PlaceMarkerData): Boolean {
        val foundPlacemark = findOne(placemark.id)
        return if (foundPlacemark != null) {
            foundPlacemark.title = placemark.title
            foundPlacemark.description = placemark.description
            foundPlacemark.x = placemark.x
            foundPlacemark.y = placemark.y
            true
        } else {
            false
        }
    }

    fun delete(id: Long): Boolean {
        val foundPlacemark = findOne(id)
        return if (foundPlacemark != null) {
            placemarks.remove(foundPlacemark)
            true
        } else {
            false
        }
    }

    fun findOne(id: Long): PlaceMarkerData? {
        return placemarks.find { p -> p.id == id }
    }
}
