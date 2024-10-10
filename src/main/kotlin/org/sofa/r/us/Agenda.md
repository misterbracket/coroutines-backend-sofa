# Agenda

1. Introduction
    * Who am I?
    * Concurrency and Parallelism
2. Talk about Threads on the JVM
   * What is a Thread?
   * How to create a Thread?
   * Problems with Threads
     * Live lock and Dead lock
     * Not very ergonomic
     * Expensive to create
   * Why are virtual threads better?
     * Virtual threads are lightweight and cooperative
     * Store the state of the thread in the heap - Continuation Passing Style
3. Enter Coroutines
   * Structured Concurrency
      1. Introduce the morning routine and what we and to accomplish
      2. Create an unstructured morning routine and use .join to block execution
      3. Change it to use coroutines scopes
      4. Show how context and continuation objects are passed around
      5. ~~Address cancellations and exceptions~~
    * Cooperative Scheduling
      1. Create a workingHard routine (blocks)
      2. Bring up dispatchers and limit thread to 1.
      3. Change thread count to 2.
      4. Explain why workingHard Routine never yields back
      5. Create a new function workingNicely with suspension point.
      6. How does the OS assign resources to processes?