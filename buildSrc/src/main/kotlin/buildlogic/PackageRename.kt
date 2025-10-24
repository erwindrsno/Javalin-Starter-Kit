package org.buildlogic

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.Internal
import org.gradle.api.file.FileSystemOperations
import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.file.ConfigurableFileTree
import org.gradle.api.file.ConfigurableFileCollection // 👈 Add this import
import javax.inject.Inject

abstract class PackageRename : DefaultTask() {

  @get:Input
  abstract val oldPackage: Property<String>

  @get:Input
  abstract val newPackage: Property<String>

  @get:Internal
  val oldPath: Provider<String> = oldPackage.map { it.replace('.', '/') }

  @get:Internal
  val newPath: Provider<String> = newPackage.map { it.replace('.', '/') }

  @get:InputFiles
  abstract val sourceFiles: ConfigurableFileCollection

  @get:InputFile
  abstract val buildFile: RegularFileProperty

  @get:Internal
  abstract val javaRoot: DirectoryProperty

  @get:Internal
  abstract val testRoot: DirectoryProperty

  @get:Inject
  abstract val fs: FileSystemOperations

  @TaskAction
  fun execute() {
    val oldPkg = oldPackage.get()
    val newPkg = newPackage.get()
    val oldP = oldPath.get()
    val newP = newPath.get()

    println("🚀 Initializing project with package: $newPkg")

    sourceFiles.forEach { file ->
      println("Updating: ${file.name}")
      val content = file.readText()
      val updatedContent = content.replace(oldPkg, newPkg)
      file.writeText(updatedContent)
    }

    val bFile = buildFile.get().asFile
    if (bFile.exists()) {
      println("Updating: ${bFile.name}")
      val content = bFile.readText()
      val updatedContent = content.replace(oldPkg, newPkg)
      bFile.writeText(updatedContent)
    }

    val jRoot = javaRoot.get().asFile
    val tRoot = testRoot.get().asFile

    val oldDir = jRoot.resolve(oldP)
    val newDir = jRoot.resolve(newP)

    if (oldDir.exists() && oldDir.isDirectory) {
      println("Moving source directory...")
      fs.copy {
        from(oldDir)
        into(newDir)
      }
      fs.delete {
        delete(oldDir)
      }
      println("✅ Cleaned up old source directory.")
    }
    val oldTestDir = tRoot.resolve(oldP)
    val newTestDir = tRoot.resolve(newP)

    if (oldTestDir.exists() && oldTestDir.isDirectory) {
      println("Moving test directory...")
      fs.copy {
        from(oldTestDir)
        into(newTestDir)
      }
      fs.delete {
        delete(oldTestDir)
      }
      println("✅ Cleaned up old test directory.")
    }
    println("✅ Initialization complete! You may want to delete this task from your build.gradle.kts now.")
  }
}
