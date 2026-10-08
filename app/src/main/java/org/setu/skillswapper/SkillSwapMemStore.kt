package org.setu.skillswapper

import org.setu.skillswapper.SkillSwapModel
import java.util.concurrent.atomic.AtomicLong
class SkillSwapMemStore {

    private val placemarks = ArrayList<SkillSwapModel>()
    private val lastId = AtomicLong(0L)

    fun findAll(): List<SkillSwapModel> {
        return placemarks
    }

    fun create(placemark: SkillSwapModel) {
        placemark.id = lastId.incrementAndGet()
        placemarks.add(placemark)
    }

    fun update(placemark: SkillSwapModel): Boolean {
        val foundPlacemark = findOne(placemark.id)
        return if (foundPlacemark != null) {
            foundPlacemark.skillWanted = placemark.skillWanted
            foundPlacemark.skillOffered = placemark.skillOffered
            foundPlacemark.location = placemark.location
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

    fun findOne(id: Long): SkillSwapModel? {
        return placemarks.find { p -> p.id == id }
    }
}
