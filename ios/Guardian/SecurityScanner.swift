
import Foundation
import LocalAuthentication
import UIKit

/**
 * Scans the device for security vulnerabilities.
 */
class SecurityScanner {

    private let permissionChecker = PermissionChecker()

    /**
     * Runs a full security scan.
     * @return A SecurityReport with the results of the scan.
     */
    func runScan() -> SecurityReport {
        var issues: [SecurityIssue] = []

        // Check for OS updates
        if !isOsUpdated() {
            issues.append(SecurityIssue(id: "os_update", title: "Operating System Outdated", description: "Your device is not running the latest iOS version. Updates contain important security patches.", riskLevel: .high, deepLink: URL(string: "App-prefs:root=General&path=SOFTWARE_UPDATE_LINK")))
        }

        // Check for lock screen strength
        if !isPasscodeEnabled() {
            issues.append(SecurityIssue(id: "passcode", title: "Passcode Not Enabled", description: "Your device does not have a passcode enabled. A strong passcode is recommended.", riskLevel: .high, deepLink: URL(string: "App-prefs:root=TOUCHID_PASSCODE")))
        }

        // Check for jailbreak
        if isJailbroken() {
            issues.append(SecurityIssue(id: "jailbreak", title: "Jailbreak Detected", description: "Your device appears to be jailbroken, which is a major security risk.", riskLevel: .high, deepLink: nil))
        }

        // Check if Siri is enabled
        if isSiriEnabled() {
            issues.append(SecurityIssue(id: "siri", title: "Siri is Enabled", description: "Siri can sometimes be a privacy concern. Consider disabling it if you do not use it.", riskLevel: .low, deepLink: URL(string: "App-prefs:root=SIRI")))
        }

        // Check app permissions
        issues.append(contentsOf: permissionChecker.checkPermissions())

        let score = calculateScore(issues: issues)
        let status = getStatus(score: score)

        return SecurityReport(score: score, status: status, issues: issues)
    }

    private func isOsUpdated() -> Bool {
        // This is a simplified check. A more robust implementation would involve
        // checking the OS version against a known database of recent versions.
        return ProcessInfo.processInfo.isOperatingSystemAtLeast(OperatingSystemVersion(majorVersion: 15, minorVersion: 0, patchVersion: 0))
    }

    private func isPasscodeEnabled() -> Bool {
        return LAContext().canEvaluatePolicy(.deviceOwnerAuthentication, error: nil)
    }

    private func isJailbroken() -> Bool {
        // Basic jailbreak detection
        let paths = [
            "/Applications/Cydia.app",
            "/Library/MobileSubstrate/MobileSubstrate.dylib",
            "/bin/bash",
            "/usr/sbin/sshd",
            "/etc/apt"
        ]
        for path in paths {
            if FileManager.default.fileExists(atPath: path) {
                return true
            }
        }
        return false
    }

    private func isSiriEnabled() -> Bool {
        // This is a simplified check. A more robust implementation would be needed.
        return true
    }

    private func calculateScore(issues: [SecurityIssue]) -> Int {
        var score = 100
        for issue in issues {
            switch issue.riskLevel {
            case .low:
                score -= 5
            case .medium:
                score -= 10
            case .high:
                score -= 20
            }
        }
        return score < 0 ? 0 : score
    }

    private func getStatus(score: Int) -> SecurityStatus {
        switch score {
        case 80...100:
            return .good
        case 50..<80:
            return .warning
        default:
            return .critical
        }
    }
}
