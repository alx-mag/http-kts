package io.amplicode.http

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.runBlocking

class MyRequestBuilder {

    /**
     * Do GET request
     */
    fun get(url: String) = runBlocking {
        val response = HttpClient(CIO).get(url)
        val text = response.bodyAsText()
        println(text)
    }

    /**
     * Do POST request
     */
    fun post(url: String, value: Any) = runBlocking {
        val response = HttpClient(CIO).post(url) {
            setBody(value)
        }
        val text = response.bodyAsText()
        println(text)
    }
}