package org.sofa.r.us.starter

import kotlinx.coroutines.*

suspend fun bathTime() {
    // Continuation = data structure that stores all local context
    logger.info("Going to the bathroom")
    delay(500L) // suspends/"blocks" the computation
    // Continuation restored here
    logger.info("Bath time done ✅")
}

suspend fun boilingWater() {
    logger.info("Boiling Water")
    delay(1000L)
    logger.info("Water boiled ✅")
}

suspend fun makeCoffee() {
    logger.info("Starting to make coffee")
    delay(500L)
    logger.info("Done with coffee ✅")
}

// Cooperative Scheduling
suspend fun workingHard() {
    org.sofa.r.us.two.logger
        .info("Working Hard")
    // CPU intensive computation
    while (true) {
        // do some hard work
    }
    delay(100L)
    org.sofa.r.us.two.logger
        .info("Hard work done ✅")
}

suspend fun takeABreak() {
    org.sofa.r.us.two.logger
        .info("Taking a break")
    delay(1000L)
    org.sofa.r.us.two.logger
        .info("Break done ✅")
}

suspend fun workingHardRoutine() {
    val dispatcher: CoroutineDispatcher = Dispatchers.Default.limitedParallelism(2)

    coroutineScope {
        launch(dispatcher) {
            org.sofa.r.us.two
                .workingHard()
        }
        launch(dispatcher) { takeABreak() }
    }
}
