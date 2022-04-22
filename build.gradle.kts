version = project.property("version") as? String ?: "unconfigured-version"
group = "com.github.shatteredsoftware"

plugins {
    id("io.gitlab.arturbosch.detekt").version("1.20.0-RC1")
}

repositories {
    mavenCentral()
}