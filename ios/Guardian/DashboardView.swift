
import SwiftUI

struct GuardianTabView: View {
    var body: some View {
        TabView {
            DashboardView()
                .tabItem {
                    Label("Dashboard", systemImage: "shield.fill")
                }
            CrisisModeView(steps: CrisisGuide().getCrisisSteps())
                .tabItem {
                    Label("Crisis Mode", systemImage: "exclamationmark.triangle.fill")
                }
            EducationView()
                .tabItem {
                    Label("Education", systemImage: "book.fill")
                }
        }
    }
}

struct DashboardView: View {
    @State private var securityReport: SecurityReport? = nil
    @State private var isScanning = false
    @State private var selectedIssue: SecurityIssue? = nil
    @State private var showDisclaimer = true
    private let securityScanner = SecurityScanner()

    var body: some View {
        NavigationView {
            ZStack {
                VStack {
                    if isScanning {
                        ProgressView("Scanning...")
                            .progressViewStyle(CircularProgressViewStyle(tint: .accentColor))
                            .scaleEffect(1.5)
                    } else if let report = securityReport {
                        SecurityReportView(report: report, onFixClick: { issue in
                            selectedIssue = issue
                        })
                    } else {
                        VStack {
                            Spacer()
                            Button("Run Security Scan") {
                                isScanning = true
                                DispatchQueue.global().async {
                                    let report = securityScanner.runScan()
                                    DispatchQueue.main.async {
                                        self.securityReport = report
                                        self.isScanning = false
                                    }
                                }
                            }
                            .font(.headline)
                            .padding()
                            .background(Color.accentColor)
                            .foregroundColor(.white)
                            .cornerRadius(10)
                            Spacer()
                        }
                    }
                }
                .navigationTitle("Guardian")
                .sheet(item: $selectedIssue) { issue in
                    FixGuideView(issue: issue)
                }

                if showDisclaimer {
                    DisclaimerView(onDismiss: { showDisclaimer = false })
                }
            }
        }
    }
}

struct SecurityReportView: View {
    let report: SecurityReport
    let onFixClick: (SecurityIssue) -> Void

    var body: some View {
        VStack {
            SecurityScoreView(score: report.score)
                .padding(.bottom, 20)
            List(report.issues) { issue in
                SecurityIssueRow(issue: issue, onFixClick: onFixClick)
            }
        }
    }
}

struct SecurityScoreView: View {
    let score: Int

    var body: some View {
        ZStack {
            Circle()
                .stroke(lineWidth: 20.0)
                .opacity(0.3)
                .foregroundColor(Color.gray)

            Circle()
                .trim(from: 0.0, to: CGFloat(min(Double(score) / 100.0, 1.0)))
                .stroke(style: StrokeStyle(lineWidth: 20.0, lineCap: .round, lineJoin: .round))
                .foregroundColor(colorForScore(score))
                .rotationEffect(Angle(degrees: 270.0))
                .animation(.linear, value: score)
            
            Text("\(score)")
                .font(.largeTitle)
                .bold()
        }
        .frame(width: 150, height: 150)
    }
    
    func colorForScore(_ score: Int) -> Color {
        switch score {
        case 80...100:
            return .green
        case 50..<80:
            return .yellow
        default:
            return .red
        }
    }
}

struct SecurityIssueRow: View {
    let issue: SecurityIssue
    let onFixClick: (SecurityIssue) -> Void

    var body: some View {
        HStack {
            VStack(alignment: .leading) {
                Text(issue.title)
                    .font(.headline)
                Text(issue.description)
                    .font(.subheadline)
            }
            Spacer()
            Button("FIX") {
                onFixClick(issue)
            }
            .buttonStyle(.bordered)
        }
    }
}

struct DisclaimerView: View {
    let onDismiss: () -> Void

    var body: some View {
        ZStack {
            Color.black.opacity(0.4).edgesIgnoringSafeArea(.all)

            VStack(spacing: 20) {
                Text("ALPHA VERSION - FOR TESTING ONLY")
                    .font(.title2)
                    .fontWeight(.bold)
                    .foregroundColor(.red)
                Text(
                    "This is an early alpha version of Guardian. It is for testing purposes only and should NOT be relied upon for real-world security protection. It has not been thoroughly tested or audited. Use at your own risk."
                )
                .font(.body)
                .multilineTextAlignment(.center)
                Button("I Understand") {
                    onDismiss()
                }
                .buttonStyle(.borderedProminent)
            }
            .padding()
            .background(Color.white)
            .cornerRadius(15)
            .shadow(radius: 10)
            .padding(20)
        }
    }
}

struct DashboardView_Previews: PreviewProvider {
    static var previews: some View {
        GuardianTabView()
    }
}
