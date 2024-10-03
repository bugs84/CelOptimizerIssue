plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("dev.cel:cel:0.7.1")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

tasks.register<JavaExec>("runHelloWorld") {
    group = "application"
    description = "Run the HelloWorld class"

    // Specify the main class to run
    mainClass.set("HelloWorld")

    // Specify the classpath
    classpath = sourceSets["main"].runtimeClasspath

    // Optionally, you can pass arguments to the program here
    args = listOf() // Add program arguments if needed
}