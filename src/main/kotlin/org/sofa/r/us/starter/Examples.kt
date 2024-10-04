@file:Suppress("ktlint:standard:no-wildcard-imports")

package org.sofa.r.us.starter

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
5. Address cancellations an exceptions

Cooperative Scheduling
1. Create a workingHard routine (blocks)
2. Bring up dispatchers and limit thread to 1.
3. Change thread count to 2.
4. Explain why workingHard Routine never yields back
5. Create a new function workingNicely with suspension point.
6. How does the OS assign resources to processes?
 */

val logger: Logger = LoggerFactory.getLogger("CoroutineLogger")

fun main() {
}
