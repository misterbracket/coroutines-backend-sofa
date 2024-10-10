@file:Suppress("ktlint:standard:no-wildcard-imports")

package org.sofa.r.us.three

import kotlinx.coroutines.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory

// coroutine - "thread"
// actual thread - platform thread
// parallel + concurrent apps

/*
AGENDA

Intro
1. Talk about coroutines vs Threads

Structured Concurrency
1. Create a morning routine
2. Use .join to block execution
3. Change it to use coroutines scopes
4. Talk about context and continuation objects
5. Address cancellations and exceptions

Cooperative Scheduling
1. Create a workingHard routine (blocks)
2. Bring up dispatchers and limit thread to 1.
3. Change thread count to 2.
4. Explain why workingHard Routine never yields back
5. Create a new function workingNicely with suspension point.
6. How does the OS assign resources to processes?
 */

val logger: Logger = LoggerFactory.getLogger("CoroutineLogger")

//
// first bathtime.
// while bathtime I also want to boil water
// after both are done I want to make coffee

suspend fun bathTime() {
    // Continuation = data structure that stores all local context
    logger.info("Start Bath Time")
    delay(500L) // suspends/"blocks" the computation
    // Continuation restored here
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

suspend fun myMorningRoutineNoStructure() {
    coroutineScope {
        val bathJob = launch { bathTime() }
        val waterJob = launch { boilingWater() }

        // semantic blocking
        bathJob.join()
        waterJob.join()

        launch { makeCoffee() }
    }
}

suspend fun myMorningRoutineWithStructure() {
    coroutineScope {
        coroutineScope {
            launch { bathTime() }
            launch { boilingWater() }
        }

        launch { makeCoffee() }
    }
}

suspend fun myMorningRoutineWithStructureAndCancellation() {
    coroutineScope {
        coroutineScope {
            val bathJob = launch { bathTime() }
            launch { boilingWater() }
            bathJob.cancelAndJoin()
        }
        launch { makeCoffee() }
    }
}

suspend fun playingWithRubberDuck() {
    logger.info("Start Playing with Rubber Duck")
    delay(2000L)
    logger.info("Duck playing done ✅")
}

suspend fun myMorningRoutineWithStructureAndCancellationAndNesting() {
    coroutineScope {
        launch {
            launch { bathTime() }
            launch { playingWithRubberDuck() }
        }
        launch {
            boilingWater()
        }
    }
    coroutineScope {
        launch { makeCoffee() }
    }

//    CoroutineScope(Dispatchers.Default).launch {
//        launch { makeCoffee() }
//    }
}

suspend fun main() {
    myMorningRoutineWithStructureAndCancellationAndNesting()
//    CoroutineScope(Dispatchers.Default).launch {
//        launch { makeCoffee() }
//    }
}
