# Course Management Service

This repository contains the backend implementation for the Course and Enrollment Management Microservice built using Spring Boot, Hibernate, MySQL, and Reactive architectures.

---

## 📓 Session 09 Reflection — Reactive Streams

### Q1. The Blocking Question: If you built this same infinite stream using traditional Spring MVC (a blocking while(true) loop), what would happen to the server if 100 users visited the URL at the same time?

**Answer:**
In a traditional Spring MVC blocking architecture, the server utilizes a **Thread-per-Request** model (managed by Tomcat). Each incoming user connection hijacks a dedicated execution thread from the internal thread pool and holds onto it for the entire lifespan of the request. 

If an infinite `while(true)` loop is introduced, that thread remains permanently blocked, waiting indefinitely on the loop stream. If 100 users hit that endpoint simultaneously, 100 execution threads are instantly locked up (causing **Thread Starvation**). Once the maximum server thread pool capacity is reached, the server will completely run out of available threads. It will refuse any incoming traffic, fail to answer health checks, cause incoming requests to hang indefinitely, or crash entirely under resource exhaustion.

---

### Q2. The Reactive Answer: How does Spring WebFlux handle 100 users hitting the /api/news/live endpoint differently? (Think about the Netty event loop from Session 4).

**Answer:**
Spring WebFlux completely eliminates the thread-per-request limitation by dropping Tomcat in favor of a non-blocking **Netty Event Loop** model. Netty relies on a very small, fixed number of worker threads (typically matching the exact number of CPU cores on the host machine) to process thousands of concurrent operations.

When a user subscribes to the `/api/news/live` endpoint, WebFlux registers an asynchronous callback listener on the socket channel. Instead of a thread waiting around doing nothing during that 1-second idle interval, the Netty thread immediately returns to the event loop to service other concurrent network tasks. When the 1-second interval timer fires, the Event Loop quickly picks up the signal, transmits the single text slice to the user's browser via Server-Sent Events (SSE), and immediately frees itself again. Consequently, 100 or even thousands of concurrent users consume minimal RAM and CPU overhead.
