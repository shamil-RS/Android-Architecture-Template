plugins {
    alias(libs.plugins.architecturetemplate.android.library)
    alias(libs.plugins.architecturetemplate.android.library.compose)
    alias(libs.plugins.architecturetemplate.android.library.jacoco)
}

android {
    testOptions.unitTests.isIncludeAndroidResources = true
}

dependencies {
    lintPublish(projects.lint)

    api(libs.androidx.compose.foundation)
    api(libs.androidx.compose.foundation.layout)
    api(libs.androidx.compose.material.iconsExtended)
    api(libs.androidx.compose.material3)
    api(libs.androidx.compose.material3.adaptive)
    api(libs.androidx.compose.material3.navigationSuite)
    api(libs.androidx.compose.runtime)
    api(libs.androidx.compose.ui.util)

    implementation(libs.kotlinx.collections.immutable)
    implementation(libs.material)
    implementation(libs.coil.kt.compose)
    implementation(libs.androidx.core.splashscreen)

    testImplementation(libs.androidx.compose.ui.test)
    testImplementation(libs.androidx.compose.ui.testManifest)

    testImplementation(libs.hilt.android.testing)
//    testImplementation(projects.core.screenshotTesting)
}
