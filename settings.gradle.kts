pluginManagement {
    listOf(repositories, dependencyResolutionManagement.repositories).forEach {
        it.apply {
            mavenLocal()
            google()
            mavenCentral()
            maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
            maven("https://maven.pkg.jetbrains.space/kotlin/p/wasm/experimental")
            maven("https://androidx.dev/storage/compose-compiler/repository")
            // For uuid wasm
            maven { url = uri("https://repo.repsy.io/mvn/mbonnin/default") }
            // For okio wasm
            maven { url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/") }
        }
    }
}