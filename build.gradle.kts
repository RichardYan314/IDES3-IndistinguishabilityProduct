/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Java Library project to get you started.
 * For more details take a look at the Java Libraries chapter in the Gradle
 * User Manual available at https://docs.gradle.org/6.6.1/userguide/java_library_plugin.html
 */

plugins {
    scala

    // Apply the java-library plugin to add support for Java Library
    `java-library`
}

repositories {
    // Use jcenter for resolving dependencies.
    // You can declare any Maven/Ivy/file repository here.
    mavenCentral()

    flatDir{
        dirs("libs/IDES3api")
    }
}

dependencies {
    implementation(files("libs/IDES3api/IDES3api.jar"))

    // Use Scala 2.13 in our library project
    implementation("org.scala-lang:scala-library:2.13.2")
}

sourceSets {
    main {
        withConvention(ScalaSourceSet::class) {
            scala {
                setSrcDirs(listOf("src"))
            }
        }
        java {
            setSrcDirs(emptyList<String>())
        }
    }
}

tasks {
    jar {
        //destinationDir = file("../IDES-3.1.3/plugins") //for development
        archiveFileName.set("IDES3IndistinguishabilityProduct.IDES3IndistinguishabilityProduct.jar")

        configurations["compileClasspath"].forEach { file: File ->
            from(zipTree(file.absoluteFile))
        }
    }
}
