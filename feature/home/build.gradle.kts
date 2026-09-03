plugins {
    alias(libs.plugins.architecturetemplate.android.library.compose)
    alias(libs.plugins.architecturetemplate.hilt)
}

dependencies {
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(projects.core.navigation)
    implementation(libs.androidx.lifecycle.viewModel.navigation3)
    implementation(projects.core.network)
    implementation(projects.core.designsystem)
    implementation(projects.core.ui)
    implementation(projects.core.model)
    implementation(projects.core.common)
    implementation(libs.hilt.core)
    implementation(libs.androidx.hilt.lifecycle.viewModelCompose)
    implementation(libs.kotlinx.collections.immutable)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.foundation.layout)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.runtime)
}