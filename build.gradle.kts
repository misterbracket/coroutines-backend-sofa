plugins {
    kotlin("jvm") version "2.0.20"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0-RC.2")
    implementation("org.slf4j:slf4j-api:1.7.30") // Use the latest stable version

    // Logback (SLF4J Binding)
    implementation("ch.qos.logback:logback-classic:1.2.3") // Use the latest version of Logback
    implementation(kotlin("script-runtime"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}
