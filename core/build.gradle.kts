import org.gradle.configurationcache.extensions.capitalized

plugins {
    kotlin("jvm") version "1.6.10"
}

repositories {
    mavenLocal()
    mavenCentral()
}

version = parent?.version ?: project.property("version") as? String ?: "unconfigured-version"
group = "com.github.shatteredsoftware"

val arrowVersion: String by project
val coroutinesVersion: String by project
val daggerVersion: String by project
val exposedVersion: String by project
val flywayVersion: String by project
val hikariVersion: String by project
val jacksonVersion: String by project
val kotlinVersion: String by project
val osgiVersion: String by project
val reactorVersion: String by project

dependencies {
    implementation(kotlin("stdlib"))

    implementation("io.arrow-kt:arrow-core:$arrowVersion")

    implementation("com.google.dagger:dagger:$daggerVersion")
    annotationProcessor("com.google.dagger:dagger-compiler:$daggerVersion")

    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")

    implementation("org.flywaydb:flyway-core:$flywayVersion")

    implementation("com.zaxxer:HikariCP:$hikariVersion")

    implementation("com.fasterxml.jackson.core:jackson-core:$jacksonVersion")
    implementation("com.fasterxml.jackson.core:jackson-annotations:$jacksonVersion")
    implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:$jacksonVersion")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-toml:$jacksonVersion")

    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

    implementation("io.projectreactor:reactor-core:$reactorVersion")
    testImplementation("io.projectreactor:reactor-test:$reactorVersion")

    implementation("org.osgi:org.osgi.core:$osgiVersion")
}
