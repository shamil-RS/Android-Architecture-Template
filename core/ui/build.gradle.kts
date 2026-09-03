plugins {
    alias(libs.plugins.architecturetemplate.android.library)
    alias(libs.plugins.architecturetemplate.android.library.compose)
    alias(libs.plugins.architecturetemplate.android.library.jacoco)
}

dependencies {
    api(projects.core.designsystem)
    api(projects.core.model)
    api(projects.core.data)

    implementation(libs.kotlinx.collections.immutable)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.coil.kt)
    implementation(libs.coil.kt.compose)
}
