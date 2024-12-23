package com.example.core


import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.math.BigInteger
import java.util.stream.Stream


class ConverterKtTest {
    @ParameterizedTest
    @MethodSource("provideInvalidTimes")
    fun shouldThrowIllegalArgument(input: String?) {
        assertThrows<IllegalArgumentException> {
            input.toSeconds()
        }
    }

    @ParameterizedTest
    @MethodSource("provideValidTimes")
    fun validTimes(input: String?, result: BigInteger) {
        val response = input.toSeconds()
        assertEquals(result, response)
    }

    companion object {
        @JvmStatic
        private fun provideInvalidTimes(): Stream<Arguments> = Stream.of(
            Arguments.of(null),
            Arguments.of("1:0"),
            Arguments.of(""),
            Arguments.of("a"),
            Arguments.of("1ha"),
            Arguments.of("ah0"),
            Arguments.of("aha"),
            Arguments.of("1h60"),
            Arguments.of("-1h30"),
            Arguments.of("-1h"),
            Arguments.of("1h-3"),
            Arguments.of("6"),
            Arguments.of("0h"),
            Arguments.of("0h0"),
            Arguments.of("0")
        )

        @JvmStatic
        private fun provideValidTimes(): Stream<Arguments> = Stream.of(
            Arguments.of("1h30", BigInteger.valueOf(1*3600 + 30*60)),
            Arguments.of("36h", BigInteger.valueOf(36*3600)),
            Arguments.of("36h00", BigInteger.valueOf(36*3600)),
            Arguments.of("36h15", BigInteger.valueOf(36*3600 + 15*60)),
            Arguments.of("36h59", BigInteger.valueOf(36*3600 + 59*60)),
            Arguments.of("6h59", BigInteger.valueOf(6*3600 + 59*60)),
            Arguments.of("6h", BigInteger.valueOf(6*3600)),
            Arguments.of("1h", BigInteger.valueOf(3600)),
            Arguments.of("0h10", BigInteger.valueOf(10*60)),
            Arguments.of("00h15", BigInteger.valueOf(15*60)),
            Arguments.of("00h05", BigInteger.valueOf(5*60)),
            Arguments.of("00h5", BigInteger.valueOf(5*60)),
            Arguments.of("0h5", BigInteger.valueOf(5*60))
        )
    }
}