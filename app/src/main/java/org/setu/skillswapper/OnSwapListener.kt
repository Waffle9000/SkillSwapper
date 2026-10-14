package org.setu.skillswapper

interface OnSwapListener {
    fun onSwapClick(swap: SkillSwapModel)
    fun onSwapLongClick(swap: SkillSwapModel): Boolean
}