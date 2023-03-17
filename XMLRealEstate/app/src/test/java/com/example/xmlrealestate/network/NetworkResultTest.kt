package com.example.xmlrealestate.network

import org.junit.Assert.assertEquals
import org.junit.Test

class NetworkResultTest {

    @Test
    fun successWithData() {
        val data = "Test Data"
        val result = NetworkResult.Success(data)
        assertEquals(data, result.data)
        assertEquals(null, result.message)
    }

    @Test
    fun errorWithDataAndMessage() {
        val data = "Test Data"
        val message = "Error Test Message"
        val result = NetworkResult.Error(message, data)
        assertEquals(data, result.data)
        assertEquals(message, result.message)
    }

    @Test
    fun errorWithMessageOnly() {
        val message = "Error Test Message"
        val result = NetworkResult.Error<String>(message)
        assertEquals(null, result.data)
        assertEquals(message, result.message)
    }

    @Test
    fun loading() {
        val result = NetworkResult.Loading<String>()
        assertEquals(null, result.data)
        assertEquals(null, result.message)
    }
}

