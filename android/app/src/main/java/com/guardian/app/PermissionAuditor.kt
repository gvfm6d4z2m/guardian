
package com.guardian.app

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build

/**
 * Audits app permissions to identify potentially dangerous permissions.
 */
class PermissionAuditor(private val context: Context) {

    // A list of permissions that are considered dangerous
    private val dangerousPermissions = listOf(
        android.Manifest.permission.CAMERA,
        android.Manifest.permission.RECORD_AUDIO,
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.READ_CONTACTS,
        android.Manifest.permission.READ_CALL_LOG,
        android.Manifest.permission.READ_SMS
    )

    /**
     * Scans all installed apps for dangerous permissions.
     * @return A list of SecurityIssues related to dangerous permissions.
     */
    fun auditPermissions(): List<SecurityIssue> {
        val issues = mutableListOf<SecurityIssue>()
        val packageManager = context.packageManager
        val packages = packageManager.getInstalledPackages(PackageManager.GET_PERMISSIONS)

        for (packageInfo in packages) {
            if (packageInfo.requestedPermissions != null) {
                for (permission in packageInfo.requestedPermissions) {
                    if (dangerousPermissions.contains(permission)) {
                        if (isPermissionGranted(packageInfo.packageName, permission)) {
                            issues.add(
                                SecurityIssue(
                                    id = "perm_${packageInfo.packageName}_$permission",
                                    title = "Dangerous Permission Granted",
                                    description = "The app '${packageInfo.applicationInfo.loadLabel(packageManager)}' has been granted the dangerous permission: $permission",
                                    riskLevel = RiskLevel.MEDIUM,
                                    deepLink = "android.settings.APPLICATION_DETAILS_SETTINGS"
                                )
                            )
                        }
                    }
                }
            }
        }
        return issues
    }

    private fun isPermissionGranted(packageName: String, permission: String): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context.packageManager.checkPermission(permission, packageName) == PackageManager.PERMISSION_GRANTED
        } else {
            // For older Android versions, permissions are granted at install time.
            // This is a simplification; a more robust check would be needed for pre-M devices.
            true
        }
    }
}
