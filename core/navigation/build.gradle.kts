plugins {
    alias(libs.plugins.architecturetemplate.android.library)
    alias(libs.plugins.architecturetemplate.hilt)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.compose)
}

dependencies {
    api(libs.androidx.navigation3.runtime)

    implementation(projects.core.network)
    implementation(projects.core.designsystem)
    implementation(libs.androidx.savedstate.compose)
    implementation(libs.androidx.lifecycle.viewModel.navigation3)

    testImplementation(libs.truth)

    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.compose.ui.testManifest)
    androidTestImplementation(libs.androidx.lifecycle.viewModel.testing)
    androidTestImplementation(libs.truth)
}
