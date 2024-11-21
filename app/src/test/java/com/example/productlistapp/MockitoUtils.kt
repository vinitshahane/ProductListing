package com.example.productlistapp

import org.mockito.Mockito
import org.mockito.stubbing.OngoingStubbing

class MockitoUtils {
    inline fun <reified T> mock() = Mockito.mock(T::class.java)
    inline fun <T> whenever(methodCall: T) : OngoingStubbing<T> =
        Mockito.`when`(methodCall)
}