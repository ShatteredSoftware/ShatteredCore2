import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import java.util.jar.JarFile

plugins {
    kotlin("jvm") version "1.6.10"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

fun relocateExcept(
    target: ShadowJar,
    configurations: List<Configuration>,
    excluded: Set<String> = setOf(),
    prefix: String = "shadow"
) {
    val packages = mutableSetOf<String>()
    configurations.forEach { config ->
        config.files.forEach {
            val file = JarFile(it)
            file.entries().asIterator().forEach files@{ entry ->
                for (exclusion in excluded) {
                    if (entry.name.startsWith(exclusion.replace(".", "/"))) {
                        return@files
                    }
                }
                if (entry.name.endsWith(".class") && entry.name != "module-info.class") {
                    packages.add(entry.name.slice(0 until entry.name.lastIndexOf("/")).replace("/", "."))
                }
            }
        }
    }

    packages.forEach {
        target.relocate(it, "${prefix}.${it}")
    }
}

group = "com.github.shatteredsoftware"
version = parent?.version ?: project.property("version") as? String ?: "unconfigured-version"

val arrowVersion: String by project
val daggerVersion: String by project
val exposedVersion: String by project
val flywayVersion: String by project
val hikariVersion: String by project
val jacksonVersion: String by project
val kotlinVersion: String by project

repositories {
    mavenCentral()
}

val include = configurations.create("include")


dependencies {
    include(project(":core"))

    implementation("io.arrow-kt", "arrow-core", arrowVersion)

    implementation("com.google.dagger", "dagger", daggerVersion)
    annotationProcessor("com.google.dagger", "dagger-compiler", daggerVersion)

    implementation("org.jetbrains.exposed", "exposed-core", exposedVersion)
    implementation("org.jetbrains.exposed", "exposed-dao", exposedVersion)
    implementation("org.jetbrains.exposed", "exposed-jdbc", exposedVersion)

    implementation("org.flywaydb", "flyway-core", flywayVersion)

    implementation("com.zaxxer", "HikariCP", hikariVersion)

    implementation("com.fasterxml.jackson.core", "jackson-core", jacksonVersion)
    implementation("com.fasterxml.jackson.core", "jackson-annotations", jacksonVersion)
    implementation("com.fasterxml.jackson.core", "jackson-databind", jacksonVersion)
    implementation("com.fasterxml.jackson.dataformat", "jackson-dataformat-yaml", jacksonVersion)
    implementation("com.fasterxml.jackson.dataformat", "jackson-dataformat-toml", jacksonVersion)

    implementation("org.jetbrains.kotlin", "kotlin-stdlib", kotlinVersion)
}

tasks.jar {
    dependsOn(tasks.shadowJar)
}