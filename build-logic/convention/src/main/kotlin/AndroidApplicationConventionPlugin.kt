import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import com.architecturetemplate.convention.configureBadgingTasks
import com.architecturetemplate.convention.configureGradleManagedDevices
import com.architecturetemplate.convention.configureKotlinAndroid
import com.architecturetemplate.convention.configurePrintApksTask
import com.architecturetemplate.convention.configureSpotlessForAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure

abstract class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "com.android.application")
            apply(plugin = "architecturetemplate.android.lint")

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = 36
                testOptions.animationsDisabled = true
                configureGradleManagedDevices(this)
            }
            extensions.configure<ApplicationAndroidComponentsExtension> {
                configurePrintApksTask(this)
                configureBadgingTasks(this)
            }
            configureSpotlessForAndroid()
        }
    }
}