package net.koinlab.services

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

val client = HttpClient(CIO) {
    install(ContentNegotiation) {
        json()
    }
}

@Serializable
data class CryptoPriceResponse(val bitcoin: CurrencyInfo?, val ethereum: CurrencyInfo?)

@Serializable
data class CurrencyInfo(val usd: Double)

suspend fun getCryptoPrices(): CryptoPriceResponse {
    val apiKey = System.getenv("COINGECKO_API_KEY")
    val response: HttpResponse = client.get("https://api.coingecko.com/api/v3/simple/price") {
        parameter("ids", "bitcoin,ethereum")
        parameter("vs_currencies", "usd")
        header("Authorization", "Bearer $apiKey")
    }
    val responseBody = response.bodyAsText()
    return Json.decodeFromString(CryptoPriceResponse.serializer(), responseBody)
}