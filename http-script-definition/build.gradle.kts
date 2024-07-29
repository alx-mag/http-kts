import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    id("java-library")
    id("maven-publish")
}

val kotlinCoroutinesVersion: String by project

group = "io.amplicode"
version = "2.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${kotlinCoroutinesVersion}") {
        version {
            strictly(kotlinCoroutinesVersion)
        }
    }

    implementation("org.jetbrains.kotlin:kotlin-scripting-common")
    implementation("org.jetbrains.kotlin:kotlin-scripting-jvm")
    implementation("org.jetbrains.kotlin:kotlin-scripting-jvm-host")
    implementation("org.jetbrains.kotlin:kotlin-scripting-dependencies")
    implementation("org.jetbrains.kotlin:kotlin-scripting-dependencies-maven-all")

    implementation("io.ktor:ktor-client-core:2.3.12")
    implementation("io.ktor:ktor-client-cio:2.3.12")
    implementation("io.ktor:ktor-client-cio-jvm:2.3.12")
}


sourceSets {
    test {}
}

java {
    withSourcesJar()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

publishing {
    publications {
        create<MavenPublication>("scriptDefinition") {
            from(components["java"])
            artifactId = "http-script-definition"
            pom {
                name.set("HTTP Script Definition")
                description.set("Kotlin script definition module for HTTP requests")
                groupId = "io.amplicode"
            }
        }
    }
}