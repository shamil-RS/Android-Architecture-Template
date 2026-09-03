plugins {
    alias(libs.plugins.architecturetemplate.android.library)
    alias(libs.plugins.architecturetemplate.hilt)
}

dependencies {
    api(libs.kotlinx.coroutines.test)
    api(projects.core.common)
    api(projects.core.data)
    api(projects.core.model)

    implementation(libs.androidx.test.rules)
    implementation(libs.hilt.android.testing)
    implementation(libs.kotlinx.datetime)
}
