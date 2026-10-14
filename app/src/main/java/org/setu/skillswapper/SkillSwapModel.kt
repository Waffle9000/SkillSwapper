package org.setu.skillswapper

data class SkillSwapModel(
    var id: Long = 0,
    var skillWanted: String,
    var skillOffered: String,
    var location: String
)
