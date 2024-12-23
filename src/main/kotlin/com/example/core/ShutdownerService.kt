package com.example.core

import tornadofx.*
import java.math.BigInteger

class ShutdownerService : Controller() {
    val runtime = Runtime.getRuntime()
    fun programShutdown(seconds: BigInteger) {
        println("Shutting down in $seconds seconds")
        runtime.exec("shutdown /s /t $seconds")
    }

    fun abortShutdown() {
        runtime.exec("shutdown -a")
        println("Shutdown aborted!")
    }
}