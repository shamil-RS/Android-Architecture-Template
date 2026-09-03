plugins {
    alias(libs.plugins.architecturetemplate.android.library)
    alias(libs.plugins.architecturetemplate.android.library.jacoco)
    alias(libs.plugins.architecturetemplate.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.architecturetemplate.core.data"
    testOptions.unitTests.isIncludeAndroidResources = true
}

dependencies {
    api(projects.core.common)
    api(projects.core.database)
    api(projects.core.network)

    implementation(libs.kotlinx.collections.immutable)
    implementation(libs.androidx.work.runtime.ktx)

    implementation(libs.hilt.ext.work)
    ksp(libs.hilt.ext.compiler)

    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.kotlinx.serialization.json)
    testImplementation(libs.androidx.work.testing)
    testImplementation(libs.mockk)
    testImplementation(libs.turbine)
}
