@file:OptIn(DelicateCoroutinesApi::class)
@file:Suppress("ktlint:standard:no-wildcard-imports")

package org.sofa.r.us.two

import kotlinx.coroutines.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory

// coroutine - "thread"
// parallel + concurrent apps

val logger: Logger = LoggerFactory.getLogger("CoroutineLogger")

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

suspend fun notStructuredMorningRoutine() {
    coroutineScope {
        val bathJob = launch { bathTime() }
        val waterJob =
            launch {
                boilingWater()
            }
        // block here
        // semantic block
        bathJob.join()
        waterJob.join()

        launch {
            makeCoffee()
        }
    }
}

suspend fun structuredMorningRoutineWithException() {
    supervisorScope {
        coroutineScope {
            launch { bathTime() }
            launch { boilingWater() }
            throw Exception("Boom 💥")
        }

        launch { makeCoffee() }
    }
}

// Cooperative scheduling
suspend fun workingHard() {
    logger.info("Working Hard")
    // CPU intensive computation
    while (true) {
        // do some hard work
    }
    delay(100L)
    logger.info("Hard work done ✅")
}

suspend fun takeABreak() {
    logger.info("Taking a break")
    delay(1000L)
    logger.info("Break done ✅")
}

suspend fun workingHardRoutine() {
    val dispatcher: CoroutineDispatcher = Dispatchers.Default.limitedParallelism(2)

    coroutineScope {
        launch(dispatcher) { workingHard() }
        launch(dispatcher) { takeABreak() }
    }
}

suspend fun workingNicely() {
    logger.info("Working Nicely")
    // CPU intensive computation
    while (true) {
        // do some hard work
        //  delay(100L)
        yield()
    }
    logger.info("Hard work done ✅")
}

suspend fun workingNicelyRoutine() {
    val dispatcher: CoroutineDispatcher = Dispatchers.Default.limitedParallelism(1)

    coroutineScope {
        launch(dispatcher) { workingNicely() }
        launch(dispatcher) { takeABreak() }
    }
}

suspend fun main() {
    notStructuredMorningRoutine()
}
