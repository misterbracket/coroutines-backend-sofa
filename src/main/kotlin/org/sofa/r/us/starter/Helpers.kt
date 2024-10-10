package org.sofa.r.us.starter

import kotlinx.coroutines.*

// suspend fun bathTime() {
//    // Continuation = data structure that stores all local context
//    logger.info("Start Bath Time")
//    delay(500L) // suspends/"blocks" the computation
//    // Continuation restored here
//    logger.info("Bath time done ✅")
// }
//
// suspend fun boilingWater() {
//    logger.info("Start Boiling Water")
//    delay(1000L)
//    logger.info("Water boiling done✅")
// }
//
// suspend fun makeCoffee() {
//    logger.info("Start making coffee")
//    delay(500L)
//    logger.info("Coffee making done ✅")
// }
//
// suspend fun playingWithRubberDuck() {
//    logger.info("Start Playing with Rubber Duck")
//    delay(2000L)
//    logger.info("Duck playing done ✅")
// }

/*
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
*/

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
