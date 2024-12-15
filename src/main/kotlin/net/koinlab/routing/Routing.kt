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
                    meta(name = "viewport", content = "width=device-width, initial-scale=1.0")
                    script(src = "https://unpkg.com/htmx.org@1.9.2") {}
                    script(src = "https://kit.fontawesome.com/54fa2e724a.js") {}
                    style {
                        +"""
                             body {
                                font-family: Arial, sans-serif; 
                                background-color: #121212; 
                                margin: 0; 
                                padding: 0;
                             } 
                             h1 { 
                                text-align: center; 
                                color: #e0e0e0; 
                             } 
                             p {
                                text-align: center;
                                color: #e0e0e0;
                             }
                             #crypto-prices { 
                                display: flex;
                                justify-content: center;
                                align-items: center;
                                flex-direction: column;
                                text-align: center; 
                                margin-top: 20px; 
                                border: 1px solid #444; 
                                border-radius: 8px; 
                                background-color: #1e1e1e; 
                                padding: 20px; 
                                max-width: 600px; 
                                margin: 20px auto;
                                color: #e0e0e0;
                             } 
                             #crypto-prices .fa-bitcoin {
                                color: #f2a900;
                             }
                             #crypto-prices .fa-ethereum {
                                color: #3c3c3d;
                             }
                             footer { 
                                display: flex;
                                justify-content: center;
                                align-items: center;
                                padding: 5px 0;
                                margin-top: 5px; 
                                background-color: #1e1e1e;
                             }
                             footer ul {
                                display: flex;
                                gap: 20px;
                                list-style: none;
                                padding: 0;
                             }
                             footer li {
                               margin: 0 10px;
                             }
                             a {
                                color: #0077ff
                                text-decoration: none;
                             }
                             a:hover {
                                text-decoration: underline;
                             }
                             @media (max-width: 600px) {
                                #crypto-prices {
                                    padding: 10px;
                                    max-width: 90%;
                                }
                                footer ul {
                                    flex-direction: row;
                                }
                                footer li {
                                    margin: 5px 0;
                                }
                             }
                        """.trimIndent()
                    }
                }
                body {
                    h1 { +"Welcome to KoinLab!" }
                    p { +"""
                            Future home of the world's ultimate crypto-currency!!! For now it's API practice 😂
                         """.trimIndent()}
                    div {
                        id = "crypto-prices"
                        attributes["hx-get"] = "/prices"
                        attributes["hx-trigger"] = "load"
                    }
                    footer {
                        ul {
                            li {
                                a("https://linkedin.com/in/V3ND3TTi") { i(classes = "fa-brands fa-linkedin-in fa-2x") }
                            }
                            li {
                                a("https://github.com/V3ND3TTi") { i(classes = "fa-brands fa-github fa-2x") }
                            }
                            li {
                                a("https://twitter.com/V3ND3TTi") { i(classes = "fa-brands fa-x-twitter fa-2x") }
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
                        h2 {
                            i(classes = "fa-brands fa-bitcoin")
                            +" Bitcoin: \$ ${prices.bitcoin?.usd.let { String.format("%.0f", it) } }"
                        }
                        h2 {
                            i(classes = "fa-brands fa-ethereum")
                            +" Ethereum: \$ ${prices.ethereum?.usd.let { String.format("%.0f", it) } }"
                        }
                    }
                }
            }
        }
    }
}
