
package com.guardian.app.ui

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.guardian.app.SecurityIssue

@Composable
fun FixGuideScreen(issue: SecurityIssue, onBack: () -> Unit) {
    val context = LocalContext.current

    Column(modifier = Modifier.padding(16.dp)) {
        Text(issue.title, style = androidx.compose.material3.MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Why this is a risk:", style = androidx.compose.material3.MaterialTheme.typography.titleMedium)
        Text(issue.description)
        Spacer(modifier = Modifier.height(16.dp))
        Text("How to fix it:", style = androidx.compose.material3.MaterialTheme.typography.titleMedium)
        Text("1. Tap the button below to open the relevant system setting.")
        Text("2. Follow the on-screen instructions to resolve the issue.")
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = { 
            val intent = Intent(issue.deepLink)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }) {
            Text("Open Settings")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onBack) {
            Text("Back")
        }
    }
}
