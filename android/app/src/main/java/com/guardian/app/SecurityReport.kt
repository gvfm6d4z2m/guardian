
package com.guardian.app

import androidx.compose.ui.graphics.Color

/**
 * Represents the overall security score and status.
 */
data class SecurityReport(
    val score: Int,
    val status: SecurityStatus,
    val issues: List<SecurityIssue>
)

/**
 * Represents a single security issue found during a scan.
 */
data class SecurityIssue(
    val id: String,
    val title: String,
    val description: String,
    val riskLevel: RiskLevel,
    val deepLink: String // For the 'FIX NOW' button
)

/**
 * Enum representing the security status based on the score.
 */
enum class SecurityStatus(val color: Color) {
    GOOD(Color.Green),
    WARNING(Color.Yellow),
    CRITICAL(Color.Red)
}

/**
 * Enum representing the risk level of a security issue.
 */
enum class RiskLevel {
    LOW,
    MEDIUM,
    HIGH
}
