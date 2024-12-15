package net.koinlab.routing

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.html.*
import kotlinx.html.*
import net.koinlab.services.getCryptoPrices

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondHtml {
                head {
                    title { +"KoinLab" }
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
                                a("https://github.com/yourusername") { +"GitHub" }
                            }
                            li {
                                a("https://linkedin.com/in/yourusername") { +"LinkedIn" }
                            }
                        }
                    }
                }
            }
        }

        get("/prices") {
            val prices = getCryptoPrices()
            call.respondHtml {
                body {
                    div {
                        h2 { +"Bitcoin Price: ${prices.bitcoin?.usd} USD" }
                        h2 { +"Ethereum Price: ${prices.ethereum?.usd} USD" }
                    }
                }
            }
        }
    }
}
