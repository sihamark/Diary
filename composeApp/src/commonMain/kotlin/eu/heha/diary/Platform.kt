package eu.heha.diary

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform