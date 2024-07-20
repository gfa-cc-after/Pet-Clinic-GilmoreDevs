plugins {
    java
}
dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.3"))
    testImplementation(platform("io.cucumber:cucumber-bom:7.18.1"))

    // Cucumber dependencies
    testImplementation("io.cucumber:cucumber-java")
    testImplementation("io.cucumber:cucumber-junit-platform-engine")
    testImplementation("org.junit.platform:junit-platform-suite")
    testImplementation("org.junit.jupiter:junit-jupiter")


    // Playwright dependencies
    implementation("com.microsoft.playwright:playwright:1.45.0")

    // TestContainers dependencies
    testImplementation("org.testcontainers:testcontainers:1.20.0")
}

repositories {
    mavenLocal()
    mavenCentral()
}

tasks.withType<Test> {
    useJUnitPlatform()
    // Work around. Gradle does not include enough information to disambiguate
    // between different examples and scenarios.
    systemProperty("cucumber.junit-platform.naming-strategy", "long")
}
