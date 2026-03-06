import nu.studer.gradle.jooq.JooqEdition
import org.jooq.meta.jaxb.Property

plugins {
    java
    id("org.springframework.boot") version "3.2.0"
    id("io.spring.dependency-management") version "1.1.4"
    id("org.jetbrains.kotlin.plugin.spring") version "2.3.10"
    id("nu.studer.jooq") version "8.2"
    kotlin("kapt") version "1.9.20"

    kotlin("jvm")
}

group = "konffach"
version = "1.0-SNAPSHOT"

val mapstructVersion = "1.5.5.Final"
val springBootVersion = "3.2.0"
val jooqVersion = "3.20.5"
val postgresqlVersion = "42.6.0"
val liquibaseVersion = "4.25.0"
val swaggerVersion = "2.6.0"

repositories {
    mavenCentral()
}

dependencies {
    // Spring
    implementation("org.springframework.boot:spring-boot-starter-web:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-jooq:$springBootVersion")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$swaggerVersion")
    implementation("org.springframework.boot:spring-boot-starter-security:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-validation:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-actuator:$springBootVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // jwt
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

    implementation("org.mapstruct:mapstruct:$mapstructVersion")
    kapt("org.mapstruct:mapstruct-processor:$mapstructVersion")

    // JOOQ
    implementation("org.jooq:jooq-kotlin:$jooqVersion")

    // Database
    implementation("org.liquibase:liquibase-core:$liquibaseVersion")
    implementation("org.postgresql:postgresql:$postgresqlVersion")

    // JOOQ Generator
    jooqGenerator("org.jooq:jooq-meta-extensions:$jooqVersion")
    jooqGenerator("org.jooq:jooq-meta-extensions-liquibase:$jooqVersion")
    jooqGenerator(files("src/main/resources"))
}

jooq {
    version.set(jooqVersion)
    edition.set(JooqEdition.OSS)

    configurations {
        create("main") {
            generateSchemaSourceOnCompilation.set(true)

            jooqConfiguration.apply {
                generator.apply {
                    database.apply {
                        name = "org.jooq.meta.extensions.liquibase.LiquibaseDatabase"

                        properties = listOf(
                            Property().apply {
                                key = "scripts"
                                value = "db/changelog/db.changelog-master.yml"
                            },
                            Property().apply {
                                key = "useParsingConnection"
                                value = "true"
                            }
                        )

                    }

                    target.apply {
                        packageName = "konffach.generated.jooq.package"
                        directory = "build/generated-src/jooq/main"
                    }
                }
            }
        }
    }
}

kapt {
    arguments {
        arg("mapstruct.defaultComponentModel", "spring")
    }
    correctErrorTypes = true
}

kotlin {
    jvmToolchain(21)
}