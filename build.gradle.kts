import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.5.0"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("org.flywaydb.flyway") version "7.9.1"
    id("org.jlleitschuh.gradle.ktlint") version "10.0.0"
    kotlin("jvm") version "1.5.0"
    kotlin("plugin.spring") version "1.5.0"
    kotlin("kapt") version "1.5.10"
    jacoco
}

group = "com.kaiyujin"
version = "0.1"

java.sourceCompatibility = JavaVersion.VERSION_11

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    // db
    implementation("org.seasar.doma.boot:doma-spring-boot-starter:1.5.0")
    implementation("org.seasar.doma:doma-kotlin:2.46.2")
    kapt("org.seasar.doma:doma-processor:2.46.2")
    implementation("org.flywaydb:flyway-core")
    runtimeOnly("org.postgresql:postgresql")

    // test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.kotest:kotest-runner-junit5:4.6.0")
    testImplementation("io.kotest:kotest-property:4.6.0")
    testImplementation("io.kotest.extensions:kotest-extensions-spring:1.0.0")
    testImplementation("io.mockk:mockk:1.11.0")
    testImplementation("com.ninja-squad:DbSetup-kotlin:2.1.0")
}

val compileKotlin: KotlinCompile by tasks

kapt {
    arguments {
        arg("doma.resources.dir", compileKotlin.destinationDir)
    }
}

tasks.register("copyDomaResources", Sync::class) {
    from("src/main/resources")
    into(compileKotlin.destinationDir)
    include("doma.compile.config")
    include("META-INF/**/*.sql")
    include("META-INF/**/*.script")
}

tasks.withType<KotlinCompile> {
    dependsOn(tasks.getByName("copyDomaResources"))
    dependsOn("ktlintFormat")
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

val jarName = "app.jar"
tasks.jar {
    archiveFileName.set(jarName)
}

tasks.bootJar {
    archiveFileName.set(jarName)
}

tasks.jacocoTestReport {
    reports {
        xml.isEnabled = true
        html.isEnabled = true
        html.destination = layout.buildDirectory.dir("jacocoHtml").get().asFile
    }
    dependsOn(tasks.test)
}

jacoco {
    toolVersion = "0.8.7" // 0.8.6 doesn't work due to a bug.
}
