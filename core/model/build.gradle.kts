plugins {
    alias(libs.plugins.architecturetemplate.jvm.library)
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    api(libs.kotlinx.datetime)
}