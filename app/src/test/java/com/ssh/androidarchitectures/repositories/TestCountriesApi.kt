package com.ssh.androidarchitectures.repositories

import com.ssh.androidarchitectures.model.Country
import com.ssh.androidarchitectures.utils.FileUtil
import org.junit.Assert.assertEquals
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Test

class TestCountriesApi {
    private val server = MockWebServer()
    private lateinit var countriesApi: CountriesService

    @Before
    fun setup() {
        server.start(8080)
        // val baseUrl: HttpUrl = server.url("/v1/")
        countriesApi = CountriesService()
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun `500 error`() {
        val response = MockResponse().setResponseCode(500)
        server.enqueue(response)

        countriesApi.getCountries()

        assertEquals("HTTP/1.1 500 Server Error", response.status)
    }

//    @Test(expected = SocketTimeoutException::class)
//    fun `Timeout example`() {
//        val response = MockResponse()
//            .addHeader("Content-Type", "application/json; charset=utf-8")
//            .addHeader("Cache-Control", "no-cache")
//            .setBody("{}")
//            .throttleBody(1, 100, TimeUnit.SECONDS)
//
//        server.enqueue(response)
//        server.enqueue(MockResponse().setBody("success"))
//
//        countriesApi.getCountries()
//    }

    @Test
    fun `Dispatcher example`() {
        val dispatcher: Dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                when (request.path) {
                    "/rest/v2/all" -> return MockResponse().setResponseCode(200)
                        .setBody(FileUtil.readFileWithoutNewLineFromResources("Countries.json"))
                }
                return MockResponse().setResponseCode(404)
            }
        }
        server.dispatcher = dispatcher

        val result = countriesApi.getCountries()
        val expected = Country("Afghanistan")
        assertEquals(expected.name, result.blockingGet().first().name)

    }

    @Test
    fun `test file reading`() {
        val finalResult = String(FileUtil.readBinaryFileFromResources("Countries.json"))

        assertEquals(
            finalResult,
            FileUtil.readFileWithNewLineFromResources("Countries.json")
        )

        assertEquals(
            finalResult,
            FileUtil.kotlinReadFileWithNewLineFromResources("Countries.json")
        )

        assertEquals(
            finalResult,
            String(FileUtil.readBinaryFileFromResources("Countries.json"))
        )

        assertEquals(
            finalResult,
            String(FileUtil.kotlinReadBinaryFileFromResources("Countries.json"))
        )
    }
}