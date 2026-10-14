package org.setu.skillswapper

import java.util.concurrent.atomic.AtomicLong
import timber.log.Timber

class SkillSwapMemStore {

    private val swaps = ArrayList<SkillSwapModel>()
    private val lastId = AtomicLong(0L)

    fun findAll(): List<SkillSwapModel> {
        return swaps
    }

    fun create(swap: SkillSwapModel) {
        swap.id = lastId.incrementAndGet()
        swaps.add(swap)
        Timber.d("Store: created swap id=${swap.id}")
    }

    fun update(swap: SkillSwapModel): Boolean {
        val foundSwap = findOne(swap.id)
        return if (foundSwap != null) {
            foundSwap.skillWanted = swap.skillWanted
            foundSwap.skillOffered = swap.skillOffered
            foundSwap.location = swap.location
            Timber.d("Store: updated swap id=${swap.id}")
            true
        } else {
            false
        }
    }

    fun delete(id: Long): Boolean {
        val foundSwap = findOne(id)
        return if (foundSwap != null) {
            swaps.remove(foundSwap)
            Timber.d("Store: deleted swap id=$id")
            true
        } else {
            false
        }
    }

    fun findOne(id: Long): SkillSwapModel? {
        return swaps.find { s -> s.id == id }
    }
}