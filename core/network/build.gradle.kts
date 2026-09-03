import com.android.build.api.variant.BuildConfigField
import java.io.StringReader
import java.util.Properties

plugins {
    alias(libs.plugins.architecturetemplate.android.library)
    alias(libs.plugins.architecturetemplate.android.library.jacoco)
    alias(libs.plugins.architecturetemplate.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.architecturetemplate.core.network"

    buildFeatures {
        buildConfig = true
    }

    testOptions.unitTests.isIncludeAndroidResources = true
}

dependencies {
    api(libs.kotlinx.datetime)
    api(projects.core.common)
    api(projects.core.model)

    implementation(libs.coil.kt)
    implementation(libs.coil.kt.svg)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.okhttp.logging)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.kotlinx.collections.immutable)

    testImplementation(libs.kotlinx.coroutines.test)
}

val backendUrl = providers.fileContents(
    isolated.rootProject.projectDirectory.file("local.properties")
).asText.map { text ->
    val properties = Properties()
    properties.load(StringReader(text))
    properties["BACKEND_URL"]
}.orElse("http://example.com")

val authToken = providers.fileContents(
    isolated.rootProject.projectDirectory.file("local.properties")
).asText.map { text ->
    val properties = Properties()
    properties.load(StringReader(text))
    properties["PROJECT_TOKEN"]?.toString() ?: ""
}.orElse("")

androidComponents {
    onVariants { variant ->
        variant.buildConfigFields!!.put("BACKEND_URL", backendUrl.map { value ->
            BuildConfigField(type = "String", value = """"$value"""", comment = null)
        })

        variant.buildConfigFields!!.put("AUTH_TOKEN", authToken.map { value ->
            BuildConfigField(type = "String", value = """"$value"""", comment = null)
        })
    }
}
