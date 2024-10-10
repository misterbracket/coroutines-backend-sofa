@file:Suppress("ktlint:standard:no-wildcard-imports")

package org.sofa.r.us.starter

import kotlinx.coroutines.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory

val logger: Logger = LoggerFactory.getLogger("CoroutineLogger")

suspend fun bathTime() {
    logger.info("Start Bath Time")
    delay(500L)
    logger.info("Bath time done ✅")
}

suspend fun boilingWater() {
    logger.info("Start Boiling Water")
    delay(1000L)
    logger.info("Water boiling done✅")
}

suspend fun makeCoffee() {
    logger.info("Start making coffee")
    delay(500L)
    logger.info("Coffee making done ✅")
}

suspend fun playingWithRubberDuck() {
    logger.info("Start Playing with Rubber Duck")
    delay(2000L)
    logger.info("Duck playing done ✅")
}

suspend fun noStructureConcurrency() {
    coroutineScope {
        val bathJob = launch { bathTime() }
        val duckJob = launch { playingWithRubberDuck() }

        // block until done
        bathJob.join()
        duckJob.join()

        // after bathtime and rubber duck session
        launch { boilingWater() }
    }
}

suspend fun structuredConcurrency() {
    coroutineScope {
        coroutineScope {
            launch { bathTime() }
            launch { playingWithRubberDuck() }
        }
        // block until done
        coroutineScope {
            // after bathtime and rubber duck session
            launch { boilingWater() }
        }
    }
}

// --------------------------------------

// Cooperative Scheduling
suspend fun workingHard() {
    logger.info("Working Hard")
    // CPU intensive computation
    while (true) {
        // do some hard work
    }
    delay(500L)
    logger.info("Hard work done ✅")
}

suspend fun takeABreak() {
    logger.info("Taking a break")
    delay(1000L)
    logger.info("Break done ✅")
}

val dispatcher = Dispatchers.Default.limitedParallelism(2)

suspend fun workingHardCoroutine() {
    coroutineScope {
        launch(dispatcher) { workingHard() }
        launch(dispatcher) { takeABreak() }
    }
}

suspend fun main() {
    workingHardCoroutine()
}
