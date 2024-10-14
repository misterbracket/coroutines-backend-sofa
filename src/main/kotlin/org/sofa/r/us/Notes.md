
## Structural Concurrency
The coroutines scope represents the implementation of structural concurrency in Kotlin. 
The runtime blocks the execution of the block lambda until all the coroutines started inside the block lambda are completed.
These coroutines are called children coroutines of the scope.
Moreover, structural concurrency also brings us the following features:

* Children coroutines inherit the context (CoroutineContext) of the parent coroutine, and they can override it.
The coroutine’s context is part of the Continuation object we’ve seen before.
It contains the name of the coroutine, the dispatcher (aka, the pool of threads executing the coroutines), the exception handler, and so on.
* When the parent coroutine is canceled, it also cancels the children coroutines.
* When a child coroutine throws an exception, the parent coroutine is also stopped.

## Closing Resources

```kotlin
class Desk : AutoCloseable {
    init {
        logger.info("Starting to work on the desk")
    }

    override fun close() {
        logger.info("Cleaning the desk")
    }
}


suspend fun forgettingTheBirthDayRoutineAndCleaningTheDesk() {
    val desk = Desk()
    coroutineScope {
        val workingJob = launch {
            desk.use { _ ->
                workingConsciousness()
            }
        }
        launch {
            delay(2000L)
            workingJob.cancelAndJoin()
            logger.info("I forgot the birthday! Let's go to the mall!")
        }
    }
}
```


Passing a continuation object between threads is called a continuation passing style (CPS).
Go to Tools => Kotlin => Show Kotlin Bytecode => Decompile to see the continuation object.
```kotlin
// Show a continuation object
suspendCancellableCoroutine<Unit> { continuation -> logger.info("Continuation: $continuation") }
```