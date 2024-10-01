// build.gradle.kts - The Gradle build script.
//
// This file tells Gradle how to configure your project.
//
// In general, most Gradle build scripts can be separated into three parts:
// - Plugins: Adds extra functionality to your project (e.g. compiling other languages)
// - Dependencies: Adds external libraries to your project (e.g. JUnit)
// - Tasks: Configures existing tasks provided by Gradle or a plugin, or adds new tasks
//
// Although the use of Gradle is out-of-syllabus (and thus will not be tested in any assessment), we will use Gradle for
// all labs and assignments. Don't worry, all Gradle scripts will be been written for you, so you don't have to touch
// anything (unless you want to).
//
// In other words, if you don't understand any of this (or just don't want to understand), that is fine, as long as you
// know how to run Gradle tasks from IntelliJ IDEA.
//

// Use the `java` plugin - Enables automatic download of JDK
plugins {
    java
}

group = "hk.ust.comp3021.lab"

// Configures the `java` plugin
java {
    // Configures the *toolchain* (i.e. JDK) that is used by this project
    toolchain {
        // We need a toolchain that supports Java 17
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

// Configures repositories - Locations to find library dependencies; See the below `dependencies` block
repositories {
    mavenCentral()
}

// Configures dependencies - External libraries that this project uses
dependencies {
    // The below dependencies are for JUnit 5, a unit testing framework
    // We use these in MainTest.java
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.0")
}

// Configures Gradle tasks - Gradle tasks are similar to Make targets
//
// One key difference is that Gradle provides a set of default tasks, and plugins can add additional tasks to your
// project
tasks {
    // The two `withType` blocks below configures all tasks with the given type in the angle brackets (`<...>`)
    // This is necessary to enable preview features for Java 17
    withType<JavaCompile> {
        options.compilerArgs = listOf("--enable-preview")
    }
    withType<JavaExec> {
        jvmArgs("--enable-preview")
    }

    // This block specifically configures the `test` task of Gradle
    //
    // Like above, we need to enable preview features for Java 17
    getByName<Test>("test") {
        // By default, Gradle tests projects using JUnit 4
        // In this course we are using JUnit 5, so this line tells Gradle to use that instead
        useJUnitPlatform()

        jvmArgs("--enable-preview")
    }
}