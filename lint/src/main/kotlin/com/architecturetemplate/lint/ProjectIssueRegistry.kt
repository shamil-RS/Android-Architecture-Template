package com.architecturetemplate.lint

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.client.api.Vendor
import com.android.tools.lint.detector.api.CURRENT_API
import com.architecturetemplate.lint.designsystem.DesignSystemDetector

class ProjectIssueRegistry : IssueRegistry() {

    override val issues = listOf(
        DesignSystemDetector.ISSUE,
        TestMethodNameDetector.FORMAT,
        TestMethodNameDetector.PREFIX,
    )

    override val api: Int = CURRENT_API

    override val minApi: Int = 12

    override val vendor: Vendor = Vendor(
        vendorName = "Android Architecture Template",
        feedbackUrl = "https://github.com", // Ссылка на баги на вашем GitHub
        contact = "https://github.com", // Ссылка на главную страницу репозитория
    )
}
