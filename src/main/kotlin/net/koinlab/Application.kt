package net.koinlab

import io.ktor.server.application.*
import net.koinlab.routing.configureRouting

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()
    configureTemplating()
    configureRouting()
}
