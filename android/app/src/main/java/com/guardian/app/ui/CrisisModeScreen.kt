
package com.guardian.app.ui

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.guardian.app.CrisisStep

@Composable
fun CrisisModeScreen(steps: List<CrisisStep>, onDeactivate: () -> Unit) {
    val context = LocalContext.current
    val crisisSteps = remember { mutableStateListOf<CrisisStep>().apply { addAll(steps) } }

    Column(modifier = Modifier.padding(8.dp)) {
        Text("Crisis Mode Protocol", style = androidx.compose.material3.MaterialTheme.typography.headlineMedium)
        Text("Follow these steps to secure your device immediately.")
        LazyColumn {
            items(crisisSteps) { step ->
                CrisisStepCard(step = step) { isCompleted ->
                    val index = crisisSteps.indexOf(step)
                    crisisSteps[index] = step.copy(isCompleted = isCompleted)
                }
            }
        }
        Button(onClick = onDeactivate, modifier = Modifier.fillMaxWidth().padding(top = 16.dp)) {
            Text("Deactivate Crisis Mode")
        }
    }
}

@Composable
fun CrisisStepCard(step: CrisisStep, onCompletedChange: (Boolean) -> Unit) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(step.title, style = androidx.compose.material3.MaterialTheme.typography.titleMedium)
                Text(step.description)
                Button(onClick = { 
                    step.intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    context.startActivity(step.intent)
                }) {
                    Text("OPEN SETTINGS")
                }
            }
            Checkbox(checked = step.isCompleted, onCheckedChange = onCompletedChange)
        }
    }
}
