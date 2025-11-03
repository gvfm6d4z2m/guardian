
package com.guardian.app

import android.app.admin.DevicePolicyManager
import android.content.Context
import android.os.Build
import android.provider.Settings
import androidx.core.content.ContextCompat

/**
 * Scans the device for security vulnerabilities.
 */
class SecurityScanner(private val context: Context) {

    private val permissionAuditor = PermissionAuditor(context)

    /**
     * Runs a full security scan.
     * @return A SecurityReport with the results of the scan.
     */
    fun runScan(): SecurityReport {
        val issues = mutableListOf<SecurityIssue>()

        // Check for OS updates
        if (!isOsUpdated()) {
            issues.add(
                SecurityIssue(
                    id = "os_update",
                    title = "Operating System Outdated",
                    description = "Your device is not running the latest OS version. Updates contain important security patches.",
                    riskLevel = RiskLevel.HIGH,
                    deepLink = Settings.ACTION_SYSTEM_UPDATE_SETTINGS
                )
            )
        }

        // Check for lock screen strength
        if (!isLockScreenSecure()) {
            issues.add(
                SecurityIssue(
                    id = "lock_screen",
                    title = "Weak Lock Screen",
                    description = "Your device does not have a secure lock screen. A strong password or biometric authentication is recommended.",
                    riskLevel = RiskLevel.HIGH,
                    deepLink = Settings.ACTION_SECURITY_SETTINGS
                )
            )
        }

        // Check for developer mode
        if (isDeveloperModeEnabled()) {
            issues.add(
                SecurityIssue(
                    id = "dev_mode",
                    title = "Developer Mode Enabled",
                    description = "Developer mode provides access to sensitive settings and should be disabled for security.",
                    riskLevel = RiskLevel.MEDIUM,
                    deepLink = Settings.ACTION_DEVICE_INFO_SETTINGS
                )
            )
        }

        // Check for unknown sources
        if (canInstallFromUnknownSources()) {
            issues.add(
                SecurityIssue(
                    id = "unknown_sources",
                    title = "Unknown Sources Enabled",
                    description = "Allowing app installations from unknown sources can be risky. It is recommended to disable this setting.",
                    riskLevel = RiskLevel.HIGH,
                    deepLink = Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES
                )
            )
        }

        // Check for device encryption
        if (!isDeviceEncrypted()) {
            issues.add(
                SecurityIssue(
                    id = "encryption",
                    title = "Device Not Encrypted",
                    description = "Device encryption helps protect your data if your device is lost or stolen.",
                    riskLevel = RiskLevel.HIGH,
                    deepLink = Settings.ACTION_SECURITY_SETTINGS
                )
            )
        }

        // Audit app permissions
        issues.addAll(permissionAuditor.auditPermissions())

        val score = calculateScore(issues)
        val status = getStatus(score)

        return SecurityReport(score, status, issues)
    }

    private fun isOsUpdated(): Boolean {
        // This is a simplified check. A more robust implementation would involve
        // checking the security patch level against a known database of recent patch levels.
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                Settings.Secure.getInt(context.contentResolver, "security_patch_level", 0) != 0
    }

    private fun isLockScreenSecure(): Boolean {
        return ContextCompat.getSystemService(context, android.app.KeyguardManager::class.java)?.isDeviceSecure ?: false
    }

    private fun isDeveloperModeEnabled(): Boolean {
        return Settings.Secure.getInt(context.contentResolver, Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0) != 0
    }

    private fun canInstallFromUnknownSources(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.packageManager.canRequestPackageInstalls()
        } else {
            Settings.Secure.getInt(context.contentResolver, Settings.Secure.INSTALL_NON_MARKET_APPS, 0) != 0
        }
    }

    private fun isDeviceEncrypted(): Boolean {
        val dpm = context.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        return dpm.storageEncryptionStatus == DevicePolicyManager.ENCRYPTION_STATUS_ACTIVE || dpm.storageEncryptionStatus == DevicePolicyManager.ENCRYPTION_STATUS_ACTIVE_DEFAULT_KEY
    }

    private fun calculateScore(issues: List<SecurityIssue>): Int {
        var score = 100
        for (issue in issues) {
            when (issue.riskLevel) {
                RiskLevel.LOW -> score -= 5
                RiskLevel.MEDIUM -> score -= 10
                RiskLevel.HIGH -> score -= 20
            }
        }
        return if (score < 0) 0 else score
    }

    private fun getStatus(score: Int): SecurityStatus {
        return when {
            score >= 80 -> SecurityStatus.GOOD
            score >= 50 -> SecurityStatus.WARNING
            else -> SecurityStatus.CRITICAL
        }
    }
}
