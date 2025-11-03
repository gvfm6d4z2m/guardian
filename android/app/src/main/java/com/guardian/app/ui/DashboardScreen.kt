
package com.guardian.app.ui

import android.content.Intent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.guardian.app.CrisisManager
import com.guardian.app.SecurityReport
import com.guardian.app.SecurityScanner
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen() {
    val context = LocalContext.current
    val securityScanner = remember { SecurityScanner(context) }
    val crisisManager = remember { CrisisManager(context) }
    var securityReport by remember { mutableStateOf<SecurityReport?>(null) }
    var isScanning by remember { mutableStateOf(false) }
    var currentScreen by remember { mutableStateOf(Screen.Dashboard) }
    var showDisclaimer by remember { mutableStateOf(true) }
    var selectedIssue by remember { mutableStateOf<com.guardian.app.SecurityIssue?>(null) }

    GuardianTheme {
        Scaffold(
            topBar = { TopAppBar(title = { Text("Guardian") }) }
        ) {
            Box(modifier = Modifier.padding(it)) {
                if (showDisclaimer) {
                    DisclaimerCard(onDismiss = { showDisclaimer = false })
                } else if (selectedIssue != null) {
                    FixGuideScreen(issue = selectedIssue!!) { selectedIssue = null }
                } else {
                    when (currentScreen) {
                        Screen.Dashboard -> DashboardContent(
                            securityReport = securityReport,
                            isScanning = isScanning,
                            onScanClick = {
                                isScanning = true
                                coroutineScope.launch {
                                    securityReport = securityScanner.runScan()
                                    isScanning = false
                                }
                            },
                            onNavigate = { screen -> currentScreen = screen },
                            onFixClick = { issue -> selectedIssue = issue }
                        )
                        Screen.CrisisMode -> CrisisModeScreen(steps = crisisManager.getCrisisSteps()) { currentScreen = Screen.Dashboard }
                        Screen.Education -> EducationScreen { currentScreen = Screen.Dashboard }
                    }
                }
            }
        }
    }
}

enum class Screen {
    Dashboard,
    CrisisMode,
    Education
}

@Composable
fun DashboardContent(
    securityReport: SecurityReport?,
    isScanning: Boolean,
    onScanClick: () -> Unit,
    onNavigate: (Screen) -> Unit,
    onFixClick: (com.guardian.app.SecurityIssue) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isScanning) {
            CircularProgressIndicator(modifier = Modifier.size(120.dp))
            Text("Scanning...", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(top = 16.dp))
        } else if (securityReport != null) {
            SecurityScoreIndicator(score = securityReport.score)
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn {
                items(securityReport.issues) {
                    SecurityIssueCard(issue = it, onFixClick = onFixClick)
                }
            }
        } else {
            Button(onClick = onScanClick, modifier = Modifier.fillMaxWidth().height(50.dp)) {
                Text("Run Security Scan", fontSize = 18.sp)
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(onClick = { onNavigate(Screen.CrisisMode) }) { Text("Crisis Mode") }
            Button(onClick = { onNavigate(Screen.Education) }) { Text("Education") }
        }
    }
}

@Composable
fun SecurityScoreIndicator(score: Int) {
    val animatedScore by animateFloatAsState(targetValue = score.toFloat() / 100f)

    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(150.dp)) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawArc(
                color = Color.LightGray,
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = 12f, cap = StrokeCap.Round)
            )
            drawArc(
                color = when {
                    score >= 80 -> Color.Green
                    score >= 50 -> Color.Yellow
                    else -> Color.Red
                },
                startAngle = -90f,
                sweepAngle = 360 * animatedScore,
                useCenter = false,
                style = Stroke(width = 12f, cap = StrokeCap.Round)
            )
        }
        Text("$score", fontSize = 48.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun SecurityIssueCard(issue: com.guardian.app.SecurityIssue, onFixClick: (com.guardian.app.SecurityIssue) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(issue.title, style = MaterialTheme.typography.titleMedium)
                Text(issue.description, style = MaterialTheme.typography.bodyMedium)
            }
            Button(onClick = { onFixClick(issue) }) {
                Text("FIX")
            }
        }
    }
}

@Composable
fun DisclaimerCard(onDismiss: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("ALPHA VERSION - FOR TESTING ONLY", style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.onErrorContainer)
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.Close, contentDescription = "Dismiss", tint = MaterialTheme.colorScheme.onErrorContainer)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "This is an early alpha version of Guardian. It is for testing purposes only and should NOT be relied upon for real-world security protection. It has not been thoroughly tested or audited. Use at your own risk.",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onErrorContainer
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onDismiss, modifier = Modifier.fillMaxWidth()) {
                Text("I Understand")
            }
        }
    }
}
