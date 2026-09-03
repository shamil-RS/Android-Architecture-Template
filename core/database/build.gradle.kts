plugins {
    alias(libs.plugins.architecturetemplate.android.library)
    alias(libs.plugins.architecturetemplate.android.library.jacoco)
    alias(libs.plugins.architecturetemplate.android.room)
    alias(libs.plugins.architecturetemplate.hilt)
}

android {
    namespace = "com.architecturetemplate.core.database"
}

dependencies {
    api(projects.core.model)

    implementation(libs.kotlinx.datetime)

    androidTestImplementation(libs.androidx.test.core)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.kotlinx.coroutines.test)
}
