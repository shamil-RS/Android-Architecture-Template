package com.architecturetemplate.lint.designsystem

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import org.jetbrains.uast.UCallExpression
import org.jetbrains.uast.UElement
import org.jetbrains.uast.UQualifiedReferenceExpression

class DesignSystemDetector : Detector(), Detector.UastScanner {

    override fun getApplicableUastTypes(): List<Class<out UElement>> = listOf(
        UCallExpression::class.java,
        UQualifiedReferenceExpression::class.java,
    )

    override fun createUastHandler(context: JavaContext): UElementHandler =
        object : UElementHandler() {
            override fun visitCallExpression(node: UCallExpression) {
                val name = node.methodName ?: return
                val preferredName = METHOD_NAMES[name] ?: return
                reportIssue(context, node, name, preferredName)
            }

            override fun visitQualifiedReferenceExpression(node: UQualifiedReferenceExpression) {
                val name = node.receiver.asRenderString()
                val preferredName = RECEIVER_NAMES[name] ?: return
                reportIssue(context, node, name, preferredName)
            }
        }

    companion object {
        @JvmField
        val ISSUE: Issue = Issue.create(
            id = "DesignSystem",
            briefDescription = "Design system",
            explanation = "This check highlights calls in code that use stock Compose Material " +
                    "composables instead of the tailored equivalents from the local design system " +
                    "module.",
            category = Category.CUSTOM_LINT_CHECKS,
            priority = 7,
            severity = Severity.ERROR,
            implementation = Implementation(
                DesignSystemDetector::class.java,
                Scope.JAVA_FILE_SCOPE,
            ),
        )

        // Unfortunately :lint is a Java module and thus can't depend on the :core-designsystem
        // Android module, so we can't use composable function references (eg. ::Button.name)
        // instead of hardcoded names.
        val METHOD_NAMES = mapOf(
            "MaterialTheme" to "AppTheme",
            "Button" to "ProjectButton",
            "OutlinedButton" to "ProjectOutlinedButton",
            "TextButton" to "ProjectTextButton",
            "FilterChip" to "ProjectFilterChip",
            "ElevatedFilterChip" to "ProjectFilterChip",
            "NavigationBar" to "ProjectNavigationBar",
            "NavigationBarItem" to "ProjectNavigationBarItem",
            "NavigationRail" to "ProjectNavigationRail",
            "NavigationRailItem" to "ProjectNavigationRailItem",
            "TabRow" to "ProjectTabRow",
            "Tab" to "ProjectTab",
            "IconToggleButton" to "ProjectIconToggleButton",
            "FilledIconToggleButton" to "ProjectIconToggleButton",
            "FilledTonalIconToggleButton" to "ProjectIconToggleButton",
            "OutlinedIconToggleButton" to "ProjectIconToggleButton",
            "CenterAlignedTopAppBar" to "ProjectTopAppBar",
            "SmallTopAppBar" to "ProjectTopAppBar",
            "MediumTopAppBar" to "ProjectTopAppBar",
            "LargeTopAppBar" to "ProjectTopAppBar",
        )
        val RECEIVER_NAMES = mapOf(
            "Icons" to "ProjectIcons",
        )

        fun reportIssue(
            context: JavaContext,
            node: UElement,
            name: String,
            preferredName: String,
        ) {
            context.report(
                ISSUE,
                node,
                context.getLocation(node),
                "Using $name instead of $preferredName",
            )
        }
    }
}
