import org.buildlogic.PackageRename

plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    application
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    // Use JUnit Jupiter for testing.
    testImplementation(libs.junit.jupiter)

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // This dependency is used by the application.
    implementation(libs.guava)

    // Personal Implementations
    implementation(libs.javalin)
    implementation(libs.guice)
    implementation(libs.dotenv)
    implementation(libs.slf4j)
    implementation(libs.jackson)
    implementation(libs.argon2)
    testImplementation(libs.assertj)
    testImplementation(libs.mockito)
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(24)
    }
}

application {
    // Define the main class for the application.
    mainClass = "cool.company.main.Server"
}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}

tasks.register<PackageRename>("initializeProject") {
    group = "Setup"

    if (!project.hasProperty("newPackageName") || project.property("newPackageName") == "com.mycompany.mycoolapp") {
        throw InvalidUserDataException(
            "ERROR: Please set 'newPackageName' in your gradle.properties file before running this task."
        )
    }

    val newPackageName = project.property("newPackageName") as String
    val newPath = newPackageName.replace('.', '/')
    val javaRoot = project.file("src/main/java")
    val targetDirectory = javaRoot.resolve(newPath)

    if (targetDirectory.exists() && targetDirectory.isDirectory) {
        enabled = false
        description = "Project is already initialized (task is disabled)."
        project.logger.info("INFO: 'initializeProject' task is disabled because the target package '$newPackageName' already exists.")

    } else {
        description = "Sets up the project with your custom package name defined in gradle.properties."

        val defaultOldPackage = "cool.company"

        this.oldPackage.set(defaultOldPackage)
        this.newPackage.set(newPackageName)
        
        val testRoot = project.file("src/test/java")
        
        this.javaRoot.set(javaRoot)
        this.testRoot.set(testRoot)
        this.buildFile.set(project.file("build.gradle.kts"))

        this.sourceFiles.setFrom(
            project.fileTree(javaRoot) {
                include("**/*.java", "**/*.kt")
            },
            project.fileTree(testRoot) {
                include("**/*.java", "**/*.kt")
            }
        )
    }
}
