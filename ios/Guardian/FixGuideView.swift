
import SwiftUI

struct FixGuideView: View {
    let issue: SecurityIssue
    @Environment(\.presentationMode) var presentationMode

    var body: some View {
        NavigationView {
            VStack(alignment: .leading, spacing: 20) {
                Text(issue.title)
                    .font(.largeTitle)
                
                Text("Why this is a risk:")
                    .font(.headline)
                Text(issue.description)
                
                Text("How to fix it:")
                    .font(.headline)
                Text("1. Tap the button below to open the relevant system setting.")
                Text("2. Follow the on-screen instructions to resolve the issue.")
                
                if let deepLink = issue.deepLink {
                    Button("Open Settings") {
                        if UIApplication.shared.canOpenURL(deepLink) {
                            UIApplication.shared.open(deepLink)
                        }
                    }
                    .buttonStyle(.borderedProminent)
                }
                
                Spacer()
            }
            .padding()
            .navigationTitle("Fix Guide")
            .navigationBarItems(trailing: Button("Done") {
                presentationMode.wrappedValue.dismiss()
            })
        }
    }
}
