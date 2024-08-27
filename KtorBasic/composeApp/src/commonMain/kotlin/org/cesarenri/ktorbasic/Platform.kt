package org.cesarenri.ktorbasic

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform