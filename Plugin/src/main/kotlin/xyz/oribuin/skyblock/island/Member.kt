package xyz.oribuin.skyblock.island

import java.util.*

data class Member(val user: UUID, val island: Int) {
    var role: Role = Role.MEMBER

    enum class Role {
        OWNER, MODERATOR, MEMBER
    }

}