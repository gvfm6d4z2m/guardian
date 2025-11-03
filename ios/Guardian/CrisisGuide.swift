
import Foundation
import UIKit

/**
 * Represents a single step in the crisis protocol.
 */
struct CrisisStep: Identifiable {
    let id = UUID()
    let title: String
    let description: String
    let deepLink: URL?
    var isCompleted: Bool = false
}

/**
 * Manages the Crisis Mode feature by providing a list of actionable steps.
 */
class CrisisGuide {

    /**
     * Gathers all the steps for the crisis protocol.
     * @return A list of CrisisStep objects.
     */
    func getCrisisSteps() -> [CrisisStep] {
        var steps: [CrisisStep] = []

        // Step 1: Enable Lockdown Mode
        steps.append(
            CrisisStep(
                title: "Enable Lockdown Mode",
                description: "Immediately enable Lockdown Mode to limit your device’s functionality and reduce the attack surface.",
                deepLink: URL(string: "App-prefs:root=Privacy&path=LOCKDOWN_MODE")
            )
        )

        // Step 2: Disconnect from all networks
        steps.append(
            CrisisStep(
                title: "Disconnect from Network",
                description: "Immediately disconnect from all Wi-Fi and cellular networks to prevent further communication.",
                deepLink: URL(string: "App-prefs:root=WIFI")
            )
        )

        // Step 3: Review App Permissions
        steps.append(
            CrisisStep(
                title: "Review App Permissions",
                description: "Review the permissions granted to all apps and revoke any that are not necessary.",
                deepLink: URL(string: "App-prefs:root=Privacy")
            )
        )

        // Step 4: Secure Communications (Placeholder)
        steps.append(
            CrisisStep(
                title: "Use Secure Communication",
                description: "Switch to a secure communication channel like Signal for any further communication.",
                deepLink: nil // Placeholder
            )
        )
        
        // Step 5: Emergency Contact (Placeholder)
        steps.append(
            CrisisStep(
                title: "Alert Emergency Contacts",
                description: "Inform your pre-defined emergency contacts about the situation.",
                deepLink: nil // Placeholder
            )
        )

        return steps
    }
}
