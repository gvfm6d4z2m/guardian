
import Foundation
import Photos
import Contacts
import EventKit

/**
 * Checks for app permissions.
 */
class PermissionChecker {

    /**
     * Checks for potentially dangerous permissions that have been granted.
     * @return A list of SecurityIssues related to dangerous permissions.
     */
    func checkPermissions() -> [SecurityIssue] {
        var issues: [SecurityIssue] = []

        if isPhotoLibraryAccessGranted() {
            issues.add(SecurityIssue(id: "photos_access", title: "Photo Library Access", description: "An app has access to your photo library.", riskLevel: .medium, deepLink: URL(string: UIApplication.openSettingsURLString)))
        }

        if isContactsAccessGranted() {
            issues.add(SecurityIssue(id: "contacts_access", title: "Contacts Access", description: "An app has access to your contacts.", riskLevel: .medium, deepLink: URL(string: UIApplication.openSettingsURLString)))
        }

        if isCalendarAccessGranted() {
            issues.add(SecurityIssue(id: "calendar_access", title: "Calendar Access", description: "An app has access to your calendar.", riskLevel: .medium, deepLink: URL(string: UIApplication.openSettingsURLString)))
        }

        return issues
    }

    private func isPhotoLibraryAccessGranted() -> Bool {
        return PHPhotoLibrary.authorizationStatus() == .authorized
    }

    private func isContactsAccessGranted() -> Bool {
        return CNContactStore.authorizationStatus(for: .contacts) == .authorized
    }

    private func isCalendarAccessGranted() -> Bool {
        return EKEventStore.authorizationStatus(for: .event) == .authorized
    }
}
