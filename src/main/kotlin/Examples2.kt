package org.example

import kotlinx.coroutines.*

suspend fun bathTime1() {
    // Continuation = data structure that stores all local context
    logger.info("Going to the bathroom")
    delay(500L) // suspends/"blocks" the computation
    // Continuation restored here
    logger.info("Bath time done ✅")
}

suspend fun boilingWater1() {
    logger.info("Boiling Water")
    delay(1000L)
    logger.info("Water boiled ✅")
}

suspend fun makeCoffee1() {
    logger.info("Starting to make coffee")
    delay(500L)
    logger.info("Done with coffee ✅")
}

suspend fun structuredMorningRoutine() {
    coroutineScope {
        val bathJob = launch { bathTime1() }
        val waterJob =
            launch {
                boilingWater1()
            }
        // block here
        // semantic block
        bathJob.join()
        waterJob.join()

        launch {
            makeCoffee1()
        }
    }
}

suspend fun structuredMorningRoutine2() {
    coroutineScope {
        coroutineScope {
            launch { bathTime1() }
            launch {
                boilingWater1()
            }
        }

        launch {
            makeCoffee1()
        }
    }
}

// Cooperative scheduling
suspend fun workingHard1() {
    logger.info("Working Hard")
    // CPU intensive computation
    while (true) {
        // do some hard work
    }
    delay(100L)
    logger.info("Hard work done ✅")
}

suspend fun takeABreak1() {
    logger.info("Taking a break")
    delay(1000L)
    logger.info("Break done ✅")
}

suspend fun workingHardRoutine1() {
    val dispatcher: CoroutineDispatcher = Dispatchers.Default.limitedParallelism(2)

    coroutineScope {
        launch(dispatcher) { workingHard1() }
        launch(dispatcher) { takeABreak1() }
    }
}

suspend fun workingNicely1() {
    logger.info("Working Nicely")
    // CPU intensive computation
    while (true) {
        // do some hard work
        delay(100L)
    }
    logger.info("Hard work done ✅")
}

suspend fun workingNicelyRoutine1() {
    val dispatcher: CoroutineDispatcher = Dispatchers.Default.limitedParallelism(1)

    coroutineScope {
        launch(dispatcher) { workingNicely1() }
        launch(dispatcher) { takeABreak1() }
    }
}

suspend fun main() {
    workingNicelyRoutine1()
}
