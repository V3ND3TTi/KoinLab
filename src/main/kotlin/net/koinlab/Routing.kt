package net.koinlab

import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.html.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondHtml {
                head {
                    title { +"KoinLab Portfolio" }
                    script(src = "https://unpkg.com/htmx.org@1.9.2") {}
                }
                body {
                    h1 { +"Welcome to KoinLab!" }
                    div {
                        id = "crypto-prices"
                        attributes["hx-get"] = "/prices"
                        attributes["hx-trigger"] = "load"
                    }
                    footer {
                        p { +"Connect with me:" }
                        ul {
                            li {
                                a("https://github.com/V3ND3TTi") { +"GitHub" }
                            }
                            li {
                                a("https://linkedin.com/in/V3ND3TTi") { +"LinkedIn" }
                            }
                        }
                    }
                }
            }
        }

        get("/prices") {
            // Dummy response for now
            call.respondText("""{ "bitcoin": 45000, "ethereum": 3000 }""", ContentType.Application.Json)
        }
    }
}
