import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    id("org.jetbrains.kotlin.multiplatform").version("1.9.0")
    id("org.jetbrains.compose").version("1.4.0-dev-wasm09")
    id("com.apollographql.apollo3").version("4.0.0-alpha.3-SNAPSHOT")
    application
}

group = "com.example"
version = "1.0-SNAPSHOT"

@OptIn(ExperimentalComposeLibrary::class, ExperimentalWasmDsl::class)
kotlin {
    wasm {
        moduleName = "Confetti"
        browser {
            commonWebpackConfig(Action {
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).copy(
                    static = (devServer?.static ?: mutableListOf()).apply {
                        // Serve sources to debug inside browser
                        add(project.rootDir.path)
                        add(project.rootDir.path + "/compose-web/")
                    },)

            })
        }
        binaries.executable()
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.components.resources)
                implementation("com.apollographql.apollo3:apollo-runtime")
            }
        }
    }
}

compose.experimental {
    web.application {}
}

compose {
    kotlinCompilerPlugin.set("1.4.0-dev-wasm09")
}

apollo {
    service("api") {
        srcDir("graphql")
        packageName.set("api")
    }
}