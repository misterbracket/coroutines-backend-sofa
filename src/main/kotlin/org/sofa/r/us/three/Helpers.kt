@file:Suppress("ktlint:standard:no-wildcard-imports")

package org.sofa.r.us.three

import kotlinx.coroutines.*

// Cooperative Scheduling
suspend fun workingHard() {
    logger
        .info("Working Hard")
    // CPU intensive computation
    while (true) {
        // do some hard work
    }
    delay(100L)
    logger
        .info("Hard work done ✅")
}

suspend fun takeABreak() {
    logger
        .info("Taking a break")
    delay(1000L)
    logger
        .info("Break done ✅")
}

suspend fun workingHardRoutine() {
    val dispatcher: CoroutineDispatcher = Dispatchers.Default.limitedParallelism(2)

    coroutineScope {
        launch(dispatcher) {
            workingHard()
        }
        launch(dispatcher) { takeABreak() }
    }
}
