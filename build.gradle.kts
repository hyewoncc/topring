plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

java.sourceCompatibility = JavaVersion.VERSION_11
java.targetCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    implementation ("mysql:mysql-connector-java:8.0.28")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
