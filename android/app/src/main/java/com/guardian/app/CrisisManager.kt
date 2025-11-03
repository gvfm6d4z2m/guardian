
package com.guardian.app

import android.content.Context
import android.content.Intent
import android.provider.Settings

/**
 * Represents a single step in the crisis protocol.
 */
data class CrisisStep(
    val title: String,
    val description: String,
    val intent: Intent,
    var isCompleted: Boolean = false
)

/**
 * Manages the Crisis Mode feature by providing a list of actionable steps.
 */
class CrisisManager(private val context: Context) {

    /**
     * Gathers all the steps for the crisis protocol.
     * @return A list of CrisisStep objects.
     */
    fun getCrisisSteps(): List<CrisisStep> {
        val steps = mutableListOf<CrisisStep>()

        // Step 1: Disconnect from all networks
        steps.add(
            CrisisStep(
                title = "Disconnect from Network",
                description = "Immediately disconnect from all Wi-Fi and cellular networks to prevent further communication.",
                intent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
            )
        )

        // Step 2: Review recently installed apps
        steps.add(
            CrisisStep(
                title = "Review Installed Apps",
                description = "Check for any recently installed applications that you do not recognize.",
                intent = Intent(Settings.ACTION_APPLICATION_SETTINGS)
            )
        )
        
        // Step 3: Force stop suspicious apps
        steps.add(
            CrisisStep(
                title = "Force Stop Suspicious Apps",
                description = "If you see a suspicious app, go to its settings and force stop it to prevent it from running.",
                intent = Intent(Settings.ACTION_APPLICATION_SETTINGS)
            )
        )

        // Step 4: Secure Communications (Placeholder)
        steps.add(
            CrisisStep(
                title = "Use Secure Communication",
                description = "Switch to a secure communication channel like Signal for any further communication.",
                intent = Intent(Intent.ACTION_VIEW) // Placeholder, would ideally launch Signal or a guide
            )
        )
        
        // Step 5: Emergency Contact (Placeholder)
        steps.add(
            CrisisStep(
                title = "Alert Emergency Contacts",
                description = "Inform your pre-defined emergency contacts about the situation.",
                intent = Intent(Intent.ACTION_VIEW) // Placeholder for emergency contact feature
            )
        )

        return steps
    }
}
