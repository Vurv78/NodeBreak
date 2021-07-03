import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "1.5.20"

    // https://plugins.gradle.org/plugin/com.github.johnrengelman.shadow
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

group = "Vurv78"
version = "0.1.0"

repositories {
    maven {
        url = uri("https://papermc.io/repo/repository/maven-public/")
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("io.papermc.paper", "paper-api", "1.17-R0.1-SNAPSHOT")
}

tasks {
    processResources {
        filesMatching("**/*.yml") {
            expand(project.properties)
        }
    }

    build {
        dependsOn(shadowJar)
    }
}