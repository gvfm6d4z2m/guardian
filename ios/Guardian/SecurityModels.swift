
import Foundation
import SwiftUI

/**
 * Represents the overall security score and status.
 */
struct SecurityReport {
    let score: Int
    let status: SecurityStatus
    let issues: [SecurityIssue]
}

/**
 * Represents a single security issue found during a scan.
 */
struct SecurityIssue: Identifiable {
    let id: String
    let title: String
    let description: String
    let riskLevel: RiskLevel
    let deepLink: URL? // For the 'FIX NOW' button
}

/**
 * Enum representing the security status based on the score.
 */
enum SecurityStatus {
    case good
    case warning
    case critical

    var color: Color {
        switch self {
        case .good: return .green
        case .warning: return .yellow
        case .critical: return .red
        }
    }
}

/**
 * Enum representing the risk level of a security issue.
 */
enum RiskLevel {
    case low
    case medium
    case high
}
