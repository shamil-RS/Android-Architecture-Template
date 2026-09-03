plugins {
    alias(libs.plugins.architecturetemplate.jvm.library)
    alias(libs.plugins.architecturetemplate.hilt)
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    testImplementation(libs.kotlinx.coroutines.test)
}