plugins {
    id("java")
    id("application")
    id("io.franzbecker.gradle-lombok") version "5.0.0"
}

group = "org.tour"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

application {
    mainClass.set("org.tour.Main")
}

lombok {
    version = "1.18.22"
}


dependencies {
    implementation("com.fasterxml.jackson.core:jackson-core:2.14.2")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.14.2")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.14.2")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.14.2")
    implementation("org.apache.logging.log4j:log4j-core:2.20.0")
    implementation("org.apache.logging.log4j:log4j-api:2.20.0")
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.20.0")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-json-org:2.14.2")
    // overwrite versions since fasterxml depends on versions with known CVEs
    implementation("org.json:json:20230227")
    implementation("org.yaml:snakeyaml:2.0")


    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
