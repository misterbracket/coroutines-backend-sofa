@file:OptIn(DelicateCoroutinesApi::class)

package org.example

import kotlinx.coroutines.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory

// coroutine - "thread"
// parallel + concurrent apps

val logger: Logger = LoggerFactory.getLogger("CoroutineLogger")

suspend fun bathTime() {
    // Continuation = datastructure that stores all local context
    logger.info("Going to the bathroom")
    delay(500L) // suspends/"blocks" the computation
    // Continuation restored here
    logger.info("Bath time done ✅")
}

// structured concurrency
suspend fun boilingWater() {
    logger.info("Boiling Water")
    delay(1000L)
    logger.info("Water boiled ✅")
}

// sequential morning routine
suspend fun sequentialMorningRoutine() {
    coroutineScope {
        bathTime()
    }
    coroutineScope {
        boilingWater()
    }
}

// concurrent morning routine
suspend fun concurrentMorningRoutine() {
    coroutineScope {
        launch { bathTime() } // new Thread(() => ...).start
        launch { boilingWater() } // this is a child of the coroutineScope
    }
}

// run with a Thread.sleep(3000L) in main because main exits before coroutines complete
suspend fun noStructConcurrencyMorningRoutine() {
    GlobalScope.launch { bathTime() }
    GlobalScope.launch { boilingWater() }
}

suspend fun makeCoffee() {
    logger.info("Starting to make coffee")
    delay(500L)
    logger.info("Done with coffee ✅")
}

/*
 **plan coroutines**

   take a bath
   start boiling water

   after => make coffee
 */

suspend fun morningRoutineWithCoffee() {
    coroutineScope {
        val bathTimeJob = launch { bathTime() }
        val boilingWaterJob = launch { boilingWater() }

        bathTimeJob.join() // Semantic blocking here to wait until the bathTime is done
        boilingWaterJob.join() // Semantic blocking here to wait until the boilingWater is done

        // Now start a new task of the morning routine
        launch { makeCoffee() }
    }
}

// stuctured concurrency
suspend fun morningRoutineWithCoffeeStructured() {
    coroutineScope {
        coroutineScope {
            // parallel jobs
            launch { bathTime() }
            launch { boilingWater() }
        }

        // both coroutines are done
        launch { makeCoffee() }
    }
}

// Return values from Coroutine
suspend fun prepareJavaCoffee(): String {
    logger.info("Starting to make Coffee")
    delay(500L)
    logger.info("Done with Coffee ✅")
    return "Java Coffee"
}

suspend fun toastingBread(): String {
    logger.info("Starting to toast bread")
    delay(100L)
    logger.info("Done with toasting ✅")
    return "Toasted Bread"
}

suspend fun prepareBreakfast() {
    coroutineScope {
        val coffee = async { prepareJavaCoffee() }
        val bread = async { toastingBread() }

        // semantic blocking
        val finalCoffee = coffee.await()
        val finalToast = bread.await()
        logger.info("Drinking $finalCoffee and eating $finalToast")
    }
}

// 1. Cooperative Scheduling - coroutines yield manually
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
    val dispatcher: CoroutineDispatcher = Dispatchers.Default.limitedParallelism(1)

    coroutineScope {
        launch(dispatcher) { workingHard() }
        launch(dispatcher) { takeABreak() }
    }
}

suspend fun workingNicely() {
    logger.info("Working Nicely")
    // CPU intensive computation
    while (true) {
        delay(100L) // Give a change to the dispatcher to yield and run another coroutine
    }
    delay(100L)
    logger.info("Hard work done ✅")
}

suspend fun workingNicelyRoutine() {
    val dispatcher: CoroutineDispatcher = Dispatchers.Default.limitedParallelism(1)

    coroutineScope {
        launch(dispatcher) { workingNicely() }
        launch(dispatcher) { takeABreak() }
    }
}

/*
Dispatchers:
- Dispatchers.Default: For normal code or yielding coroutines
- Dispatchers.IO: IO bound code that is blocking and coroutines that are not going to yield
- Custom Dispatcher: Executors.newFixedThreadPool(8).asCoroutinesDispatchers()
 */

// Cancellation
suspend fun forgettingFriendBirthdayRoutine() {
    coroutineScope {
        val workingJob: Job = launch { workingNicely() }
        launch {
            delay(2000L) // After 2 seconds I remember my friends birthday
            workingJob.cancel() // Sends a SIGNAL to the coroutine to cancel. Cancellation happens at first yielding point
            workingJob.join() //  At this point you are sure that the coroutine was cancelled
            logger.info("I forgot my friends birthday! Buying a present now! 🎁")
        }
    }
}

suspend fun main() {
    forgettingFriendBirthdayRoutine()
}
