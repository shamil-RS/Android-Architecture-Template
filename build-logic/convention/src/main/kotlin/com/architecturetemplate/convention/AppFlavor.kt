package com.architecturetemplate.convention

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.ApplicationProductFlavor
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.ProductFlavor
import org.gradle.kotlin.dsl.invoke

@Suppress("EnumEntryName")
enum class FlavorDimension {
    contentType,
}

// The content for the app can either come from local static data which is useful for demo
// purposes, or from a production backend server which supplies up-to-date, real content.
// These two product flavors reflect this behaviour.
@Suppress("EnumEntryName")
enum class AppFlavor(val dimension: FlavorDimension, val applicationIdSuffix: String? = null) {
    demo(FlavorDimension.contentType, applicationIdSuffix = ".demo"),
    prod(FlavorDimension.contentType),
}

fun configureFlavors(
    commonExtension: CommonExtension,
    flavorConfigurationBlock: ProductFlavor.(flavor: AppFlavor) -> Unit = {},
) {
    commonExtension.apply {
        FlavorDimension.entries.forEach { flavorDimension ->
            flavorDimensions += flavorDimension.name
        }

        productFlavors {
            AppFlavor.entries.forEach { AppFlavor ->
                register(AppFlavor.name) {
                    dimension = AppFlavor.dimension.name
                    flavorConfigurationBlock(this, AppFlavor)
                    if (commonExtension is ApplicationExtension && this is ApplicationProductFlavor) {
                        if (AppFlavor.applicationIdSuffix != null) {
                            applicationIdSuffix = AppFlavor.applicationIdSuffix
                        }
                    }
                }
            }
        }
    }
}