plugins {
    alias(libs.plugins.architecturetemplate.android.library)
    alias(libs.plugins.architecturetemplate.android.library.jacoco)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.architecturetemplate.core.domain"
}

dependencies {
    api(projects.core.data)
    api(projects.core.model)

    implementation(libs.javax.inject)
}
